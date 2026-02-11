package com.prof;

class ProfDTO
{
   private String open_sub_name, subject_cd;
   java.sql.Date start_dt, end_dt;
   private int att,wri,pra;
	   	
	String getOpen_sub_name()
	{
		return open_sub_name;
	}
	void setOpen_sub_name(String open_sub_name)
	{
		this.open_sub_name = open_sub_name;
	}
	String getSubject_cd()
	{
		return subject_cd;
	}
	void setSubject_cd(String subject_cd)
	{
		this.subject_cd = subject_cd;
	}
	java.sql.Date getStart_dt()
	{
		return start_dt;
	}
	void setStart_dt(java.sql.Date start_dt)
	{
		this.start_dt = start_dt;
	}
	java.sql.Date getEnd_dt()
	{
		return end_dt;
	}
	void setEnd_dt(java.sql.Date end_dt)
	{
		this.end_dt = end_dt;
	}
	int getAtt()
	{
		return att;
	}
	void setAtt(int att)
	{
		this.att = att;
	}
	int getWri()
	{
		return wri;
	}
	void setWri(int wri)
	{
		this.wri = wri;
	}
	int getPra()
	{
		return pra;
	}
	void setPra(int pra)
	{
		this.pra = pra;
	}
 }


