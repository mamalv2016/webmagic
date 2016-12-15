package com.renjie120.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.StringUtils;
import com.renjie120.common.exception.ExceptionCode;
import com.renjie120.common.exception.ExceptionWrapper;
import com.renjie120.db.DbPoolConnection;
import com.renjie120.dto.StatisPage;

public class StatisPageDao {
	 
	public void batchInsert(List<StatisPage> pages) {
		DbPoolConnection dbp = DbPoolConnection.getInstance();
		DruidPooledConnection con;
		try {
			con = dbp.getConnection();
			con.setAutoCommit(false);
			String sql = "insert into table_tongji_page_url (url,deleteflag,status,title) values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			for (int k = 0; k < pages.size(); k++) {
				StatisPage sp = pages.get(k);
				pstmt.setString(1, sp.getUrl());
				pstmt.setString(2, sp.getDeleteFlag());
				pstmt.setString(3, sp.getStatus() + "");
				pstmt.setString(4, sp.getTitle());
				// 加入批处理
				pstmt.addBatch();
			}
			pstmt.executeBatch(); // 执行批处理
			con.commit();
			pstmt.close();
			con.close();
			dbp = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionWrapper(ExceptionCode.DB_FAIL);
		}
	}

	public void update(StatisPage condition, StatisPage newPage) {
		DbPoolConnection dbp = DbPoolConnection.getInstance();
		DruidPooledConnection con;
		try {
			con = dbp.getConnection();
			String sql = getUpdateStr(condition, newPage);
			PreparedStatement pstmt = con.prepareStatement(sql);
			int index = 1;
			if (!StringUtils.isEmpty(newPage.getUrl())) {
				pstmt.setString(index++, newPage.getUrl());
			}

			if (!StringUtils.isEmpty(newPage.getTitle())) {
				pstmt.setString(index++, newPage.getTitle());
			}

			if (newPage.getStatus() != null) {
				pstmt.setString(index++, newPage.getStatus() + "");
			}

			if (!StringUtils.isEmpty(newPage.getDeleteFlag())) {
				pstmt.setString(index++, newPage.getDeleteFlag());
			}

			pstmt.execute();
			con.close();
			pstmt.close();
			dbp = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionWrapper(ExceptionCode.DB_FAIL);
		}
	}

	private String getQueryStr(StatisPage page) {
		StringBuilder query = new StringBuilder(
				"select * from table_tongji_page_url where 1=1 ");
		query.append(getWhereSql(page));
		return query.toString();
	}

	private String getUpdateStr(StatisPage condition, StatisPage newPage) {
		StringBuilder query = new StringBuilder(
				"update table_tongji_page_url set ");
		if (!StringUtils.isEmpty(newPage.getUrl())) {
			query.append(" url =? ,");
		}

		if (!StringUtils.isEmpty(newPage.getTitle())) {
			query.append(" title  = ?,");
		}

		if (newPage.getStatus() != null) {
			query.append("  status = ?,");
		}

		if (!StringUtils.isEmpty(newPage.getDeleteFlag())) {
			query.append(" deleteflag = ?,");
		}
		query = query.deleteCharAt(query.lastIndexOf(","));
		query.append("  where 1=1 ");
		query.append(getWhereSql(condition));
		return query.toString();
	}

	private String getWhereSql(StatisPage page) {
		StringBuilder query = new StringBuilder("");
		if (page == null) {
			return query.append(" and 1=2").toString();
		}
		if (page.getId() != null && page.getId() > 0) {
			query.append(" and id = " + page.getId());
		}

		if (!StringUtils.isEmpty(page.getUrl())) {
			query.append(" and url like '%" + page.getUrl() + "%' ");
		}

		if (!StringUtils.isEmpty(page.getTitle())) {
			query.append(" and title like '%" + page.getTitle() + "%' ");
		}

		if (page.getStatus() != null) {
			query.append(" and status = '" + page.getStatus() + "' ");
		}

		if (!StringUtils.isEmpty(page.getDeleteFlag())) {
			query.append(" and deleteflag = '" + page.getDeleteFlag() + "' ");
		}

		return query.toString();
	}

	public List<StatisPage> query(StatisPage page) {
		DruidPooledConnection con;
		List<StatisPage> ans = new ArrayList<StatisPage>();
		try {
			DbPoolConnection dbp = DbPoolConnection.getInstance();
			con = dbp.getConnection();

			PreparedStatement ps = null;
			ps = con.prepareStatement(getQueryStr(page));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StatisPage sp = new StatisPage();
				sp.setId(rs.getLong("id"));
				sp.setTitle(rs.getString("title"));
				sp.setUrl(rs.getString("url"));
				sp.setDeleteFlag(rs.getString("deleteflag"));
				sp.setStatus(rs.getInt("status") + "");
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
