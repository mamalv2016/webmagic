package com.renjie120.jsoup;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.renjie120.db.DbPoolConnection;
import com.renjie120.dto.DataInfo;

/**
 * 解析table对象，提取出来对应的表格数据.
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

	public ParseTable(Element table) {
		this.table = table;
		dataes = new ArrayList<DataInfo>();
		allCity = new ArrayList<String>();
		maps = new HashMap<String, DataInfo>();
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

	private void parseYearAndMonth(String str) {
		Pattern pattern = Pattern.compile("([0-9]+)年([0-9]+)月");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			year = Integer.parseInt(matcher.group(1));
			month = Integer.parseInt(matcher.group(2));
		}
	}

	abstract List<DataInfo> parseCityAndData(Element tr);

	public boolean validateTable() {
		boolean result = true;
		if (table == null) {
			throw new NullPointerException("待解析dom元素未设置");
		}
		if (!table.tagName().equals("table")) {
			throw new IllegalArgumentException("待解析对象不是table");
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

	public void parseTable() {
		if (validateTable()) {
			Elements trs = table.select("tr");
			int row = 1;
			String title = trs.get(0).text();
			parseYearAndMonth(title);

			for (Element tr : trs) {
				if (row > 4) {
					dataes.addAll(parseCityAndData(tr));
				}
				row++;
			}
		}
	}
}
