package com.renjie120.jsoup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.renjie120.dto.DataInfo;
import com.renjie120.dto.HouseType;
import com.renjie120.dto.StatisType;

/**
 * 提取二手房分类数据.
 * 插入数据表：table_sec_hand_house_sort
 * @author Administrator
 *
 */
public class ParseSecHandsHouseSortTable2 extends ParseTable {
	public ParseSecHandsHouseSortTable2(Element table,String title) {
		super(table,title);
	}

	private DataInfo parseAData(Iterator<Element> it, String cityname,
			HouseType houseType) {
		DataInfo data = new DataInfo();
		Element tongbi = it.next();
		Element huanbi = it.next();
		Element dingji = it.next();
		data.setCity(cityname);
		data.setTongbi(parseDouble(tongbi.text()));
		data.setHuanbi(parseDouble(huanbi.text()));
		data.setDingji(parseDouble(dingji.text()));
		data.setYear(year);
		data.setStatisType(StatisType.SECOND_HANDS_HOUSE_SORTED);
		data.setSortType(houseType.getType());
		data.setMonth(month);
		return data;
	}

	protected String generateSql(DataInfo data) {
		String ans = new String("insert into table_sec_hand_house_sort (year,month,city,tongbi,huanbi,dingji,sorttype) "
				+ "values( "+data.getYear()+","+data.getMonth()+",'"+data.getCity()+"',"+data.getTongbi()+","+data.getHuanbi()
				+","+data.getDingji()+","+data.getSortType()+" )");
		return ans;
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
			String cityname = parseCityName(city.text());
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
