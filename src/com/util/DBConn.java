package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{
	private static Connection dbConn;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		if(dbConn == null)
		{
			String url = "jdbc:oracle:thin:@192.168.0.111:1521:xe";
			String user = "TEAM1";
			String psw = "1234";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			dbConn = DriverManager.getConnection(url,user,psw);
		}
		
		return dbConn;
	}
	
	public static Connection getConnection(String url, String user, String psw) throws ClassNotFoundException, SQLException
	{
		if(dbConn == null)
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			dbConn = DriverManager.getConnection(url,user,psw);
		}
		
		return dbConn;
	} 
	
	public static void close() 
	{
		try
		{
			if(dbConn != null)
			{
				if(!dbConn.isClosed())
				{
					dbConn.close();
				}
			}
		} 
		
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		dbConn = null;
	}
	
	public void asd()
	{
		System.out.println("hi");
	}
	

	
}

