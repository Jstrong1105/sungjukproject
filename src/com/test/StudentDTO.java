package com.test;

public class StudentDTO
{
	// 주요 속성
	private String studentCd, pw, name, ssn, createDt;

	// getter setter
	public String getStudentCd()
	{
		return studentCd;
	}

	public void setStudentCd(String studentCd)
	{
		this.studentCd = studentCd;
	}

	public String getPw()
	{
		return pw;
	}

	public void setPw(String pw)
	{
		this.pw = pw;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSsn()
	{
		return ssn;
	}

	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}

	public String getCreateDt()
	{
		return createDt;
	}

	public void setCreateDt(String createDt)
	{
		this.createDt = createDt;
	}

}
