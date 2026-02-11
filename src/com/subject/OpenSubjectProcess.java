package com.subject;

import java.util.ArrayList;
import java.util.Scanner;

import com.jsb.test.OpenCourseDTO;
import com.jsb.test.TempProcess;

public class OpenSubjectProcess
{
	private OpenSubjectDAO dao;
	
	public OpenSubjectProcess()
	{
		dao = new OpenSubjectDAO();
	}
	
	// 개설 과목 출력
	public boolean printOpenSubList(ArrayList<OpenSubjectDTO> osList)
	{
		if (osList.size() > 0)
		{
			for (OpenSubjectDTO os : osList)
			{
				String str = String.format("개설과목코드: %s | ", os.getOpenSubCD());
				str += String.format("과정명: %s | ", os.getCourName());
				str += String.format("강의실명: %s | ", os.getClassroomName());
				str += String.format("과목명: %s | ", os.getSubName());
				str += String.format("과목시작일: %s | ", os.getStartDT());
				str += String.format("과목종료일: %s | ", os.getEndDT());
				str += String.format("교재명: %s | ", os.getTextbookName());
				str += String.format("교수명: %s", os.getProfName());
				System.out.println(str);
			}
			return true;
		}
		return false;
	}
	
	// 개설 과목 출력
	public void printOpenSubject()
	{
		try
		{
			dao.connection();
			
			ArrayList<OpenSubjectDTO> osList = dao.selectOpenSubject();
			
			if (!printOpenSubList(osList)) {
				System.out.println("개설 과목이 없습니다.");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	// 개설 과목 입력
	public void inputOpenSubject()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			
			OpenSubjectDTO dto = new OpenSubjectDTO();
			
			// FIXME 개설과정 가져오기
			TempProcess tempProcess = new TempProcess();
			ArrayList<OpenCourseDTO> ocList = tempProcess.getOpenCourse();
			tempProcess.printOpenCourse(ocList);
			
			System.out.print("\n개설과정코드 입력 : ");
			String openCourCD = sc.nextLine();
			
			new SubjectProcess().printSubject();
			System.out.print("과정명 : ");
			String subName = sc.nextLine();
			
			System.out.print("시작일 : ");
			String startDT = sc.nextLine();
			System.out.print("종료일 : ");
			String endDT = sc.nextLine();
			
			// 교재목록
			new TextbookProcess().printTextbook();
			System.out.print("교재명 : ");
			String textbookName = sc.nextLine();
			
			// FIXME 교수목록
			tempProcess.printProfessor();
			
			System.out.print("교수코드 : ");
			String profCd = sc.nextLine();
			
			dto.setOpenCourCD(openCourCD);
			dto.setSubName(subName);
			dto.setStartDT(startDT);
			dto.setEndDT(endDT);
			dto.setTextbookName(textbookName);
			dto.setProfCD(profCd);

			dao.connection();
			
			int result = dao.insertOpenSubject(dto);
			
			if (result > 0)
			{
				System.out.println(">> 개설 과목 등록 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	// 개설과목코드 선택
	public String getSelectOpenSubCD() {
		String openSubCD = null;
		
		try
		{
			Scanner sc = new Scanner(System.in);

			OpenSubjectDTO dto = new OpenSubjectDTO();
			
			dao.connection();
			
			do
			{
				System.out.print("개설과목코드 입력 : ");
				openSubCD = sc.next();

				ArrayList<OpenSubjectDTO> osList = dao.selectOpenSubject(openSubCD);
							
				if (printOpenSubList(osList)) break;
				else System.out.println("존재하지 않는 개설과목코드입니다. 다시 입력해 주세요.");
				
			} while (true);
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return openSubCD;
	}
	
	// 개설과목의 개설과정 변경
	public void modifyOpenSubCourse()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			
			OpenSubjectDTO dto = new OpenSubjectDTO();

			String openSubCD = getSelectOpenSubCD();
			
			// FIXME 개설과정 가져오기
			TempProcess tempProcess = new TempProcess();
			ArrayList<OpenCourseDTO> ocList = tempProcess.getOpenCourse();
			tempProcess.printOpenCourse(ocList);
			
			System.out.print("\n변경될 개설과정코드 입력 : ");
			String openCourCD = sc.next();
			
			dto.setOpenSubCD(openSubCD);
			dto.setOpenCourCD(openCourCD);

			dao.connection();
			int result = dao.updateOpenSubCourse(dto);
			
			if (result > 0)
			{
				System.out.println(">> 개설과목의 개설과정 수정 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 개설과목의 시작날짜/종료날짜 변경
	public void modifyOpenSubDate()
	{
		try
		{
			Scanner sc = new Scanner(System.in);

			OpenSubjectDTO dto = new OpenSubjectDTO();
			
			String openSubCD = getSelectOpenSubCD();
			
			System.out.print("시작일 : ");
			String startDT = sc.next();
			
			System.out.print("종료일 : ");
			String endDT = sc.next();
			
			dto.setOpenSubCD(openSubCD);
			dto.setStartDT(startDT);
			dto.setEndDT(endDT);

			dao.connection();
			
			int result = dao.updateOpenSubDate(dto);
			
			if (result > 0)
			{
				System.out.println(">> 개설과목의 시작날짜/종료날짜 수정 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 개설과목의 교수 수정
	public void modifyOpenSubProf()
	{
		try
		{
			Scanner sc = new Scanner(System.in);

			OpenSubjectDTO dto = new OpenSubjectDTO();
			
			String openSubCD = getSelectOpenSubCD();

			// FIXME 교수 가져오기
			new TempProcess().printProfessor();
			
			System.out.print("교수코드 : ");
			String profCD = sc.next();
			
			dto.setOpenSubCD(openSubCD);
			dto.setProfCD(profCD);

			dao.connection();
			
			int result = dao.updateOpenSubProf(dto);
			
			if (result > 0)
			{
				System.out.println(">> 개설과목의 교수 수정 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 개설과목의 과목 수정
	public void modifyOpenSubSubject()
	{
		try
		{
			Scanner sc = new Scanner(System.in);

			OpenSubjectDTO dto = new OpenSubjectDTO();
			
			String openSubCD = getSelectOpenSubCD();

			new SubjectProcess().printSubject();
			System.out.print("과정명 : ");
			String subName = sc.nextLine();
			
			dto.setOpenSubCD(openSubCD);
			dto.setSubName(subName);

			dao.connection();
			
			int result = dao.updateOpenSubSubject(dto);
			
			if (result > 0)
			{
				System.out.println(">> 개설과목의 과목 수정 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 개설과목의 교재 수정
	public void modifyOpenSubTextbook()
	{
		try
		{
			Scanner sc = new Scanner(System.in);

			OpenSubjectDTO dto = new OpenSubjectDTO();
			
			String openSubCD = getSelectOpenSubCD();

			new TextbookProcess().printTextbook();
			System.out.print("교재명 : ");
			String tbName = sc.nextLine();
			
			dto.setOpenSubCD(openSubCD);
			dto.setTextbookName(tbName);

			dao.connection();
			
			int result = dao.updateOpenSubTextbook(dto);
			
			if (result > 0)
			{
				System.out.println(">> 개설과목의 교재 수정 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 개설과목 삭제
	public void removeOpenSubject()
	{
		try
		{
			Scanner sc = new Scanner(System.in);

			String openSubCD = getSelectOpenSubCD();

			System.out.print("정말 삭제하시겠습니까?(Y/N) : ");
			String delYN = sc.nextLine();

			int result = 0;

			dao.connection();
			
			if (delYN.equals("Y") || delYN.equals("y"))
			{
				result = dao.deleteOpenSubject(openSubCD);
			}
			
			if (result > 0)
			{
				System.out.println(">> 개설과목 삭제 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

}
