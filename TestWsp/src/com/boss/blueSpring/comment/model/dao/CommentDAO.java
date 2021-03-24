package com.boss.blueSpring.comment.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.boss.blueSpring.comment.model.vo.Comment;

public class CommentDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop;
	
	public CommentDAO(){
		String fileName = CommentDAO.class.getResource("/com/boss/blueSpring/sql/comment/comment-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 댓글 목록 조회 DAO
	 * @param conn
	 * @param parentBoardNo
	 * @return cList
	 * @throws Exception
	 */
	public List<Comment> selectList(Connection conn, int parentBoardNo) throws Exception {
		List<Comment> cList = null;
		
		String query = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, parentBoardNo);
			
			rset = pstmt.executeQuery();
			
			cList = new ArrayList<Comment>();
			
			while(rset.next()) {
				Comment comment = new Comment();
				
				comment.setComNo(rset.getInt("COM_NO"));
				comment.setComContent(rset.getString("COM_CONTENT"));
				comment.setComCreateDate(rset.getTimestamp("COM_CRT_DT"));
				comment.setMemberNickName(rset.getString("MEM_NICKNAME"));
				comment.setMemberId(rset.getString("MEM_ID"));
				
				cList.add(comment);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return cList;
	}


	/** 댓글 등록 DAO
	 * @param conn
	 * @param comment
	 * @return result
	 * @throws Exception
	 */
	public int insertComment(Connection conn, Comment comment) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("insertComment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, comment.getParentBoardNo());
			pstmt.setString(2, comment.getMemberId());
			pstmt.setString(3, comment.getComContent());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 댓글 수정 DAO
	 * @param conn
	 * @param comment
	 * @return result
	 * @throws Exception
	 */
	public int updateComment(Connection conn, Comment comment) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updateComment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, comment.getComContent());
			pstmt.setInt(2, comment.getComNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 댓글 삭제 DAO
	 * @param conn
	 * @param comNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteComment(Connection conn, int comNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("deleteComment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, comNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
