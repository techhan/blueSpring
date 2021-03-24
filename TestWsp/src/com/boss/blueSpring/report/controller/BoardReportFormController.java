package com.boss.blueSpring.report.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/boardReportForm.do")
public class BoardReportFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

		String boardNo = request.getParameter("brdNo");
		String memberId = request.getParameter("memNo");
		String target = request.getParameter("target");
		

		
//		System.out.println("=================[변환창]");
//		System.out.println(target);
//		System.out.println(boradNo);
//		System.out.println(memberId);
		
		try {
			
			request.setAttribute("boardNo", boardNo);
			request.setAttribute("memberId", memberId);
			request.setAttribute("target", target);
			
			String path = "/WEB-INF/views/board/boardReport.jsp";
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		
			
		} catch (Exception e) {
			e.printStackTrace();
			String path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "검색 과정에서 오류 발생");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
