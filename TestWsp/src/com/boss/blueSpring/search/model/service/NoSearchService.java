package com.boss.blueSpring.search.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;


import com.boss.blueSpring.notice.model.vo.Notice;
import com.boss.blueSpring.notice.model.vo.PageInfo;
import com.boss.blueSpring.search.model.dao.NoSearchDAO;

public class NoSearchService {

	private NoSearchDAO dao = new NoSearchDAO();
	
	

	
	/** 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception{
		Connection conn = getConnection();
		 
		// 얻어온 파라미터가 null이면 1, 아니면 int 형으로 파싱     // map에 중복되는 값이 있으면 기존 정보에 덮어짐
		map.put("currentPage",  
				(map.get("currentPage") == null)  ? 1 
							: Integer.parseInt((String)map.get("currentPage")   )  );
		
		// 검색 조건에 따른 SQL 조건문을 조합라는 메소드 호출
		String condition = createCondition(map);
		
		//SQL 조건문을 map에 추가
		map.put("condition", condition);
		
		//DB에서 조건을 만족하는 게시글의 수를 조회하기
		int listCount = dao.getListCount(conn, condition);
		
		// 커넥션 반환
		close(conn);
		
		// PageInfo 객체를 생성하여 반환
		return new PageInfo((int)map.get("currentPage"), listCount);
	}




	/** 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return
	 */
	private String createCondition(Map<String, Object> map) {
		
		String condition = null;
		
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		
		// 검색 조건(searckey)에 따라 SQL 조합
		switch(searchKey) {
		case "title" : 
			condition = " NOTICE_TITLE LIKE '%' || '" + searchValue + "' || '%' ";
						// "NOTICE_TITLE LIKE '%' || 49 || '%' "
			break;
			
		case "content" : 
			condition = " NOTICE_CONTENT LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "titcont" : 
			condition = " (NOTICE_TITLE LIKE '%' || '" + searchValue + "' || '%' "
						+ "OR NOTICE_CONTENT LIKE '%' || '" + searchValue + "' || '%') ";
			break;
			
		case "writer" : 
			condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
			break;
		}
		
		return condition;
	}




	/** 검색 공지글 목록 리스트 조회 Service
	 * @param map
	 * @param pInfo 
	 * @return list
	 * @throws Exception
	 */
	public List<Notice> searchNoticeList(Map<String, Object> map, PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
		String condition = createCondition(map);   //
		 
		List<Notice> list = dao.searchNoticeList(conn, pInfo, condition);

		close(conn);
		
		return list;
	}



	
	
	
	
	
}
