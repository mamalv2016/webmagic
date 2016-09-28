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
@ExtractBy(value = "//ul[class=center_list_contlist]/li",multi = true)
public class Tongjiju {

	// @ExtractBy("//h3[@class=repo-list-name]/a/text()")
	// private String shopName;

	@ExtractBy("//title")
	private String title;
//
//	@ExtractBy(value = "div#blog_content", type = ExtractBy.Type.Css)
//	private String content;

	@Override
	public String toString() {
		return "IteyeBlog{" + "shopName='" + title +'}';
	}

	public static void main(String[] args) throws JMException { 

		Spider iteyeSpider = OOSpider
				.create(Site.me().setSleepTime(10000),
						new ConsolePageModelPipeline(), Tongjiju.class)
				.addUrl("http://www.stats.gov.cn/was5/web/search?page=1&channelid=288041&orderby=-DOCRELTIME&was_custom_expr=like%2870%E4%B8%AA%E5%A4%A7%E4%B8%AD%E5%9F%8E%E5%B8%82%29%2Fsen&perpage=10&outlinepage=10")
				.addPipeline(new MultiPagePipeline())
				.addPipeline(new ConsolePipeline()).thread(1);
 
		iteyeSpider.start();
		// OOSpider.create(
		// Site.me().addStartUrl("http://flashsword20.iteye.com/blog"),
		// IteyeBlog.class).run();
	}
}
