package com.student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

class StudentDAO
{
	// 학생이 성적을 가져오는 메소드
	List<StudentDTO> getRecord(String sid)
	{
		List<StudentDTO> list = new ArrayList<StudentDTO>();
		
		try
		{
			Connection conn = DBConn.getConnection();
			
			try
			{
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
			}
			
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return list;
	}
}
