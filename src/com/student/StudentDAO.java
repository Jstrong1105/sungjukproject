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
	
	  // 학생 성적 출력
	   ArrayList<StudentDTO> getRecord(String opensubcd) throws SQLException
	   {
	      ArrayList<StudentDTO> result = new ArrayList<StudentDTO>(); 
	         
	         // SQL 구문      
	          String sql = 
	                   " SELECT T.이름 , T.출결 , T.필기, T.실기, T.합 , RANK() OVER(ORDER BY T.합 DESC) AS 등수 "
	                    + " FROM( "
	                    + " SELECT T4.NAME AS 이름, "
	                    + " T1.ATTENDANCE AS 출결, "
	                    + " T1.WRITTEN AS 필기, "
	                    + " T1.PRACTICAL AS 실기, "
	                    + " ( "
	                    + "   T1.ATTENDANCE * T5.ATTENDANCE_PCT / 100.0 "
	                    + " + T1.WRITTEN    * T5.WRITTEN_PCT    / 100.0 "
	                    + " + T1.PRACTICAL  * T5.PRACTICAL_PCT  / 100.0 "
	                    + " ) AS 합 "
	                    + " FROM SCORE T1 "
	                    + " JOIN OPEN_SUBJECT T2 "
	                    + " ON T1.OPEN_SUB_CD = T2.OPEN_SUB_CD "
	                    + " JOIN COURSE_REGISTRATION T3 "
	                    + " ON T1.COUR_REGI_CD = T3.COUR_REGI_CD "
	                    + " JOIN STUDENTS T4 "
	                    + " ON T3.STUDENT_CD = T4.STUDENT_CD "
	                    + " JOIN SCORE_PERCENTAGE T5 "
	                    + " ON T1.OPEN_SUB_CD = T5.OPEN_SUB_CD "
	                    + " WHERE T1.OPEN_SUB_CD = ? "
	                    + " ) T ";
	          
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1,opensubcd);
	         
	         ResultSet rs = pstmt.executeQuery();
	         
	         while(rs.next())
	         {
	            StudentDTO dto = new StudentDTO();
	            
	            dto.setName(rs.getString("이름"));
	            dto.setAttendance(rs.getInt("출결"));
	            dto.setWritten(rs.getInt("필기"));
	            dto.setPractical(rs.getInt("실기"));
	            dto.setRanking(rs.getInt("등수"));
	            dto.setTotal(rs.getInt("합"));
	            
	            result.add(dto);
	         }

	         pstmt.close();
	         rs.close();
	         
	         return result;
	   }
}
