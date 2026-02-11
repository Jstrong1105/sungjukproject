package com.subject;

import java.util.ArrayList;
import java.util.Scanner;

import com.main.MenuRender;
import com.util.FunctionUtil;

public class TextbookProcess
{
	private TextbookDAO dao;
	
	public TextbookProcess()
	{
		dao = new TextbookDAO();
	}
	
	public void textBookFunc()
	{
		MenuRender<TextBookMenu> menu = new MenuRender<>(TextBookMenu.values());
		menu.run("교재", "");
	}
	
	private enum TextBookMenu implements FunctionUtil
	{
		TEXTBOOK_PRINT("교재 출력", new TextbookProcess() :: printTextbook),
		TEXTBOOK_INSERT("교재 추가",new TextbookProcess() :: inputTextbook),
		TEXTBOOK_UPDATE("교재 수정",new TextbookProcess() :: modifyTextbook),
		TEXTBOOK_DELETE("교재 삭제",new TextbookProcess() :: removeTextbook)
		;

		TextBookMenu(String name,Runnable func)
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
	
	// 교재 전체 출력
	void printTextbook()
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
	private void inputTextbook()
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
	private void modifyTextbook()
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
	private void removeTextbook()
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
