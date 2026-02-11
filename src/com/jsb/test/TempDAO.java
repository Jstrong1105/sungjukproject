package com.jsb.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class TempDAO
{
	// # FIXME 임시.........
	
	private Connection conn;
	
	// 열기
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 개설 과정 목록 조회(개설과정코드, 과정명, 과정시작일, 과정종료일, 과정생성일, 강의실명)
	public ArrayList<OpenCourseDTO> selectOpenCourse() throws SQLException
	{
		ArrayList<OpenCourseDTO> result = new ArrayList<OpenCourseDTO>();
		
		String sql = "SELECT A.OPEN_COUR_CD, B.COUR_NAME, A.START_DT, A.END_DT, A.CREATE_DT, C.CLASSROOM_NAME"
				+ " FROM OPEN_COURSE A, COURSE B, CLASSROOM C"
				+ " WHERE B.COUR_CD = A.COUR_CD"
				+ " AND C.CLASSROOM_CD = A.CLASSROOM_CD"
				+ " ORDER BY A.OPEN_COUR_CD";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
	    ResultSet rs = pstmt.executeQuery();
	    
	    while (rs.next())
		{
	    	OpenCourseDTO dto = new OpenCourseDTO();
	    	
	    	dto.setOpenCourCD(rs.getString("OPEN_COUR_CD"));
	    	dto.setCourName(rs.getString("COUR_NAME"));
	    	dto.setStartDT(rs.getString("START_DT"));
	    	dto.setEndDT(rs.getString("END_DT"));
	    	dto.setCreateDT(rs.getString("CREATE_DT"));
	    	dto.setClassroomName(rs.getString("CLASSROOM_NAME"));
	    	
	    	result.add(dto);
		}
	    
	    rs.close();
	    pstmt.close();
		
		return result;
	}
	
	// 교수 조회
	public ArrayList<ProfessorDTO> selectProfessor() throws SQLException
	{
		ArrayList<ProfessorDTO> result = new ArrayList<ProfessorDTO>();
		
		String sql = "SELECT PROF_CD, NAME, SSN, CREATE_DT FROM PROFESSOR ORDER BY PROF_CD ASC";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			ProfessorDTO dto = new ProfessorDTO();
			
			dto.setProfCD(rs.getString("PROF_CD"));
			dto.setName(rs.getString("NAME"));
			dto.setCreateDT(rs.getString("CREATE_DT"));
			
			result.add(dto);
		}
		
		rs.close();
		pstmt.close();
		
		return result;
	}
	
	
	// 닫기
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
}
