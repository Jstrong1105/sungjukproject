package com.subject;

import java.util.ArrayList;
import java.util.Scanner;

public class SubjectProcess
{
	private SubjectDAO dao;
	
	public SubjectProcess()
	{
		dao = new SubjectDAO();
	}

	// 과목 전체 출력
	public void printSubject()
	{
		try
		{
			dao.connection();
			
			ArrayList<SubjectDTO> subList = dao.selectSubject();
			
			if (subList.size() > 0)
			{
				System.out.println("=== 과목 목록 ===");
				
				for (SubjectDTO sub : subList)
				{
					String str = String.format("과목코드: %s | 과목명: %s", sub.getSubCD(), sub.getSubName());
					System.out.println(str);
				}
			} else
			{
				System.out.println("과목이 없습니다.");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	// 과목 입력
	public void inputSubject()
	{
		try
		{
			dao.connection();
			
			Scanner sc = new Scanner(System.in);
			
			SubjectDTO dto = new SubjectDTO();
			
			System.out.print("과목명 입력 : ");
			String subName = sc.nextLine();
			
			dto.setSubName(subName);
			
			int result = dao.insertSubject(dto);
			
			if (result > 0)
			{
				System.out.println(">> 과목 등록 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 과목 수정
	public void modifySubject()
	{
		try
		{
			dao.connection();
			
			Scanner sc = new Scanner(System.in);
			
			SubjectDTO dto = new SubjectDTO();
			
			String subCd;
			
			do
			{
				System.out.print("수정할 과목코드 입력 : ");
				subCd = sc.nextLine();

				ArrayList<SubjectDTO> subList = dao.selectSubject(subCd);

				if (subList.size() > 0)
				{
					for (SubjectDTO sub : subList)
					{
						String str = String.format("과목코드: %s | 과목명: %s", sub.getSubCD(), sub.getSubName());
						System.out.println(str);
					}
					break;
				} else
				{
					System.out.println("존재하지 않는 과목코드입니다. 다시 입력해 주세요.");
				}

			} while (true);
			
			System.out.print("과목명 수정 : ");
			String subName = sc.nextLine();
			
			dto.setSubCD(subCd);
			dto.setSubName(subName);
			
			int result = dao.updateSubject(dto);
			
			if (result > 0)
			{
				System.out.println(">> 과목 수정 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	// 과목 삭제
	public void removeSubject()
	{
		try
		{
			dao.connection();

			Scanner sc = new Scanner(System.in);

			String subCd;
			
			do
			{
				System.out.print("삭제할 과목코드 입력 : ");
				subCd = sc.nextLine();

				ArrayList<SubjectDTO> subList = dao.selectSubject(subCd);

				if (subList.size() > 0)
				{
					for (SubjectDTO sub : subList)
					{
						String str = String.format("과목코드: %s | 과목명: %s", sub.getSubCD(), sub.getSubName());
						System.out.println(str);
					}
					break;
				} else
				{
					System.out.println("존재하지 않는 과목코드입니다. 다시 입력해 주세요.");
				}

			} while (true);

			System.out.print("정말 삭제하시겠습니까?(Y/N) : ");
			String delYN = sc.nextLine();
			
			int result = 0;
			
			if (delYN.equals("Y") || delYN.equals("y"))
			{
				result = dao.deleteSubject(subCd);
			}
			
			if (result > 0)
			{
				System.out.println(">> 과목 삭제 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	

}
