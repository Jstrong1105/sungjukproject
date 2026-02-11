package com.subject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class TextbookDAO
{
	private Connection conn;
	
	// 연결
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 교재 출력(textbookCd 이 null || "" 이 아니면 코드 조건절)
	public ArrayList<TextbookDTO> selectTextbook(String textbookCd) throws SQLException
	{
		ArrayList<TextbookDTO> result = new ArrayList<TextbookDTO>();
		
		String sql = "SELECT TEXTBOOK_CD, TEXTBOOK_NAME FROM TEXTBOOK";
		
		if (textbookCd != null && textbookCd != "")
		{
			sql += String.format(" WHERE TEXTBOOK_CD = '%s'" , textbookCd);
		}
		 
		sql += " ORDER BY TEXTBOOK_CD ASC";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			TextbookDTO dto = new TextbookDTO();
			
			dto.setTextbookCD(rs.getString("TEXTBOOK_CD"));
			dto.setTextbookName(rs.getString("TEXTBOOK_NAME"));
			
			result.add(dto);
		}
		
		rs.close();
		pstmt.close();
		
		return result;
	}
	
	// 교재 등록
	public int insertTextbook(TextbookDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_TEXTBOOK_INPUT(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getTextbookName());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}
	
	// 교재 수정
	public int updateTextbook(TextbookDTO dto) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_TEXTBOOK_UPDATE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, dto.getTextbookCD());
		cstmt.setString(2, dto.getTextbookName());
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}

	// 교재 삭제
	public int deleteTextbook(String textbookCd) throws SQLException
	{
		int result = 0;
		
		String sql = "{call PRC_ADMIN_TEXTBOOK_DELETE(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		
		cstmt.setString(1, textbookCd);
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		
		return result;
	}
	
	// 닫기
	public void close() throws SQLException
	{
		DBConn.close();
	}
}
