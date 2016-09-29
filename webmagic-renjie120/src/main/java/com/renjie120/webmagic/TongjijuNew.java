package com.renjie120.webmagic;

import java.util.List;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import com.renjie120.jsoup.ParseNewCommercialHouseTable;
import com.renjie120.jsoup.ParseNewHouseSortTable;
import com.renjie120.jsoup.ParseNewHouseTable;
import com.renjie120.jsoup.ParseSecHandlHouseTable;
import com.renjie120.jsoup.ParseSecHandsHouseSortTable;

public class TongjijuNew implements PageProcessor, AfterExtractor {
	private static String startUrl = "http://www.stats.gov.cn/was5/web/search?page=1&channelid=288041&orderby=-DOCRELTIME&was_custom_expr=like%2870%E4%B8%AA%E5%A4%A7%E4%B8%AD%E5%9F%8E%E5%B8%82%29%2Fsen&perpage=10&outlinepage=10";
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等site
	private Site site = Site.me().setHttpProxy(new HttpHost("50.30.152.130",8086)).addHeader("Referer","https://news.baidu.com").setRetryTimes(3).setSleepTime(10000);
	private static int size = 0;// 共抓取到的文章数量

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
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

			// 添加发布详情页 
			List<String> allUrl = page
					.getHtml()
					.xpath("//ul[@class=center_list_contlist]/li[@class!=cont_line]/span[@class=cont_tit][1]/font[1]/*")
					.regex("urlstr\\s=\\s'(http://www.stats.gov.cn/tjsj/zxfb/\\S+)'",1).all();
			System.out.println(allUrl);
//			page.addTargetRequests(allUrl); 
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		TongjijuNew newTest = new TongjijuNew();
		Spider iteyeSpider = OOSpider
				.create(newTest.getSite(),
						new ConsolePageModelPipeline(), TongjijuNew.class)
				.addUrl("http://www.stats.gov.cn/was5/web/search?page=1&channelid=288041&orderby=-DOCRELTIME&was_custom_expr=like%2870%E4%B8%AA%E5%A4%A7%E4%B8%AD%E5%9F%8E%E5%B8%82%29%2Fsen&perpage=10&outlinepage=10")
				.addPipeline(new MultiPagePipeline())
				.addPipeline(new ConsolePipeline()).thread(5);

		iteyeSpider.start(); 
	}

	@Override
	public void afterProcess(Page page) {
		// 如果是具体的详情页.
		if (page.getUrl().regex("http://www.stats.gov.cn/tjsj/zxfb/\\S+")
				.match()) {
			System.out.println("处理。。。。"+page.getUrl());
			Document doc = Jsoup.parse(page.getHtml().get());
			Elements allP = doc.select("table.MsoNormalTable");
			// 如果有5个表格就进行解析处理.
			if (allP.size() == 5) {
				size++;
				ParseNewHouseTable t1 = new ParseNewHouseTable(allP.get(0));
				ParseNewCommercialHouseTable t2 = new ParseNewCommercialHouseTable(
						allP.get(1));
				ParseSecHandlHouseTable t3 = new ParseSecHandlHouseTable(
						allP.get(2));
				ParseNewHouseSortTable t4 = new ParseNewHouseSortTable(
						allP.get(3));
				ParseSecHandsHouseSortTable t5 = new ParseSecHandsHouseSortTable(
						allP.get(4));
				t1.saveToDb();
				t2.saveToDb();
				t3.saveToDb();
				t4.saveToDb();
				t5.saveToDb();
			}
		}
	}
}