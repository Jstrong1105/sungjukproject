package com.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminProcess
{
// 주요 속성
	private AdminDAO dao;
	
// 생성자
	AdminProcess()
	{
		dao = new AdminDAO();
	}
	
	
// 어드민 기능 내에서 공용 사용하는 메소드
	// 주민등록번호 유효성 검증
	private boolean confirmSSN(String ssn)
	{		
		// 주민등록번호를 순수 숫자형으로 변형
		ssn = ssn.replaceAll("-", "");
		
		
		// 주민등록번호 자릿수 확인
		if (ssn.length() != 13)
		{
			return false;
		}
		
		// 주민등록번호에 문자가 섞여있는지 확인
		char[] ssnCharArr = ssn.toCharArray();
		
		for (char a : ssnCharArr)
		{
			// 숫자 여부 + 공백 여부 확인
			if(!Character.isDigit(a) || Character.isWhitespace(a))
			{
				return false;
			}
		}
				
		return true;
	}
	
	// 성적의 숫자 범위 검증 (0~100)
	public boolean confirmScoreNum(ScoreDTO dto)
	{
		List<Integer> scoreList = new ArrayList<Integer>();
		
		scoreList.add(dto.getAttendance());
		scoreList.add(dto.getWritten());
		scoreList.add(dto.getPractical());
		
		for (int score : scoreList)
		{
			if (score < 0 || score > 100)
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	
	
// 교수 기능
	// 교수 등록
	public int createProfessor(ProfessorDTO profDto)
	{
		int result = 0;
		
		// 주민등록번호 유효성 체크
		if (!confirmSSN(profDto.getSsn()))
		{
			return -1;
		}
		
		// 주민등록번호를 순수 숫자형으로 변형
		profDto.setSsn( profDto.getSsn().replaceAll("-", "") );
		
		
		// 등록시, 초기 비밀번호를 주민등록번호 뒷자리로 설정
		profDto.setPw( profDto.getSsn().substring(7) );
		
		
		
		// 프로시저 검증 로직 → JAVA 코드로 처리하도록 변경
		try
		{
			dao.connect();
			result = dao.insertProfessor(profDto);
			
			if (result > 0)
			{
				System.out.println("교수 계정이 생성되었습니다.");		
			}
			
			dao.disconnect();
		}
		catch (SQLException  e)
		{
			int errorCode = e.getErrorCode();
			
			if (errorCode == 20003)
			{
				System.out.println("이미 존재하는 주민등록번호입니다.");
				return -1;
			}
		}
		
		return result;
	} // createProfessor() END
	
	
	// 교수 조회
	public List<ProfessorDetailDTO> getAllProfessor()
	{
		List<ProfessorDetailDTO> result = new ArrayList<ProfessorDetailDTO>();
		
		dao.connect();
		result = dao.selectAllProfessor();
		dao.disconnect();
		
		return result;
	}
	
	
	// 교수 수정
	public int modifyProfessor(ProfessorDTO profDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.updateProfessor(profDto);
		dao.disconnect();
		
		return result;
	}
	
	
	// 교수 삭제
	public int removeProfessor(String profCd)
	{
		int result = 0;
		
		dao.connect();
		result = dao.deleteProfessor(profCd);
		dao.disconnect();
		
		return result;
	}


// 학생 기능
	// 학생 등록
	public int createStudent(StudentDTO stdtDto)
	{
		int result;
		
		// 주민등록번호 유효성 체크
		if (!confirmSSN(stdtDto.getSsn()))
		{
			return -1;
		}

		// 주민등록번호를 순수 숫자형으로 변형
		stdtDto.setSsn( stdtDto.getSsn().replaceAll("-", "") );
		
		// 등록시, 초기 비밀번호를 주민등록번호 뒷자리로 설정
		stdtDto.setPw( stdtDto.getSsn().substring(7) );
		
		
		// 등록 진행
		dao.connect();
		result = dao.insertStudent(stdtDto);
		dao.disconnect();
		
		return result;
	}
	
	
	// 학생 전체 조회
	public List<StudentDetailDTO> getAllStudents()
	{
		List<StudentDetailDTO> result = new ArrayList<StudentDetailDTO>();
		
		dao.connect();
		result = dao.selectAllStudents();
		dao.disconnect();
		
		return result;
	}
	
	
	// 학생 수정
	public int modifyStudent(StudentDTO stdtDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.updateStudent(stdtDto);
		dao.disconnect();
		
		return result;
	}

	
	// 학생 삭제
	public int removeStudent(String studentCd)
	{
		int result = 0;
		
		dao.connect();
		result = dao.deleteStudent(studentCd);
		dao.disconnect();
		
		return result;
	}

	
// (관리자) 성적 관리
	// 성적 입력
	public int createScore(ScoreDTO scoreDto)
	{
		int result = 0;
		
		if (!confirmScoreNum(scoreDto))
		{
			return -1;
		}
		
		dao.connect();
		result = dao.insertScore(scoreDto);
		dao.disconnect();

		return result;
	}
	
	
	// 성적 전체 조회
	public List<ScoreDetailDTO> getAllScore()
	{
		List<ScoreDetailDTO> result = new ArrayList<ScoreDetailDTO>();
		
		dao.connect();
		result = dao.selectAllScore();
		dao.disconnect();
		
		return result;
	}
	
	
	// 성적 수정
	public int modifyScore(ScoreDTO scoreDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.updateScore(scoreDto);
		dao.disconnect();
		
		return result;
	}
	
	
	// 성적 삭제
	public int removeScore(String scoreCd)
	{
		int result = 0;
	
		dao.connect();
		result = dao.deleteScore(scoreCd);
		dao.disconnect();
		
		return result;
	}
	
	
	
// (관리자) 수강 신청 관리
	// 수강 신청
	public int createCourRegi(CourseRegistrationDTO courRegiDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.insertCourRegi(courRegiDto);
		dao.disconnect();
		
		return result;
	}
	
	// 수강 신청 전체 조회
	public List<CourseRegistrationDetailDTO> getAllCourRegi()
	{
		List<CourseRegistrationDetailDTO> result = new ArrayList<CourseRegistrationDetailDTO>();
		
		dao.connect();
		result = dao.selectAllCourRegi();
		dao.disconnect();
		
		return result;
	}
	
	
	// 수강 신청 수정
	public int modifyCourRegi(CourseRegistrationDTO courRegiDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.updateCourRegi(courRegiDto);
		dao.disconnect();
		
		return result;
	}
	
	
	// 수강 신청 삭제
	public int removeCourRegi(String courRegiCd)
	{
		int result = 0;
		
		dao.connect();
		result = dao.deleteCourRegi(courRegiCd);
		dao.disconnect();
		
		return result;
	}
	
} // class AdminProcess END