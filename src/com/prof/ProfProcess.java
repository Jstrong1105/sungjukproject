package com.prof;

import java.util.ArrayList;

import com.student.StudentDTO;
import com.util.InputHandler;

/*
 * 교사가 실행하는 기능들
 */
class ProfProcess
{
	private ProfDAO dao = new ProfDAO();
	
	private ArrayList<ProfDTO> subList;	// 강의 목록
	
	private ArrayList<StudentDTO> stuList;	// 학생 목록
	
	// 비밀 번호 수정
	void updatePassword(String profCd)
	{
		try
		{
			String password;
			
			while(true)
			{
				password = InputHandler.readString("변경할 비밀번호 입력 : ");
				
				if(password.length() >= 6)
				{
					break;
				}
				else
				{
					System.out.println("비밀번호는 6자리를 넘어야합니다.");
				}
			}
			
			if(dao.updatePassword(profCd, password) > 0)
			{
				System.out.println(">> 비밀번호 변경 완료");
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 강의 목록 출력
	void subList(String profCd)
	{
		try
		{
			subList =  dao.selectSubject(profCd);
			
			if(subList.size() > 0)
			{
				System.out.println("=======================================================================================");
				
				System.out.printf("번호      과목명   시작일     종료일   출결배점  필기배점  실기배점  교재명    \n");
				
				int i = 1;
				
				for(ProfDTO dto : subList)
				{
					System.out.printf("%2d %13s %tF %tF   %2d        %2d        %2d      %s\n",i++,dto.getOpen_sub_name(),dto.getStart_dt(),dto.end_dt
										,dto.getAtt(),dto.getWri(),dto.getPra(),dto.getTextBook());
				}
				
				System.out.println("=======================================================================================");
				
				int answer = InputHandler.readInt(">> 강의 번호를 선택 : ",1,subList.size());
				
				// 1 / 2 선택 받아서 배점 수정하기 or 학생 보기
				int choice = InputHandler.readInt(">> 1. 성적 출력 / 2. 성적 입력 / 3. 배점 수정 : ",1,3);

				System.out.println();
				
				// 학생 성적 출력하기
				if(choice == 1)
				{
					scorePrint(subList.get(answer-1).getSubject_cd());
				}
				
				// 학생 목록 조회하기
				else if(choice == 2)
				{
					stuList(subList.get(answer-1).getSubject_cd());
				}
				
				// 배점 수정하기
				else if(choice == 3)
				{
					percentageUpdate(subList.get(answer-1).getSubject_cd(),profCd);
				}
			}
			else
			{
				System.out.println(">> 강의가 없습니다.");
			}
		}
		
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 학생 성적 출력하기
	private void scorePrint(String subCd)
	{
		try
		{
			stuList = dao.printScore(subCd);
			
			if(stuList.size() > 0)
			{
				System.out.println("=============================================");
				
				System.out.printf("번호    학생명  출결 실기 필기 총점 등수\n");
				
				int i = 1;
				
				for(StudentDTO dto : stuList)
				{
					System.out.printf("%2d %8s   %2d   %2d   %2d   %3d  %2d\n",i++,dto.getName(),dto.getAttendance(),dto.getWritten(),dto.getPractical(),dto.getTotal(),dto.getRanking());
				}
				
				System.out.println("=============================================");
				
				InputHandler.readString("");
			}
			else
			{
				System.out.println(">> 수강 학생이 없습니다.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	
   // 배점 수정하기
   private void percentageUpdate(String oscd, String profcd)
   {
      try
      {   
         int att,wri,pra;
         
         while(true)
         {
            att = InputHandler.readInt("출결 배점 입력 : ",0,100);
            wri = InputHandler.readInt("필기 배점 입력 : ",0,100);
            pra = InputHandler.readInt("실기 배점 입력 : ",0,100);
            
            int add = att + wri + pra;
            
            if(add == 100)
            {
               break;
            }   
            System.out.println("배점의 합이 100이 되어야 합니다.");
                  
         }
         
         dao.updatePCT(oscd, profcd, att, wri, pra);
            
      }
      catch (Exception e)
      {         
         System.out.println(e.toString());
      }
         
   }
	
	// 학생 목록 출력
	private void stuList(String subCd)
	{
		try
		{
			stuList = dao.selectStudent(subCd);
			
			if(stuList.size() > 0)
			{
				System.out.println("==================");
				
				System.out.printf("번호      학생명\n");
				
				int i = 1;
				
				for(StudentDTO dto : stuList)
				{
					System.out.printf("%2d %10s\n",i++,dto.getName());
				}
				
				System.out.println("==================");
				
				int answer = InputHandler.readInt(">> 학생 번호를 선택 : ",1,stuList.size());
				
				// 학생 성적 수정하기
				insertScore(stuList.get(answer-1).getRegiCd(),subCd);
				
			}
			else
			{
				System.out.println(">> 수강 학생이 없습니다.");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	private void insertScore(String regiCd, String subjectCd)
	{
		try
		{
              int att = InputHandler.readInt(">> 출결 점수 : ",0,100);
              int wri = InputHandler.readInt(">> 필기 점수 : ",0,100);
              int pra = InputHandler.readInt(">> 실기 점수 : ",0,100);

              int check = dao.checkScore(regiCd);

              if (check == 0)
              {
                  dao.insertScore(subjectCd, regiCd);
              }
              
              dao.updateScore(regiCd,att, wri, pra);
              InputHandler.readString(">> 수정 완료.");
		} 
        
        catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}