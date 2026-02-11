package com.classroom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.course.CourseDAO;
import com.main.MenuRender;
import com.opencourse.OpenCourseDAO;
import com.util.FunctionUtil;

public class ClassRoomProcess
{
	Scanner sc = new Scanner(System.in);
	
	private CourseDAO courdao;
	private ClassRoomDAO roomdao;
	private OpenCourseDAO opcourdao;
	
	public void classRoomFunc()
	{
		MenuRender<ClassRoomMenu> menu = new MenuRender<>(ClassRoomMenu.values());
		menu.run("강의실","");
	}
	
	private enum ClassRoomMenu implements FunctionUtil
	{
		CLASSROOM_PRINT("강의실 출력", new ClassRoomProcess() :: roomNameSelectAll),
		CLASSROOM_INSERT("강의실 등록",new ClassRoomProcess() :: roomNameInsert),
		CLASSROOM_UPDATE("강의실 수정",new ClassRoomProcess() :: roomNameUpdate),
		CLASSROOM_DELETE("강의실 삭제",new ClassRoomProcess() :: roomNameDelete)
		;
		
		ClassRoomMenu(String name,Runnable func)
		{
			this.name = name;
			this.func = func;
		}
		
		private final String name;
		private final Runnable func;

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
	
	public ClassRoomProcess() 
	{
		try
		{
			courdao = new CourseDAO();
			roomdao = new ClassRoomDAO();
			opcourdao = new OpenCourseDAO();
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	// 강의실명 등록
	private void roomNameInsert()
		{
			try
			{
				ArrayList<ClassRoomDTO> list = roomdao.list();
				System.out.println("\n----------------------------------");
				System.out.println("강의실코드        강의실명");
				System.out.println("----------------------------------");
				for (ClassRoomDTO dto1 : list)
				{
					System.out.printf("%s      %s\n", dto1.getRoomcd(), dto1.getRoomname());
				}
				System.out.println("----------------------------------");
				
				while (true)
				{
					System.out.println("[강의실명 등록]");
					System.out.print("강의실명 : ");
					String name = sc.next();
					
					ArrayList<ClassRoomDTO> listname = roomdao.list("CLASSROOM_NAME", name);
					
					if (listname.size() > 0)
					{
						System.out.println(">> 해당 강의실명은 이미 존재합니다.\n");
						continue;
					}
					
					ClassRoomDTO dto = new ClassRoomDTO();
					dto.setRoomname(name);
					int result = roomdao.add(dto);
					
					if (result > 0)
					{
						System.out.println(">> 등록이 완료되었습니다.");
						break;
					}	
				}
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		// 강의실명 수정
	private void roomNameUpdate()
		{	
			try
			{
				ArrayList<ClassRoomDTO> list = roomdao.list();
				System.out.println("\n----------------------------------");
				System.out.println("강의실코드        강의실명");
				System.out.println("----------------------------------");
				for (ClassRoomDTO cl : list)
				{
					System.out.printf("%s      %s\n", cl.getRoomcd(), cl.getRoomname());
				}
				System.out.println("----------------------------------");
				
				System.out.print("수정할 강의실명(강의실코드) : ");
				String cd = sc.next();
				
				ArrayList<ClassRoomDTO> list1 = roomdao.list("CLASSROOM_CD", cd);
				
				if (list1.size() > 0)
				{
					System.out.println("[현재 정보 확인]");
					System.out.println("\n----------------------------------");
					System.out.println("강의실코드        강의실명");
					System.out.println("----------------------------------");
					for (ClassRoomDTO cl : list1)
					{
						System.out.printf("%s      %s\n", cl.getRoomcd(), cl.getRoomname());
					}
					System.out.println("----------------------------------");
					
					while (true)
					{
						System.out.print("새로운 강의실명 : ");
						String name = sc.next();
						
						ArrayList<ClassRoomDTO> listname = roomdao.list("CLASSROOM_NAME", name);
						
						if (listname.size() > 0)
						{
							System.out.println(">> 해당 강의실명은 이미 존재합니다.\n");
							continue;
						}
						ClassRoomDTO dto = new ClassRoomDTO();
						dto.setRoomname(name);
						dto.setRoomcd(cd);
						
						int result = roomdao.modify(dto);
						
						if (result > 0)
						{
							System.out.println("\n>> 수정이 완료되었습니다.");
							break;
						}	
					}
				}
				else
				{
					System.out.println(">> 존재하지 않는 강의실코드 입니다.\n");
				}
			} 
			catch (Exception e)
			{
				
			}
		} // roomNameUpdate() end
		
		// 강의실명 삭제
	private void roomNameDelete() 
		{
			try
			{
				ArrayList<ClassRoomDTO> list = roomdao.list();
				System.out.println("\n----------------------------------");
				System.out.println("강의실코드        강의실명");
				System.out.println("----------------------------------");
				for (ClassRoomDTO cl : list)
				{
					System.out.printf("%s      %s\n", cl.getRoomcd(), cl.getRoomname());
				}
				System.out.println("----------------------------------");
				System.out.print("삭제할 강의실명(강의실코드) : ");
				String cd = sc.next();
				
				ArrayList<ClassRoomDTO> list1 = roomdao.list("CLASSROOM_CD", cd);
				if (list1.size() > 0)
				{
					System.out.print("정말 삭제하시겠습니까(Y/N) : ");
					String yn = sc.next();
					
					if (yn.equals("y") || yn.equals("Y"))
					{
						int result = roomdao.remove(cd);
						
						if (result > 0)
						{
							System.out.println(">> 삭제가 완료되었습니다.");
						}
					}
					else
					{
						System.out.println();
						return;
					}
				}
				else
				{
					System.out.println(">> 존재하지 않는 강의실코드 입니다.\n");
				}
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		// 강의실명 전체출력
	private void roomNameSelectAll()
		{
			try
			{
				System.out.println("\n----------------------------------");
				System.out.println("강의실코드        강의실명");
				System.out.println("----------------------------------");
				for (ClassRoomDTO dto : roomdao.list())
				{
					System.out.printf("%s      %s\n", dto.getRoomcd(), dto.getRoomname());
				}
				System.out.println("----------------------------------\n");
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
}
