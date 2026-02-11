package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login
{
	public Login()
	{
		try
		{
			conn = DBConn.getConnection();
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	private Connection conn;
	
	// 학생 로그인
	public boolean studentLogin(String id, String psw)
	{
		try
		{
			String sql = "SELECT * "+ 
					" FROM STUDENTS"+ 
					" WHERE STUDENT_CD = ? AND PW = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, psw);
			
			ResultSet result = pstmt.executeQuery();
			
			if(result.next())
			{
				pstmt.close();
				return true;
			}
			else
			{
				pstmt.close();
				return false;
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return false;
	}
	
	// 교수 로그인
	public boolean profLogin(String id, String psw)
	{
		try
		{
			String sql = "SELECT *" + 
					" FROM PROFESSOR" + 
					" WHERE PROF_CD = ? AND PW = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, psw);
			
			ResultSet result = pstmt.executeQuery();
			
			if(result.next())
			{
				pstmt.close();
				return true;
			}
			else
			{
				pstmt.close();
				return false;
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return false;
	}
	
	// 관리자 로그인
	public boolean adminLogin(String id, String psw)
	{
		System.out.println("미구현입니다.");
		return false;
	}
}
