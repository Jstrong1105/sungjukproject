package com.jsb.test;

import java.util.Scanner;

public class AdminManagement
{
	// FIXME ※ 대충함....
	// 과목 관리
	public void runSubjectMgmt()
	{
		SubjectManagement subMgmt = new SubjectManagement();
		Scanner sc = new Scanner(System.in);

		do
		{
			System.out.println("\n==== 과목 관리 목록 ====");
			System.out.println("1. 과목 관리");
			System.out.println("2. 교재 관리");
			System.out.println("3. 개설과목 관리");
			System.out.print("메뉴 선택(1~3, -1종료) : ");

			String menus = sc.next();

			try
			{
				int menu = Integer.parseInt(menus);

				if (menu == -1)
				{
					System.out.println(">> 과목 관리 목록 종료");
					break;
				}

				switch (menu)
				{
				case 1:
					subMgmt.runSubjectMgmt();
					break;
				case 2:
					subMgmt.runTextbookMgmt();
					break;
				case 3:
					subMgmt.runOpenSubjectMgmt();
					break;

				}

			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		} while (true);

	}

}
