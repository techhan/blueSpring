package com.boss.blueSpring.admin.controller;

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

import com.boss.blueSpring.admin.model.service.AdminSearchService;
import com.boss.blueSpring.admin.model.vo.BlacklistPageInfo;
import com.boss.blueSpring.admin.model.vo.CenterPageInfo;
import com.boss.blueSpring.admin.model.vo.ChallCrtfdPageInfo;
import com.boss.blueSpring.admin.model.vo.ChallPageInfo;
import com.boss.blueSpring.admin.model.vo.MemberPageInfo;
import com.boss.blueSpring.admin.model.vo.ReportPageInfo;
import com.boss.blueSpring.board.model.vo.Board;
import com.boss.blueSpring.board.model.vo.PageInfo;
import com.boss.blueSpring.center.model.vo.Center;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.member.model.vo.Member;
import com.boss.blueSpring.report.model.vo.Report;

@WebServlet("/adminSearch/*")
public class AdminSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/adminSearch").length());

		String path = null;
		RequestDispatcher view = null;

		String errorMsg = null;
		
		try {
			AdminSearchService service = new AdminSearchService();
			
			// 자유게시판 관리자 검색 Controller *************************************************
			if(command.equals("/board.do")) {
				errorMsg = "자유게시판 관리자 검색 페이지 조회 중 오류 발생.";

				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchKey", searchKey);
				map.put("searchValue", searchValue);
				map.put("currentPage", cp);
				
				PageInfo pInfo = service.getPageInfo(map);
				
				List<Board> aList = service.searchBoardList(map, pInfo);
	
				path = "/WEB-INF/views/admin/adminBoard.jsp";
				
				request.setAttribute("aList", aList);
				request.setAttribute("pInfo", pInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			
			}
			
			// 신고 목록 관리자 검색 Controller *************************************************
			else if(command.equals("/report.do")) {
				errorMsg = "신고 목록 관리자 검색 페이지 조회 중 오류 발생.";
				
				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchKey", searchKey);
				map.put("searchValue", searchValue);
				map.put("currentPage", cp);
				
				ReportPageInfo rpInfo = service.getPageInfoReport(map);
			
				List<Report> rList = service.searchReportList(map, rpInfo);
				
				path = "/WEB-INF/views/admin/adminReport.jsp";
				
				request.setAttribute("rList", rList);
				request.setAttribute("rpInfo", rpInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			// 센터 목록 관리자 검색 Controller *************************************************
			else if(command.equals("/center.do")) {
				errorMsg = "센터 목록 관리자 검색 페이지 조회 중 오류 발생.";
				
				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchKey", searchKey);
				map.put("searchValue", searchValue);
				map.put("currentPage", cp);
				
				CenterPageInfo cpInfo = service.getPageInfoCenter(map);
								
				List<Center> cList = service.searchCenterList(map, cpInfo);

				path = "/WEB-INF/views/admin/adminCenterInfo.jsp";
				
				request.setAttribute("cList", cList);
				request.setAttribute("cpInfo", cpInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			// 회원 정보 조회 관리자 검색 Controller *************************************************
			else if(command.equals("/member.do")) {
				errorMsg = "회원 정보 조회 관리자 검색 페이지 조회 중 오류 발생.";
				
				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchKey", searchKey);
				map.put("searchValue", searchValue);
				map.put("currentPage", cp);
				
				MemberPageInfo mpInfo = service.getPageInfoMember(map);
				
				List<Member> mList = service.searchMemberList(map, mpInfo);

				path = "/WEB-INF/views/admin/adminMemberInfo.jsp";
				
				request.setAttribute("mList", mList);
				request.setAttribute("mpInfo", mpInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}			
			
			// 블랙리스트 조회 관리자 검색 Controller *************************************************
			else if(command.equals("/blacklist.do")) {
				errorMsg = "회원 정보 조회 관리자 검색 페이지 조회 중 오류 발생.";
				
				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchKey", searchKey);
				map.put("searchValue", searchValue);
				map.put("currentPage", cp);
				
				BlacklistPageInfo bpInfo = service.getPageInfoBlack(map);
				
				List<Member> bkList = service.searchBlackList(map, bpInfo);

				path = "/WEB-INF/views/admin/adminBlacklistInfo.jsp";
				
				request.setAttribute("bkList", bkList);
				request.setAttribute("bpInfo", bpInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}			

			// 챌린지 관리자 검색 Controller *************************************************
			else if(command.equals("/chall.do")) {
				errorMsg = "챌린지 관리자 검색 페이지 조회 중 오류 발생.";
				
				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchKey", searchKey);
				map.put("searchValue", searchValue);
				map.put("currentPage", cp);
				
				ChallPageInfo chpInfo = service.getPageInfoChall(map);
				
				List<Challenge> chList = service.searchChallList(map, chpInfo);

				path = "/WEB-INF/views/admin/adminChall.jsp";
				
				request.setAttribute("chList", chList);
				request.setAttribute("chpInfo", chpInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			// 챌린지 인증게시판 관리자 검색 Controller *************************************************
			else if(command.equals("/challCrtfd.do")) {
				errorMsg = "챌린지 관리자 검색 페이지 조회 중 오류 발생.";
				
				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchKey", searchKey);
				map.put("searchValue", searchValue);
				map.put("currentPage", cp);
				
				ChallCrtfdPageInfo crtpInfo = service.getPageInfoChallCrtfd(map);
				
				List<ChallengeCrtfd> crtList = service.searchChallCrtfdList(map, crtpInfo);

				path = "/WEB-INF/views/admin/adminCrtfd.jsp";
				
				request.setAttribute("crtList", crtList);
				request.setAttribute("crtpInfo", crtpInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
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
