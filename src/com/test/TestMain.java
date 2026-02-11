package com.test;

import java.util.List;

//기능 테스트용 임시 클래스
public class TestMain
{
	public static void main(String[] args)
	{
		//AdminProcess admProc = new AdminProcess();
		
	// 교수 등록
		//ProfessorDTO profDto = new ProfessorDTO();
		//profDto.setName("jdbc 테스트");
		//profDto.setSsn("001201-1234567");
		
		// 잘못된 주민번호 삽입 후 시도
		// profDto.setSsn("000101-12345678");	// 뒷자리 8자리
		// profDto.setSsn("000101-123456 7");		// 공백 포함

		//int result = admProc.createProfessor(profDto);
		//System.out.println(result);
		
	// 교수 조회
		/*
		for (ProfessorDetailDTO dto : admProc.getAllProfessor())
		{
			System.out.printf("%s  %s  %s  %s  %s  %s  %s\n"
			          , dto.getProfName(), dto.getSubName(), dto.getStartDt(), dto.getEndDt()
			          , dto.getTextbookName(), dto.getClassroomName(), dto.getSubState());
		}
		*/
		
	// 교수 수정
		/*
		ProfessorDTO dto = new ProfessorDTO();
		dto.setProfCd("PR00000018");
		dto.setPw("New Password");
		dto.setName("JDBC 교수");
		
		int result = admProc.modifyProfessor(dto);
		System.out.println(result);
		*/
		
	// 교수 삭제
		/*
		int result = admProc.removeProfessor("PR00000018");
		System.out.println(result);
		*/

	// 학생 등록
		/*
		StudentDTO dto = new StudentDTO();
		dto.setName("JDBC 테스트");
		dto.setSsn("000809-2345678");
		
		// 잘못된 주민번호 삽입 후 시도
		// dto.setSsn("000101-12345678");	// 뒷자리 8자리
		// dto.setSsn("000101-123456 7");		// 공백 포함
		
		int result = admProc.createStudent(dto);
		System.out.println(result);
		*/
		
	// 학생 전체 조회
		/*
		for (StudentDetailDTO dto : admProc.getAllStudents())
		{
			System.out.printf("%s  %s  %s  %s  %s\n"
			          ,dto.getName(), dto.getCourseName(), dto.getSubjectName(), dto.getTotScore(), dto.getSubjectState()
			         );
		}
		*/
		
	
	// 학생 수정
		/*
		StudentDTO dto = new StudentDTO();
		dto.setStudentCd("ST00000022");
		dto.setName("JDBC 학생");
		dto.setPw("Change Password");
		
		int result = admProc.modifyStudent(dto);
		System.out.println(result);
		*/
		
	// 학생 삭제
		/*
		int result = admProc.removeStudent("ST00000022");
		System.out.println(result);
		*/
		
		
	// 성적 입력
		/*
		ScoreDTO dto = new ScoreDTO();
		dto.setOpenSubCd("TESTOPSUBJ");
		dto.setCourRegiCd("TESTCOURRE");
		dto.setAttendance(11);
		dto.setWritten(22);
		dto.setPractical(33);
		
		int result = admProc.createScore(dto);
		System.out.println(result);
		*/
		
		
	// 성적 전체 조회
		/*
		for (ScoreDetailDTO dto : admProc.getAllScore())
		{
			System.out.printf("%s  %s  %s  %s  %s  %s  %s  %s\n"
			          , dto.getCourName(), dto.getSubName(), dto.getSubStartDt(), dto.getSubEndDt(), dto.getStudentName()
			          , dto.getAttendanceApplyPct(), dto.getWrittenApplyPct(), dto.getPracticalApplyPct()
			         );
		}
		*/
		
		
		
	// 성적 수정
		/*
		ScoreDTO dto = new ScoreDTO();
		dto.setScoreCd("SC00000034");
		dto.setAttendance(50);
		dto.setWritten(60);
		dto.setPractical(70);
		
		int result = admProc.modifyScore(dto);
		
		System.out.println(result);
		*/
		
	// 성적 삭제
		/*
		String scoreCd = "SC00000024";
		
		int result = admProc.removeScore(scoreCd);
		
		System.out.println(result);
		*/
		
		
	// 수강 신청
		/*
		CourseRegistrationDTO dto = new CourseRegistrationDTO();

		dto.setOpenCourCd("OC00000020");
		dto.setStudentCd("ST00000001");	 // 25번 까지 가능
		
		int result = admProc.createCourRegi(dto);
		System.out.println(result);
		*/
		
		
	// 수강 신청 전체 조회
		/*
		for (CourseRegistrationDetailDTO dto : admProc.getAllCourRegi())
		{
			System.out.printf("%s  %s  %s\n"
			          , dto.getCourName(), dto.getStudentName(), dto.getCourRegiDt()
			         );
		}
		*/
	
		
	// 수강 신청 수정
		/*
		CourseRegistrationDTO dto = new CourseRegistrationDTO();
		
		dto.setCourRegiCd("RG00000025");
		dto.setOpenCourCd("OC00000012");
		
		int result = admProc.modifyCourRegi(dto);
		System.out.println(result);
		*/
		
		
		
	// 수강 신청 삭제
		/*
		String courRegiCd = "RG00000025";
		int result = admProc.removeCourRegi(courRegiCd);
		System.out.println(result);
		*/
		 
		
	} // main() END
}
