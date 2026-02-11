package com.student;

import java.util.List;

import com.util.InputHandler;

class StudentProcess
{
	StudentProcess()
	{
		dao = new StudentDAO();
	}
	
	private StudentDAO dao;
	
	// 성적 가져오기
	void getRecord(String sid)
	{
		List<StudentDTO> list = dao.getRecord(sid);
		
		System.out.println("========================================================================================");
		System.out.println("이름      과정명   과목명  시작일     종료일         교재명    출결 실기 필기 총점 등수");
		
		for(StudentDTO dto : list)
		{
			System.out.printf("%3s  %5s  %5s  %10s  %10s  %5s %3d  %3d  %3d  %3d  %3d"
			,dto.getName(),dto.getCourseName(),dto.getSubName(),dto.getStartDate(),dto.getEndDate(),dto.getBookName()
			,dto.getAttendance(),dto.getPractical(),dto.getWritten(),dto.getTotal(),dto.getRanking());
		}
		System.out.println("\n========================================================================================");
		
		InputHandler.readString("");
	}
	
	// 비밀번호 수정하기
	void setPassword(String sid)
	{
		
	}
}
