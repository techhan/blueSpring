package com.boss.blueSpring.admin.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;

import com.boss.blueSpring.admin.model.dao.AdminDeleteDAO;

public class AdminDeleteService {

	private AdminDeleteDAO dao = new AdminDeleteDAO();
	
	/** [자유게시판] 게시글 삭제 Service
	 * @param newNumbers
	 * @return result
	 * @throws Exception
	 */
	public int updateBoardStatus(String newNumbers) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateBoardStatus(conn, newNumbers);
		if(result > 0)	commit(conn);
		else			rollback(conn);		
		close(conn);
		return result;
	}

	/** [블랙리스트] 삭제 Service
	 * @param newBlack
	 * @return result
	 * @throws Exception
	 */
	public int updateBlackStatus(String newBlack) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateBlackStatus(conn, newBlack);
		if(result > 0)	commit(conn);
		else			rollback(conn);		
		close(conn);
		return result;
	}

	/** [챌린지] 게시글 삭제 Service
	 * @param newChall
	 * @return result
	 * @throws Exception
	 */
	public int updateChallStatus(String newChall) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateChallStatus(conn, newChall);
		if(result > 0)	commit(conn);
		else			rollback(conn);		
		close(conn);
		return result;
	}

	/** [챌린지 인증] 게시글 삭제 Service
	 * @param newCrtfd
	 * @return result
	 * @throws Exception
	 */
	public int updateCrtfdStatus(String newCrtfd) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateCrtfdStatus(conn, newCrtfd);
		if(result > 0)	commit(conn);
		else			rollback(conn);		
		close(conn);
		return result;
	}

	
	/** [센터] 삭제 Service
	 * @param newCenter
	 * @return result
	 * @throws Exception
	 */
	public int updateCenterStatus(String newCenter) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateCenterStatus(conn, newCenter);
		if(result > 0)	commit(conn);
		else			rollback(conn);		
		close(conn);
		return result;
	}

	/** [신고] 블랙리스트 등록 Service
	 * @param newReport
	 * @return result
	 * @throws Exception
	 */
	public int updateReportStatus(String newReport) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateReportStatus(conn, newReport);
		if(result > 0)	commit(conn);
		else			rollback(conn);
		close(conn);
		return result;
	}
	
}
