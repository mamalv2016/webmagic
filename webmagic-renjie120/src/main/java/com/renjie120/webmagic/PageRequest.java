package com.renjie120.webmagic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;

public class PageRequest extends AbstractPageRequest {

	public PageRequest(Page page) {
		super(page);
	}
	
	@Override
	public int consoleMethod() {
		Document doc = Jsoup.parse(getPage().getHtml().get()); 
		// 第一种解析方式.
		Elements allP = doc.select("table.MsoNormalTable");
		if(allP.size()>=5){
			return 1;
		}
		else {
			Elements allP2 = doc.select("table.NOBORDER");
			if(allP2.size()==5){
				return 2;
			}
			else
				return -1;
		}
	}

	

}
