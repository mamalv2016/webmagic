package com.renjie120.webmagic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.StringUtils;
import com.renjie120.db.DbPoolConnection;
import com.renjie120.dto.DataInfo;
import com.renjie120.dto.StatisPage;
import com.renjie120.dto.StatisPageStatus;

public class StatisPageDaoImpl implements IStatisPageDao {

	@Override
	public void batchInsert(List<StatisPage> pages) {
		DruidPooledConnection con;
		try {
			if (pages != null && pages.size() > 0) {
				DbPoolConnection dbp = DbPoolConnection.getInstance();
				con = dbp.getConnection();

				PreparedStatement ps = null;
				for (StatisPage data : pages) {
					StatisPage tempData = new StatisPage();
					tempData.setUrl(data.getUrl());
					
					query(tempData);
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

	@Override
	public void update(StatisPage page) {

	}

	private String getQueryStr(StatisPage page) {
		StringBuilder query = new StringBuilder(
				"select * from table_tongji_url where 1=1 ");
		if (page == null) {
			return query.append(" and 1=2").toString();
		}
		if (page.getId() != null && page.getId() > 0) {
			query.append(" and id = " + page.getId());
		}

		if (!StringUtils.isEmpty(page.getUrl())) {
			query.append(" and url = '" + page.getUrl() + "' ");
		}

		if (!StringUtils.isEmpty(page.getTitle())) {
			query.append(" and title = '" + page.getTitle() + "' ");
		}

		if (page.getStatus() != null) {
			query.append(" and status = '" + page.getStatus().getType() + "' ");
		}

		if (!StringUtils.isEmpty(page.getDeleteFlag())) {
			query.append(" and deleteflag = '" + page.getDeleteFlag() + "' ");
		}

		return query.toString();
	}

	 
	@Override
	public List<StatisPage> query(StatisPage page) {
		DruidPooledConnection con;
		List<StatisPage> ans = new ArrayList<StatisPage>();
		try {
			DbPoolConnection dbp = DbPoolConnection.getInstance();
			con = dbp.getConnection();

			PreparedStatement ps = null;
			ps = con.prepareStatement(getQueryStr(page));
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData m=rs.getMetaData(); 
			while (rs.next()) {
				StatisPage sp = new StatisPage();
				sp.setId(rs.getLong("id"));
				sp.setTitle(rs.getString("title"));
				sp.setUrl(rs.getString("url"));
				sp.setDeleteFlag(rs.getString("deleteflag"));
				sp.setStatus(StatisPageStatus.valueOf(StatisPageStatus.getNameByType(rs.getInt("status"))));
				ans.add(sp);
			}
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			con.close();
			dbp = null;

			return ans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
