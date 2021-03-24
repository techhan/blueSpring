package com.boss.blueSpring.board.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.board.model.dao.BoardDAO;
import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.Like;
import com.boss.blueSpring.board.model.vo.PageInfo;

public class BoardService {
	
	private BoardDAO dao = new BoardDAO();
	
	
	/** 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		int listCount = dao.getListCount(conn); 
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}


	/** 게시글 목록 조회 Service
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectBoardList(PageInfo pInfo, String cn) throws Exception {
		Connection conn = getConnection();
		
		List<Board> bList = dao.selectBoardList(conn, pInfo, cn);
		
		close(conn);
		
		return bList;
	}


	/** 게시글 상세 조회
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(int boardNo) throws Exception {
		Connection conn = getConnection();
		
		Board board = dao.selectBoard(conn, boardNo);
		
		if(board != null) {
			int result = dao.increaseReadCount(conn, boardNo);
			
			if(result > 0) {
				commit(conn);
				
				board.setReadCount(board.getReadCount() + 1);
			} else {
				rollback(conn);
			}
		}
		
		close(conn);
		
		return board;
	}


	/** 게시글 등록 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int result = 0;
				
		int boardNo = dao.selectNextNo(conn);	
		
		if(boardNo > 0) {
			map.put("boardNo", boardNo);
			
			result  = dao.insertBoard(conn, map);
			
			// 6. 트랜잭션 처리			
			if(result > 0) { 
				commit(conn);
				result = boardNo;
			} else {
				rollback(conn);
			}
		}
		close(conn);

		return result;
	}

	// 크로스 사이트 스크립팅 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		
		return result;
		
	}


	/** 게시글 수정 화면으로 전환하는 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board updateBoardForm(int boardNo) throws Exception {
		Connection conn = getConnection();
		
		Board board = dao.selectBoard(conn, boardNo);
		
//		board.setBoardContent(board.getBoardContent().replaceAll("<br>", "\r\n"));
		
		close(conn);
		
		return board;
	}


	/** 게시글 수정 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int result = 0; // service 수행 결과 저장 변수

		result = dao.updateBoard(conn, map);
		
		// 트랜잭션 처리 및 삭제 목록에 있는 파일 삭제
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}


	/** 게시글 삭제 DAO
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteBoard(int boardNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteBoard(conn, boardNo);
		
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		return result;
	}


	/** 좋아요 Service
	 * @param boardNo
	 * @param memberNo
	 * @param likeCount 
	 * @throws Exception
	 */
	public int boardLike(int boardNo, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int like = 0;
		int result = 0;
		
		try{
			result = dao.insertLikes(conn, boardNo, memberNo);
			like = 1;
		}catch(Exception e){
			result = dao.deleteLikes(conn, boardNo, memberNo);
			like = 0;
		}
		
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return like;
	}


	/** 좋아요 목록 얻어오기
	 * @param memberNo 
	 * @param boardNo 
	 * @param memberNo 
	 * @return likeList
	 * @throws Exception
	 */
	public Like selectLike(int boardNo, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		Like likeInfo = dao.selectLike(conn, boardNo, memberNo);
		
		close(conn);
		
		return likeInfo;
	}


	
}
