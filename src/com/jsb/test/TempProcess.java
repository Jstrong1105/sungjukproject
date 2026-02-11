package com.jsb.test;

import java.util.ArrayList;

public class TempProcess
{
	private TempDAO tempDao;
	
	public TempProcess()
	{
		tempDao = new TempDAO();
	}


	// FIXME ============= 임시 ===============
	// 개설 과정 가져오기
	public ArrayList<OpenCourseDTO> getOpenCourse()
	{
		ArrayList<OpenCourseDTO> ocList = new ArrayList<OpenCourseDTO>();
		
		try
		{
			tempDao.connection();
			
			ocList = tempDao.selectOpenCourse();
			
			tempDao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return ocList;
	}
	
	// 개설 과목 등록, 개설과목의 개설과정 변경에 사용될 예정
	public void printOpenCourse(ArrayList<OpenCourseDTO> ocList)
	{
		if (ocList.size() > 0)
		{
			System.out.println("=== 개설 과정 목록 ===");

			for (OpenCourseDTO oc : ocList)
			{
				String str = String.format("개설과정코드: %s | ", oc.getOpenCourCD());
				str += String.format("과정명: %s | ", oc.getCourName());
				str += String.format("과정시작일: %s | ", oc.getStartDT());
				str += String.format("과정종료일: %s | ", oc.getEndDT());
				str += String.format("과정생성일: %s | ", oc.getCreateDT());
				str += String.format("강의실명: %s", oc.getClassroomName());
				System.out.println(str);
			}

		} else
		{
			System.out.println("개설과정이 없습니다.");
		}
	}

	// 교수 조회
	public void printProfessor()
	{
		tempDao = new TempDAO();
		
		try
		{
			tempDao.connection();
			
			ArrayList<ProfessorDTO> prList = tempDao.selectProfessor();
			
			if (prList.size() > 0)
			{
				System.out.println("=== 교수 목록 ===");
				
				for (ProfessorDTO pr : prList)
				{
					String str = String.format("교수코드 : %s |", pr.getProfCD());
					str += String.format("교수명 : %s | ", pr.getName());
					str += String.format("생성일 : %s", pr.getCreateDT());
					System.out.println(str);
				}
			} else
			{
				System.out.println("교수가 없습니다.");
			}
			
			tempDao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
	}
	//============= 임시 ===============
	
}
