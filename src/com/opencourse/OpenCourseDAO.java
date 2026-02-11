package com.opencourse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class OpenCourseDAO
{
	private Connection conn;
	
	public OpenCourseDAO() throws SQLException, ClassNotFoundException
	{
	    conn = DBConn.getConnection();
	}
	// 개설과정 등록
	public int add(OpenCourseDTO dto) throws SQLException
	{
		String sql = "{call PRC_ADMIN_OPEN_COURSE_INSERT(?, ?, ?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpcourname());
		cstmt.setString(2, dto.getOpcourroom());
		cstmt.setString(3, dto.getOpcourstart());
		cstmt.setString(4, dto.getOpcourstart());
		
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
	}
	
	// 개설과정 삭제
	public int remove(String cd) throws SQLException
	{
		String sql = "{call PRC_ADMIN_OPEN_COURSE_DELETE(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, cd);
		
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;	
	}
	
	// 개설과정 과정명 수정
	public int courModify(OpenCourseDTO dto) throws SQLException
	{
		String sql = "{call PRC_ADMIN_OC_NAME_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpcourcd());
		cstmt.setString(2, dto.getOpcourname());
		
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
	}
	
	// 개설과정 강의실 수정
	public int roomModify(OpenCourseDTO dto) throws SQLException
	{
		String sql = "{call PRC_ADMIN_OC_CLASSROOM_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpcourcd());
		cstmt.setString(2, dto.getOpcourroom());
		
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
	}
	
	// 개설과정 시작일 수정
	public int startModify(OpenCourseDTO dto) throws SQLException
	{
		String sql = "{call PRC_ADMIN_OC_START_DATE_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, dto.getOpcourcd());
		cstmt.setString(2, dto.getOpcourstart());
		
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
	}
	
	// 개설과정 종료일 수정
	public int endModify(OpenCourseDTO dto) throws SQLException
	{
		String sql = "{call PRC_ADMIN_OC_END_DATE_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpcourcd());
		cstmt.setString(2, dto.getOpcourend());
		
		int result = cstmt.executeUpdate();
		
		cstmt.close();
		return result;
	}
	
	// 개설과정 조회
	public ArrayList<OpenCourseDTO> list() throws SQLException
	{
		ArrayList<OpenCourseDTO> result = new ArrayList<OpenCourseDTO>();
		String sql = "SELECT O.OPEN_COUR_CD, C.COUR_NAME, R.CLASSROOM_NAME"
				+ ", O.START_DT, O.END_DT, O.CREATE_DT"
				+ " FROM OPEN_COURSE O"
				+ " JOIN COURSE C ON O.COUR_CD = C.COUR_CD"
				+ " JOIN CLASSROOM R ON O.CLASSROOM_CD = R.CLASSROOM_CD"
				+ " ORDER BY O.OPEN_COUR_CD";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			OpenCourseDTO dto = new OpenCourseDTO();
			
			dto.setOpcourcd(rs.getString("OPEN_COUR_CD"));
			dto.setOpcourname(rs.getString("COUR_NAME"));
			dto.setOpcourroom(rs.getString("CLASSROOM_NAME"));
			dto.setOpcourstart(rs.getString("START_DT"));
			dto.setOpcourend(rs.getString("END_DT"));
			dto.setCreatedate(rs.getString("CREATE_DT"));
			
			result.add(dto);
		}

		pstmt.close();
		rs.close();
		return result;	
	}
	
	
	// 개설과정 선택 조회
	public ArrayList<OpenCourseDTO> list(String cd) throws SQLException
	{
		ArrayList<OpenCourseDTO> result = new ArrayList<OpenCourseDTO>();
		String sql = "SELECT O.OPEN_COUR_CD, C.COUR_NAME, R.CLASSROOM_NAME"
				+ ", O.START_DT, O.END_DT, O.CREATE_DT"
				+ " FROM OPEN_COURSE O"
				+ " JOIN COURSE C ON O.COUR_CD = C.COUR_CD"
				+ " JOIN CLASSROOM R ON O.CLASSROOM_CD = R.CLASSROOM_CD"
				+ " WHERE O.OPEN_COUR_CD = ?"
				+ " ORDER BY O.OPEN_COUR_CD";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, cd);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			OpenCourseDTO dto = new OpenCourseDTO();
			
			dto.setOpcourcd(rs.getString("OPEN_COUR_CD"));
			dto.setOpcourname(rs.getString("COUR_NAME"));
			dto.setOpcourroom(rs.getString("CLASSROOM_NAME"));
			dto.setOpcourstart(rs.getString("START_DT"));
			dto.setOpcourend(rs.getString("END_DT"));
			dto.setCreatedate(rs.getString("CREATE_DT"));
			
			result.add(dto);
		}

		pstmt.close();
		rs.close();
		return result;	
	}
	
	
}
