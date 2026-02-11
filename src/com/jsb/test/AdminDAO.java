package com.jsb.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

public class AdminDAO
{
private Connection conn;
	
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 귀찮으니 dto 이런거 안만들고 바로 ㅇㅇㅇ
	public String adminLogin(String id, String pw) throws SQLException
	{
		String result = null;
		
		String sql = "{call PACK_LOGIN.PRC_ADMIN_IN(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, id);
		cstmt.setString(2, pw);

		cstmt.executeQuery();
		
		cstmt.close();

		return result;
	}

	// close
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
}
