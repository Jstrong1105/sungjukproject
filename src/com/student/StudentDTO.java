package com.student;

public class StudentDTO
{
	private String name, courseName,subName,startDate,endDate,bookName,regiCd;
	private int attendance,written,practical,total,ranking;
	
	public String getRegiCd()
	{
		return regiCd;
	}
	public void setRegiCd(String regiCd)
	{
		this.regiCd = regiCd;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCourseName()
	{
		return courseName;
	}
	public void setCourseName(String courseName)
	{
		this.courseName = courseName;
	}
	public String getSubName()
	{
		return subName;
	}
	public void setSubName(String subName)
	{
		this.subName = subName;
	}
	public String getStartDate()
	{
		return startDate;
	}
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public String getBookName()
	{
		return bookName;
	}
	public void setBookName(String bookName)
	{
		this.bookName = bookName;
	}
	public int getAttendance()
	{
		return attendance;
	}
	public void setAttendance(int attendance)
	{
		this.attendance = attendance;
	}
	public int getWritten()
	{
		return written;
	}
	public void setWritten(int written)
	{
		this.written = written;
	}
	public int getPractical()
	{
		return practical;
	}
	public void setPractical(int practical)
	{
		this.practical = practical;
	}
	public int getTotal()
	{
		return total;
	}
	public void setTotal(int total)
	{
		this.total = total;
	}
	public int getRanking()
	{
		return ranking;
	}
	public void setRanking(int ranking)
	{
		this.ranking = ranking;
	}
}
