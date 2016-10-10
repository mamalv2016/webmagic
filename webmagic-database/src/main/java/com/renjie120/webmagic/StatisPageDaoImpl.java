package com.renjie120.webmagic;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.renjie120.db.DbPoolConnection;
import com.renjie120.dto.DataInfo;
import com.renjie120.dto.StatisPage;

public class StatisPageDaoImpl implements IStatisPageDao{

	@Override
	public void batchInsert(List<StatisPage> pages) {
		DruidPooledConnection con;
		try {
			if (pages != null && pages.size() > 0) {
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

	@Override
	public void update(StatisPage page) {
		 
	}

	private String getQueryStr(StatisPage page){
		StringBuilder query = new StringBuilder("select * from table_tongji_url ");
		
		return query.toString();
	}
	
	@Override
	public List<StatisPage> query(StatisPage page) {
		DruidPooledConnection con;
		try {
			if (pages != null && pages.size() > 0) {
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
		return null;
	}

}
