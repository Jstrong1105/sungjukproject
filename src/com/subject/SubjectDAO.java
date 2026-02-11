package com.subject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class SubjectDAO
{
	private Connection conn;

	// 연결
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}

	// 과목 전체 출력
	public ArrayList<SubjectDTO> selectSubject() throws SQLException
	{
		ArrayList<SubjectDTO> result = new ArrayList<SubjectDTO>();
		
		String sql = "SELECT SUB_CD, SUB_NAME FROM SUBJECT ORDER BY SUB_CD ASC";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			SubjectDTO dto = new SubjectDTO();
			
			dto.setSubCD(rs.getString("SUB_CD"));
			dto.setSubName(rs.getString("SUB_NAME"));
			
			result.add(dto);
		}
		
		rs.close();
		pstmt.close();
		
		return result;
	}

	// 과목 코드로 출력
	public ArrayList<SubjectDTO> selectSubject(String subCd) throws SQLException
	{
		ArrayList<SubjectDTO> result = new ArrayList<SubjectDTO>();

		String sql = "SELECT SUB_CD, SUB_NAME FROM SUBJECT WHERE SUB_CD = ? ORDER BY SUB_CD ASC";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, subCd);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next())
		{
			SubjectDTO dto = new SubjectDTO();

			dto.setSubCD(rs.getString("SUB_CD"));
			dto.setSubName(rs.getString("SUB_NAME"));

			result.add(dto);
		}
		
		rs.close();
		pstmt.close();

		return result;
	}
	
	// 과목 입력
	public int insertSubject(SubjectDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_SUBJECT_INPUT(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getSubName());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}

	// 과목 수정
	public int updateSubject(SubjectDTO dto) throws SQLException
	{
		int result = 0;

		String sql = "{call PRC_ADMIN_SUBJECT_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getSubCD());
		cstmt.setString(2, dto.getSubName());
		
		result = cstmt.executeUpdate();

		cstmt.close();
		
		return result;
	}
	
	// 과목 삭제
	public int deleteSubject(String subCd) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_SUBJECT_DELETE(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, subCd);
		
		result = cstmt.executeUpdate();

		cstmt.close();
		
		return result;
	}
	
	// 닫기
	public void close() throws SQLException
	{
		DBConn.close();
	}
}
