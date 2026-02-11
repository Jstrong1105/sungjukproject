package com.jsb.test;

import java.util.Scanner;

import com.util.DBConn;

public class TeamMain
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		// 로그인
//		AdminProcess ap = new AdminProcess();
//		
//		String id = "adminId";
//		String pw = "adminPw";
//		
//		boolean loginSuccess = ap.login(id, pw);
		
		AdminManagement adminMgmt = new AdminManagement();

		do
		{
			System.out.println("==== 관리자 페이지 ====");
			System.out.println("1. 관리자");
			System.out.println("2. 교수자");
			System.out.println("3. 과정");
			System.out.println("4. 과목");
			System.out.println("5. 학생");
			System.out.println("6. 성적");
			System.out.print("메뉴 선택(1~6, -1종료) : ");
			String infoMenus = sc.next();
			
			try
			{
				int infoMenu = Integer.parseInt(infoMenus);
				
				if (infoMenu == -1)
				{
					System.out.println(">> 프로그램 종료");
					break;
				}
				
				switch (infoMenu)
				{
				case 1:
					System.out.println("관리자");
					break;
				case 2:
					System.out.println("교수자");
					break;
				case 3:
					System.out.println("과정");
					break;
				case 4:
					adminMgmt.runSubjectMgmt();
					break;
				case 5:
					System.out.println("학생");
					break;
				case 6:
					System.out.println("성적");
					break;
				}

			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		} while (true);

	}
}
