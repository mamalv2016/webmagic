package com.renjie120.jsoup;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.renjie120.common.exception.ExceptionCode;
import com.renjie120.common.exception.ExceptionWrapper;
import com.renjie120.dao.NewCommercialHouseNutzDao;
import com.renjie120.dao.NewHouseDataNutzDao;
import com.renjie120.dao.NewHouseSortDataNutzDao;
import com.renjie120.dao.SecHouseDataNutzDao;
import com.renjie120.dao.SecHouseSortDataNutzDao;
import com.renjie120.db.DbPoolConnection;
import com.renjie120.dto.DataInfo;
import com.renjie120.dto.NewCommercialHouseData;
import com.renjie120.dto.NewHouseData;
import com.renjie120.dto.NewHouseSortData;
import com.renjie120.dto.SecHouseData;
import com.renjie120.dto.SecHouseSortData;
import com.renjie120.dto.StatisType;

/**
 * 解析table对象，提取出来对应的表格数据.
 * 
 * @author Administrator
 *
 */
public abstract class ParseTable implements IParseTable {
	protected Element table;
	protected List<String> allCity;
	protected int year;
	protected int month;
	protected List<DataInfo> dataes;
	protected Map<String, DataInfo> maps;
	private String title ; 
	public ParseTable(Element table,String title) {
		this.table = table;
		this.title = title;
		dataes = new ArrayList<DataInfo>();
		allCity = new ArrayList<String>();
		maps = new HashMap<String, DataInfo>(); 
		parseYearAndMonth();
		parseTable();
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public List<String> getAllCity() {
		return allCity;
	}

	public List<DataInfo> getDataes() {
		return dataes;
	}

	public Map<String, DataInfo> getMaps() {
		return maps;
	}

	protected void parseYearAndMonth() {
		Pattern pattern = Pattern.compile("([0-9]+)年([0-9]+)月");
		Matcher matcher = pattern.matcher(title);
		while (matcher.find()) {
			year = Integer.parseInt(matcher.group(1));
			month = Integer.parseInt(matcher.group(2));
		}
	}

	protected boolean isFirstRow(String str) {
		Pattern pattern = Pattern.compile("([0-9]+)年([0-9]+)月");
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	abstract List<DataInfo> parseCityAndData(Element tr);

	public boolean validateTable() {
		boolean result = true;
		if (table == null) {
			throw new ExceptionWrapper(ExceptionCode.DOM_NOT_FOUND);
		}
		if (!table.tagName().equals("table")) {
			throw new ExceptionWrapper(ExceptionCode.DOM_ISNOT_TABLE);
		}
		return result;
	}

	abstract String generateSql(DataInfo data);

	public void saveToDb() {
		DruidPooledConnection con;
		try {
			if (dataes != null && dataes.size() > 0) {
				DbPoolConnection dbp = DbPoolConnection.getInstance();
				con = dbp.getConnection();

				PreparedStatement ps = null;
				for (DataInfo data : dataes) {
					ps = con.prepareStatement(generateSql(data));
					ps.executeUpdate();
				}
				if (ps != null && !ps.isClosed())
					ps.close();
				con.close();
				dbp = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	private <T> List<T> changeDatas(
			List<DataInfo> dataes,Class<T> a) {
		List<T> ans = new ArrayList<T>();
		try {
			if (!CollectionUtils.isEmpty(dataes)) {
				for (DataInfo _data : dataes) {
					T newD = a.newInstance();
					BeanUtils.copyProperties(newD, _data);
					ans.add(newD);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	} 

	public void newSaveToDb() {
		if (dataes != null && dataes.size() > 0) {
			DataInfo firstData = dataes.get(0);
			StatisType statisType = firstData.getStatisType();
			if (statisType == StatisType.NEW_COMMERCIAL_HOUSE) {
				List<NewCommercialHouseData> newDatas = changeDatas(dataes,NewCommercialHouseData.class);
				NewCommercialHouseNutzDao dao = new NewCommercialHouseNutzDao();
				dao.batchInsert(newDatas);
			} else if (statisType == StatisType.NEW_HOUSE) {
				List<NewHouseData> newDatas = changeDatas(dataes,NewHouseData.class); 
				NewHouseDataNutzDao dao = new NewHouseDataNutzDao();
				dao.batchInsert(newDatas);
			} else if (statisType == StatisType.NEW_HOUSE_SORTED) { 
				List<NewHouseSortData> newDatas = changeDatas(dataes,NewHouseSortData.class); 
				NewHouseSortDataNutzDao dao = new NewHouseSortDataNutzDao();
				dao.batchInsert(newDatas);
			} else if (statisType == StatisType.SECOND_HANDS_HOUSE) {
				List<SecHouseData> newDatas = changeDatas(dataes,SecHouseData.class);  
				SecHouseDataNutzDao dao = new SecHouseDataNutzDao();
				dao.batchInsert(newDatas);
			} else if (statisType == StatisType.SECOND_HANDS_HOUSE_SORTED) { 
				List<SecHouseSortData> newDatas = changeDatas(dataes,SecHouseSortData.class);  
				SecHouseSortDataNutzDao dao = new SecHouseSortDataNutzDao();
				dao.batchInsert(newDatas);
			}
		}
	}

	public void parseTable() {
		if (validateTable()) {
			Elements trs = table.select("tr");
			int startRow = 0;
			for (Element tr : trs) {
				String t = tr.text();
				if (isFirstRow(t)) {
					break;
				}
				startRow++;
			} 

			int row = 1;
			startRow = startRow + 4;
			for (Element tr : trs) {
				if (row > startRow) {
					dataes.addAll(parseCityAndData(tr));
				}
				row++;
			}
		}
	}
}
