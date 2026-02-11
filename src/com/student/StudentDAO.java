package com.student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

class StudentDAO
{
	 private Connection conn;
	   
	 StudentDAO() 
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

	// 학생 비밀번호 변경
	int updatePassword(String sid,String password) throws SQLException
	{
		int result = 0;
		
		String sql = "UPDATE STUDENTS SET PW = ? WHERE STUDENT_CD = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, password);
		pstmt.setString(2, sid);
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		
		return result;
	}
	
	// 학생이 성적을 가져오는 메소드
	List<StudentDTO> getRecord(String sid) throws SQLException
	{
		List<StudentDTO> list = new ArrayList<StudentDTO>();

		String sql = "{call PRC_STUDENT_SCORE_ALL_SELECT(?,?)}";
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, sid);
		cstmt.registerOutParameter(2, OracleTypes.CURSOR);	// OUT 매개변수 등록
		cstmt.executeQuery();
		
		ResultSet rs = (ResultSet)cstmt.getObject(2);
	
		while(rs.next())
		{
			StudentDTO dto = new StudentDTO();
			dto.setName(rs.getString("ST_NAME"));
			dto.setCourseName(rs.getString("COUR_NAME"));
			dto.setSubName(rs.getString("SUB_NAME"));
			dto.setStartDate(rs.getString("STARTDT"));
			dto.setEndDate(rs.getString("ENDDT"));
			dto.setBookName(rs.getString("TEXTBOOK_NAME"));
			dto.setAttendance(rs.getInt("APLLY_PCT_ATTENDANCE"));
			dto.setWritten(rs.getInt("APPLY_PCT_WRITTEN"));
			dto.setPractical(rs.getInt("APPLY_PCT_PRACTICAL"));
			dto.setTotal(rs.getInt("APPLY_PCT_TOT_SCORE"));
			dto.setRanking(rs.getInt("RANKING"));
			list.add(dto);
		}
		
		cstmt.close();
		rs.close();

		return list;
	}
}
