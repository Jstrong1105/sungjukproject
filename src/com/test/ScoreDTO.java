package com.test;

public class ScoreDTO
{
	// 주요 속성
	String scoreCd, openSubCd, courRegiCd;
	int attendance, written, practical;					// 출결, 필기, 실기
	int attendancePct, writtenPct, practicalPct;		// 배점 (출결, 필기, 실기)
	
	public String getScoreCd()
	{
		return scoreCd;
	}
	public void setScoreCd(String scoreCd)
	{
		this.scoreCd = scoreCd;
	}
	public String getOpenSubCd()
	{
		return openSubCd;
	}
	public void setOpenSubCd(String openSubCd)
	{
		this.openSubCd = openSubCd;
	}
	public String getCourRegiCd()
	{
		return courRegiCd;
	}
	public void setCourRegiCd(String courRegiCd)
	{
		this.courRegiCd = courRegiCd;
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
	public int getAttendancePct()
	{
		return attendancePct;
	}
	public void setAttendancePct(int attendancePct)
	{
		this.attendancePct = attendancePct;
	}
	public int getWrittenPct()
	{
		return writtenPct;
	}
	public void setWrittenPct(int writtenPct)
	{
		this.writtenPct = writtenPct;
	}
	public int getPracticalPct()
	{
		return practicalPct;
	}
	public void setPracticalPct(int practicalPct)
	{
		this.practicalPct = practicalPct;
	}
}