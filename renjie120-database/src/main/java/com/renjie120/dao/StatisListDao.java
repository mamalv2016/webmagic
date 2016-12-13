package com.renjie120.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.StringUtils;
import com.renjie120.common.exception.ExceptionCode;
import com.renjie120.common.exception.ExceptionWrapper;
import com.renjie120.db.DbPoolConnection;
import com.renjie120.dto.StatisList;
import com.renjie120.dto.StatisPageStatus;

public class StatisListDao {
	public static void main(String[] args) {
		StatisListDao ado = new StatisListDao();
		
	
		List<StatisList> pages = new ArrayList();
		for(int i=0;i<100;i++){
			StatisList page = new StatisList();
			page.setDeleteFlag("1");
			page.setStatus(StatisPageStatus.NEW);
			page.setTitle("标题"+i);
			page.setUrl("ssssssssss"+i);
			pages.add(page);
		} 
		
		ado.batchInsert(pages);
		
		
		StatisList query = new StatisList();
		query.setTitle("2");
		
		List<StatisList> ans = ado.query(query);
		System.out.println(ans);
	}

	public void batchInsert(List<StatisList> pages) {
		DbPoolConnection dbp = DbPoolConnection.getInstance();
		DruidPooledConnection con;
		try {
			con = dbp.getConnection();
			con.setAutoCommit(false);
			String sql = "insert into table_tongji_url (url,deleteflag,status,title) values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			for (int k = 0; k < pages.size(); k++) {
				StatisList sp = pages.get(k);
				pstmt.setString(1, sp.getUrl());
				pstmt.setString(2, sp.getDeleteFlag());
				pstmt.setString(3, sp.getStatus().getType() + "");
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

	public void update(StatisList page) {

	}

	private String getQueryStr(StatisList page) { 
		StringBuilder query = new StringBuilder(
				"select * from table_tongji_url where 1=1 ");
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
			query.append(" and status = '" + page.getStatus().getType() + "' ");
		}

		if (!StringUtils.isEmpty(page.getDeleteFlag())) {
			query.append(" and deleteflag = '" + page.getDeleteFlag() + "' ");
		}

		return query.toString();
	}

	public List<StatisList> query(StatisList page) {
		DruidPooledConnection con;
		List<StatisList> ans = new ArrayList<StatisList>();
		try {
			DbPoolConnection dbp = DbPoolConnection.getInstance();
			con = dbp.getConnection();

			PreparedStatement ps = null;
			ps = con.prepareStatement(getQueryStr(page));
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			while (rs.next()) {
				StatisList sp = new StatisList();
				sp.setId(rs.getLong("id"));
				sp.setTitle(rs.getString("title"));
				sp.setUrl(rs.getString("url"));
				sp.setDeleteFlag(rs.getString("deleteflag"));
				sp.setStatus(StatisPageStatus.valueOf(StatisPageStatus
						.getNameByType(rs.getInt("status"))));
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
			throw new ExceptionWrapper(ExceptionCode.DB_FAIL);
		} 
	}

}
