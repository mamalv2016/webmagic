package com.renjie120.webmagic;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class TongjijuNew implements PageProcessor {
	private static String startUrl = "http://www.stats.gov.cn/was5/web/search?page=1&channelid=288041&orderby=-DOCRELTIME&was_custom_expr=like%2870%E4%B8%AA%E5%A4%A7%E4%B8%AD%E5%9F%8E%E5%B8%82%29%2Fsen&perpage=10&outlinepage=10";
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	private static int size = 0;// 共抓取到的文章数量

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		String url = page.getUrl().toString();

		// 如果是列表页面
		if (page.getUrl()
				.regex("http://www.stats.gov.cn/was5/web/search\\?page=\\S+")
				.match()) {
			// 添加新的分页列表頁面
			page.addTargetRequests(page.getHtml().xpath("//dl[@class=fenye]")
					.links()
					.regex("http://www.stats.gov.cn/was5/web/search\\?\\S+")
					.all());

			// 添加发布数据
			List<String> allUrl = page
					.getHtml()
					.xpath("//ul[@class=center_list_contlist]/li[@class!=cont_line]/span[@class=cont_tit][1]/font[1]/*")
					.regex("urlstr\\s=\\s'(http://www.stats.gov.cn/tjsj/zxfb/\\S+)'",
							1).all();

			page.addTargetRequests(allUrl);
		} else {
			size++;
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {

		Spider.create(new TongjijuNew())
		// 从"https://github.com/code4craft"开始抓
				.addUrl(startUrl)
				// 开启5个线程抓取
				.thread(1)
				// 启动爬虫
				.run();
	}
}