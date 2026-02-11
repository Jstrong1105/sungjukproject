package com.main;

import com.util.FunctionUtil;
import com.util.InputHandler;

public class MenuRender<E extends Enum<E> & FunctionUtil>
{
	public MenuRender(E[] list)
	{
		this.list = list;
	}
	
	private E[] list;
	
	public void run(String name,String id)
	{
		while(true)
		{
			System.out.println();
			
			System.out.println("======="+ name +"=======");
			
			System.out.println("0. 로그아웃");
			
			for(E function : list)
			{
				System.out.println(function.ordinal()+1 + ". " + function.getName());
			}
			
			for(int i = 0; i < name.length(); i++)
			{
				System.out.print("==");
			}
			
			System.out.println("==============");
			
			int num = InputHandler.readInt(">> 번호를 선택 : ", 0, list.length);
			
			if(num == 0)
			{
				System.out.println("\033[H\033[2J\033[3J");
				System.out.flush();
				
				break;
			}
			
			System.out.println();
			
			list[num-1].run(id);
		}
	}
}
