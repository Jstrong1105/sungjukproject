package com.jsb.test;

import java.util.Scanner;

import com.subject.OpenSubjectProcess;
import com.subject.SubjectProcess;
import com.subject.TextbookProcess;

public class SubjectManagement
{
	// 과목 관리
	public void runSubjectMgmt()
	{
		SubjectProcess sp = new SubjectProcess();
		Scanner sc = new Scanner(System.in);

		do
		{
			System.out.println("\n==== 과목 관리 ====");
			System.out.println("1. 과목 등록");
			System.out.println("2. 과목 조회");
			System.out.println("3. 과목 수정");
			System.out.println("4. 과목 삭제");
			System.out.print("메뉴 선택(1~4, -1종료) : ");

			String menus = sc.next();

			try
			{
				int menu = Integer.parseInt(menus);

				if (menu == -1)
				{
					System.out.println(">> 과목 관리 종료");
					break;
				}

				switch (menu)
				{
				case 1:
					sp.inputSubject();
					break;
				case 2:
					sp.printSubject();
					break;
				case 3:
					sp.modifySubject();
					break;
				case 4:
					sp.removeSubject();
					break;

				}

			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		} while (true);

	}

	// 교재 관리
	public void runTextbookMgmt()
	{
		TextbookProcess tp = new TextbookProcess();
		Scanner sc = new Scanner(System.in);

		do
		{
			System.out.println("\n==== 교재 관리 ====");
			System.out.println("1. 교재 등록");
			System.out.println("2. 교재 조회");
			System.out.println("3. 교재 수정");
			System.out.println("4. 교재 삭제");
			System.out.print("메뉴 선택(1~4, -1종료) : ");

			String menus = sc.next();

			try
			{
				int menu = Integer.parseInt(menus);

				if (menu == -1)
				{
					System.out.println(">> 교재 관리 종료");
					break;
				}

				switch (menu)
				{
				case 1:
					tp.inputTextbook();
					break;
				case 2:
					tp.printTextbook();
					break;
				case 3:
					tp.modifyTextbook();
					break;
				case 4:
					tp.removeTextbook();
					break;

				}

			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		} while (true);

	}
	
	// 개설 과목 관리
	public void runOpenSubjectMgmt()
	{
		OpenSubjectProcess op = new OpenSubjectProcess();
		Scanner sc = new Scanner(System.in);

		do
		{
			System.out.println("\n==== 개설과목 관리 ====");
			System.out.println("1. 개설과목 등록");
			System.out.println("2. 개설과목 조회");
			System.out.println("3. 개설과목의 개설과정 변경");
			System.out.println("4. 개설과목 시작날짜/종료날짜 변경");
			System.out.println("5. 개설과목 교수 변경");
			System.out.println("6. 개설과목 과목 수정");
			System.out.println("7. 개설과목 교재 수정");
			System.out.println("8. 개설과목 삭제");
			System.out.print("메뉴 선택(1~8, -1종료) : ");

			String menus = sc.next();

			try
			{
				int menu = Integer.parseInt(menus);

				if (menu == -1)
				{
					System.out.println(">> 개설과목 관리 종료");
					break;
				}

				switch (menu)
				{
				case 1:
					// 1. 개설과목 등록
					op.inputOpenSubject();
					break;
				case 2:
					// 2. 개설과목 조회
					op.printOpenSubject();
					break;
				case 3:
					// 3. 개설과목의 개설과정 변경
					op.modifyOpenSubCourse();
					break;
				case 4:
					// 4. 개설과목 시작날짜/종료날짜 변경
					op.modifyOpenSubDate();
					break;
				case 5:
					// 5. 개설과목 교수 변경
					op.modifyOpenSubProf();
					break;
				case 6:
					// 6. 개설과목 과목 수정
					op.modifyOpenSubSubject();
					break;
				case 7:
					// 7. 개설과목 교재 수정
					op.modifyOpenSubTextbook();
					break;
				case 8:
					// 8. 개설과목 삭제
					op.removeOpenSubject();
					break;
				}

			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		} while (true);

	}
	
}
