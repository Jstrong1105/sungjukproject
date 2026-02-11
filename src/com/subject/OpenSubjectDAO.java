package com.subject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class OpenSubjectDAO
{
	private Connection conn;
	
	// 연결
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 개설과목 출력
	public ArrayList<OpenSubjectDTO> selectOpenSubject() throws SQLException
	{
		ArrayList<OpenSubjectDTO> result = new ArrayList<OpenSubjectDTO>();
		
		String sql = "SELECT * FROM VIEW_ADMIN_OS_PRINT ORDER BY 개설과목코드 ASC";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			OpenSubjectDTO dto = new OpenSubjectDTO();
			dto.setOpenSubCD(rs.getString("개설과목코드"));
			dto.setCourName(rs.getString("과정명"));
			dto.setClassroomName(rs.getString("강의실명"));
			dto.setSubName(rs.getString("과목명"));
			dto.setStartDT(rs.getString("과목시작일"));
			dto.setEndDT(rs.getString("과목종료일"));
			dto.setTextbookName(rs.getString("교재명"));
			dto.setProfName(rs.getString("교수명"));
			
			result.add(dto);
		}
		
		rs.close();
		pstmt.close();
		
		return result;
	}
	
	// 개설과목 출력
	public ArrayList<OpenSubjectDTO> selectOpenSubject(String openSubCD) throws SQLException
	{
		ArrayList<OpenSubjectDTO> result = new ArrayList<OpenSubjectDTO>();

		String sql = "SELECT * FROM VIEW_ADMIN_OS_PRINT WHERE 개설과목코드 = ? ORDER BY 개설과목코드 ASC";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, openSubCD);
		
		ResultSet rs = pstmt.executeQuery();

		while (rs.next())
		{
			OpenSubjectDTO dto = new OpenSubjectDTO();
			dto.setOpenSubCD(rs.getString("개설과목코드"));
			dto.setCourName(rs.getString("과정명"));
			dto.setClassroomName(rs.getString("강의실명"));
			dto.setSubName(rs.getString("과목명"));
			dto.setStartDT(rs.getString("과목시작일"));
			dto.setEndDT(rs.getString("과목종료일"));
			dto.setTextbookName(rs.getString("교재명"));
			dto.setProfName(rs.getString("교수명"));

			result.add(dto);
		}

		rs.close();
		pstmt.close();

		return result;
	}
	
	// 개설과목 등록
	public int insertOpenSubject(OpenSubjectDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_OS_INPUT(?, ?, ?, ?, ?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpenCourCD());
		cstmt.setString(2, dto.getSubName());
		cstmt.setString(3, dto.getStartDT());
		cstmt.setString(4, dto.getEndDT());
		cstmt.setString(5, dto.getTextbookName());
		cstmt.setString(6, dto.getProfCD());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}

	// 개설과목의 개설과정 변경
	public int updateOpenSubCourse(OpenSubjectDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_OS_COUR_NAME_UPDATE(?, ?)}";

		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpenSubCD());
		cstmt.setString(2, dto.getOpenCourCD());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}
	
	// 개설과목의 시작날짜/종료날짜 변경
	public int updateOpenSubDate(OpenSubjectDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_OS_DATE_UPDATE(?, ?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpenSubCD());
		cstmt.setString(2, dto.getStartDT());
		cstmt.setString(3, dto.getEndDT());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}

	// 개설과목의 교수 수정
	public int updateOpenSubProf(OpenSubjectDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_OS_PR_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpenSubCD());
		cstmt.setString(2, dto.getProfCD());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}

	// 개설과목의 과목 수정
	public int updateOpenSubSubject(OpenSubjectDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_OS_SB_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpenSubCD());
		cstmt.setString(2, dto.getSubName());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}

	// 개설과목의 교재 수정
	public int updateOpenSubTextbook(OpenSubjectDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_OS_TB_NAME_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getOpenSubCD());
		cstmt.setString(2, dto.getTextbookName());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}
	
	// 개설과목 삭제
	public int deleteOpenSubject(String openSubCD) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_OS_SB_DELETE(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, openSubCD);
		
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
