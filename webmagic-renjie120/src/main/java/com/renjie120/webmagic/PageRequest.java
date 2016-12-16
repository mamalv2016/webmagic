package com.renjie120.webmagic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;

public class PageRequest extends AbstractPageRequest {

	public PageRequest(Page page) {
		super(page);
	}

	/**
	 * 根据标题判断是否要继续进行筛选.
	 * 
	 * @param title
	 * @return
	 */
	private boolean chargeByTitle(String title) {
		Pattern pattern = Pattern.compile("([0-9]+)年([0-9]+)月份70个");
		Matcher matcher = pattern.matcher(title);
		return matcher.find();
	}

	@Override
	public int consoleMethod() {
		Document doc = Jsoup.parse(getPage().getHtml().get());
		Elements title = doc.select("head title");
		String _t = title.html();
		if (!chargeByTitle(_t)) {
			return ConsoleMethodConstants.NOT_SUITABLE_URL;
		}
		// 第一种解析方式.
		Elements allP = doc.select("table.MsoNormalTable");
		if (allP.size() >= 5 && allP.size() < 7) {
			return ConsoleMethodConstants.METHOD_1;
		} else if (allP.size() == 7) {
			return ConsoleMethodConstants.METHOD_3;
		} else {
			Elements allP2 = doc.select("table.NOBORDER");
			if (allP2.size() == 5) {
				return ConsoleMethodConstants.METHOD_2;
			} else{
				Elements allP3 = doc.select("table.MsoTableGrid");
				if(allP3.size()==5){
					return ConsoleMethodConstants.METHOD_4;
				}
				else
					return ConsoleMethodConstants.NOT_SUITABLE_TABLE;
			}
				
		}
	}

}
