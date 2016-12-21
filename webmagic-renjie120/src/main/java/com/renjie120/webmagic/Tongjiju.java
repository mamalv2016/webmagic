package com.renjie120.webmagic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.renjie120.dao.StatisPageNutzDao;
import com.renjie120.dto.StatisPage;
import com.renjie120.dto.StatisPageStatus;

public class Tongjiju implements PageProcessor {
	private static String startUrl = "http://www.stats.gov.cn/was5/web/search?page=1&channelid=288041&orderby=-DOCRELTIME&was_custom_expr=like%2870%E4%B8%AA%E5%A4%A7%E4%B8%AD%E5%9F%8E%E5%B8%82%29%2Fsen&perpage=10&outlinepage=10";
//	private static String startUrl = "http://www.stats.gov.cn/tjsj/zxfb/201612/t20161219_1442973.html";
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等site
	private Site site = Site
			.me()
			.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
			.addHeader("Referer", "https://news.baidu.com").setRetryTimes(3)
			.setSleepTime(10000);
	private static int size = 0;// 共抓取到的文章数量

	private List<String> urls;

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	} 

	protected StatisPage queryPage(String url) {
		StatisPage pageVo = new StatisPage();
		pageVo.setUrl(url);
		StatisPageNutzDao dao = new StatisPageNutzDao();
		List<StatisPage> ans = dao.query(pageVo);
		if (CollectionUtils.isEmpty(ans)) {
			return null;
		} else {
			return ans.get(0);
		}
	}
	
	/**
	 * 解析具体的页面.
	 * 
	 * @param page
	 */
	private void processDetail(Page page) { 
		AbstractHandler handler00 = new PageHandlerFirst();
		AbstractHandler handler01 = new PageHandler1();
		AbstractHandler handler02 = new PageHandler2();
		AbstractHandler handler03 = new PageHandler3();
		AbstractHandler handler04 = new PageHandler4();
		AbstractHandler handler05 = new PageHandler5();
		AbstractHandler handler09 = new PageHandlerLast();

		// 进行链的组装，即头尾相连，一层套一层
		handler00.setNextHandler(handler01);
		handler01.setNextHandler(handler02);
		handler02.setNextHandler(handler03);
		handler03.setNextHandler(handler04);
		handler04.setNextHandler(handler05);
		handler05.setNextHandler(handler09);

		// 创建请求并提交到指责链中进行处理
		AbstractPageRequest request01 = new PageRequest(page); 

		// 每次提交都是从链头开始遍历
		handler00.handlerRequest(request01);  
	}

	@Override
	public void process(Page page) {
		// 如果是列表页面
		if (page.getUrl()
				.regex("http://www.stats.gov.cn/was5/web/search\\?page=\\S+")
				.match()) {
			// 添加新的分页列表頁面
			page.addTargetRequests(page.getHtml().xpath("//dl[@class=fenye]")
					.links()
					.regex("http://www.stats.gov.cn/was5/web/search\\?\\S+")
					.all());

			// 添加发布详情页(带后面到了pipeline中再处理)
			List<String> allUrl = page
					.getHtml()
					.xpath("//ul[@class=center_list_contlist]/li[@class!=cont_line]/span[@class=cont_tit][1]/font[1]/*")
					.regex("urlstr\\s=\\s'(http://www.stats.gov.cn/tjsj/zxfb/\\S+)'",
							1).all();
			urls = new ArrayList<String>();
			
			for(String u:allUrl){
				StatisPage pageVo = queryPage(u);
				if(pageVo==null){
					urls.add(u);
				}else{
					String status = pageVo.getStatus();
					// 如果之前已经处理成功了，表明不用再处理了。
					if (!status.equals(StatisPageStatus.SUCCESS.toString())) {
						//如果不是pass的连接，就重新准备进行爬虫处理一遍.
						if(!StatisPageStatus.PASS.toString().equals(status)){
							urls.add(u);
						}
					}
				}
			} 
			
			page.putField("urls", urls);
			page.addTargetRequests(urls);
		}
		// 如果是具体的详情页.
		else if (page.getUrl().regex("http://www.stats.gov.cn/tjsj/zxfb/\\S+")
				.match()) { 
			
			processDetail(page);
		}
	}

	public String toString() {
		return "url";
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Tongjiju())
		// 从"https://github.com/code4craft"开始抓
				.addUrl(startUrl).addPipeline(new TongjiPipeline())
				// 开启5个线程抓取
				.thread(5)
				// 启动爬虫
				.run();
	}

}