package com.subject;

import java.util.ArrayList;
import java.util.Scanner;

public class TextbookProcess
{
	private TextbookDAO dao;
	
	public TextbookProcess()
	{
		dao = new TextbookDAO();
	}
	
	// 교재 전체 출력
	public void printTextbook()
	{
		try
		{
			dao.connection();
			
			ArrayList<TextbookDTO> tbList = dao.selectTextbook("");
			
			if (tbList.size() > 0)
			{
				System.out.println("=== 교재 목록 ===");
				
				for (TextbookDTO tb : tbList)
				{
					String str = String.format("교재코드: %s | 교재명: %s", tb.getTextbookCD(), tb.getTextbookName());
					System.out.println(str);
				}
			} else
			{
				System.out.println("교재가 없습니다.");
			}
			
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	// 교재 입력
	public void inputTextbook()
	{
		try
		{
			dao.connection();
			
			Scanner sc = new Scanner(System.in);
			
			TextbookDTO dto = new TextbookDTO();
			
			System.out.print("교재명 입력 : ");
			String tbName = sc.nextLine();
			
			dto.setTextbookName(tbName);
			
			int result = dao.insertTextbook(dto);
			
			if (result > 0)
			{
				System.out.println(">> 교재 등록 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	// 교재 수정
	public void modifyTextbook()
	{
		try
		{
			dao.connection();
			
			Scanner sc = new Scanner(System.in);
			
			TextbookDTO dto = new TextbookDTO();
			
			String tbCd;
			
			do
			{
				System.out.print("수정할 교재코드 입력 : ");
				tbCd = sc.nextLine();

				ArrayList<TextbookDTO> tbList = dao.selectTextbook(tbCd);

				if (tbList.size() > 0)
				{
					for (TextbookDTO tb : tbList)
					{
						String str = String.format("교재코드: %s | 교재명: %s", tb.getTextbookCD(), tb.getTextbookName());
						System.out.println(str);
					}
					break;
				} else
				{
					System.out.println("존재하지 않는 교재코드입니다. 다시 입력해 주세요.");
				}

			} while (true);
			
			System.out.print("교재명 수정 : ");
			String tbName = sc.nextLine();
			
			dto.setTextbookCD(tbCd);
			dto.setTextbookName(tbName);
			
			int result = dao.updateTextbook(dto);
			
			if (result > 0)
			{
				System.out.println(">> 교재 수정 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	// 교재 삭제
	public void removeTextbook()
	{
		try
		{
			dao.connection();
			
			Scanner sc = new Scanner(System.in);

			String tbCd;
			
			do
			{
				System.out.print("삭제할 교재코드 입력 : ");
				tbCd = sc.nextLine();

				ArrayList<TextbookDTO> tbList = dao.selectTextbook(tbCd);

				if (tbList.size() > 0)
				{
					for (TextbookDTO tb : tbList)
					{
						String str = String.format("교재코드: %s | 교재명: %s", tb.getTextbookCD(), tb.getTextbookName());
						System.out.println(str);
					}
					break;
				} else
				{
					System.out.println("존재하지 않는 교재코드입니다. 다시 입력해 주세요.");
				}

			} while (true);

			System.out.print("정말 삭제하시겠습니까?(Y/N) : ");
			String delYN = sc.nextLine();

			int result = 0;

			if (delYN.equals("Y") || delYN.equals("y"))
			{
				result = dao.deleteTextbook(tbCd);
			}
			
			if (result > 0)
			{
				System.out.println(">> 교재 삭제 성공");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
