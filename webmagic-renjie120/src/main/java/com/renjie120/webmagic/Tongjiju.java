package com.renjie120.webmagic;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.renjie120.common.exception.ExceptionCode;
import com.renjie120.common.exception.ExceptionWrapper;
import com.renjie120.dao.StatisPageNutzDao;
import com.renjie120.dto.StatisPage;
import com.renjie120.dto.StatisPageStatus;
import com.renjie120.jsoup.ParseNewCommercialHouseTable;
import com.renjie120.jsoup.ParseNewHouseSortTable;
import com.renjie120.jsoup.ParseNewHouseTable;
import com.renjie120.jsoup.ParseSecHandlHouseTable;
import com.renjie120.jsoup.ParseSecHandsHouseSortTable;
import com.renjie120.tool.DateTool;

public class Tongjiju implements PageProcessor {
	private static String startUrl = "http://www.stats.gov.cn/was5/web/search?page=1&channelid=288041&orderby=-DOCRELTIME&was_custom_expr=like%2870%E4%B8%AA%E5%A4%A7%E4%B8%AD%E5%9F%8E%E5%B8%82%29%2Fsen&perpage=10&outlinepage=10";
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

	/**
	 * 根据标题判断是否要继续进行筛选.
	 * 
	 * @param title
	 * @return
	 */
	private boolean chargeByTitle(String title) {
		Pattern pattern = Pattern.compile("([0-9]+)年([0-9]+)月70个");
		Matcher matcher = pattern.matcher(title);
		return matcher.find();
	}

	/**
	 * 解析具体的页面.
	 * 
	 * @param page
	 */
	private void processDetail(Page page) { 
		AbstractHandler handler01 = new PageHandler1();
		AbstractHandler handler02 = new PageHandler2();
		AbstractHandler handler03 = new PageHandlerLast();

		// 进行链的组装，即头尾相连，一层套一层
		handler01.setNextHandler(handler02);
		handler02.setNextHandler(handler03);

		// 创建请求并提交到指责链中进行处理
		AbstractPageRequest request01 = new PageRequest(page); 

		// 每次提交都是从链头开始遍历
		handler01.handlerRequest(request01);  
	}

	@Override
	public void process(Page page) {
		// 如果是列表页面
		if (page.getUrl()
				.regex("http://www.stats.gov.cn/was5/web/search\\?page=\\S+")
				.match()) {
			// 添加新的分页列表頁面
//			page.addTargetRequests(page.getHtml().xpath("//dl[@class=fenye]")
//					.links()
//					.regex("http://www.stats.gov.cn/was5/web/search\\?\\S+")
//					.all());

			// 添加发布详情页(带后面到了pipeline中再处理)
			List<String> allUrl = page
					.getHtml()
					.xpath("//ul[@class=center_list_contlist]/li[@class!=cont_line]/span[@class=cont_tit][1]/font[1]/*")
					.regex("urlstr\\s=\\s'(http://www.stats.gov.cn/tjsj/zxfb/\\S+)'",
							1).all();
			urls = allUrl;
			page.putField("urls", urls);
			page.addTargetRequests(allUrl);
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