package com.boss.blueSpring.main.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.main.model.vo.MainPageInfo;
import com.boss.blueSpring.main.model.vo.MainSearch;
import com.boss.blueSpring.member.model.vo.Member;
import com.boss.blueSpring.notice.model.vo.Notice;

public class MainDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public MainDAO() {
		String fileName = MainDAO.class.getResource("/com/boss/blueSpring/sql/main/main-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		

	/** [메인] 공지사항 조회 DAO
	 * @param conn
	 * @return nList
	 * @throws Exception
	 */
	public List<Notice> selectMainNotice(Connection conn) throws Exception {
		List<Notice> nList = null;
		String query = prop.getProperty("selectMainNotice");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			nList = new ArrayList<Notice>();
			while(rset.next()) {
				Notice notice = new Notice();
				notice.setNoticeNo(rset.getInt("NOTICE_NO"));
				notice.setNoticeTitle(rset.getString("NOTICE_TITLE"));
				nList.add(notice);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return nList;
	}

	/** [메인] 자유게시판 조회 DAO
	 * @param conn
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectMainBoard(Connection conn) throws Exception {
		List<Board> bList = null;
		String query = prop.getProperty("selectMainBoard");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			bList = new ArrayList<Board>();
			while(rset.next()) {
				Board board = new Board();
				board.setBoardNo(rset.getInt("BRD_NO"));
				board.setCategoryName(rset.getString("CATEGORY_NM"));
				board.setBoardTitle(rset.getString("BRD_TITLE"));
				bList.add(board);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return bList;
	}

	/** [메인] 챌린지 조회 DAO
	 * @param conn
	 * @return cList
	 * @throws Exception
	 */
	public List<Challenge> selectMainChallenge(Connection conn) throws Exception {
		List<Challenge> cList = null;
		String query = prop.getProperty("selectMainChallenge");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			cList = new ArrayList<Challenge>();
			while(rset.next()) {
				Challenge challenge = new Challenge();
				challenge.setChlngNo(rset.getInt("CHLNG_NO"));
				challenge.setchlngCateNm(rset.getString("CHLNG_CATE_NM"));
				challenge.setChlngTitle(rset.getString("CHLNG_TITLE"));
				cList.add(challenge);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return cList;
	}

	/** [메인] 챌린지 인증게시판 조회 DAO
	 * @param conn
	 * @return crtList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectMainChallengeCrtfd(Connection conn) throws Exception {
		List<ChallengeCrtfd> crtList = null;
		String query = prop.getProperty("selectMainChallengeCrtfd");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			crtList = new ArrayList<ChallengeCrtfd>();
			while(rset.next()) {
				ChallengeCrtfd challengeCrtfd = new ChallengeCrtfd();
				challengeCrtfd.setChlngBoardNo(rset.getInt("CHLNG_BRD_NO"));
				challengeCrtfd.setChlngCateNm(rset.getString("CHLNG_CATE_NM"));
				challengeCrtfd.setChlngBoardTitle(rset.getString("CHLNG_BRD_TITLE"));
				crtList.add(challengeCrtfd);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return crtList;
	}


	public int getListCount(Connection conn, String sv) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sv);
			pstmt.setString(2, sv);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(pstmt);
			
		}
		
		return result;
	}


	public List<MainSearch> searchMainList(Connection conn, MainPageInfo mpInfo, String sv) throws Exception {
		List<MainSearch> mList = null;
		String query = prop.getProperty("searchMainList");
		
		try {
			int startRow = (mpInfo.getCurrentPage() - 1) * mpInfo.getLimit() + 1;
			int endRow = startRow + mpInfo.getLimit() - 1;
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, sv);
			pstmt.setString(2, sv);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			mList = new ArrayList<MainSearch>();
			while (rset.next()) {
					MainSearch mainsearch = new MainSearch(
							rset.getInt("NO"),
							rset.getString("TITLE"),
							rset.getString("CONTENT"), 
							rset.getDate("CRT_DT"),
							rset.getInt("TYPE"),
							rset.getString("DEL_FL")); 
							mList.add(mainsearch);
					}
		} finally {
			close(rset);
			close(pstmt);
		}
		return mList;
	}
	
	
	
	
	
	

}
