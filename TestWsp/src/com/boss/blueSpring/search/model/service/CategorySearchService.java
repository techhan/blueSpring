package com.boss.blueSpring.search.model.service;

import static com.boss.blueSpring.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.search.model.dao.CategorySearchDAO;

public class CategorySearchService {
	private CategorySearchDAO dao = new CategorySearchDAO();
	
	
	/** 카테고리 종류에 따른 페이징 처리 정보 생성 Service
	 * @param categoryCode
	 * @param cp
	 * @return 
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		// 얻어온 파라미터 cp가 null이면 1, 아니면 int형으로 파싱
		map.put("currentPage",  
				(map.get("currentPage") == null) ? 1 
							: Integer.parseInt((String)map.get("currentPage")));
		
		// DB에서 조건을 만족하는 게시글의 수를 조회하기
		int listCount = dao.getListCount(conn, map);
		
		// 커넥션 반환
		close(conn);
		
		// PageInfo 객체를 생성하여 반환
		
		return new PageInfo((int)map.get("currentPage"), listCount);
	}


	
	/** 카테고리 조건에 맞는 목록 리스트 조회 Service
	 * @param map
	 * @param pInfo 
	 * @return bList
	 * @throws Exception
	 */
	public List<Board> searchBoardList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		List<Board> bList = dao.searchBoardList(conn, pInfo, map);
		
		close(conn);
		
		return bList;
	}

}
