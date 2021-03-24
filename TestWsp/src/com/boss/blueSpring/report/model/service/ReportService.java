package com.boss.blueSpring.report.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.Map;

import com.boss.blueSpring.report.model.dao.ReportDAO;

public class ReportService {
	private ReportDAO dao = new ReportDAO();
	
	/** 게시글 신고 접수 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertBoardReport(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.insertBoardReport(conn, map);
		
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	
	/** 신고 접수 글 블라인드 처리
	 * @param map
	 * @return blindCheck
	 * @throws Exception
	 */
	public int boardBlind(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int blindCheck = dao.boardBlind(conn, map);
		
		if(blindCheck > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return blindCheck;
	}


	/** 댓글 신고 접수 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertCommentReport(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.insertCommentReport(conn, map);
		
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}


	/** 댓글 블라인드 처리 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int commentBlind(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int blindCheck = dao.commentBlind(conn, map);
		
		if(blindCheck > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return blindCheck;
	}

}
