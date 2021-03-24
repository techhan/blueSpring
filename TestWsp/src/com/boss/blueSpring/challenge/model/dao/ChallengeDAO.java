package com.boss.blueSpring.challenge.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.boss.blueSpring.challenge.model.vo.Attachment;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challenge.model.vo.Like;
import com.boss.blueSpring.challenge.model.vo.PageInfo;

public class ChallengeDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	Properties prop = null;
	
	public ChallengeDAO() {
		String fileName = ChallengeDAO.class.getResource("/com/boss/blueSpring/sql/challenge/challenge-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 전체 챌린지 수 반환 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getChallengeCount(Connection conn) throws Exception{
		int listCount = 0;
		
		String query = prop.getProperty("getChallengeCount");
		
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


	/** 공지사항 목록 조회
	 * @param conn
	 * @param orderBy
	 * @return list
	 * @throws Exception
	 */
	public List<Challenge> selectList(Connection conn, PageInfo pInfo, String orderBy) throws Exception{
		List<Challenge> list = null;
		
		//String query = prop.getProperty("selectList");         //+ end
		String query = "SELECT* FROM " + 
						"	(SELECT ROWNUM RNUM, V.* " + 
						"	FROM\r\n" + 
						"		(SELECT * FROM V_CHLNG_MISSION_LIST WHERE CHLNG_FL = 'N' " +
				    	" ORDER BY " + orderBy + " CHLNG_NO DESC) V ) " + 
						"WHERE RNUM BETWEEN ? AND ? " 
						;
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;    // 시작은 1부터 
			int endRow = startRow + pInfo.getLimit() - 1; // 9?????????
						
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
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


	/** 챌린지 상세 조회 DAO
	 * @param conn
	 * @param challengeNo
	 * @return challenge
	 * @throws Exception
	 */
	public Challenge selectChallenge(Connection conn, int challengeNo) throws Exception{
		Challenge challenge = null;
		
		String query = prop.getProperty("selectChallenge");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, challengeNo);
			
			rset = pstmt.executeQuery();
			
			//챌린지 번호 , 제목, 내용, 작성자(닉네임), 시작일, 종료일, 카테고리, 좋아요
			if(rset.next()) {
				challenge = new Challenge();
				
				challenge.setChlngNo(rset.getInt("CHLNG_NO"));
				challenge.setChlngTitle(rset.getString("CHLNG_TITLE"));
				challenge.setChlngContent(rset.getString("CHLNG_CONTENT"));
				challenge.setMemNickname(rset.getString("MEM_NICKNAME"));
				challenge.setChlngStartDt(rset.getTimestamp("STR_DT"));
				challenge.setChlngEndDt(rset.getTimestamp("END_DT"));
				challenge.setchlngCateNm(rset.getString("CHLNG_CATE_NM"));
				challenge.setLikeCount(rset.getInt("LIKE_COUNT"));
				challenge.setMemberId(rset.getString("MEM_ID"));
			}
			
			
			
		} finally {
			close(rset);
			close(pstmt);
	
		}
		return challenge;
	}


	/** 다음 챌릴지 번호 조회
	 * @param conn
	 * @return chlngNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception{
		int chlngNo = 0;
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				chlngNo = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return chlngNo;
	}


	/** 챌린지 삽입
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertChallenge(Connection conn, Map<String, Object> map) throws Exception{

		int result = 0;
		
		String query = prop.getProperty("insertChallenge");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, (int)map.get("chlngNo"));
			pstmt.setString(2, (String)map.get("chlngTitle"));
			pstmt.setString(3, (String)map.get("chlngContent"));
			pstmt.setString(4, (String)map.get("chlngStartDt"));
			pstmt.setString(5, (String)map.get("chlngEndDt"));
			pstmt.setInt(6, (int)map.get("chlngeWriter"));    // 글을 작성한 멤버 번호가 들어가있음
			pstmt.setInt(7, (int)map.get("chlngCateNo"));
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}


	/** 파일 정보 삽입
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, at.getFilePath());
			pstmt.setString(2, at.getFileName());
			pstmt.setInt(3, at.getParentChNo());
			pstmt.setInt(4, at.getFileLevel());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	
	
	
	
	
	/** 챌린지 삭제 여부 (상테 업데이트) DAO
	 * @param conn
	 * @param chlngNo
	 * @return result
	 * @throws Exception
	 */
	public int updateChFl(Connection conn, int chlngNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("updateChFl");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, chlngNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 해당 글에 포함되는 이미지들 목록 조회 DAO
	 * @param conn
	 * @param challengeNo
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> selectChallengeFiles(Connection conn, int challengeNo) throws Exception{

		List<Attachment> fList = null;
		
		String query = prop.getProperty("selectChallengeFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, challengeNo);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				Attachment at = new Attachment(
						rset.getInt("CHLNG_IMG_NO"),
						rset.getString("C_FILE_NAME"),
						rset.getInt("FILE_LEVEL"));
				
				at.setFilePath(rset.getString("FILE_PATH"));
				
				fList.add(at);
			}
		
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return fList;
	}


	/** 파일 레벨이 0인 사진(대표사진)을 가져오기 위한  DAO
	 * @param conn
	 * @param challengeNo
	 * @return fmList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbFiles(Connection conn, PageInfo pInfo) throws Exception{
		List<Attachment> fmList = null;
		
		String query = prop.getProperty("selectThumbFiles");
		
		try {
			// 위치홀더에 들어갈 시작행, 끝 행번호 계산
			int startRow = (pInfo.getCurrentPage()-1) * pInfo.getLimit() + 1;    // 1행부터 시작
			int endRow = startRow + pInfo.getLimit() -1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			fmList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				
				Attachment at = new Attachment();
				at.setParentChNo(rset.getInt("CHLNG_NO"));
				at.setFileName(rset.getString("C_FILE_NAME"));
				
				
				fmList.add(at);
			}
		
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return fmList;
	}


	/**  챌린지 좋아요 가져오기DAO
	 * @param conn
	 * @param challengeNo
	 * @param memberNo
	 * @return likeInfo
	 * @throws Exception
	 */
	public Like selectLike(Connection conn, int challengeNo, int memberNo) throws Exception{
		Like likeInfo = null;
		
		String query = "SELECT * FROM CHALLENGE_LIKES WHERE CHLNG_NO = ? AND MEM_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, challengeNo);
			pstmt.setInt(2, memberNo);
			
			rset = pstmt.executeQuery();
			
			likeInfo = new Like();
			
			if(rset.next()) {
				Like like = new Like();
				like.setChallengeNo(rset.getInt("CHLNG_NO"));
				like.setMemberNo(rset.getInt("MEM_NO"));
				
				likeInfo = like;
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return likeInfo;
	}


	/** 좋아요 증가
	 * @param conn
	 * @param chlngNo
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int insertLikes(Connection conn, int chlngNo, int memberNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("insertLikes");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, chlngNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 좋아요 감소
	 * @param conn
	 * @param chlngNo
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteLikes(Connection conn, int chlngNo, int memberNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("deleteLikes");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, chlngNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 챌린지 조인 DAO
	 * @param conn
	 * @param chlngNo
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int join(Connection conn, int chlngNo, int memberNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("join");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, chlngNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}


	/** 챌린지 참여 여부
	 * @param conn
	 * @param challengeNo
	 * @param memberNo
	 * @return check
	 * @throws Exception
	 */
	public int check(Connection conn, int challengeNo, int memberNo) throws Exception{

		int check = 0;
		
		String query = prop.getProperty("check");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, challengeNo);
			pstmt.setInt(2, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				check = rset.getInt(1);
			}
			
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return check;
	}


	/** 챌린지 수정 DAO
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateChallenge(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateChallenge");
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, (String)map.get("chlngTitle"));
			pstmt.setString(2, (String)map.get("chlngContent"));
			pstmt.setString(3, (String)map.get("chlngStartDt"));
			pstmt.setString(4, (String)map.get("chlngEndDt"));
			pstmt.setString(5, (String)map.get("chlngCateNm"));
			pstmt.setInt(6, (int)map.get("chlngNo"));
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}

	
	/** 챌린지 파일 정보 수정 DAO
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
	public int updateAttachment(Connection conn, Attachment newFile)  throws Exception{
		int result = 0;
		String query = prop.getProperty("updateAttachment");
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, newFile.getFilePath());
			pstmt.setString(2, newFile.getFileName());
			pstmt.setInt(3, newFile.getFileNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
