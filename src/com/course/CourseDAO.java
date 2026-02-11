package com.course;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

public class CourseDAO
{
	private Connection conn;
	
	public CourseDAO() throws SQLException, ClassNotFoundException
	{
	    conn = DBConn.getConnection();
	}
	
	// 과정명 추가
	public int add(CourseDTO dto) throws SQLException
	{
		int result = 0;
		String sql = "{call PRC_ADMIN_COURSE_INSERT(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);	
		cstmt.setString(1, dto.getCourname());
		
		result = cstmt.executeUpdate();

		cstmt.close();
		return result;
	}
	
	// 과정명 조회
	public ArrayList<CourseDTO> list() throws SQLException 
	{
		ArrayList<CourseDTO> result = new ArrayList<CourseDTO>();
		
		String sql = "SELECT COUR_CD, COUR_NAME FROM COURSE ORDER BY COUR_CD";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			CourseDTO dto = new CourseDTO();
			
			dto.setCourcd(rs.getString("COUR_CD"));
			dto.setCourname(rs.getString("COUR_NAME"));
			
			result.add(dto);
		}
		
		pstmt.close();
		rs.close();
		
		return result;	
	}
	
	// 과정명 선택 조회
	public ArrayList<CourseDTO> list(String where, String cour) throws SQLException 
	{
		ArrayList<CourseDTO> result = new ArrayList<CourseDTO>();
		
		String sql = String.format("SELECT COUR_CD, COUR_NAME FROM COURSE WHERE %s = ? ORDER BY COUR_CD", where);
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, cour);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			CourseDTO dto = new CourseDTO();
			
			dto.setCourcd(rs.getString("COUR_CD"));
			dto.setCourname(rs.getString("COUR_NAME"));
			
			result.add(dto);
		}
		
		pstmt.close();
		rs.close();
		
		return result;	
	}
	
	// 과정명 삭제
	public int remove(String courcd) throws SQLException
	{
		String sql = "{call PRC_ADMIN_COURSE_DELETE(?)}";
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, courcd);
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
	}
	
	// 과정명 수정
	public int modify(CourseDTO dto) throws SQLException
	{
		String sql = "{call PRC_ADMIN_COURSE_UPDATE(?, ?)}";
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getCourcd());
		cstmt.setString(2, dto.getCourname());
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
	}
	
	
	
	
	

	
	
	
	
	
}
