package com.renjie120.webmagic;

import java.util.List;

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
	 * 判断数据库中是否存在值指定的url对应的数据，防止多次重复插入
	 * 
	 * @param url
	 * @return
	 */
	private StatisPage queryPage(String url) {
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
		Document doc = Jsoup.parse(page.getHtml().get());
		Elements title = doc.select("head title");
		Elements allP = doc.select("table.MsoNormalTable");
		// 如果有5个表格就进行解析处理.
		if (allP.size() == 5) {
			int tableLen = allP.size();
			size++;

			String url = page.getUrl().toString();
			
			StatisPageNutzDao dao = new StatisPageNutzDao();
			// 先根据url查询数据库中是否已经存在
			StatisPage pageVo = queryPage(url);
			//不存在，说明之前没有处理过,新建一个记录，保存到page表中，表示是新插入.
			if (pageVo == null) {
				pageVo = new StatisPage();
				pageVo.setDeleteFlag("0");
				pageVo.setStatus(StatisPageStatus.NEW.toString());
				pageVo.setUrl(url);
				pageVo.setTitle(title.html());
				dao.insert(pageVo);
			}else{
				String status = pageVo.getStatus();
				//如果之前已经处理成功了，表明不用再处理了。
				if(status.equals(StatisPageStatus.SUCCESS.toString())){
					return ;
				}
			}

			StatisPage newPageVo = new StatisPage();
			try {
				BeanUtils.copyProperties(newPageVo, pageVo);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// 进行解析
			try {
				ParseNewHouseTable t1 = new ParseNewHouseTable(
						allP.get(tableLen - 5));
				ParseNewCommercialHouseTable t2 = new ParseNewCommercialHouseTable(
						allP.get(tableLen - 4));
				ParseSecHandlHouseTable t3 = new ParseSecHandlHouseTable(
						allP.get(tableLen - 3));
				ParseNewHouseSortTable t4 = new ParseNewHouseSortTable(
						allP.get(tableLen - 2));
				ParseSecHandsHouseSortTable t5 = new ParseSecHandsHouseSortTable(
						allP.get(tableLen - 1));

				t1.newSaveToDb();
				t2.newSaveToDb();
				t3.newSaveToDb();
				t4.newSaveToDb();
				t5.newSaveToDb();

				// 解析完之后，更新解析状态为成功..
				newPageVo.setStatus(StatisPageStatus.SUCCESS.toString());
				dao.update(pageVo, newPageVo);

			} catch (Exception e) { 
				// 出现异常，就更新状态为失败
				newPageVo.setStatus(StatisPageStatus.FAILURE.toString());
				dao.update(pageVo, newPageVo);
				
				e.printStackTrace();
				
				throw new ExceptionWrapper(e.getMessage(),ExceptionCode.URL_PARSE_ERROR, url);
			}

		}
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