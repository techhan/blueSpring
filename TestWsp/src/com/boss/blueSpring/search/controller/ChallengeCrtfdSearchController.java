package com.boss.blueSpring.search.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.challengecrtfd.model.vo.PageInfo;
import com.boss.blueSpring.search.model.service.ChCrSearchService;

@WebServlet("/challengeCrtfdSearch.do")
public class ChallengeCrtfdSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchKey = request.getParameter("sk");
		String searchValue = request.getParameter("sv");
		String cp = request.getParameter("cp");
		String chlngCategoryNm = request.getParameter("cn");
		String sort = request.getParameter("sort");
		System.out.println(sort);
		
		try {
			ChCrSearchService service = new ChCrSearchService();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("chlngCategoryNm", chlngCategoryNm);
			map.put("searchKey", searchKey);
			map.put("searchValue", searchValue);
			map.put("currentPage", cp);
			map.put("sort", sort);
			
			// 페이징 처리를 위한 데이터를 계산하고 저장하는 객체 PageInfo 얻어오기
			PageInfo pInfo = service.getPageInfo(map);
			
			// 검색 게시글 목록 조회
			List<ChallengeCrtfd> list = service.searchChallengeCrtfdList(map, pInfo);
			 
			// 조회된 내용과 PageInfo 객체를 request 객체에 담아서 요청 위임
			String path = "/WEB-INF/views/challengeCrtfd/challengeCrtfdList.jsp";			
			
			request.setAttribute("list", list);
			request.setAttribute("pInfo", pInfo);
			
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			String path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "챌린지 인증 게시판에서 검색 과정 중 오류 발생");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		
		}
		
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
