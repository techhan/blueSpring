package com.boss.blueSpring.board.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.Like;
import com.boss.blueSpring.board.model.vo.PageInfo;

public class BoardDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop = null;

	public BoardDAO() {
		String fileName = BoardDAO.class.getResource("/com/boss/blueSpring/sql/board/board-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param conn
	 * @return result
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return result;
	}

	
	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectBoardList(Connection conn, PageInfo pInfo, String cn) throws Exception {
		List<Board> bList = null;
		
		String query = prop.getProperty("selectBoardList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, cn);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<Board>();
			
			while(rset.next()) {
				Board board = new Board(rset.getInt("BRD_NO"), 
						rset.getString("BRD_TITLE"),
						rset.getString("MEM_ID"), 
						rset.getInt("BRD_VIEWS"),
						rset.getString("CATEGORY_NM"), 
						rset.getTimestamp("BRD_CRT_DT"),
						rset.getString("MEM_NICKNAME"),
						rset.getInt("LIKES"));
				bList.add(board);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return bList;
	}

	
	/** 게시글 상세 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception {
		Board board = null;
		
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new Board();
				
				board.setBoardNo(rset.getInt("BRD_NO"));
				board.setBoardTitle(rset.getString("BRD_TITLE"));
				board.setBoardContent(rset.getString("BRD_CONTENT"));
				board.setMemberId(rset.getString("MEM_ID"));
				board.setReadCount(rset.getInt("BRD_VIEWS"));
				board.setBoardCreateDate(rset.getTimestamp("BRD_CRT_DT"));
				board.setBoardModifyDate(rset.getTimestamp("BRD_UPDATE_DT"));
				board.setCategoryName(rset.getString("CATEGORY_NM"));
				board.setMemberNickname(rset.getString("MEM_NICKNAME"));
				board.setLikeCount(rset.getInt("LIKES"));
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return board;
	}

	/** 조회수 증가 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int boardNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 다음 게시글 번호 얻어오는 DAO
	 * @param conn
	 * @return boardNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception {
		int boardNo = 0;
		
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				boardNo = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return boardNo;
	}

	
	/** 게시글 삽입 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("boardNo"));
			pstmt.setString(2, (String)map.get("boardTitle"));
			pstmt.setString(3, (String)map.get("boardContent"));
			pstmt.setInt(4, (int)map.get("boardWriter"));
			pstmt.setInt(5, (int)map.get("categoryCode"));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	
	/** 게시글 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("boardTitle"));
			pstmt.setString(2, (String)map.get("boardContent"));
//			pstmt.setInt(4, (int)map.get("boardWriter"));
			pstmt.setInt(3, (int)map.get("categoryCode"));
			pstmt.setInt(4, (int)map.get("boardNo"));
			
			result = pstmt.executeUpdate();
			
			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 게시글 삭제 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteBoard(Connection conn, int boardNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	

	/** 좋아요 증가
	 * @param conn
	 * @param boardNo
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int insertLikes(Connection conn, int boardNo, int memberNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("insertLikes");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 좋아요 감소
	 * @param conn
	 * @param boardNo
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteLikes(Connection conn, int boardNo, int memberNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("deleteLikes");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;

	}

	/** 좋아요 목록을 얻기 위한 DAO
	 * @param conn
	 * @param boardNo 
	 * @param memberNo 
	 * @return likeInfo
	 * @throws Exception
	 */
	public Like selectLike(Connection conn, int boardNo, int memberNo) throws Exception {
		Like likeInfo = null;
		
		String query = "SELECT * FROM BOARD_LIKES BRD WHERE BRD_NO = ? AND MEM_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, memberNo);
			
			rset = pstmt.executeQuery();
			
			likeInfo = new Like();
			
			if(rset.next()) {
				Like like = new Like();
				like.setBoardNo(rset.getInt("BRD_NO"));
				like.setMemberNo(rset.getInt("MEM_NO"));
				
				likeInfo = like;
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return likeInfo;
	}

	

}
