package us.codecraft.webmagic.model.samples;

import javax.management.JMException;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;

@TargetUrl("http://*.iteye.com/blog/(\\d+)")
public class MyFirstSample {

	// @ExtractBy("//h3[@class=repo-list-name]/a/text()")
	// private String shopName;

	@ExtractBy("//title")
	private String title;

	@ExtractBy(value = "div#blog_content", type = ExtractBy.Type.Css)
	private String content;

	@Override
	public String toString() {
		return "IteyeBlog{" + "shopName='" + title + '\'' + ",content="
				+ content + '}';
	}

	public static void main(String[] args) throws JMException {
		// OOSpider.create(Site.me(), new ConsolePageModelPipeline(),
		// MyFirstSample.class)
		// .addUrl("https://github.com/search?l=Java&p=1&q=stars%3A%3E1&s=stars&type=Repositories")
		// .addPipeline(new MultiPagePipeline())
		// .addPipeline(new ConsolePipeline()).thread(4).run();

		Spider iteyeSpider = OOSpider
				.create(Site.me().setSleepTime(10000),
						new ConsolePageModelPipeline(), MyFirstSample.class)
				.addUrl("http://usenrong.iteye.com/")
				.addPipeline(new MultiPagePipeline())
				.addPipeline(new ConsolePipeline()).thread(1);

		SpiderMonitor.instance().register(iteyeSpider);
		iteyeSpider.start();
		// OOSpider.create(
		// Site.me().addStartUrl("http://flashsword20.iteye.com/blog"),
		// IteyeBlog.class).run();
	}
}
