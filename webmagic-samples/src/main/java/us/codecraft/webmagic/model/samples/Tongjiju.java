package us.codecraft.webmagic.model.samples;

import javax.management.JMException;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;

@TargetUrl("http://www.stats.gov.cn/was5/web/search\\?\\S+")
// @TargetUrl("http://www.stats.gov.cn/was5/web/search\\?page=1&\\S+")
@ExtractBy(value = "//ul[@class=center_list_contlist]/li[@class!=cont_line]", multi = true)
public class Tongjiju {

	@ExtractBy("//span[@class=cont_tit][1]/font[1]/text()")
	private String urlname;

	@ExtractBy("//span[@class=cont_tit][1]/font[1]/*")
	private String url;

	@Override
	public String toString() {
		return "数据{" + "urlname='" + urlname + ",url='" + url + "'}";
	}

	public static void main(String[] args) throws JMException {
		// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等site
		Site site = Site
				.me()
				.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
				.addHeader("Referer", "https://news.baidu.com")
				.setRetryTimes(3).setSleepTime(10000);

		Spider iteyeSpider = OOSpider
				.create(site, new ConsolePageModelPipeline(), Tongjiju.class)
				.addUrl("http://www.stats.gov.cn/was5/web/search?page=1&channelid=288041&orderby=-DOCRELTIME&was_custom_expr=like%2870%E4%B8%AA%E5%A4%A7%E4%B8%AD%E5%9F%8E%E5%B8%82%29%2Fsen&perpage=10&outlinepage=10")
				.addPipeline(new MultiPagePipeline())
				.addPipeline(new ConsolePipeline()).thread(1);

		iteyeSpider.start();
		// OOSpider.create(
		// Site.me().addStartUrl("http://flashsword20.iteye.com/blog"),
		// IteyeBlog.class).run();
	}
}
