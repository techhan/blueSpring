package com.boss.blueSpring.admin.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.boss.blueSpring.admin.model.vo.BlacklistPageInfo;
import com.boss.blueSpring.admin.model.vo.CenterPageInfo;
import com.boss.blueSpring.admin.model.vo.ChallCrtfdPageInfo;
import com.boss.blueSpring.admin.model.vo.ChallPageInfo;
import com.boss.blueSpring.admin.model.vo.MemberPageInfo;
import com.boss.blueSpring.admin.model.vo.ReportPageInfo;
import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.center.model.vo.Center;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.member.model.vo.Member;
import com.boss.blueSpring.report.model.vo.Report;

public class AdminSearchDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	
	// ************************************************************************* 자유게시판
	
	/** [자유게시판] 관리: 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String query = "SELECT COUNT(*) FROM V_BOARD WHERE " + condition;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}


	/** [자유게시판] 관리 : 검색 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return aList
	 * @throws Exception
	 */
	public List<Board> searchBoardList(Connection conn, PageInfo pInfo, String condition) throws Exception {
		List<Board> aList = null;
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT BRD_NO, BRD_TITLE, MEM_ID, BRD_DEL_FL FROM V_BOARD " + 
				"        WHERE " + condition + 
				"        ORDER BY BRD_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		try {
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() -1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			aList = new ArrayList<Board>();
			while(rset.next()) {
				Board board = new Board(
	                		   			rset.getInt("BRD_NO"), 
	                		   			rset.getString("BRD_TITLE"),
	                		   			rset.getString("MEM_ID"),
	                		   			rset.getString("BRD_DEL_FL"));
				aList.add(board);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return aList;
	}


	// ************************************************************************* 신고
	
	/** [신고] 관리 : 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCountReport(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String query = "SELECT COUNT(*) FROM V_REPORT WHERE " + condition;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}
	
	/** [신고] 관리 : 목록 검색 DAO
	 * @param conn
	 * @param rpInfo
	 * @param condition
	 * @return rList
	 * @throws Exception
	 */
	public List<Report> searchReportList(Connection conn, ReportPageInfo rpInfo, String condition) throws Exception {
		List<Report> rList = null;
		String query = 
				"SELECT * FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM " + 
				"        (SELECT REPORT_NO, REPORT_TYPE, BRD_NO, REPORT_CATE_NO, MEM_ID, TARGET_ID, MEM_BLACKLIST FROM V_REPORT " + 
				"        WHERE " + condition + 
				"        ORDER BY REPORT_NO DESC) V ) " + 
				" WHERE RNUM BETWEEN ? AND ?";
		try {
			int startRow = (rpInfo.getCurrentPage() -1) * rpInfo.getLimit() + 1;
			int endRow = startRow + rpInfo.getLimit() -1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			rList = new ArrayList<Report>();
			while(rset.next()) {
				Report report = new Report(
						   				rset.getInt("REPORT_NO"),
						   				rset.getString("REPORT_TYPE"),
						   				rset.getInt("BRD_NO"),
						   				rset.getInt("REPORT_CATE_NO"),
						   				rset.getString("MEM_ID"),
						   				rset.getString("TARGET_ID"),
						   				rset.getString("MEM_BLACKLIST"));
				rList.add(report);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return rList;
	}
	
	// ************************************************************************* 센터

	/** [센터] 관리 : 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCountCenter(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String query = "SELECT COUNT(*) FROM V_CENTER WHERE " + condition;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	/** [센터] 관리 : 목록 검색 DAO
	 * @param conn
	 * @param cpInfo
	 * @param condition
	 * @return cList
	 * @throws Exception
	 */
	public List<Center> searchCenterList(Connection conn, CenterPageInfo cpInfo, String condition) throws Exception {
		List<Center> cList = null;
		String query = 
				"SELECT * FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM " + 
				"        (SELECT CENTER_NO, CENTER_CLA, CENTER_AREA1, CENTER_AREA2, CENTER_NM, CENTER_TEL, CENTER_URL, CENTER_DEL_FL FROM V_CENTER " + 
				"        WHERE " + condition + 
				"        ORDER BY CENTER_NO DESC) V ) " + 
				" WHERE RNUM BETWEEN ? AND ?";
		try {
			int startRow = (cpInfo.getCurrentPage() -1) * cpInfo.getLimit() + 1;
			int endRow = startRow + cpInfo.getLimit() -1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			cList = new ArrayList<Center>();
			while(rset.next()) {
				Center center = new Center(
						rset.getInt("CENTER_NO"), 
						rset.getString("CENTER_CLA"),
						rset.getString("CENTER_AREA1"), 
						rset.getString("CENTER_AREA2"), 
						rset.getString("CENTER_NM"),
						rset.getString("CENTER_TEL"), 
						rset.getString("CENTER_URL"), 
						rset.getString("CENTER_DEL_FL").charAt(0));
				cList.add(center);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return cList;
	}

	// ************************************************************************* 회원정보

	/** [회원정보] 관리 : 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCountMember(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String query = "SELECT COUNT(*) FROM V_MEMBER WHERE " + condition;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	/** [회원정보] 관리 : 목록 검색 DAO
	 * @param conn
	 * @param mpInfo
	 * @param condition
	 * @return mList
	 * @throws Exception
	 */
	public List<Member> searchMemberList(Connection conn, MemberPageInfo mpInfo, String condition) throws Exception {
		List<Member> mList = null;
		String query = 
				"SELECT * FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM " + 
				"        (SELECT MEM_NO, MEM_ID, MEM_NICKNAME, MEM_NM, MEM_BIRTH, MEM_GENDER, MEM_PHONE, MEM_ADDR, MEM_JOINED, MEM_SCSN_FL, MEM_BLACKLIST, MEM_LEVEL FROM V_MEMBER " + 
				"        WHERE " + condition + 
				"        ORDER BY MEM_NO DESC) V ) " + 
				" WHERE RNUM BETWEEN ? AND ?";
		try {
			int startRow = (mpInfo.getCurrentPage() -1) * mpInfo.getLimit() + 1;
			int endRow = startRow + mpInfo.getLimit() -1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Member>();
			while (rset.next()) {
				Member member = new Member(
						rset.getInt("MEM_NO"),
						rset.getString("MEM_ID"),
						rset.getString("MEM_NICKNAME"), 
						rset.getString("MEM_NM"),
						rset.getDate("MEM_BIRTH"),
						rset.getString("MEM_GENDER").charAt(0), 
						rset.getString("MEM_PHONE"), 
						rset.getString("MEM_ADDR"),
						rset.getDate("MEM_JOINED"),
						rset.getString("MEM_SCSN_FL").charAt(0),
						rset.getString("MEM_BLACKLIST").charAt(0),
						rset.getString("MEM_LEVEL").charAt(0));
						mList.add(member);
				};
		} finally {
			close(rset);
			close(pstmt);
		}
		return mList;
	}

	
	// ************************************************************************* 블랙리스트
	
	/** [블랙리스트] 관리 : 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getBlackListCount(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String query = "SELECT COUNT(*) FROM V_MEMBER WHERE MEM_BLACKLIST = 'Y' AND " + condition;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	/** [블랙리스트] 관리 : 목록 검색 DAO
	 * @param conn
	 * @param bpInfo
	 * @param condition
	 * @return bkList
	 * @throws Exception
	 */
	public List<Member> searchBlackList(Connection conn, BlacklistPageInfo bpInfo, String condition) throws Exception {
		List<Member> bkList = null;
		String query = 
				"SELECT * FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM " + 
				"        (SELECT MEM_NO, MEM_ID, MEM_NICKNAME, MEM_NM, MEM_BIRTH, MEM_GENDER, MEM_PHONE, MEM_JOINED, MEM_SCSN_FL, MEM_BLACKLIST, MEM_LEVEL FROM V_MEMBER " + 
				"        WHERE MEM_BLACKLIST = 'Y' AND " + condition + 
				"        ORDER BY MEM_NO DESC) V ) " + 
				" WHERE RNUM BETWEEN ? AND ?";
		try {
			int startRow = (bpInfo.getCurrentPage() -1) * bpInfo.getLimit() + 1;
			int endRow = startRow + bpInfo.getLimit() -1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			bkList = new ArrayList<Member>();
			while (rset.next()) {
				Member member = new Member(
						rset.getInt("MEM_NO"),
						rset.getString("MEM_ID"),
						rset.getString("MEM_NICKNAME"), 
						rset.getString("MEM_NM"),
						rset.getDate("MEM_BIRTH"),
						rset.getString("MEM_GENDER").charAt(0), 
						rset.getString("MEM_PHONE"), 
						rset.getDate("MEM_JOINED"),
						rset.getString("MEM_SCSN_FL").charAt(0),
						rset.getString("MEM_BLACKLIST").charAt(0),
						rset.getString("MEM_LEVEL").charAt(0));
						bkList.add(member);
				};
		} finally {
			close(rset);
			close(pstmt);
		}
		return bkList;
	}


	// ************************************************************************* 챌린지

	/** [챌린지] 관리 : 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getChallListCount(Connection conn, String condition) throws Exception {
		int listCount = 0; 
		String query = "SELECT COUNT(*) FROM V_CHALLENGE_MISSION WHERE " + condition;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	/** [챌린지] 관리 : 목록 검색 DAO
	 * @param conn
	 * @param chpInfo
	 * @param condition
	 * @return chList
	 * @throws Exception
	 */
	public List<Challenge> searchChallList(Connection conn, ChallPageInfo chpInfo, String condition) throws Exception {
		List<Challenge> chList = null;
		String query = 
				"SELECT * FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM " + 
				"        (SELECT CHLNG_NO, CHLNG_TITLE, MEM_ID, CHLNG_FL FROM V_CHALLENGE_MISSION " + 
				"        WHERE " + condition + 
				"        ORDER BY CHLNG_NO DESC) V ) " + 
				" WHERE RNUM BETWEEN ? AND ?";
		try {
			int startRow = (chpInfo.getCurrentPage() -1) * chpInfo.getLimit() + 1;
			int endRow = startRow + chpInfo.getLimit() -1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			chList = new ArrayList<Challenge>();
			while (rset.next()) {
				Challenge challenge = new Challenge(
						rset.getInt("CHLNG_NO"),
						rset.getString("CHLNG_TITLE"),
						rset.getString("MEM_ID"), 
						rset.getString("CHLNG_FL").charAt(0));
						chList.add(challenge);
				};
		} finally {
			close(rset);
			close(pstmt);
		}
		return chList;
	}

	// ************************************************************************* 챌린지 인증게시판

	/** [챌린지 인증게시판] 관리 : 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getChallCrtfdListCount(Connection conn, String condition) throws Exception {
		int listCount = 0; 
		String query = "SELECT COUNT(*) FROM V_CHALLENGERS WHERE " + condition;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	/** [챌린지 인증게시판] 관리 : 목록 검색 DAO
	 * @param conn
	 * @param crtpInfo
	 * @param condition
	 * @return crtList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> searchChallCrtfdList(Connection conn, ChallCrtfdPageInfo crtpInfo, String condition) throws Exception {
		List<ChallengeCrtfd> crtList = null;
		String query = 
				"SELECT * FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM " + 
				"        (SELECT CHLNG_NO, CHLNG_BRD_NO, MEM_ID, CHLNG_BRD_DEL_FL, CHLNG_BRD_TITLE FROM V_CHALLENGERS " + 
				"        WHERE " + condition + 
				"        ORDER BY CHLNG_BRD_NO DESC) V ) " + 
				" WHERE RNUM BETWEEN ? AND ?";
		try {
			int startRow = (crtpInfo.getCurrentPage() -1) * crtpInfo.getLimit() + 1;
			int endRow = startRow + crtpInfo.getLimit() -1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			crtList = new ArrayList<ChallengeCrtfd>();
			while (rset.next()) {
				ChallengeCrtfd challengeCrtfd = new ChallengeCrtfd(
						rset.getInt("CHLNG_NO"),
						rset.getInt("CHLNG_BRD_NO"),
						rset.getString("CHLNG_BRD_TITLE"),
						rset.getString("MEM_ID"), 
						rset.getString("CHLNG_BRD_DEL_FL").charAt(0));
						crtList.add(challengeCrtfd);
				};
		} finally {
			close(rset);
			close(pstmt);
		}
		return crtList;
	}
	
	





	

}
