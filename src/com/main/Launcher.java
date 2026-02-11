package com.main;

import com.prof.ProfFunction;
import com.sign.SignUpList;
import com.student.StudentFunction;
import com.util.DBConn;
import com.util.InputHandler;
import com.util.Login;

public class Launcher
{
	private static StudentFunction[] studentF = StudentFunction.values();	// 학생 사용 메소드
	private static ProfFunction[] profF = ProfFunction.values();			// 교수 사용 메소드
	private static SignUpList[] signUp = SignUpList.values();				// 회원 가입 메소드
	
	public static void main(String[] args)
	{
		while(true)
		{
			System.out.println("==== 성적 관리 프로그램 ====");
			System.out.println(" 0. 종료           ┌─────┐");
			System.out.println(" 1. 로그인         │GRADE│");
			System.out.println(" 2. 회원가입       └─────┘");
			System.out.println("============================");
			int answer = InputHandler.readInt(">> 번호를 선택 : ", 0, 2);
			
			if(answer == 0)
			{
				System.out.println(">> 프로그램을 종료합니다.");
				break;
			}
			else if(answer == 1)
			{
				login();
			}
			else if(answer == 2)
			{
				signUp();
			}
		}
		
		DBConn.close();
		System.out.println(">> 데이터베이스 연결 종료");
	}
	
	// 회원가입 진행
	private static void signUp()
	{
		MenuRender<SignUpList> menu = new MenuRender<>(signUp);
		menu.run("회원가입","");
	}
	
	// 로그인 진행
	private static void login()
	{
		Login login = new Login();
		
		while(true)
		{
			System.out.println();
			System.out.println("==== 로그인 ====");
			System.out.println("0. 뒤로가기");
			System.out.println("1. 학생   로그인");
			System.out.println("2. 교수   로그인");
			System.out.println("3. 관리자 로그인");
			System.out.println("================");
			
			int answer = InputHandler.readInt(">> 번호를 선택 : ",0,3);
			
			// 뒤로가기
			if(answer == 0)
			{
				System.out.println();
				return;
			}
		
			// 로그인 시도
			else
			{
				String id = InputHandler.readString(">> 아이디 입력 : ");
				String psw = InputHandler.readString(">> 비밀번호 입력 : ");
				
				// 학생 로그인
				if(answer == 1)
				{
					if(login.studentLogin(id, psw)) 
					{
						InputHandler.readString(">> 학생 로그인 성공");
		
						MenuRender<StudentFunction> menu = new MenuRender<>(studentF);
						
						menu.run("학생",id);
					}
					else
					{
						System.out.println(">> 로그인 실패");
					}
				}
				
				// 교수 로그인
				else if(answer == 2)
				{
					if(login.profLogin(id, psw))
					{
						InputHandler.readString(">> 교수 로그인 성공");
						
						MenuRender<ProfFunction> menu = new MenuRender<>(profF);
						
						menu.run("교수",id);
					}
					else 
					{
						System.out.println(">> 로그인 실패");
					}
				}
				
				// 관리자 로그인
				else if(answer == 3)
				{
					InputHandler.readString("관리자 기능 입니다. 미구현");
				}
			}
		}
	}
}
