package com.sign;

import com.util.FunctionUtil;

public enum SignUpList implements FunctionUtil
{
	STUDENT("학생 회원가입",new SignUp() :: studentSignUp),
	PROFESSOR("교수 회원가입", new SignUp() :: profSignUp),
	//ADMIN("관리자 회원가입", new SignUp() :: adminSignUp)
	;
	
	SignUpList(String name,Runnable signUp)
	{
		this.name = name;
		this.signUp = signUp;
	}
	
	private String name;
	private Runnable signUp;
	
	public String getName() { return name; }
	public void run(String sid) { signUp.run(); } 
}
