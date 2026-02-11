package com.student;

import java.util.function.Consumer;

import com.util.FunctionUtil;

/*
 * 학생이 사용하는 기능을 모아놓은 enum
 * 하나밖에 없지만...
 */
public enum StudentFunction implements FunctionUtil
{
	SCORE_PRINT("성적 조회",
			(studentCd) -> {StudentProcess sp = new StudentProcess();
					  sp.getRecord(studentCd);}),
	UPDATE_PASSWORD("비밀번호 변경",
			(studentCd)->{StudentProcess sp = new StudentProcess();
					  sp.updatePassword(studentCd);})
	;
	
	StudentFunction(String name, Consumer<String> function)
	{
		this.name = name;
		this.function = function;
	}
	
	private final String name;
	private final Consumer<String> function;
	
	public String getName() { return name; }
	public void run(String studentCd)
	{
		function.accept(studentCd);
	}
}
