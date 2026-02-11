package com.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.FunctionUtil;

public class AdminDAO
{
// 주요 속성
	private Connection conn;

// DB Connection 관련 메소드
	// DB 연결
	public void connect() throws RuntimeException
	{
		if (conn == null)
		{
			conn = DBConn.getConnection();
		}
	}
	
	// DB 연결 해제
	public void disconnect() throws RuntimeException
	{
		DBConn.close();
		
		conn = null;
	}
	
	
// 교수 CRUD	
	// 교수 등록
	public int insertProfessor(ProfessorDTO profDto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_PROF_INSERT(?, ?)}";
		// PRC_ADMIN_PROF_INSERT( P_NAME, P_SSN )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, profDto.getName());
			cstmt.setString(2, profDto.getSsn());
		
			result = cstmt.executeUpdate();
		}

		return result;
	} // insertProfessor() END
	
	
	
	// 교수 조회
	public List<ProfessorDetailDTO> selectAllProfessor() 
	{
		List<ProfessorDetailDTO> result = new ArrayList<ProfessorDetailDTO>();
		
		// String sql = "SELECT * FROM VIEW_ADMIN_PROF_INFO";
		String sql = "SELECT *"
				     + " FROM VIEW_ADMIN_PROF_INFO"
				     // + " ORDER BY 3 DESC, 4 DESC"
				     ;

		try (PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery())
		{
			while (rs.next())
			{
				ProfessorDetailDTO vo = new ProfessorDetailDTO();
				
				vo.setProfName(rs.getString("PROF_NAME"));
				vo.setSubName(rs.getString("SUB_NAME"));
				vo.setStartDt(rs.getString("START_DT"));
				vo.setEndDt(rs.getString("END_DT"));
				vo.setTextbookName(rs.getString("TEXTBOOK_NAME"));
				vo.setClassroomName(rs.getString("CLASSROOM_NAME"));
				vo.setSubState(rs.getString("SUB_STATE"));
				
				result.add(vo);
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException("조회 실패... 잠시 후 재시도 하세요...", e);
		}
		
		return result;
	}
	
	
	
	// 교수 정보 수정
	public int updateProfessor(ProfessorDTO profDto)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_PROF_UPDATE(?, ?, ?)}";
		// PRC_ADMIN_PROF_UPDATE (P_PROF_CD, P_NAME, P_PW)
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, profDto.getProfCd());
			cstmt.setString(2, profDto.getName());
			cstmt.setString(3, profDto.getPw());
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// 교수 정보 삭제
	public int deleteProfessor(String profCd)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_PROF_DELETE(?)}";
		// PRC_ADMIN_PROF_DELETE( P_PROF_CD )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, profCd);
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}


// 학생 CRUD
	// 학생 등록
	public int insertStudent(StudentDTO stdtDto)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_STUDENT_INSERT( ?, ? )}";
		// PRC_ADMIN_STUDENT_INSERT( P_NAME, P_SSN )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, stdtDto.getName());
			cstmt.setString(2, stdtDto.getSsn());
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}

	
	// 학생 전체 출력
	public List<StudentDetailDTO> selectAllStudents()
	{
		List<StudentDetailDTO> result = new ArrayList<StudentDetailDTO>();
		
		String sql = "SELECT *"
					 + " FROM VIEW_ADMIN_STUDENT_INFO"
					 // + " ORDER BY 1, 2, 3"
					 ;
		
		try ( PreparedStatement pstmt = conn.prepareStatement(sql);
			  ResultSet rs = pstmt.executeQuery() )
		{
			while (rs.next())
			{
				String name = rs.getString("학생명");
				String courseName = rs.getString("과정명");
				String subjectName = rs.getString("과목명");
				String totScore = rs.getString("과목 총점");
				String subjectState = rs.getString("수강 상태");
				
				StudentDetailDTO stdtVo = new StudentDetailDTO(name, courseName, subjectName, totScore, subjectState);
				
				result.add(stdtVo);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// 학생 수정
	public int updateStudent(StudentDTO stdtDto)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_STUDENT_UPDATE( ?, ?, ?, ? )}";
		// PRC_ADMIN_STUDENT_UPDATE( P_STUDENT_CD, P_NAME, P_PW, P_SSN )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, stdtDto.getStudentCd());
			cstmt.setString(2, stdtDto.getName());
			cstmt.setString(3, stdtDto.getPw());
			cstmt.setString(4, stdtDto.getSsn());
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	// 학생 삭제
	public int deleteStudent(String studentCd)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_STUDENT_DELETE( ? )}";
		// PRC_ADMIN_STUDENT_DELETE ( P_STUDENT_CD )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, studentCd);
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	
// (관리자) 성적 관리
	// 성적 입력
	public int insertScore(ScoreDTO scoreDto)
	{
		int result = 0;
		
		String sql = "INSERT INTO SCORE(SCORE_CD, OPEN_SUB_CD, COUR_REGI_CD, ATTENDANCE, WRITTEN, PRACTICAL, SCORE_CREATE_DT)"
					 + " VALUES('SC' || TO_CHAR(SEQ_SC.NEXTVAL, 'FM00000000'), ?, ?, ?, ?, ?, SYSDATE)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1, scoreDto.getOpenSubCd());
			pstmt.setString(2, scoreDto.getCourRegiCd());
			pstmt.setInt(3, scoreDto.getAttendance());
			pstmt.setInt(4, scoreDto.getWritten());
			pstmt.setInt(5, scoreDto.getPractical());
			
			result = pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	
	// 성적 전체 조회
	public List<ScoreDetailDTO> selectAllScore()
	{
		List<ScoreDetailDTO> result = new ArrayList<ScoreDetailDTO>();
		
		String sql = "SELECT *"
				     + " FROM VIEW_ADMIN_SCORE_PRINT"
				     + " ORDER BY COUR_NAME ASC, SUB_NAME ASC, STUDENT_NAME ASC";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery())
		{
			while (rs.next())
			{
				ScoreDetailDTO vo = new ScoreDetailDTO();
				
				vo.setCourName(rs.getString("COUR_NAME"));
				vo.setSubName(rs.getString("SUB_NAME"));
				vo.setSubStartDt(rs.getString("SUB_START_DT"));
				vo.setSubEndDt(rs.getString("SUB_END_DT"));
				vo.setStudentName(rs.getString("STUDENT_NAME"));
				vo.setAttendanceApplyPct(rs.getInt("ATTENDANCE_APPLY_PCT"));
				vo.setWrittenApplyPct(rs.getInt("WRITTEN_APPLY_PCT"));
				vo.setPracticalApplyPct(rs.getInt("PRACTICAL_APPLY_PCT"));
				
				result.add(vo);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	 
	
	// 성적 수정
	public int updateScore(ScoreDTO scoreDto)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_SCORE_UPDATE ( ?, ?, ?, ? )}";
		// PRC_ADMIN_SCORE_UPDATE ( P_SCORE_CD, P_ATTENDANCE, P_WRITTEN, P_PRACTICAL )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, scoreDto.getScoreCd());
			cstmt.setInt(2, scoreDto.getAttendance());
			cstmt.setInt(3, scoreDto.getWritten());
			cstmt.setInt(4, scoreDto.getPractical());
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// 성적 삭제
	public int deleteScore(String scoreCd)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_SCORE_DELETE( ? )}";
		// PRC_ADMIN_SCORE_DELETE( ? )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, scoreCd);
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		return result;
	}
	
	
	
// (관리자) 수강 신청 관리
	// 수강 신청 입력
	public int insertCourRegi(CourseRegistrationDTO courRegiDto)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_COURSE_REGI_INSERT ( ?, ? )}";
		// PRC_ADMIN_COURSE_REGI_INSERT ( P_OPEN_COUR_CD, P_STUDENT_CD )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, courRegiDto.getOpenCourCd());
			cstmt.setString(2, courRegiDto.getStudentCd());
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	
	// 수강 신청 내역 조회
	public List<CourseRegistrationDetailDTO> selectAllCourRegi()
	{
		List<CourseRegistrationDetailDTO> result = new ArrayList<CourseRegistrationDetailDTO>();
		
		String sql = "SELECT RG.COUR_REGI_CD"
				             + ", OC.OPEN_COUR_CD"
				             + ", CR.COUR_NAME"
				             + ", ST.STUDENT_CD"
				             + ", ST.NAME AS STUDENT_NAME"
				             + ", TO_CHAR(RG.COUR_REGI_DT, 'YYYY-MM-DD') AS COUR_REGI_DT" 
				   + " FROM COURSE_REGISTRATION RG"
				            + " JOIN OPEN_COURSE OC"
				            + " ON RG.OPEN_COUR_CD = OC.OPEN_COUR_CD"
				            + " JOIN COURSE CR"
				            + " ON OC.COUR_CD = CR.COUR_CD"
				            + " JOIN STUDENTS ST"
				            + " ON RG.STUDENT_CD = ST.STUDENT_CD"
				   // + " ORDER BY RG.COUR_REGI_DT DESC, CR.COUR_NAME ASC, ST.NAME ASC"
				  ;

		try (PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery())
		{
			while (rs.next())
			{
				CourseRegistrationDetailDTO dto = new CourseRegistrationDetailDTO();
				
				dto.setCourRegiCd(rs.getString("COUR_REGI_CD"));
				dto.setOpenCourCd(rs.getString("OPEN_COUR_CD"));
				dto.setCourName(rs.getString("COUR_NAME"));
				dto.setStudentCd(rs.getString("STUDENT_CD"));
				dto.setStudentName(rs.getString("STUDENT_NAME"));
				dto.setCourRegiDt(rs.getString("COUR_REGI_DT"));
				
				result.add(dto);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// 수강 신청 수정
	public int updateCourRegi(CourseRegistrationDTO courRegiDto)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_COURSE_REGI_UPDATE( ?, ? )}";
		// PRC_ADMIN_COURSE_REGI_UPDATE( P_COUR_REGI_CD, P_OPEN_COUR_CD )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, courRegiDto.getCourRegiCd());
			cstmt.setString(2, courRegiDto.getOpenCourCd());
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	// 수강 신청 삭제
	public int deleteCourRegi(String courRegiCd)
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_COURSE_REGI_DELETE( ? )}";
		// PRC_ADMIN_COURSE_REGI_DELETE( ? )
		
		try (CallableStatement cstmt = conn.prepareCall(sql))
		{
			cstmt.setString(1, courRegiCd);
			
			result = cstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
} // class AdminDAO END