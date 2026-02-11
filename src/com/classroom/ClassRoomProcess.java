package com.classroom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.course.CourseDAO;
import com.opencourse.OpenCourseDAO;

public class ClassRoomProcess
{
	Scanner sc = new Scanner(System.in);
	
	private CourseDAO courdao;
	private ClassRoomDAO roomdao;
	private OpenCourseDAO opcourdao;
	
	public ClassRoomProcess() throws ClassNotFoundException, SQLException
	{
		courdao = new CourseDAO();
		roomdao = new ClassRoomDAO();
		opcourdao = new OpenCourseDAO();
	}
	// 강의실명 등록
		public void roomNameInsert() throws ClassNotFoundException, SQLException
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
		
		// 강의실명 수정
		public void roomNameUpdate() throws ClassNotFoundException, SQLException
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
				
		} // roomNameUpdate() end
		
		// 강의실명 삭제
		public void roomNameDelete() throws ClassNotFoundException, SQLException
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
		
		// 강의실명 전체출력
		public void roomNameSelectAll() throws SQLException, ClassNotFoundException
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
}
