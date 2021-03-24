package com.boss.blueSpring.search.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.close;
import static com.boss.blueSpring.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.search.model.dao.SearchDAO;

public class SearchService {
	private SearchDAO dao = new SearchDAO();
	
	
	/**	검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		map.put("currentPage",  
				(map.get("currentPage") == null) ? 1 
							: Integer.parseInt((String)map.get("currentPage")));
		
		String condition = createCondition(map);
		
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
		String categoryName = (String)map.get("categoryName");
		
		int flag;
		
		if(categoryName.length() > 0) {
			switch(searchKey) {
			case "title" : 
				condition = " BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' AND CATEGORY_NM = " + "'" + categoryName + "'";
				break;
				
			case "content" : 
				condition = " BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%' AND CATEGORY_NM = " + "'" + categoryName + "'";
				break;
				
			case "titcont" : 
				condition = " (BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' "
						  + "OR BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%') AND CATEGORY_NM = " + "'" + categoryName + "'";                  
				break;
				
			case "writer" : 
				condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' AND CATEGORY_NM = " + "'" + categoryName + "'";
				break;
			}
		} else {
			switch(searchKey) {
			case "title" : 
				condition = " BRD_TITLE LIKE '%' || '" + searchValue + "' || '%'";
				break;
				
			case "content" : 
				condition = " BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%'";
				break;
				
			case "titcont" : 
				condition = " (BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' "
						  + "OR BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%')";                  
				break;
				
			case "writer" : 
				condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%'";
				break;
			}	
		}

		
//		switch(searchKey) {
//		case "title" : 
//			condition = " BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' AND CATEGORY_NM = " + "'" + categoryName + "'";
//			break;
//			
//		case "content" : 
//			condition = " BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%' AND CATEGORY_NM = " + "'" + categoryName + "'";
//			break;
//			
//		case "titcont" : 
//			condition = " (BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' "
//					  + "OR BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%') AND CATEGORY_NM = " + "'" + categoryName + "'";                  
//			break;
//			
//		case "writer" : 
//			condition = " MEMBER_ID LIKE '%' || '" + searchValue + "' || '%' AND CATEGORY_NM = " + "'" + categoryName + "'";
//			break;
//		}

		
		
		
		
		return condition;
	}

	
	/** 검색 게시글 목록 리스트 조회 Service
	 * @param map
	 * @param pInfo 
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> searchBoardList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		String condition = createCondition(map);
		
		List<Board> bList = dao.searchBoardList(conn, pInfo, condition);
		
		close(conn);
		
		return bList;
	}

}
