package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{
	private static Connection dbConn;
	
	public static Connection getConnection()
	{
		try
		{
			if (dbConn == null)
			{
				String url = "jdbc:oracle:thin:@192.168.0.111:1521:xe";  // 프로젝트 공용 DB
				String user = "TEAM1";
				String pwd = "1234";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				dbConn = DriverManager.getConnection(url, user, pwd);
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("JDBC 드라이버 로드 실패", e);
		}
		catch (SQLException e) {
			throw new RuntimeException("DB 연결 실패", e);
		}

		
		return dbConn;
	}
	
	
	public static void close()
	{
		try
		{
			if (dbConn != null)
			{
				if (!dbConn.isClosed())
				{
					dbConn.close();
				}
				
				dbConn = null;
			}
		}
		catch (SQLException e)
		{
			throw new RuntimeException("DB 연결 해제 실패", e);
		}
	}
}
