package com.boss.blueSpring.admin.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Statement;

public class AdminDeleteDAO {
	
	private Statement stmt = null;
	
	/** [자유게시판] 게시글 삭제 DAO
	 * @param conn
	 * @param newNumbers
	 * @return result
	 * @throws Exception
	 */
	public int updateBoardStatus(Connection conn, String newNumbers) throws Exception {
		int result = 0;
		String query = "UPDATE BOARD SET BRD_DEL_FL = 'Y' WHERE " + newNumbers;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}

	/** [블랙리스트] 삭제 DAO
	 * @param conn
	 * @param newBlack
	 * @return result
	 * @throws Exception
	 */
	public int updateBlackStatus(Connection conn, String newBlack) throws Exception {
		int result = 0;
		String query = "UPDATE MEMBER SET MEM_BLACKLIST = 'N' WHERE " + newBlack;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}

	/** [챌린지] 게시글 삭제 DAO
	 * @param conn
	 * @param newChall
	 * @return result
	 * @throws Exception
	 */
	public int updateChallStatus(Connection conn, String newChall) throws Exception {
		int result = 0;
		String query = "UPDATE CHALLENGE_MISSION SET CHLNG_FL = 'Y' WHERE " + newChall;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}

	/** [챌린지 인증] 게시글 삭제 DAO
	 * @param conn
	 * @param newCrtfd
	 * @return result
	 * @throws Exception
	 */
	public int updateCrtfdStatus(Connection conn, String newCrtfd) throws Exception {
		int result = 0;
		String query = "UPDATE CHALLENGERS SET CHLNG_BRD_DEL_FL = 'Y' WHERE " + newCrtfd;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}

	
	/** [센터] 삭제 DAO
	 * @param conn
	 * @param newCenter
	 * @return result
	 * @throws Exception
	 */
	public int updateCenterStatus(Connection conn, String newCenter) throws Exception {
		int result = 0;
		String query = "UPDATE CENTER SET CENTER_DEL_FL = 'Y' WHERE " + newCenter;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}

	
	/** [신고] 블랙리스트 등록 DAO
	 * @param conn
	 * @param newReport
	 * @return result
	 * @throws Exception
	 */
	public int updateReportStatus(Connection conn, String newReport) throws Exception {
		int result = 0;
		String query = "UPDATE MEMBER SET MEM_BLACKLIST = 'Y' WHERE " + newReport;
		
		System.out.println(query);
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}
	
	
	
	
	



}
