package com.boss.blueSpring.mypage.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.challenge.model.vo.Attachment;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.comment.model.vo.Comment;
import com.boss.blueSpring.member.model.dao.MemberDAO;
import com.boss.blueSpring.member.model.vo.Member;

public class MypageDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public MypageDAO() {
		try {
			String filePath
			= MemberDAO.class.getResource("/com/boss/blueSpring/sql/mypage/mypage-query.xml").getPath();
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**  내 정보 수정 확인용 비밀번호 DAO
	 * @param conn
	 * @param memberNo
	 * @param pwd
	 * @return result
	 * @throws Exception
	 */
	public int myInfoPwCheck(Connection conn, int memberNo, String pwd) throws Exception {
		int result = 0;
		String query = prop.getProperty("myInfoPwCheck");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, pwd);
			
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

	/** 내 정보 수정 - 비밀번호 변경 DAO
	 * @param conn
	 * @param newPw
	 * @param memNo
	 * @return result
	 * @throws Exception
	 */
	public int changePw(Connection conn, String newPw, int memNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("changePw");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newPw);
			pstmt.setInt(2, memNo);
			
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 내 정보 수정 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Connection conn, Member member) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberNickname());
			pstmt.setString(2, member.getMemberPhone());
			pstmt.setString(3, member.getMemberAddr());
			pstmt.setInt(4, member.getMemberNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 닉네임 중복 체크 DAO
	 * @param conn
	 * @param nickname
	 * @return result
	 * @throws Exception
	 */
	public int nicknameDubCheck(Connection conn, String nickname) throws Exception {
		int result = 0;
		String query = prop.getProperty("nicknameDupCheck");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nickname);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	/** 회원 탈퇴 DAO
	 * @param conn
	 * @param memNo
	 * @return result
	 * @throws Exception
	 */
	public int updateStatus(Connection conn, int memNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateStatus");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 페이징 처리를 위한 값 계산 DAO
	 * @param conn
	 * @return result
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String memId) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
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
	
	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param memId 
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectBoardList(Connection conn, PageInfo pInfo, String cn, String memId) throws Exception {
		
		List<Board> bList = null;
		
		String query = prop.getProperty("selectBoardList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			pstmt.setString(1, memId);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<Board>();
			
			while(rset.next()) {
				Board board = new Board(rset.getInt("BRD_NO"), 
						rset.getString("BRD_TITLE"),
						rset.getString("MEM_ID"), 
						rset.getInt("BRD_VIEWS"),
						rset.getString("CATEGORY_NM"), 
						rset.getTimestamp("BRD_CRT_DT"),
						rset.getInt("LIKES"));
				bList.add(board);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return bList;
	}

	/** 댓글 목록 DAO
	 * @param conn
	 * @param parentBoardNo
	 * @param memId
	 * @return cList
	 * @throws Exception
	 */
	public List<Comment> selectList(Connection conn,PageInfo pInfo, String cn, String memId) throws Exception{
		List<Comment> cList = null;
		
		String query = prop.getProperty("selectList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			cList = new ArrayList<Comment>();
			

			
			while(rset.next()) {
				Comment comment = new Comment(rset.getInt("COM_NO"), 
											  rset.getString("COM_CONTENT"),
											  rset.getTimestamp("COM_CRT_DT"),
											  rset.getInt("BRD_NO"), 
											  rset.getString("MEM_ID"), 
											  rset.getString("COM_DEL_FL"));
				cList.add(comment);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return cList;
	}

	/** 댓글 페이징 처리를 위한 값 계산 DAO
	 * @param conn
	 * @param memId
	 * @return result
	 * @throws Exception
	 */
	public int getCommentPageInfo(Connection conn, String memId) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("getCommentListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
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

	/** 내가 참여한 챌린지 수 계산 DAO
	 * @param conn
	 * @param memId
	 * @return listCount
	 * @throws Exception
	 */
	public int getMyChallengeCount(Connection conn, String memId) throws Exception{
		int listCount = 0;
		
		String query = prop.getProperty("getMyChallengeCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	/** 내 챌린지 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param memId
	 * @return list
	 * @throws Exception
	 */
	public List<Challenge> selectChallengeList(Connection conn, PageInfo pInfo, String memId) throws Exception {
		List<Challenge> list = null;
		
		String query = prop.getProperty("selectChallengeList"); 

		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;   
			int endRow = startRow + pInfo.getLimit() - 1; 
						
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, memId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			
			rset = pstmt.executeQuery();
			
			// 오류 없을시 ArrayList 생성
			list = new ArrayList<Challenge>();
			
			while(rset.next()) {
				
				Challenge challenge = new Challenge();
				challenge.setChlngNo(  	rset.getInt("CHLNG_NO")  );
				challenge.setChlngTitle(  rset.getString("CHLNG_TITLE")  );
				challenge.setChlngStartDt(  rset.getTimestamp ("STR_DT")  );
				challenge.setChlngEndDt(  rset.getTimestamp ("END_DT")  );
				challenge.setLikeCount(  	rset.getInt("LIKE_COUNT")  );
				challenge.setchlngCateNm(  	rset.getString("CHLNG_CATE_NM")  );
				
				
				list.add(challenge);
				
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	/**  썸네일 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(Connection conn, PageInfo pInfo) throws Exception {
		List<Attachment> fList = null;
		
		
		String query = prop.getProperty("selectThumbnailList");
		
		try {
			// 위치 홀더에 들어갈 시작 행, 끝 행번호 계산
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			// 조회 결과를 저장할 List 생성
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				
				Attachment at = new Attachment();
				at.setParentChNo(rset.getInt("CHLNG_NO"));
				at.setFileName(rset.getString("C_FILE_NAME"));
				
				
				fList.add(at);
			}
		
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return fList;
	}

	/** 작성한 인증게시글 계산 DAO
	 * @param conn
	 * @param memId
	 * @return result
	 * @throws Exception
	 */
	public int getcrtfdPageInfo(Connection conn, String memId) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("getcrtfdPageInfo");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
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

	/** 작성한 인증게시글 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param cn
	 * @param memId
	 * @return bList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectcrtfdList(Connection conn, PageInfo pInfo, String cn, String memId) throws Exception {
		List<ChallengeCrtfd> bList = null;
		
		String query = prop.getProperty("selectcrtfdList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			pstmt.setString(3, memId);
			
			rset = pstmt.executeQuery();

			bList = new ArrayList<ChallengeCrtfd>();
			while(rset.next()) {
				ChallengeCrtfd board = new ChallengeCrtfd(rset.getInt("CHLNG_BRD_NO"),
						rset.getTimestamp("CHLNG_BRD_CRT_DT"),
						rset.getString("CHLNG_BRD_DEL_FL").charAt(0),
						rset.getInt("CHLNG_BRD_VIEWS"), 
						rset.getInt("CHLNG_NO"),
						rset.getString("MEM_ID"), 
						rset.getString("CHLNG_BRD_TITLE"), 
						rset.getString("CHLNG_CATE_NM"));
						
				bList.add(board);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return bList;
	}

	/** 마이페이지 메인 내가 쓴 게시글
	 * @param conn
	 * @param memId
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> selectMainBoard(Connection conn, String memId) throws Exception {
		List<Board> bList = null;
		String query = prop.getProperty("selectMainBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
			rset = pstmt.executeQuery();
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

	/** 마이페이지 메인 내가 쓴 댓글
	 * @param conn
	 * @param memNo
	 * @return cList
	 * @throws Exception
	 */
	public List<Comment> selectMainComment(Connection conn, String memId)  throws Exception {
		List<Comment> cList = null;
		String query = prop.getProperty("selectMainComment");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
			rset = pstmt.executeQuery();
			cList = new ArrayList<Comment>();
			
			while(rset.next()) {
				Comment comment = new Comment(rset.getInt("COM_NO"), 
											  rset.getString("COM_CONTENT"),
											  rset.getTimestamp("COM_CRT_DT"),
											  rset.getInt("BRD_NO"), 
											  rset.getString("MEM_ID"), 
											  rset.getString("COM_DEL_FL"));
				cList.add(comment);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return cList;
	}

	/** 마이페이지 메인 역대 챌린지 
	 * @param conn
	 * @param memNo 
	 * @return asList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectMainacList(Connection conn, String memId) throws Exception {
		List<ChallengeCrtfd> acList = null;
		String query = prop.getProperty("selectMainctcrtfd");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
			rset = pstmt.executeQuery();
			acList = new ArrayList<ChallengeCrtfd>();
			while(rset.next()) {
				ChallengeCrtfd crtfd = new ChallengeCrtfd(rset.getInt("CHLNG_NO"),
						rset.getTimestamp("CHLNG_BRD_CRT_DT"),
						rset.getString("CHLNG_BRD_DEL_FL").charAt(0),
						rset.getInt("CHLNG_BRD_VIEWS"), 
						rset.getInt("CHLNG_BRD_NO"),
						rset.getString("MEM_ID"), 
						rset.getString("CHLNG_BRD_TITLE"), 
						rset.getString("CHLNG_CATE_NM"));
				acList.add(crtfd);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return acList;
	}

	/** 현재 참여중인 챌린지 조회 DAO
	 * @param conn
	 * @param memId
	 * @return nc
	 * @throws Exception
	 */
	public Challenge nowChallenge(Connection conn, String memId) throws Exception{
		Challenge nc = null;
		
		String query = prop.getProperty("nowChallenge");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
			
			rset = pstmt.executeQuery();
	
			//(int chlngNo, String chlngTitle, String chlngContent, Timestamp chlngStartDt, Timestamp chlngEndDt,
			//String chlngCateNm, int likeCount)
		if(rset.next()) {
			nc = new Challenge(rset.getInt("CHLNG_NO"),
								rset.getString("CHLNG_TITLE"),
								rset.getString("CHLNG_CONTENT"),
								rset.getTimestamp ("STR_DT"),
								rset.getTimestamp ("END_DT"),
								rset.getString("CHLNG_CATE_NM"),
								rset.getInt("LIKE_COUNT")
								);
	
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return nc;
	}

	/** 현재 참여중인 챌린지 썸네일 DAO
	 * @param conn
	 * @param memId
	 * @return thumbnail
	 * @throws Exception
	 */
	public Attachment nowThumbnail(Connection conn, String memId) throws Exception {
		Attachment thumbnail = null;
		
		String query = prop.getProperty("nowThumbnail");
		
		try {

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
					thumbnail = new Attachment(
							rset.getString("C_FILE_NAME"),
							rset.getInt("CHLNG_NO")
							);

			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return thumbnail;
	}

	/** 현재 참여중인 챌린지 달성률 DAO
	 * @param conn
	 * @param memNo
	 * @param challengeNo
	 * @return result 
	 * @throws Exception
	 */
	public int progressBar(Connection conn, int memNo, int challengeNo) throws Exception{
		int result = 0;
		String query = prop.getProperty("progressBar");
		
		try {

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, challengeNo);
			pstmt.setInt(3, challengeNo);
			
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




}
