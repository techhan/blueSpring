package com.boss.blueSpring.challengecrtfd.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.boss.blueSpring.challengecrtfd.model.vo.Attachment;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.challengecrtfd.model.vo.PageInfo;
import com.boss.blueSpring.notice.model.dao.NoticeDAO;

public class ChallengeCrtfdDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	Properties prop = null;
	
	public ChallengeCrtfdDAO() {
		String fileName = NoticeDAO.class.getResource("/com/boss/blueSpring/sql/challengecrtfd/challengecrtfd-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 전체 공지글 수 반환 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception{
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
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
	 * @param pInfo
	 * @param orderBy 
	 * @return
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> selectList(Connection conn, PageInfo pInfo, String orderBy) throws Exception{
		List<ChallengeCrtfd> list = null;
		
		//String query = prop.getProperty("selectList");         //+ end
		String query = "SELECT* FROM " + 
						"	(SELECT ROWNUM RNUM, V.* " + 
						"	FROM\r\n" + 
						"		(SELECT * FROM V_CHALLENGERS WHERE CHLNG_BRD_DEL_FL = 'N' " +
				    	" ORDER BY " + orderBy + " CHLNG_BRD_NO DESC) V ) " + 
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
			list = new ArrayList<ChallengeCrtfd>();
			
			while(rset.next()) { ///번호, 제목, 작성자 닉네임, 조회수, 작성일
				
				ChallengeCrtfd challengeCrtfd = new ChallengeCrtfd();
				challengeCrtfd.setChlngBoardNo(  	rset.getInt("CHLNG_BRD_NO")  );
				challengeCrtfd.setChlngBoardTitle(  rset.getString("CHLNG_BRD_TITLE")  );
				challengeCrtfd.setMemNickname(  rset.getString("MEM_NICKNAME")  );
				challengeCrtfd.setChlngBoardViews(  rset.getInt("CHLNG_BRD_VIEWS")  );
				challengeCrtfd.setChlngBoardCrtDt(  	rset.getTimestamp("CHLNG_BRD_CRT_DT")  );
				
				
				list.add(challengeCrtfd);
				
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}


	/** 인증글 상세조회
	 * @param conn
	 * @param challengeCrtfdNo
	 * @return challengeCrtfd
	 * @throws Exception
	 */
	public ChallengeCrtfd selectChallengeCrtfd(Connection conn, int challengeCrtfdNo) throws Exception{
		ChallengeCrtfd challengeCrtfd = null;
		 
		String query = prop.getProperty("selectChallengeCrtfd");
				
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, challengeCrtfdNo);
			
			rset = pstmt.executeQuery();
			
			// 인증글 번호, 제목 , 내용(인증 내용), 작성자(닉네인), 등록일, 조회수, 카테고리
			if(rset.next()) {
				challengeCrtfd = new ChallengeCrtfd();
				
				challengeCrtfd.setChlngBoardNo(rset.getInt("CHLNG_BRD_NO"));
				challengeCrtfd.setChlngBoardTitle(rset.getString("CHLNG_BRD_TITLE"));
				challengeCrtfd.setChlngBoardContent(rset.getString("CHLNG_BRD_CONTENT"));
				challengeCrtfd.setMemNickname(rset.getString("MEM_NICKNAME"));
				challengeCrtfd.setChlngBoardCrtDt(rset.getTimestamp("CHLNG_BRD_CRT_DT"));
				challengeCrtfd.setChlngBoardViews(rset.getInt("CHLNG_BRD_VIEWS"));
				challengeCrtfd.setMemberId(rset.getString("MEM_ID"));
				challengeCrtfd.setChlngStartDt(rset.getTimestamp("STR_DT"));
				challengeCrtfd.setChlngEndDt(rset.getTimestamp("END_DT"));
				challengeCrtfd.setChlngCateNm(rset.getString("CHLNG_CATE_NM"));
				challengeCrtfd.setChlngTitle(rset.getString("CHLNG_TITLE"));
				
			}
			
			
		} finally {
			close(rset);
			close(pstmt);
		}
		return challengeCrtfd;
	}


	/** 인증글에  포함되는 이미지  DAO
	 * @param conn
	 * @param challengeCrtfdNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectChallengeCrtfdFiles(Connection conn, int challengeCrtfdNo) throws Exception {

		List<Attachment> fList = null;
		
		String query = prop.getProperty("selectChallengeCrtfdFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, challengeCrtfdNo);
			
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


	/** 인증 삭제 여부 (상테 업데이트) DAO
	 * @param conn
	 * @param chlngBoardNo
	 * @return result
	 * @throws Exception
	 */
	public int updateChCrFl(Connection conn, int chlngBoardNo) throws Exception{

		int result = 0;
		
		String query = prop.getProperty("updateChCrFl");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, chlngBoardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/**
	 * @param conn
	 * @param memberNo
	 * @return joinInfo
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectjoinInfo(Connection conn, int memberNo) throws Exception{
	
		List<Map<String, Object>> joinInfo = null;
		
		String query = prop.getProperty("selectjoinInfo");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();
			
			joinInfo= new ArrayList<Map<String,Object>>();
			
			
			while(rset.next()){
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("challengeNo", rset.getInt(1));
				map.put("memberNo", rset.getInt(2));
				map.put("challengeTitle", rset.getString(3));
				map.put("chlngStartDt", rset.getTimestamp(4));
				map.put("chlngEndDt", rset.getTimestamp(5));
				map.put("memNickname", rset.getString(6));
				
				joinInfo.add(map);
			}
			
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return joinInfo;
	}


	/** 조회수 증가
	 * @param conn
	 * @param challengeCrtfdNo
	 * @return
	 */
	public int increaseReadCount(Connection conn, int challengeCrtfdNo) throws Exception{

		int result = 0;
		
		String query = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,	challengeCrtfdNo);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}

		return result;
	}


	/**. 다음 인증글 번호 조회
	 * @param conn
	 * @return chlngBoardNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception{
		int chlngBoardNo = 0;
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				chlngBoardNo = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return chlngBoardNo;
	}


	/** 챌린지 삽입
	 * @param conn
	 * @param map
	 * @return result
	 */
	public int insertChallengeCrtfd(Connection conn, Map<String, Object> map) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("insertChallengeCrtfd");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			//번호, 제목, 내용
			pstmt.setInt(1, (int)map.get("chlngBoardNo")); 
			pstmt.setInt(2, (int)map.get("chlngeWriter")); 
			pstmt.setString(3, (String)map.get("chlngNo"));
			pstmt.setString(4, (String)map.get("chlngCrContent"));
			pstmt.setString(5, (String)map.get("chlngCrTitle"));
			
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


	/** 인증글 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateChallengeCr(Connection conn, Map<String, Object> map) throws Exception{

		int result = 0;
		
		String query = prop.getProperty("updateChallengeCr");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("chlngCrTitle") );
			pstmt.setString(2, (String)map.get("chlngCrContent") );
			pstmt.setInt(3, (int)map.get("chlngNo") );
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 인증글 파일 수정 DAO
	 * @param conn
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
	public int updateAttachment(Connection conn, Attachment newFile) throws Exception{
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
