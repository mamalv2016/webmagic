package com.renjie120.jsoup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.renjie120.dto.DataInfo;
import com.renjie120.dto.StatisType;

/**
 * 解析商品房销售数据
 * 保存表table_new_commercial_house
 * @author Administrator
 *
 */
public class ParseNewCommercialHouseTable2 extends ParseTable {
	public ParseNewCommercialHouseTable2(Element table,String title) {
		super(table,title);
	}
	

	protected List<DataInfo> parseCityAndData(Element tr) {
		List<DataInfo> ans = new ArrayList<DataInfo>();
		Elements ps = tr.select("p");
		if (ps.size() != 8) {
			return ans;
		}
		Iterator<Element> it = ps.iterator();
		while (it.hasNext()) {
			DataInfo data = new DataInfo();
			Element city = it.next();
			Element tongbi = it.next();
			Element huanbi = it.next();
			Element dingji = it.next();
			data.setCity(parseCityName(city.text()));
			data.setTongbi(parseDouble(tongbi.text()));
			data.setHuanbi(parseDouble(huanbi.text()));
			data.setDingji(parseDouble(dingji.text()));
			data.setYear(year);
			data.setStatisType(StatisType.NEW_COMMERCIAL_HOUSE);
			data.setMonth(month);
			allCity.add(data.getCity());
			maps.put(data.getCity(), data);
			ans.add(data);
		}
		return ans;
	}
	
	@Override
	protected String generateSql(DataInfo data) {
		String ans = new String("insert into table_new_commercial_house (year,month,city,tongbi,huanbi,dingji) "
				+ "values( "+data.getYear()+","+data.getMonth()+",'"+data.getCity()+"',"+data.getTongbi()+","+data.getHuanbi()
				+","+data.getDingji()+" )");
		return ans;
	} 
	
	@Override
	public void parseTable() {
		if (validateTable()) {
			Elements trs = table.select("tr");
			int startRow = 3;   
			int row = 1; 
			for (Element tr : trs) {
				if (row > startRow) {
					dataes.addAll(parseCityAndData(tr));
				}
				row++;
			}
		}
	}
}
