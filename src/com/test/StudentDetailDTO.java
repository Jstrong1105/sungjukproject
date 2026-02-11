package com.test;


//Read Only
public class StudentDetailDTO
{
	// 주요 속성
	private String name, courseName, subjectName, totScore, subjectState;
	
	// 생성자
	StudentDetailDTO(String name, String courseName, String subjectName, String totScore, String subjectState)
	{
		this.name = name;
		this.courseName = courseName;
		this.subjectName = subjectName;
		this.totScore = totScore;
		this.subjectState = subjectState;
	}

	// getter (Read Only)
	public String getName()
	{
		return name;
	}

	public String getCourseName()
	{
		return courseName;
	}

	public String getSubjectName()
	{
		return subjectName;
	}

	public String getTotScore()
	{
		return totScore;
	}

	public String getSubjectState()
	{
		return subjectState;
	}
}
