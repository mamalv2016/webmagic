package com.renjie120.jsoup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.renjie120.jsoup.dto.DataInfo;
import com.renjie120.jsoup.dto.StatisType;

public class ParseNewCommercialHouseTable extends ParseTable {
	public ParseNewCommercialHouseTable(Element table) {
		super(table);
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
			data.setCity(city.text().replace("　", "").replace(" ", ""));
			data.setTongbi(Double.parseDouble(tongbi.text()));
			data.setHuanbi(Double.parseDouble(huanbi.text()));
			data.setDingji(Double.parseDouble(dingji.text()));
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
}
