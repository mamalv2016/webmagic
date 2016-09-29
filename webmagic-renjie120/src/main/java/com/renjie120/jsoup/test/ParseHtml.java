package com.renjie120.jsoup.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.renjie120.jsoup.ParseNewCommercialHouseTable;
import com.renjie120.jsoup.ParseNewHouseSortTable;
import com.renjie120.jsoup.ParseNewHouseTable;
import com.renjie120.jsoup.ParseSecHandlHouseTable;
import com.renjie120.jsoup.ParseSecHandsHouseSortTable;
import com.renjie120.jsoup.dto.ReadFromUrl;
import com.renjie120.jsoup.util.MockUtil;

public class ParseHtml {
	final static String url = "http://www.stats.gov.cn/tjsj/zxfb/201503/t20150318_696253.html";
	private List<String> allCity = new ArrayList<String>();
	 

	public static void main(String[] args) throws IOException {
//		Document doc = Jsoup.connect(url).get();
//		ReadFromUrl readFromUrl = new ReadFromUrl(url, "d:\\tongji.html"); 
//		String content = readFromUrl.readFromUrl(); 
// 
		String content  = MockUtil.readFile("d:\\tongji.html","");
		Document doc = Jsoup.parse(content);
		Elements allP = doc.select("table.MsoNormalTable");
//		ParseNewHouseTable t1 = new ParseNewHouseTable(allP.get(0));
//		ParseNewCommercialHouseTable t2 = new ParseNewCommercialHouseTable(allP.get(1));
//		ParseSecHandlHouseTable t3 = new ParseSecHandlHouseTable(allP.get(2));
		ParseNewHouseSortTable t4 = new ParseNewHouseSortTable(allP.get(3));
		t4.saveToDb();
//		ParseSecHandsHouseSortTable t5 = new ParseSecHandsHouseSortTable(allP.get(4)); 
	}
}
