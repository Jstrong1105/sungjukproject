package com.opencourse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.classroom.ClassRoomDAO;
import com.classroom.ClassRoomDTO;
import com.course.CourseDAO;
import com.course.CourseDTO;

public class OpenCourseProcess
{
	Scanner sc = new Scanner(System.in);
	
	private CourseDAO courdao;
	private ClassRoomDAO roomdao;
	private OpenCourseDAO opcourdao;
	
	public OpenCourseProcess() throws ClassNotFoundException, SQLException
	{
		courdao = new CourseDAO();
		roomdao = new ClassRoomDAO();
		opcourdao = new OpenCourseDAO();
	}
	
	// 개설과정 등록
	public void opcourInsert() throws ClassNotFoundException, SQLException
	{
		System.out.println("\n[개설과정 등록]");
		System.out.println("------------------");
		System.out.println("과정명");
		System.out.println("------------------");
		for (CourseDTO dto : courdao.list())
		{
			System.out.println(dto.getCourname());
		}
		System.out.println("------------------");
		
		while (true)
		{
			System.out.print("과정명 : ");
			String courname = sc.next();
			System.out.println();
			
			ArrayList<CourseDTO> courlist = courdao.list("COUR_NAME", courname);
			
			if (courlist.size() > 0)
			{
				System.out.println("------------------");
				System.out.println("강의실");
				System.out.println("------------------");
				for (ClassRoomDTO dto : roomdao.list())
				{
					System.out.println(dto.getRoomname());
				}
				System.out.println("------------------");
				
				while (true)
				{
					System.out.print("강의실 : ");
					String roomname = sc.next();
					ArrayList<ClassRoomDTO> roomlist = roomdao.list("CLASSROOM_NAME", roomname);
					
					if (roomlist.size() > 0)
					{
						String startdt = "";
						while (true)
						{
							try
							{
								System.out.print("시작일(YYYY-MM-DD) : ");
								startdt = sc.next();
								java.sql.Date.valueOf(startdt);
								break;
								
							} catch (Exception e)
							{
								System.out.println("다시 입력하세요");
							}
						}
						
						String enddt = "";
						while (true)
						{
							try
							{
								System.out.print("종료일(YYYY-MM-DD) : ");
								enddt = sc.next();
								java.sql.Date.valueOf(enddt);
								break;
								
							} catch (Exception e)
							{
								System.out.println("다시 입력하세요");
							}	
						}

						OpenCourseDTO dto = new OpenCourseDTO();
						
						dto.setOpcourname(courname);
						dto.setOpcourroom(roomname);
						dto.setOpcourstart(startdt);
						dto.setOpcourend(enddt);
						
						int result = opcourdao.add(dto);
						
						if (result > 0)
						{
							System.out.println(">> 등록이 완료되었습니다.");
							return;
						}
					}
					else
					{
						System.out.println(">> 존재하지 않는 강의실 입니다.\n");
					}
				}
			}
			else
			{
				System.out.println(">> 존재하지 않는 과정명 입니다.\n");
			}	
		}
	}
	
	// 개설과정 수정
	public void opencourUpdate(int n) throws SQLException
	{
		ArrayList<OpenCourseDTO> list = opcourdao.list();
		System.out.println("\n-------------------------------------------------------------------");
		System.out.println("개설과정코드   과정명   강의실   시작일   종료일");
		System.out.println("-------------------------------------------------------------------");
		for (OpenCourseDTO dto : list)
		{
			System.out.printf("%s   %s   %s   %s   %s\n", dto.getOpcourcd(), dto.getOpcourname()
					, dto.getOpcourroom(), dto.getOpcourstart().substring(0, 10), dto.getOpcourend().substring(0, 10));
		}
		System.out.println("-------------------------------------------------------------------");
		while (true)
		{
			System.out.print("수정할 개설과정(개설과정코드) : ");
			String cd = sc.next();
			
			ArrayList<OpenCourseDTO> listcd = opcourdao.list(cd);
			
			if (listcd.size() > 0)
			{
				for (OpenCourseDTO dto : listcd)
				{
					System.out.println("[현재 정보 확인]");
					System.out.println("\n-------------------------------------------------------------------");
					System.out.println("개설과정코드   과정명   강의실   시작일   종료일");
					System.out.println("-------------------------------------------------------------------");
					System.out.printf("%s   %s   %s   %s   %s\n", dto.getOpcourcd(), dto.getOpcourname()
							, dto.getOpcourroom(), dto.getOpcourstart().substring(0, 10)
							, dto.getOpcourend().substring(0, 10));
					System.out.println("-------------------------------------------------------------------");
				}
				switch (n) {
				case 1 :
				{
					System.out.println("--------------");
					System.out.println("과정명");
					System.out.println("--------------");
					for (CourseDTO dto : courdao.list())
					{
						System.out.println(dto.getCourname());
					}
					System.out.println("--------------");
					while (true)
					{
						System.out.print("새로운 과정명 : ");
						String courname = sc.next();
						
						ArrayList<CourseDTO> courlist = courdao.list("COUR_NAME", courname);
						
						if (courlist.size() > 0)
						{
							OpenCourseDTO dto = new OpenCourseDTO();
							
							dto.setOpcourcd(cd);
							dto.setOpcourname(courname);
							
							int result = opcourdao.courModify(dto);
							
							if (result > 0)
							{
								System.out.println("\n>> 수정이 완료되었습니다.");
								return;
							}	
						}
						
						else
						{
							System.out.println(">> 존재하지 않는 과정명 입니다.\n");
						}
					} // while end
				} // case 1 end
				
				case 2 :
				{
					System.out.println("--------------");
					System.out.println("강의실");
					System.out.println("--------------");
					for (ClassRoomDTO dto : roomdao.list())
					{
						System.out.println(dto.getRoomname());
					}
					System.out.println("--------------");
					while (true)
					{
						System.out.print("새로운 강의실 : ");
						String roomname = sc.next();
						
						ArrayList<ClassRoomDTO> roomlist = roomdao.list("CLASSROOM_NAME", roomname);
						
						if (roomlist.size() > 0)
						{
							OpenCourseDTO dto = new OpenCourseDTO();
							
							dto.setOpcourcd(cd);
							dto.setOpcourroom(roomname);
							
							int result = opcourdao.roomModify(dto);
							
							if (result > 0)
							{
								System.out.println("\n>> 수정이 완료되었습니다.");
								return;
							}	
						}
						
						else
						{
							System.out.println(">> 존재하지 않는 강의실 입니다.\n");
						}
					}
				} // case 2 end
				case 3 :
				{	
					while (true)
					{
						String startdt ="";
						try
						{
							System.out.print("새로운 시작일(YYYY-MM-DD) : ");
							startdt = sc.next();
							java.sql.Date.valueOf(startdt);
	
							OpenCourseDTO dto = new OpenCourseDTO();
							
							dto.setOpcourcd(cd);
							dto.setOpcourstart(startdt);
							
							int result = opcourdao.startModify(dto);
							
							if (result > 0)
							{
								System.out.println("\n>> 수정이 완료되었습니다.");
								return;
							}	
							
						} catch (Exception e)
						{
							System.out.println(">> 다시 입력하세요.\n");
						}
						
					}
				} // case 3 end
				
				case 4 :
				{	
					while (true)
					{
						String enddt ="";
						try
						{
							System.out.print("새로운 종료일(YYYY-MM-DD) : ");
							enddt = sc.next();
							java.sql.Date.valueOf(enddt);

							OpenCourseDTO dto = new OpenCourseDTO();
							
							dto.setOpcourcd(cd);
							dto.setOpcourend(enddt);
							
							int result = opcourdao.endModify(dto);
							
							if (result > 0)
							{
								System.out.println("\n>> 수정이 완료되었습니다.");
								return;
							}	
							
						} catch (Exception e)
						{
							System.out.println(">> 다시 입력하세요.\n");
						}
						
					}
				} // case 4 end
				default: System.out.println(">> 잘못된 번호입니다.");
				} // switch end
				
			}
			else
			{
				System.out.println(">> 존재하지 않는 개설과정 입니다.\n");
			}
			
		}
	}
	
	// 개설과정 삭제
	public void opencourDelete() throws SQLException
	{
		ArrayList<OpenCourseDTO> list = opcourdao.list();
		System.out.println("\n-------------------------------------------------------------------");
		System.out.println("개설과정코드   과정명   강의실   시작일   종료일");
		System.out.println("-------------------------------------------------------------------");
		for (OpenCourseDTO dto : list)
		{
			System.out.printf("%s   %s   %s   %s   %s\n", dto.getOpcourcd(), dto.getOpcourname()
					, dto.getOpcourroom(), dto.getOpcourstart().substring(0, 10), dto.getOpcourend().substring(0, 10));
		}
		System.out.println("-------------------------------------------------------------------");
		
		do
		{
			System.out.print("삭제할 개설과정(개설과정코드) : ");
			String cd = sc.next();
			
			ArrayList<OpenCourseDTO> listcd = opcourdao.list(cd);
			
			if (listcd.size() > 0)
			{
				System.out.print("\n>> 정말 삭제하시겠습니까.(Y/N) : ");
				String yn = sc.next();
				
				if (yn.equals("y") || yn.equals("Y"))
				{
					int result = opcourdao.remove(cd);
					if (result > 0)
					{
						System.out.println("\n>> 삭제가 완료되었습니다.\n");
						return;
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
				System.out.println(">> 존재하지 않는 개설과정 입니다.\n");
			}
			
		} while (true);
	}
	
	// 개설과정 전체출력
	public void opencourSelectAll() throws SQLException
	{
		ArrayList<OpenCourseDTO> list = opcourdao.list();
		System.out.println("\n-------------------------------------------------------------------");
		System.out.println("개설과정코드   과정명   강의실   시작일   종료일");
		System.out.println("-------------------------------------------------------------------");
		for (OpenCourseDTO dto : list)
		{
			System.out.printf("%s   %s   %s   %s   %s\n", dto.getOpcourcd(), dto.getOpcourname()
					, dto.getOpcourroom(), dto.getOpcourstart().substring(0, 10), dto.getOpcourend().substring(0, 10));
		}
		System.out.println("-------------------------------------------------------------------\n");
	}
	
	
	
	
	
	

}
