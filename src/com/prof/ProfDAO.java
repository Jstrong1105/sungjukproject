package com.prof;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.student.StudentDTO;
import com.util.DBConn;

class ProfDAO
{
   private Connection conn;
   
 
   ProfDAO() 
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
   
   // 배점 수정
   int updatePCT(String oscd, String profcd, int att, int wri, int pra) throws SQLException
   {
      String sql = "{call PRC_SCORE_PCT_UPDATE(?,?,?,?,?)}";
      
      CallableStatement cstmt = conn.prepareCall(sql);
         
      cstmt.setString(1, profcd);
      cstmt.setString(2, oscd);
      cstmt.setInt(3, att);
      cstmt.setInt(4, wri);
      cstmt.setInt(5, pra);
      
      int result = cstmt.executeUpdate();
      
      if (result > 0)
      {
         System.out.println(">> 배점이 업데이트됨~!");
      }
      
      return result;   
   }
   
   
   // 교수 본인의 과목 출력
   ArrayList<ProfDTO> selectSubject(String profcd) throws SQLException
   {
      ArrayList<ProfDTO> result = new ArrayList<ProfDTO>(); 
      
      // SQL 구문
      String sql = 
    		   "SELECT T2.SUB_NAME 과목명, T1.START_DT 시작일, T1.END_DT 종료일,T1.OPEN_SUB_CD 개설과목 "
    		  +" , t3.attendance_pct 출결, t3.written_pct 필기, t3.practical_pct 실기"
    		  +" FROM OPEN_SUBJECT T1 JOIN SUBJECT T2 ON T1.SUB_CD = T2.SUB_CD"
    		  +" JOIN SCORE_PERCENTAGE T3 ON T3.OPEN_SUB_CD = T1.OPEN_SUB_CD"
    		  +" WHERE PROF_CD = ?";
      
      // 작업 객체 생성
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, profcd);
      
      ResultSet rs = pstmt.executeQuery();
      
      while(rs.next())
      {
         ProfDTO dto = new ProfDTO();
         
         dto.setOpen_sub_name(rs.getString("과목명"));
         dto.setStart_dt(rs.getDate("시작일"));
         dto.setEnd_dt(rs.getDate("종료일"));
         dto.setSubject_cd(rs.getString("개설과목"));
         dto.setAtt(rs.getInt("출결"));
         dto.setWri(rs.getInt("필기"));
         dto.setPra(rs.getInt("실기"));
         
         result.add(dto);
      }
      
      pstmt.close();
      rs.close();
      
      return result;
   }
   
   // 학생 성적 삭제
   int deleteScore(String openSubCd) throws SQLException
   {
	   int result = 0;
	   
	   String sql = "DELETE FROM SCORE WHERE SCORE_CD = ?";
	   
	   PreparedStatement pstmt = conn.prepareStatement(sql);
	   
	   pstmt.setString(1, openSubCd);
	   
	   result = pstmt.executeUpdate();
	   
	   return result;
   }
   
   // 학생 성적 출력
   ArrayList<StudentDTO> printScore(String opensubcd) throws SQLException
   {
	   ArrayList<StudentDTO> result = new ArrayList<StudentDTO>(); 
	      
	      // SQL 구문      
	       String sql = 
	    		   " SELECT T.이름 , T.출결 , T.필기, T.실기, T.합 , RANK() OVER(ORDER BY T.합 DESC) AS 등수"
	    		 + " FROM("
	    		 + " SELECT T4.NAME AS 이름, T1.ATTENDANCE AS 출결, T1.WRITTEN AS 필기,"
	    		 + " T1.PRACTICAL AS 실기 , T1.ATTENDANCE+T1.WRITTEN+T1.PRACTICAL AS 합"
	    		 + " FROM SCORE T1"
	    		 + " JOIN OPEN_SUBJECT T2"
	    		 + " ON T1.OPEN_SUB_CD = T2.OPEN_SUB_CD"
	    		 + " JOIN COURSE_REGISTRATION T3"
	    		 + " ON T1.COUR_REGI_CD = T3.COUR_REGI_CD"
	    		 + " JOIN STUDENTS T4"
	    		 + " ON T3.STUDENT_CD = T4.STUDENT_CD"
	    		 + " WHERE T2.OPEN_SUB_CD = ?"
	    		 + " ) T";
	       
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
	         
	         result.add(dto);
	      }

	      pstmt.close();
	      rs.close();
	      
	      return result;
   }
   
   // 수강 듣는 학생 출력
   ArrayList<StudentDTO> selectStudent(String opensubcd) throws SQLException
   {
      ArrayList<StudentDTO> result = new ArrayList<StudentDTO>(); 
      
      // SQL 구문      
       String sql = 
    		     "SELECT T3.NAME AS 이름 , T2.COUR_REGI_CD AS 수강신청"
    		  + " FROM OPEN_SUBJECT T1"
    		  + " JOIN COURSE_REGISTRATION T2"
    		  + " ON T1.OPEN_COUR_CD = T2.OPEN_COUR_CD"
    		  + " JOIN STUDENTS T3"
    		  + " ON T2.STUDENT_CD = T3.STUDENT_CD"
    		  + " WHERE T1.OPEN_SUB_CD = ?";
       
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1,opensubcd);
      
      ResultSet rs = pstmt.executeQuery();
      
      while(rs.next())
      {
         StudentDTO dto = new StudentDTO();
         
         dto.setRegiCd(rs.getString("수강신청"));
         dto.setName(rs.getString("이름"));
         result.add(dto);
      }

      pstmt.close();
      rs.close();
      
      return result;
   }
   

   // 성적 입력 메소드
   int insertScore(String oscode, String crcode) throws SQLException
   {
      int result = 0;
      
      String sql = "INSERT INTO SCORE(SCORE_CD,OPEN_SUB_CD,COUR_REGI_CD,ATTENDANCE,WRITTEN,PRACTICAL)"
            + " VALUES('SC' || TRIM(TO_CHAR(SEQ_SC.NEXTVAL, '00000000')),?,?,?,?,?)";
      
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1, oscode);
      pstmt.setString(2, crcode);
      pstmt.setInt(3, 0);
      pstmt.setInt(4, 0);
      pstmt.setInt(5, 0);

      result = pstmt.executeUpdate();
      
      
      pstmt.close();
      return result;
   }

   // 학생 성적 업데이트
   int updateScore(String crcd, int att, int wri, int pra) throws SQLException
   {
      
      int result=0;
      
      String sql = "UPDATE SCORE"
            + " SET ATTENDANCE = ? , WRITTEN = ? , PRACTICAL = ? "
            + " WHERE COUR_REGI_CD = ?";
   
         
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setInt(1, att);
      pstmt.setInt(2, wri);
      pstmt.setInt(3, pra);
      pstmt.setString(4, crcd);
      
      
      result = pstmt.executeUpdate();
      
      
      pstmt.close();
      return result;

   }
   
   // 성적 존재 여부 체크
   int checkScore(String crcd) throws SQLException
   {
      int result = 0;
      
      String sql = "SELECT COUNT(*) AS COUNT"
            + " FROM SCORE"
            + " WHERE COUR_REGI_CD = ?";
      
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, crcd);
      
      ResultSet rs = pstmt.executeQuery();
      
      
      while(rs.next())
      {
         result = rs.getInt("COUNT");
      }
      
      return result;
   }
   
}


