package com.sign;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.main.MenuRender;
import com.util.DBConn;
import com.util.FunctionUtil;
import com.util.InputHandler;

public class SignUp
{
     private Connection conn;
     
     private SignUp() 
     {
         try
         {
            conn = DBConn.getConnection();
         } 
         catch (Exception e)
         {
            System.out.println(e.toString());
         }
      }
   
     public static void signUp()
     {
    	 MenuRender<SignUpList> menu = new MenuRender<>(SignUpList.values());
    	 menu.run("로그인", "");
     }
     
     private enum SignUpList implements FunctionUtil
     {
     	STUDENT("학생 회원가입",new SignUp() :: studentSignUp),
     	PROFESSOR("교수 회원가입", new SignUp() :: profSignUp),
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
     
    private void studentSignUp()
   {
      try
      {
         String name = InputHandler.readString(">> 이름 입력 : "); 
         String ssn = InputHandler.readString(">> 주민번호 입력 : ");
         
         signUpS(name, ssn);
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }
 
   private int signUpS(String name, String ssn) throws SQLException
   {
      System.out.println();
      
      String sql = "{call PRC_ADMIN_STUDENT_INSERT(?,?)}";
      
      CallableStatement cstmt = conn.prepareCall(sql);
      
      cstmt.setString(1, name);
      cstmt.setString(2, ssn);
      
      int result = cstmt.executeUpdate();
      
      if (result > 0)
      {
         System.out.println(">> 학생 회원가입 완료~!");
      }
      
      return result;
   }
   
   private int signUpP(String name, String ssn) throws SQLException
   {
      System.out.println();
      
      String sql = "{call PRC_ADMIN_PROF_INSERT(?, ?)}";
      
      CallableStatement cstmt = conn.prepareCall(sql);
      
      cstmt.setString(1, name);
      cstmt.setString(2, ssn);
      
      int result = cstmt.executeUpdate();
      
      if (result > 0)
      {
         System.out.println(">> 교수 회원가입 완료~!!");
      }
      
      cstmt.close();
      return result;
   }
   
   void profSignUp()
   {
      try
      {
         String name = InputHandler.readString(">> 이름 입력 : ");
         String ssn = InputHandler.readString(">> 주민번호 입력 : ");
         
         signUpP(name, ssn);
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }
   
   /*
	void adminSignUp()
	{
		System.out.println("관리자 회원가입 미구현");
		InputHandler.readString("이걸 발견한 당신은 관리자 회원 가입을 구현할 권한을 얻었습니다.");
		
		for(int i = 0; i < 10; i++)
		{
			InputHandler.readString("권한을 수행해야합니다. * " + (i+1));
		}
		
		System.out.println();
	}
	*/
}
