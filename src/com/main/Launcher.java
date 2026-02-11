package com.main;

import com.classroom.ClassRoomProcess;
import com.course.CourseProcess;
import com.opencourse.OpenCourseProcess;
import com.prof.ProfProcess;
import com.sign.Login;
import com.sign.SignUp;
import com.student.StudentProcess;
import com.subject.OpenSubjectProcess;
import com.subject.SubjectProcess;
import com.subject.TextbookProcess;
import com.test.AdminProcess;
import com.util.DBConn;
import com.util.FunctionUtil;
import com.util.InputHandler;

public class Launcher
{
	public static void main(String[] args)
	{	
		while(true)
		{
			System.out.println("\033[H\033[2J\033[3J");
			System.out.flush();
			
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
		SignUp.signUp();
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
		
						StudentProcess.students(id);
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
						
						ProfProcess.prof(id);
					}
					else 
					{
						System.out.println(">> 로그인 실패");
					}
				}
				
				// 관리자 로그인
				else if(answer == 3)
				{
					
					if(login.adminLogin(id, psw))
					{
						InputHandler.readString(">> 관리자 로그인 성공");

						admin();
					}
					else 
					{
						System.out.println(">> 로그인 실패");
					}
				}
			}
		}
	}
	
	// 관리자 메뉴
	private static void admin()
	{
		MenuRender<AdminMenu> menu = new MenuRender<>(AdminMenu.values());
		menu.run("관리자", "");
	}

	private enum AdminMenu implements FunctionUtil
	{
		CLASSROOM("강의실 메뉴",new ClassRoomProcess() :: classRoomFunc),
		COURSE("과정 메뉴", new CourseProcess() :: courseFunc),
		SUBJECT("과목 메뉴", new SubjectProcess() :: subjectFunc),
		TEXTBOOK("교재 메뉴", new TextbookProcess() :: textBookFunc),
		OPEN_COURSE("개설 과정 메뉴", new OpenSubjectProcess() :: openSubjectFunc),
		OPEN_SUBJECT("개설 과목 메뉴", new OpenCourseProcess() :: openCourseFunc),
		PROFESSOR("교수 메뉴", new AdminProcess() :: profFunc),
		STUDENT("학생 메뉴",new AdminProcess() :: studentFunc),
		SCORE("성적 메뉴",new AdminProcess() :: scoreFunc),
		REGISTRATION("수강신청 메뉴",new AdminProcess() :: regiFunc)
		;
		
		AdminMenu(String name,Runnable func)
		{
			this.name = name;
			this.func = func;
		}
		
		final String name;
		final Runnable func;
		
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
}
