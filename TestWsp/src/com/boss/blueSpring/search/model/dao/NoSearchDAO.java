package com.boss.blueSpring.search.model.dao;


import static com.boss.blueSpring.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.boss.blueSpring.notice.model.vo.Notice;
import com.boss.blueSpring.notice.model.vo.PageInfo;

public class NoSearchDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	
	/**검색 내용이 포함된 페이징 처리 정보 생성 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String condition) throws Exception {

		int listCount = 0;
		
		String query = "SELECT COUNT(*) FROM V_NOTICE_LIST WHERE NOTICE_DEL_FL = 'N' AND " + condition;
		
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
	 * @return List
	 * @throws Exception
	 */
	public List<Notice> searchNoticeList(Connection conn, PageInfo pInfo, String condition) throws Exception{
		List<Notice> list = null;
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM V_NOTICE_LIST " + 
				"        WHERE " + condition + 
				"        AND NOTICE_DEL_FL = 'N' ORDER BY NOTICE_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;    // 시작은 1부터 
			int endRow = startRow + pInfo.getLimit() - 1; // 10
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Notice>();
			
			while(rset.next()) {
				Notice notice = new Notice();
				notice.setNoticeNo(  	rset.getInt("NOTICE_NO")  );
				notice.setNoticeTitle(  rset.getString("NOTICE_TITLE")  );
				notice.setMemberId(  	rset.getString("MEM_ID")  );
				notice.setNoticeViews(  	rset.getInt("NOTICE_VIEWS")  );
				notice.setNoticeCrtDt(  rset.getTimestamp("NOTICE_CRT_DT")  );
				
				list.add(notice);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}

	
	
	
}
