package com.renjie120.jsoup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.renjie120.jsoup.dto.DataInfo;
import com.renjie120.jsoup.dto.HouseType;
import com.renjie120.jsoup.dto.StatisType;

public class ParseNewHouseSortTable extends ParseTable {
	public ParseNewHouseSortTable(Element table) {
		super(table);
	}

	private DataInfo parseAData(Iterator<Element> it, String cityname,
			HouseType houseType) {
		DataInfo data = new DataInfo();
		Element tongbi = it.next();
		Element huanbi = it.next();
		Element dingji = it.next();
		data.setTongbi(Double.parseDouble(tongbi.text()));
		data.setHuanbi(Double.parseDouble(huanbi.text()));
		data.setDingji(Double.parseDouble(dingji.text()));
		data.setYear(year);
		data.setStatisType(StatisType.NEW_HOUSE_SORTED);
		data.setHouseType(houseType);
		data.setMonth(month);
		return data;
	}

	protected List<DataInfo> parseCityAndData(Element tr) {
		List<DataInfo> ans = new ArrayList<DataInfo>();
		Elements ps = tr.select("p");
		if (ps.size() != 10) {
			return ans;
		}
		Iterator<Element> it = ps.iterator();
		while (it.hasNext()) {
			Element city = it.next();
			String cityname = city.text().replace("　", "");
			cityname = cityname.replace(" ", "");
			DataInfo data1 = parseAData(it, cityname, HouseType.BELLOW_90);
			DataInfo data2 = parseAData(it, cityname,
					HouseType.ABOVE_90_BELLOW_144);
			DataInfo data3 = parseAData(it, cityname, HouseType.ABOVE_144);
			allCity.add(cityname);
			ans.add(data1);
			ans.add(data2);
			ans.add(data3);
		}
		return ans;
	}

	@Override
	protected String generateSql(DataInfo data) {
		String ans = new String("insert into table_new_house_sort (year,month,city,tongbi,huanbi,dingji,sorttype) "
				+ "values( "+data.getYear()+","+data.getMonth()+",'"+data.getCity()+"',"+data.getTongbi()+","+data.getHuanbi()
				+","+data.getDingji()+","+data.getHouseType().getType()+" )");
		return ans;
	} 
	 
}
