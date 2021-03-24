package com.boss.blueSpring.search.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.close;
import static com.boss.blueSpring.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.challengecrtfd.model.vo.PageInfo;
import com.boss.blueSpring.search.model.dao.ChCrSearchDAO;

public class ChCrSearchService {

	private ChCrSearchDAO dao = new ChCrSearchDAO();
	
	

	
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
		int listCount = dao.getChListCount(conn, condition);
		
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
		String chlngCategoryNm = (String)map.get("chlngCategoryNm");
		
		int flag;
		
		
		if(chlngCategoryNm.length() > 0) {
			switch(searchKey) {
			case "title" : 
				condition = " CHLNG_BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' AND CHLNG_CATE_NM = " + "'" +  chlngCategoryNm + "'";
							
				break;
				
			case "content" : 
				condition = " CHLNG_BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%' AND CHLNG_CATE_NM = " + "'" +  chlngCategoryNm + "'";
				break;
				
			case "titcont" : 
				condition = " (CHLNG_BRD_TITLE LIKE '%' || '" + searchValue + "' || '%' "
							+ "OR CHLNG_BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%') AND CHLNG_CATE_NM = "  + "'" +  chlngCategoryNm + "'";
				break;
				
			case "writer" : 
				condition = " MEM_NICKNAME LIKE '%' || '" + searchValue + "' || '%' AND CHLNG_CATE_NM = " + "'" +  chlngCategoryNm + "'";
				break;
			}
		} else {
			switch(searchKey) {
			case "title" : 
				condition = " CHLNG_BRD_TITLE LIKE '%' || '" + searchValue + "' || '%'";
				break;
				
			case "content" : 
				condition = " CHLNG_BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%'";
				break;
				
			case "titcont" : 
				condition = " (CHLNG_BRD_TITLE LIKE '%' || '" + searchValue + "' || '%'"
							+ "OR CHLNG_BRD_CONTENT LIKE '%' || '" + searchValue + "' || '%')";
				break;
				
			case "writer" : 
				condition = " MEM_NICKNAME LIKE '%' || '" + searchValue + "' || '%'";
				break;
			}
		}
		
		return condition;
	}




	/** 검색 공지글 목록 리스트 조회 Service
	 * @param map
	 * @param pInfo 
	 * @return list
	 * @throws Exception
	 */
	public List<ChallengeCrtfd> searchChallengeCrtfdList(Map<String, Object> map, PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
//		좋아요 정렬 부분 코드
		String sort = null;
		
		 sort = (String)map.get("sort") == null ? "" : (String)map.get("sort");
		 
		 String orderBy = null;
		 //String end = null;
		 
		 if(sort.equals("view")) {
				orderBy = " CHLNG_BRD_VIEWS DESC, ";
			}else {
				orderBy = "";
			}
		
		String condition = createCondition(map);   //
		 
		List<ChallengeCrtfd> list = dao.searchChallengeCrtfdList(conn, pInfo, condition, orderBy);
 
		close(conn);
		
		return list;
	}



	
	
	
	
	
}
