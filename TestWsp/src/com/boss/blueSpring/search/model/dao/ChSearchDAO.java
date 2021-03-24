package com.boss.blueSpring.search.model.dao;


import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.boss.blueSpring.challenge.model.vo.Attachment;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challenge.model.vo.PageInfo;

public class ChSearchDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	
	/**검색 내용이 포함된 페이징 처리 정보 생성 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getChListCount(Connection conn, String condition) throws Exception {

		int listCount = 0;
		
		String query = "SELECT COUNT(*) FROM V_CHLNG_MISSION_LIST WHERE CHLNG_FL = 'N' AND " + condition;
		
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


	/** 검색 공지글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @param orderBy 
	 * @return list
	 * @throws Exception
	 */
	public List<Challenge> searchChallengelist(Connection conn, PageInfo pInfo, String condition, String orderBy) throws Exception{
		List<Challenge> list = null;
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM V_CHLNG_MISSION_LIST " + 
				"        WHERE " + condition + 
				"        AND CHLNG_FL = 'N' ORDER BY " + orderBy + "CHLNG_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;    // 시작은 1부터 
			int endRow = startRow + pInfo.getLimit() - 1; // 10
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
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
			close(pstmt);
		}
		
		
		return list;
	}


	
	public List<Attachment> selectThumbFiles(Connection conn, PageInfo pInfo) throws Exception {
		List<Attachment> fmList = null;
		
		String query = "SELECT * " + 
				"FROM CHALLENGE_MISSION_IMG " + 
				"WHERE CHLNG_NO" + 
				"    IN (SELECT CHLNG_NO FROM" + 
				"            (SELECT ROWNUM RNUM, V.* FROM" + 
				"                ( SELECT CHLNG_NO FROM V_CHLNG_MISSION_LIST" + 
				"                WHERE CHLNG_FL = 'N'" + 
				"                ORDER BY CHLNG_NO DESC) V)" + 
				"    WHERE RNUM BETWEEN ? AND ?) " + 
				"AND FILE_LEVEL = 0";
		
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
	
	
	
}
