package com.jsb.test;

//교수
public class ProfessorDTO
{
	private String profCD;
	private String PW;
	private String name;
	private String ssn;
	private String createDT;
	
	public String getProfCD()
	{
		return profCD;
	}
	public void setProfCD(String profCD)
	{
		this.profCD = profCD;
	}
	public String getPW()
	{
		return PW;
	}
	public void setPW(String pW)
	{
		PW = pW;
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
	public String getCreateDT()
	{
		return createDT;
	}
	public void setCreateDT(String createDT)
	{
		this.createDT = createDT;
	}
}