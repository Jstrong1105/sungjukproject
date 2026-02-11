package com.jsb.test;

public class AdminProcess
{
	private AdminDAO dao;
	
	public AdminProcess()
	{
		dao = new AdminDAO();
	}
	
	// 로그인
	public boolean login(String id, String pw)
	{
		try
		{
			dao.connection();
			
			dao.adminLogin(id, pw);
			dao.close();
			
			return true;
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
			return false;
		}
	}
	
}
