package com.student;

import java.util.List;
import java.util.function.Consumer;

import com.main.MenuRender;
import com.util.FunctionUtil;
import com.util.InputHandler;

public class StudentProcess
{
	private StudentProcess()
	{
		dao = new StudentDAO();
	}
	
	public static void students(String id)
	{
		MenuRender<StudentFunction> menu = new MenuRender<>(StudentFunction.values());
		
		menu.run("학생",id);
	}
	
	private enum StudentFunction implements FunctionUtil
	{
		SCORE_PRINT("성적 조회",
				(studentCd) -> {StudentProcess sp = new StudentProcess();
						  sp.getRecord(studentCd);}),
		UPDATE_PASSWORD("비밀번호 변경",
				(studentCd)->{StudentProcess sp = new StudentProcess();
						  sp.updatePassword(studentCd);})
		;
		
		StudentFunction(String name, Consumer<String> function)
		{
			this.name = name;
			this.function = function;
		}
		
		private final String name;
		private final Consumer<String> function;
		
		public String getName() { return name; }
		public void run(String studentCd)
		{
			function.accept(studentCd);
		}
	}
	
	private StudentDAO dao;
	
	// 성적 가져오기
	private void getRecord(String sid)
	{
		try {
		
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
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
	}
	
	// 비밀번호 수정하기
	private void updatePassword(String studentCd)
	{
		try
		{
			String password;
			
			while(true)
			{
				password = InputHandler.readString("변경할 비밀번호 입력 : ");
				
				if(password.length() >= 6)
				{
					break;
				}
				else
				{
					System.out.println("비밀번호는 6자리를 넘어야합니다.");
				}
			}
			
			if(dao.updatePassword(studentCd, password) > 0)
			{
				System.out.println(">> 비밀번호 변경 완료");
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
