package com.boss.blueSpring.report.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class ReportDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	
	
	/** 게시글 신고 접수 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertBoardReport(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		
		String query = "INSERT INTO REPORT VALUES(SEQ_REPORTNO.NEXTVAL, 1, ?, ?, ?, DEFAULT, DEFAULT, ?, ?)";

		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, (int)map.get("boardNo"));
			pstmt.setInt(2, (int)map.get("memberNo"));
			pstmt.setInt(3, (int)map.get("reportCategory"));
			pstmt.setString(4, (String)map.get("reportContent"));
			pstmt.setString(5, (String)map.get("target"));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}



	/** 게시글 블라인드 처리 DAO
	 * @param conn
	 * @param map
	 * @return blindCheck
	 * @throws Exception
	 */
	public int boardBlind(Connection conn, Map<String, Object> map) throws Exception {
		int blindCheck = 0;
		
		String query = "UPDATE BOARD SET BRD_DEL_FL = 'B' WHERE BRD_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("boardNo"));
			
			blindCheck = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return blindCheck;
	}



	/** 댓글 신고 접수 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertCommentReport(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		
		String query = "INSERT INTO REPORT VALUES(SEQ_REPORTNO.NEXTVAL, 2, ?, ?, ?, DEFAULT, DEFAULT, ?, ?)";

		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, (int)map.get("commentNo"));
			pstmt.setInt(2, (int)map.get("memberNo"));
			pstmt.setInt(3, (int)map.get("reportCategory"));
			pstmt.setString(4, (String)map.get("reportContent"));
			pstmt.setString(5, (String)map.get("target"));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}



	/** 댓글 블라인드 처리 DAO
	 * @param conn
	 * @param map
	 * @return blindCheck
	 * @throws Exception
	 */
	public int commentBlind(Connection conn, Map<String, Object> map) throws Exception {
		int blindCheck = 0;
		
		String query = "UPDATE COM_MENT SET COM_DEL_FL = 'B' WHERE COM_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("commentNo"));
			
			blindCheck = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return blindCheck;
	}

}
