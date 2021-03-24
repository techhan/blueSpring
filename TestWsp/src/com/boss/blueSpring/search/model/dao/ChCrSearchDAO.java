package com.boss.blueSpring.search.model.dao;


import static com.boss.blueSpring.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.challengecrtfd.model.vo.PageInfo;

public class ChCrSearchDAO {

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
		
		String query = "SELECT COUNT(*) FROM V_CHALLENGERS WHERE CHLNG_BRD_DEL_FL = 'N' AND " + condition;
		
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
	public List<ChallengeCrtfd> searchChallengeCrtfdList(Connection conn, PageInfo pInfo, String condition, String orderBy) throws Exception{
		List<ChallengeCrtfd> list = null;
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM V_CHALLENGERS " + 
				"        WHERE " + condition + 
				"        AND CHLNG_BRD_DEL_FL = 'N' ORDER BY " + orderBy + "CHLNG_BRD_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;    // 시작은 1부터 
			int endRow = startRow + pInfo.getLimit() - 1; // 10
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<ChallengeCrtfd>();
			
			while(rset.next()) {

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
			close(pstmt);
		}
		
		
		return list;
	}

	
	
	
}
