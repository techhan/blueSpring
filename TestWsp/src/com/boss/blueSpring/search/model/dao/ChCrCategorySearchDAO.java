package com.boss.blueSpring.search.model.dao;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.challengecrtfd.model.vo.PageInfo;



public class ChCrCategorySearchDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	
	
	/** 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, Map<String, Object> map) throws Exception {
		int listCount = 0;
		
		String query = "SELECT COUNT(*) FROM V_CHALLENGERS WHERE CHLNG_BRD_DEL_FL = 'N' AND CHLNG_CATE_NM = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("chlngCategoryNm"));
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		return listCount;
	}



	/** 카테고리로 검색한 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param map
	 * @param orderBy 
	 * @return bList
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> searchChallengeCrtfdList(Connection conn, PageInfo pInfo, Map<String, Object> map, String orderBy) throws Exception {
		List<ChallengeCrtfd> cList = null;
		
		String query = 		
				"SELECT * FROM" + 
				"(SELECT  ROWNUM RNUM, V.* "+
				"FROM " +
				"    (SELECT * FROM V_CHALLENGERS "
				+ "WHERE CHLNG_CATE_NM = ? AND CHLNG_BRD_DEL_FL = 'N' "
				+ "ORDER BY " + orderBy + " CHLNG_BRD_NO DESC) V ) " + 
				"WHERE RNUM BETWEEN ? AND ? " ;
		
			System.out.println(query);

		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("chlngCategoryNm"));
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			cList = new ArrayList<ChallengeCrtfd>();
			
			while(rset.next()) {
				
				ChallengeCrtfd challengeCrtfd = new ChallengeCrtfd();
				challengeCrtfd.setChlngBoardNo(  	rset.getInt("CHLNG_BRD_NO")  );
				challengeCrtfd.setChlngBoardTitle(  rset.getString("CHLNG_BRD_TITLE")  );
				challengeCrtfd.setMemNickname(  rset.getString("MEM_NICKNAME")  );
				challengeCrtfd.setChlngBoardViews(  rset.getInt("CHLNG_BRD_VIEWS")  );
				challengeCrtfd.setChlngCateNm(  	rset.getString("CHLNG_CATE_NM")  );
				challengeCrtfd.setChlngBoardCrtDt(  	rset.getTimestamp("CHLNG_BRD_CRT_DT")  );
				
				
				cList.add(challengeCrtfd);
			}
			
		} finally {
			close(rset);
			close(pstmt);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
		}
		
		return cList;
	}
	
	
}
