package com.boss.blueSpring.main.controller;

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

import com.boss.blueSpring.main.model.service.MainService;
import com.boss.blueSpring.main.model.vo.MainPageInfo;
import com.boss.blueSpring.main.model.vo.MainSearch;

@WebServlet("/mainSearch.do")
public class MainSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = null;
		RequestDispatcher view = null;
		String errorMsg = null;
		
		try {
			MainService service = new MainService();
			
			// 메인 검색 페이지 Controller **************************************************
			errorMsg = "전체 검색 페이지 조회 중 오류 발생.";
			
			String searchKey = request.getParameter("sk");
			String searchValue = request.getParameter("sv");
			String cp = request.getParameter("cp");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("searchKey", searchKey);
			map.put("searchValue", searchValue);
			map.put("currentPage", cp);
			
			MainPageInfo mpInfo = service.getPageInfo(map);
			
			List<MainSearch> mList = service.searchMainList(map, mpInfo);
			
			path = "/WEB-INF/views/main/mainSearchPage.jsp";
			
			request.setAttribute("mList", mList);
			request.setAttribute("mpInfo", mpInfo);

			view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
						
		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
