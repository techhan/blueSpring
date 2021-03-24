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

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.search.model.service.CategorySearchService;

@WebServlet("/categorySearch.do")
public class CategorySearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String categoryName = request.getParameter("cn");
		String searchKey = request.getParameter("sk");
		String searchValue = request.getParameter("sv");
		String cp = request.getParameter("cp");	
		
//		System.out.println(searchKey);
//		System.out.println(searchValue);
//		System.out.println(categoryName);
		
		String path = null;
		RequestDispatcher view = null;
		
		try {
			CategorySearchService service = new CategorySearchService();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categoryName", categoryName);
			map.put("searchValue", searchValue);
			map.put("searchKey", searchKey);
			map.put("currentPage", cp);			
			
			PageInfo pInfo = service.getPageInfo(map);
			
			List<Board> bList = service.searchBoardList(map, pInfo);
			
			path = "/WEB-INF/views/board/boardMain.jsp";
			
			request.setAttribute("bList", bList);
			request.setAttribute("pInfo", pInfo);
			
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
