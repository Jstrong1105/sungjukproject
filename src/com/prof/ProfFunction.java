package com.prof;

import java.util.function.Consumer;

import com.util.FunctionUtil;

/*
 * 교수가 사용하는 기능을 나열한 enum
 * 하나뿐이다...
 */
public enum ProfFunction implements FunctionUtil
{
	SUBJECT_PRINT("강의 목록 출력",
			(sid)->{ ProfProcess pp = new ProfProcess();
					pp.subList(sid);})
	;
	
	ProfFunction(String name, Consumer<String> function)
	{
		this.name = name;
		this.function = function;
	}
	
	private final String name;
	private final Consumer<String> function;
	
	public String getName() { return name; }
	public void run(String sid)
	{
		function.accept(sid);
	}
}
