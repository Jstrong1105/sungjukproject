package com.sign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

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
	
	// 로그인 분기
	public boolean selectLogin(int num, String id, String pwd)
	{
		try
		{
			String s1 = "", s2 = ""; 
			
			if (num == 1)
			{
				s1 = "STUDENTS";
				s2 = "STUDENT_CD";		
			}
			else if(num ==2)
			{
				s1 = "PROFESSOR";
				s2 = "PROF_CD";
			}
			else
			{
				s1 = "ADMINS";
				s2 = "ID";		
			}
			String sql = "SELECT * FROM " + s1 + " WHERE " + s2 + " = ? AND PW = ?";
				
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
					
			ResultSet rs = pstmt.executeQuery();
			
			return rs.next();
			
			
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return false;
	}
	
	// 학생 로그인
	public boolean studentLogin(String id, String psw)
	{	
		boolean result = selectLogin(1,id,psw);
		
		return result; 
	}
	
	// 교수 로그인
	public boolean profLogin(String id, String psw)
	{		
		boolean result = selectLogin(2,id,psw);
		
		return result; 
	}
	
	// 관리자 로그인
	public boolean adminLogin(String id, String psw)
	{	
		boolean result = selectLogin(3,id,psw);
		
		return result; 
	}		
}
