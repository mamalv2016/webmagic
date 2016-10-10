package com.renjie120.webmagic;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

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

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
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
			System.out.println(allUrl);
			urls = allUrl;
			page.putField("urls", urls);
			// page.addTargetRequests(allUrl);
		}

		// 如果是具体的详情页.
//		if (page.getUrl().regex("http://www.stats.gov.cn/tjsj/zxfb/\\S+")
//				.match()) {
//			Document doc = Jsoup.parse(page.getHtml().get());
//			Elements allP = doc.select("table.MsoNormalTable"); 
//			// 如果有5个表格就进行解析处理.
//			if (allP.size() == 5) {
//				size++;
//				ParseNewHouseTable t1 = new ParseNewHouseTable(allP.get(0));
//				ParseNewCommercialHouseTable t2 = new ParseNewCommercialHouseTable(
//						allP.get(1));
//				ParseSecHandlHouseTable t3 = new ParseSecHandlHouseTable(
//						allP.get(2));
//				ParseNewHouseSortTable t4 = new ParseNewHouseSortTable(
//						allP.get(3));
//				ParseSecHandsHouseSortTable t5 = new ParseSecHandsHouseSortTable(
//						allP.get(4));
//				t1.saveToDb();
//				t2.saveToDb();
//				t3.saveToDb();
//				t4.saveToDb();
//				t5.saveToDb();
//			}
//		}
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
				.thread(1)
				// 启动爬虫
				.run();
		// TongjijuNew2 newTest = new TongjijuNew2();
		// Spider iteyeSpider = OOSpider
		// .create(newTest.getSite(),
		// TongjijuNew2.class).addUrl(startUrl)
		// .addPipeline(new TongjiPipeline()).thread(1);
		//
		// iteyeSpider.start();
	}

}