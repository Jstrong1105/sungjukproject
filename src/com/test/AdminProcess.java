package com.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.main.MenuRender;
import com.util.FunctionUtil;
import com.util.InputHandler;

public class AdminProcess
{
// 주요 속성
	private AdminDAO dao;
	
// 생성자
	public AdminProcess()
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
	private boolean confirmScoreNum(ScoreDTO dto)
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
	
	// 교수 기능 메뉴
	public void profFunc()
	{
		MenuRender<ProfMenu> menu = new MenuRender<>(ProfMenu.values());
		menu.run("교수 수정", "");
	}
	
	private enum ProfMenu implements FunctionUtil
	{
		PROF_PRINT("교수 출력", new AdminProcess() :: printProf),
		PROF_INSERT("교수 입력", new AdminProcess() :: insertProf),
		PROF_UPDATE("교수 수정", new AdminProcess() :: updateProf),
		PROF_DELETE("교수 삭제", new AdminProcess() :: deleteProf)
		;

		ProfMenu(String name,Runnable func)
		{
			this.name = name;
			this.func = func;
		}
		
		private final String name;
		private final Runnable func;
		
		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public void run(String id)
		{
			func.run();
		}
	}
	
	//교수 등록 메소드
	private void insertProf()
	{
		String name = InputHandler.readString(">> 교수 이름을 입력 : ");
		String ssn = InputHandler.readString(">> 주민번호를 입력 : ");
		
		ProfessorDTO dto = new ProfessorDTO();
		dto.setName(name);
		dto.setSsn(ssn);
		
		if(createProfessor(dto) == -1)
		{
			System.out.println(">> 계정 추가 실패");
		}
	}
	
	// 교수 수정 메소드
	private void updateProf()
	{
		String profCd = InputHandler.readString(">> 변경할 교수의 코드 : ");
		String name = InputHandler.readString(">> 변경할 교수의 이름 : ");
		String pw = InputHandler.readString(">> 변경할 교수의 패스워드 : ");
		
		ProfessorDTO dto = new ProfessorDTO();
		dto.setProfCd(profCd);
		dto.setName(name);
		dto.setPw(pw);
		
		if(modifyProfessor(dto) > 0)
		{
			System.out.println(">> 교수 데이터 수정 완료");
		}
	}
	
	// 교수 삭제 메소드
	private void deleteProf()
	{
		String profCd = InputHandler.readString(">> 삭제할 교수의 코드 : ");
		
		if(removeProfessor(profCd) > 0)
		{
			System.out.println(">> 교수 데이터 삭제 완료");
		}
	}
	
	// 교수 출력 메소드 / 밑에 교수 조회에서 받아온 리스트 출력만 하면됨
	public void printProf()
	{
		System.out.println("미완성입니다.");
	}
	
// 교수 기능
	
	// 교수 조회
	private List<ProfessorDetailDTO> getAllProfessor()
	{
		List<ProfessorDetailDTO> result = new ArrayList<ProfessorDetailDTO>();
		
		dao.connect();
		result = dao.selectAllProfessor();
		dao.disconnect();
		
		return result;
	}
	
	// 교수 등록
	private int createProfessor(ProfessorDTO profDto)
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
	private int removeProfessor(String profCd)
	{
		int result = 0;
		
		dao.connect();
		result = dao.deleteProfessor(profCd);
		dao.disconnect();
		
		return result;
	}

	// 학생 기능 메뉴
	public void studentFunc()
	{
		MenuRender<StudentMenu> menu = new MenuRender<>(StudentMenu.values());
		menu.run("학생", "");
	}
	
	private enum StudentMenu implements FunctionUtil
	{
		STU_PRINT("학생 출력",new AdminProcess() :: printStudent),
		STU_INSERT("학생 입력",new AdminProcess() :: insertStudent),
		STU_UPDATE("학생 수정",new AdminProcess() :: updateStudent),
		STU_DELETE("학생 삭제",new AdminProcess() :: deleteStudent)
		;

		StudentMenu(String name,Runnable func)
		{
			this.name = name;
			this.func = func;
		}
		
		private final String name;
		private final Runnable func;
		
		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public void run(String id)
		{
			func.run();
		}
	}
	
	// 학생 등록 메소드
	private void insertStudent()
	{
		String name = InputHandler.readString(">> 학생 이름 입력 : ");
		String ssn = InputHandler.readString(">> 주민번호 입력 : ");
		StudentDTO dto = new StudentDTO();
		dto.setName(name);
		dto.setSsn(ssn);
		
		if(createStudent(dto) > 0)
		{
			System.out.println(">> 학생 입력 완료");
		}
	}
	
	// 학생 수정 메소드
	private void updateStudent()
	{
		String studentCd = InputHandler.readString(">> 수정할 학생의 학번 : ");
		String name = InputHandler.readString(">> 변경할 이름 : ");
		String pw = InputHandler.readString(">> 변경할 비밀번호 : ");
		String ssn = InputHandler.readString(">> 변경할 주민번호 : ");
		
		StudentDTO dto = new StudentDTO();
		dto.setStudentCd(studentCd);
		dto.setName(name);
		dto.setPw(pw);
		dto.setSsn(ssn);
		
		if(modifyStudent(dto) > 0)
		{
			System.out.println(">> 학생 수정 완료");
		}
	}
	
	// 학생 삭제 메소드
	private void deleteStudent()
	{
		String studentCd = InputHandler.readString(">> 삭제할 학생의 학번 : ");
	
		if(removeStudent(studentCd) > 0)
		{
			System.out.println(">> 학생 삭제 완료");
		}
	}
	
	// 학생 조회 메소드 / 밑에 학생 전체 조회에서 받아온 리스트 출력만 하면 됨
	private void printStudent()
	{
		System.out.println("미완성입니다.");
	}
	
// 학생 기능
	
	// 학생 전체 조회
	private List<StudentDetailDTO> getAllStudents()
	{
		List<StudentDetailDTO> result = new ArrayList<StudentDetailDTO>();
		
		dao.connect();
		result = dao.selectAllStudents();
		dao.disconnect();
		
		return result;
	}
	
	// 학생 등록
	private int createStudent(StudentDTO stdtDto)
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
	
	// 학생 수정
	private int modifyStudent(StudentDTO stdtDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.updateStudent(stdtDto);
		dao.disconnect();
		
		return result;
	}

	
	// 학생 삭제
	private int removeStudent(String studentCd)
	{
		int result = 0;
		
		dao.connect();
		result = dao.deleteStudent(studentCd);
		dao.disconnect();
		
		return result;
	}

	// 성적 메뉴
	public void scoreFunc()
	{
		MenuRender<ScoreMenu> menu = new MenuRender<>(ScoreMenu.values());
		menu.run("성적", "");
	}
	
	private enum ScoreMenu implements FunctionUtil
	{
		SCORE_PRINT("성적 출력", new AdminProcess() :: printScore),
		SCORE_INSERT("성적 입력", new AdminProcess() :: insertScore),
		SCORE_UPDATE("성적 수정", new AdminProcess() :: updateScore),
		SCORE_DELETE("성적 삭제", new AdminProcess() :: deleteScore)
		;

		ScoreMenu(String name, Runnable func)
		{
			this.name = name;
			this.func = func;
		}
		
		private final String name;
		private final Runnable func;
		
		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public void run(String id)
		{
			func.run();
		}
	}
	
	// 성적 입력 메소드
	private void insertScore()
	{
		String openSubCd = InputHandler.readString(">> 입력할 개설과목 코드 : ");
		String regiCd = InputHandler.readString(">> 입력할 수강신청 코드 : ");
		int att = InputHandler.readInt(">> 출결 성적 : ",0,100);
		int wri = InputHandler.readInt(">> 필기 성적 : ",0,100);
		int pra = InputHandler.readInt(">> 실기 성적 : ",0,100);
		
		ScoreDTO dto = new ScoreDTO();
		dto.setOpenSubCd(openSubCd);
		dto.setCourRegiCd(regiCd);
		dto.setAttendance(att);
		dto.setWritten(wri);
		dto.setPractical(pra);
		
		if(createScore(dto) > 0)
		{
			System.out.println(">> 성적 입력 완료");
		}
	}
	
	// 성적 수정 메소드
	private void updateScore()
	{
		String scoreCd = InputHandler.readString(">> 수정할 성적 코드 : ");
		int att = InputHandler.readInt(">> 출결 점수 : ",0,100);
		int wri = InputHandler.readInt(">> 필기 점수 : ",0,100);
		int pra = InputHandler.readInt(">> 실기 점수 : ",0,100);
		
		ScoreDTO dto = new ScoreDTO();
		dto.setScoreCd(scoreCd);
		dto.setAttendance(att);
		dto.setWritten(wri);
		dto.setPractical(pra);
		
		if(modifyScore(dto) > 0)
		{
			System.out.println(">> 성적 수정 완료");
		}
	}
	
	// 성적 삭제 메소드
	private void deleteScore()
	{
		String scoreCd = InputHandler.readString(">> 삭제할 성적 코드 : ");
		
		if(removeScore(scoreCd) > 0)
		{
			System.out.println(">> 성적 삭제 완료");
		}
	}
	
	// 성적 조회 메소드 / 밑에 성적 전체 조회 메소드에서 받아온 리스트 출력만 하면 됨
	private void printScore()
	{
		System.out.println("미구현입니다.");
	}

// (관리자) 성적 관리
	
	// 성적 전체 조회
	private List<ScoreDetailDTO> getAllScore()
	{
		List<ScoreDetailDTO> result = new ArrayList<ScoreDetailDTO>();
		
		dao.connect();
		result = dao.selectAllScore();
		dao.disconnect();
		
		return result;
	}
	
	// 성적 입력
	private int createScore(ScoreDTO scoreDto)
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
	
	// 성적 수정
	private int modifyScore(ScoreDTO scoreDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.updateScore(scoreDto);
		dao.disconnect();
		
		return result;
	}
	
	
	// 성적 삭제
	private int removeScore(String scoreCd)
	{
		int result = 0;
	
		dao.connect();
		result = dao.deleteScore(scoreCd);
		dao.disconnect();
		
		return result;
	}
	
	// 수강신청 메뉴
	public void regiFunc()
	{
		MenuRender<RegiMenu> menu = new MenuRender<>(RegiMenu.values());
		menu.run("수강신청", "");
	}
	
	private enum RegiMenu implements FunctionUtil
	{
		REGI_PRINT("수강신청 출력",new AdminProcess() :: printRegi),
		REGI_INSERT("수강신청 입력",new AdminProcess() :: insertRegi),
		REGI_UPDATE("수강신청 수정",new AdminProcess() :: updateRegi),
		REGI_DELETE("수강신청 삭제",new AdminProcess() :: deleteRegi)
		;

		RegiMenu(String name,Runnable func)
		{
			this.name = name;
			this.func = func;
		}
		
		private final String name;
		private final Runnable func;
		
		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public void run(String id)
		{
			func.run();
		}
	}
	
	// 수강 신청 추가 메소드
	private void insertRegi()
	{
		String openCourCd = InputHandler.readString(">> 개설과정 코드 : ");
		String studentCd = InputHandler.readString(">> 학생 코드 : ");
		
		CourseRegistrationDTO dto = new CourseRegistrationDTO();
		dto.setOpenCourCd(openCourCd);
		dto.setStudentCd(studentCd);
		
		if(createCourRegi(dto) > 0)
		{
			System.out.println(">> 수강 신청 완료");
		}
	}
	
	// 수강 신청 수정 메소드
	private void updateRegi()
	{
		String courRegiCd = InputHandler.readString(">> 수정할 수강신청 코드 : ");
		String openCourseCd = InputHandler.readString(">> 수정할 개설과정 코드 : ");
		
		CourseRegistrationDTO dto = new CourseRegistrationDTO();
		dto.setCourRegiCd(courRegiCd);
		dto.setOpenCourCd(openCourseCd);
		
		if(modifyCourRegi(dto) > 0)
		{
			System.out.println(">> 수강신청 수정 완료");
		}
	}
	
	// 수강 신청 삭제 메소드
	private void deleteRegi()
	{
		String courRegiCd = InputHandler.readString(">> 삭제할 수강신청 코드 : ");
		
		if(removeCourRegi(courRegiCd) > 0)
		{
			System.out.println(">> 수강신청 삭제 완료");
		}
	}
	
	// 수강 신청 조회 메소드 / 밑에 수강 신청 전체 조회 메소드에서 받아온 리스트 출력만 하면 됨
	private void printRegi()
	{
		System.out.println("미구현입니다");
	}
	
// (관리자) 수강 신청 관리
	
	// 수강 신청 전체 조회
	private List<CourseRegistrationDetailDTO> getAllCourRegi()
	{
		List<CourseRegistrationDetailDTO> result = new ArrayList<CourseRegistrationDetailDTO>();
		
		dao.connect();
		result = dao.selectAllCourRegi();
		dao.disconnect();
		
		return result;
	}
	
	// 수강 신청
	private int createCourRegi(CourseRegistrationDTO courRegiDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.insertCourRegi(courRegiDto);
		dao.disconnect();
		
		return result;
	}
	
	// 수강 신청 수정
	private int modifyCourRegi(CourseRegistrationDTO courRegiDto)
	{
		int result = 0;
		
		dao.connect();
		result = dao.updateCourRegi(courRegiDto);
		dao.disconnect();
		
		return result;
	}
	
	
	// 수강 신청 삭제
	private int removeCourRegi(String courRegiCd)
	{
		int result = 0;
		
		dao.connect();
		result = dao.deleteCourRegi(courRegiCd);
		dao.disconnect();
		
		return result;
	}
	
} // class AdminProcess END