package com.classroom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class ClassRoomDAO
{
	private Connection conn;
	
	public ClassRoomDAO() throws SQLException, ClassNotFoundException
	{
	    conn = DBConn.getConnection();
	}
	
	// 강의실 추가
	public int add(ClassRoomDTO dto)
	{
		int result = 0;
		
		try
		{
			String sql = "{call PRC_ADMIN_CLASSROOM_INSERT(?)}";
			
			CallableStatement cstmt = conn.prepareCall(sql);	
			cstmt.setString(1, dto.getRoomname());
			
			result = cstmt.executeUpdate();
			
			cstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 강의실 조회
	public ArrayList<ClassRoomDTO> list() throws SQLException 
	{
		ArrayList<ClassRoomDTO> result = new ArrayList<ClassRoomDTO>();
		
		String sql = "SELECT CLASSROOM_CD, CLASSROOM_NAME FROM CLASSROOM ORDER BY CLASSROOM_CD";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			ClassRoomDTO dto = new ClassRoomDTO();
			
			dto.setRoomcd(rs.getString("CLASSROOM_CD"));
			dto.setRoomname(rs.getString("CLASSROOM_NAME"));
			
			result.add(dto);
		}
		
		pstmt.close();
		rs.close();
		
		return result;	
	}
	
	// 강의실 선택 조회
	public ArrayList<ClassRoomDTO> list(String where, String room) throws SQLException 
	{
		ArrayList<ClassRoomDTO> result = new ArrayList<ClassRoomDTO>();
		
		String sql = String.format("SELECT CLASSROOM_CD, CLASSROOM_NAME FROM CLASSROOM WHERE %s = ? ORDER BY CLASSROOM_CD", where);
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, room);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			ClassRoomDTO dto = new ClassRoomDTO();
			
			dto.setRoomcd(rs.getString("CLASSROOM_CD"));
			dto.setRoomname(rs.getString("CLASSROOM_NAME"));
			
			result.add(dto);
		}
		
		pstmt.close();
		rs.close();
		
		return result;	
	}
	
	
	// 강의실명 수정
	public int modify(ClassRoomDTO dto) throws SQLException
	{
		String sql = "{call PRC_ADMIN_CLASSROOM_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getRoomcd());
		cstmt.setString(2, dto.getRoomname());
		
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
		
	}
	
	// 강의실명 삭제
	public int remove(String roomcd) throws SQLException
	{
		String sql = "{call PRC_ADMIN_CLASSROOM_DELETE(?)}";
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, roomcd);
		int result = cstmt.executeUpdate();
		
		return result;

	}
}
