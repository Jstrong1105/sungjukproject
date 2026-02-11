package com.course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.classroom.ClassRoomDTO;
import com.opencourse.OpenCourseDAO;

public class CourseProcess
{
	Scanner sc = new Scanner(System.in);
	
	private CourseDAO courdao;
	private ClassRoomDTO roomdao;
	private OpenCourseDAO opcourdao;
	
	public CourseProcess() throws ClassNotFoundException, SQLException
	{
		courdao = new CourseDAO();
		roomdao = new ClassRoomDTO();
		opcourdao = new OpenCourseDAO();
	}
	
	// 과정명 등록
		public void courNameInsert() throws ClassNotFoundException, SQLException
		{
			
			ArrayList<CourseDTO> list = courdao.list();
			System.out.println("\n----------------------------------");
			System.out.println("과정코드        과정명");
			System.out.println("----------------------------------");
			
			for (CourseDTO dto1 : list)
			{
				System.out.printf("%s      %s\n", dto1.getCourcd(), dto1.getCourname());
			}
			System.out.println("----------------------------------");

			while (true)
			{
				System.out.println("[과정명 등록]");
				System.out.print("과정명 : ");
				
				String name = sc.next();
				
				ArrayList<CourseDTO> listname = courdao.list("COUR_NAME", name);
				
				if (listname.size() > 0)
				{
					System.out.println(">> 해당 과정명은 이미 존재합니다.\n");
					continue;
				}
				
				CourseDTO dto = new CourseDTO();
				dto.setCourname(name);
				int result = courdao.add(dto);
				
				if (result > 0)
				{
					System.out.println(">> 등록이 완료되었습니다.");
					break;
				}

			}
		}
		
		// 과정명 전체출력
		public void courNameSelectAll() throws SQLException, ClassNotFoundException
		{
			System.out.println("\n----------------------------------");
			System.out.println("과정코드        과정명");
			System.out.println("----------------------------------");
			for (CourseDTO dto : courdao.list())
			{
				System.out.printf("%s      %s\n", dto.getCourcd(), dto.getCourname());
			}
			System.out.println("----------------------------------");
		}
		
		// 과정명 수정
		public void courNameUpdate() throws ClassNotFoundException, SQLException
		{
			ArrayList<CourseDTO> list = courdao.list();
			
			System.out.println("\n----------------------------------");
			System.out.println("과정코드        과정명");
			System.out.println("----------------------------------");
			for (CourseDTO dto : list)
			{
				System.out.printf("%s      %s\n", dto.getCourcd(), dto.getCourname());
			}
			System.out.println("----------------------------------");
			
			System.out.print("수정할 과정명(과정코드) : ");
			String cd = sc.next();
			

			ArrayList<CourseDTO> list1 = courdao.list("COUR_CD", cd);
			
			if (list1.size() > 0)
			{
				System.out.println("\n[현재 정보 확인]");
				System.out.println("----------------------------------");
				for (CourseDTO dto : list1)
				{
					System.out.printf("과정코드 : [ %s ]\n", dto.getCourcd());
					System.out.printf("과정명   : [ %s ]", dto.getCourname());
				}
				System.out.println("\n----------------------------------");
				while (true)
				{
					System.out.print("새로운 과정명 : ");
					String name = sc.next();
					
					ArrayList<CourseDTO> listname = courdao.list("COUR_NAME", name);
					
					if (listname.size() > 0)
					{
						System.out.println(">> 해당 과정명은 이미 존재합니다.\n");
						continue;
					}
				
					CourseDTO dto = new CourseDTO();
					dto.setCourname(name);
					dto.setCourcd(cd);
					
					int result = courdao.modify(dto);
					
					if (result > 0)
					{
						System.out.println("\n>> 수정이 완료되었습니다.");
						break;
					}
				}
			}
			else
			{
				System.out.println(">> 존재하지 않는 과정코드 입니다.\n");
			}
				
		}
		
		// 과정명 삭제
		public void courNameDelete() throws ClassNotFoundException, SQLException
		{
			ArrayList<CourseDTO> list = courdao.list();
			System.out.println("\n----------------------------------");
			System.out.println("과정코드        과정명");
			System.out.println("----------------------------------");
			for (CourseDTO dto : list)
			{
				System.out.printf("%s      %s\n", dto.getCourcd(), dto.getCourname());
			}
			System.out.println("----------------------------------");
			System.out.print("삭제할 과정명(과정코드) : ");
			String cd = sc.next();
			
			ArrayList<CourseDTO> list1 = courdao.list("COUR_CD", cd);
			if (list1.size() > 0)
			{
				System.out.print(">> 정말 삭제하시겠습니까(Y/N) : ");
				String yn = sc.next();
				
				if (yn.equals("y") || yn.equals("Y"))
				{
					int result = courdao.remove(cd);
					if (result > 0)
					{
						System.out.println("\n>> 삭제가 완료되었습니다.");
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
				System.out.println(">> 존재하지 않는 과정코드 입니다.\n");
			}
		}
}
