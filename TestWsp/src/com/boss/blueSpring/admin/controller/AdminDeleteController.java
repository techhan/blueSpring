package com.boss.blueSpring.admin.controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boss.blueSpring.admin.model.service.AdminDeleteService;


@WebServlet("/adminDelete/*")
public class AdminDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/adminDelete").length());

		String path = null;
		RequestDispatcher view = null;

		String errorMsg = null;
		
		try {
			AdminDeleteService service = new AdminDeleteService();
			
			// 자유게시판 게시글 삭제(Y) Controller *************************************************
			if(command.equals("/board.do")) {
				errorMsg = "게시글 삭제 과정에서 오류 발생.";
				String newNumbers = "";
	        	String[] numbers = request.getParameterValues("numbers");
	        	
	            if(numbers != null) {
	                for(String n : numbers) {
	                	newNumbers += "BRD_NO = " + n + " OR ";
	                }
	                newNumbers = newNumbers.substring(0, newNumbers.lastIndexOf(" OR "));
	             }
	            
	        	int result = service.updateBoardStatus(newNumbers);
			
	            response.getWriter().print(result);
			}
			
			// 블랙리스트 삭제(N) Controller *************************************************
			if(command.equals("/blacklist.do")) {
				errorMsg = "블랙리스트 삭제 과정에서 오류 발생.";
				String newBlack = "";
	        	String[] black = request.getParameterValues("black");
	        	
	            if(black != null) {
	                for(String b : black) {
	                	newBlack += "MEM_NO = " + b + " OR ";               	
	                }
	                newBlack = newBlack.substring(0, newBlack.lastIndexOf(" OR "));
	             }
	            
	        	int result = service.updateBlackStatus(newBlack);
			
	            response.getWriter().print(result);
			}
			
			// 챌린지 게시글 삭제(Y) Controller *************************************************
			if(command.equals("/chall.do")) {
				errorMsg = "게시글 삭제 과정에서 오류 발생.";
				String newChall = "";
	        	String[] chall = request.getParameterValues("chall");
	        	
	            if(chall != null) {
	                for(String c : chall) {
	                	newChall += "CHLNG_NO = " + c + " OR ";               	
	                }
	                newChall = newChall.substring(0, newChall.lastIndexOf(" OR "));
	             }
	            
	        	int result = service.updateChallStatus(newChall);
			
	            response.getWriter().print(result);
			}
			
			// 챌린지 인증 게시글 삭제(Y) Controller *************************************************
			if(command.equals("/challCrtfd.do")) {
				errorMsg = "게시글 삭제 과정에서 오류 발생.";
				String newCrtfd = "";
	        	String[] crtfd = request.getParameterValues("crtfd");
	        	
	            if(crtfd != null) {
	                for(String c : crtfd) {
	                	newCrtfd += "CHLNG_BRD_NO = " + c + " OR ";               	
	                }
	                newCrtfd = newCrtfd.substring(0, newCrtfd.lastIndexOf(" OR "));
	             }
	            
	        	int result = service.updateCrtfdStatus(newCrtfd);
			
	            response.getWriter().print(result);
			}
			
			// 센터 삭제(Y) Controller *************************************************
			if(command.equals("/center.do")) {
				errorMsg = "센터 삭제 과정에서 오류 발생.";
				String newCenter = "";
	        	String[] center = request.getParameterValues("center");
	        	
	            if(center != null) {
	                for(String c : center) {
	                	newCenter += "CENTER_NO = " + c + " OR ";               	
	                }
	                newCenter = newCenter.substring(0, newCenter.lastIndexOf(" OR "));
	             }
	            
	        	int result = service.updateCenterStatus(newCenter);
			
	            response.getWriter().print(result);
			}
			
			// 블랙리스트 등록(Y) Controller *************************************************
			if(command.equals("/report.do")) {
				errorMsg = "블랙리스트 등록 과정에서 오류 발생.";
				String newReport = "";
	        	String[] report = request.getParameterValues("report");
	        	
	            if(report != null) {
	                for(String r : report) {
	                	newReport += "MEM_ID = '" + r.toLowerCase() + "' OR ";               	
	                }
	                newReport = newReport.substring(0, newReport.lastIndexOf(" OR "));
	             }
	            
	        	int result = service.updateReportStatus(newReport);
			
	            response.getWriter().print(result);
			}
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
