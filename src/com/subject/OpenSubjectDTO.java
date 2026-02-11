package com.subject;

public class OpenSubjectDTO
{
	private String openSubCD; 
	private String openCourCD; 
	private String subCD; 
	private String textbookCD; 
	private String profCD; 
	private String startDT; 
	private String endDT; 
	private String createDT; 
	
	// 과정명 - (Subject 테이블의 과정명)
	private String courName;
	// 강의실명
	private String classroomName;
	// 과목명
	private String subName; 
	// 교재명
	private String textbookName;
	// 교수명
	private String profName;
	
	public String getOpenSubCD()
	{
		return openSubCD;
	}
	public void setOpenSubCD(String openSubCD)
	{
		this.openSubCD = openSubCD;
	}
	public String getOpenCourCD()
	{
		return openCourCD;
	}
	public void setOpenCourCD(String openCourCD)
	{
		this.openCourCD = openCourCD;
	}
	public String getSubCD()
	{
		return subCD;
	}
	public void setSubCD(String subCD)
	{
		this.subCD = subCD;
	}
	public String getTextbookCD()
	{
		return textbookCD;
	}
	public void setTextbookCD(String textbookCD)
	{
		this.textbookCD = textbookCD;
	}
	public String getProfCD()
	{
		return profCD;
	}
	public void setProfCD(String profCD)
	{
		this.profCD = profCD;
	}
	public String getStartDT()
	{
		return startDT;
	}
	public void setStartDT(String startDT)
	{
		this.startDT = startDT;
	}
	public String getEndDT()
	{
		return endDT;
	}
	public void setEndDT(String endDT)
	{
		this.endDT = endDT;
	}
	public String getCreateDT()
	{
		return createDT;
	}
	public void setCreateDT(String createDT)
	{
		this.createDT = createDT;
	}
	public String getCourName()
	{
		return courName;
	}
	public void setCourName(String courName)
	{
		this.courName = courName;
	}
	public String getClassroomName()
	{
		return classroomName;
	}
	public void setClassroomName(String classroomName)
	{
		this.classroomName = classroomName;
	}
	public String getSubName()
	{
		return subName;
	}
	public void setSubName(String subName)
	{
		this.subName = subName;
	}
	public String getTextbookName()
	{
		return textbookName;
	}
	public void setTextbookName(String textbookName)
	{
		this.textbookName = textbookName;
	}
	public String getProfName()
	{
		return profName;
	}
	public void setProfName(String profName)
	{
		this.profName = profName;
	} 
}
