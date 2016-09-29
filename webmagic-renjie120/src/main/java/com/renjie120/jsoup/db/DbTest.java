package com.renjie120.jsoup.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidPooledConnection;

public class DbTest {
	private void executeUpdateBySQL(String sql) throws SQLException {
		DbPoolConnection dbp = DbPoolConnection.getInstance();
		DruidPooledConnection con = dbp.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		ps.close();
		con.close();
		dbp = null;
	}
	
	private void executeQuery(String sql) throws SQLException {
		DbPoolConnection dbp = DbPoolConnection.getInstance();
		DruidPooledConnection con = dbp.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		ps.close();
		con.close();
		dbp = null;
	}
	
	public static void main(String[] args){
		DbTest test = new DbTest();
		try {
			test.executeUpdateBySQL("insert into haah values(123)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
