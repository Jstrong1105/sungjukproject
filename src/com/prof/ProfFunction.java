package com.prof;

import java.util.function.Consumer;

import com.util.FunctionUtil;

/*
 * 교수가 사용하는 기능을 나열한 enum
 */
public enum ProfFunction implements FunctionUtil
{
	SUBJECT_PRINT("강의 목록 출력",
			(profCd)->{ ProfProcess pp = new ProfProcess();
					pp.subList(profCd);}),
	UPDATE_PASSWORD("비밀번호 변경",
			(profCd)->{ ProfProcess pp = new ProfProcess();
					pp.updatePassword(profCd);})
	;
	
	ProfFunction(String name, Consumer<String> function)
	{
		this.name = name;
		this.function = function;
	}
	
	private final String name;
	private final Consumer<String> function;
	
	public String getName() { return name; }
	public void run(String profCd)
	{
		function.accept(profCd);
	}
}
