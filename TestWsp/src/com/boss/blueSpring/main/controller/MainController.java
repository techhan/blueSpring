package com.boss.blueSpring.main.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.main.model.service.MainService;
import com.boss.blueSpring.notice.model.vo.Notice;

@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = null;
		RequestDispatcher view = null;

		String errorMsg = null;

		try {
			MainService service = new MainService();
			
			// 메인 조회 Controller ****************************************
			errorMsg = "메인 페이지 게시판 목록 조회 중 오류 발생.";
			
			List<Notice> nList = service.selectMainNotice();
			List<Board> bList = service.selectMainBoard();
			List<Challenge> cList = service.selectMainChallenge();
			List<ChallengeCrtfd> crtList = service.selectMainChallengeCrtfd();
			
			request.setAttribute("nList", nList);
			request.setAttribute("bList", bList);
			request.setAttribute("cList", cList);
			request.setAttribute("crtList", crtList);
			
			view = request.getRequestDispatcher("index.jsp");
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
