package com.boss.blueSpring.admin.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.boss.blueSpring.admin.model.service.AdminService;
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

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/admin").length());

		String path = null;
		RequestDispatcher view = null;

		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;

		String errorMsg = null;
		
		HttpSession session = request.getSession();

		try {
			AdminService service = new AdminService();
			
			// 관리자 메인 페이지 Controller ***********************************************
			if (command.equals("/adminMain.do")) {
				errorMsg = "관리자 메인 페이지 조회 중 오류 발생.";

				path = "/WEB-INF/views/admin/adminMain.jsp";

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 회원 정보 조회 Controller **************************************************
			else if (command.equals("/adminMemberInfo.do")) {
				errorMsg = "회원 정보 조회 중 오류 발생.";
				
				String cp = request.getParameter("cp");
				
				MemberPageInfo mpInfo = service.MemberPageInfo(cp);
				
				List<Member> mList = service.selectMemberList(mpInfo);

				path = "/WEB-INF/views/admin/adminMemberInfo.jsp";
				
	            request.setAttribute("mList", mList);
	            request.setAttribute("mpInfo", mpInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 신고 목록 조회 Controller **************************************************
			else if (command.equals("/adminReport.do")) {
				errorMsg = "신고 목록 조회 중 오류 발생.";
				
				String cp = request.getParameter("cp");
				
				ReportPageInfo rpInfo = service.ReportPageInfo(cp);
				
				List<Report> rList = service.selectReportList(rpInfo);

				path = "/WEB-INF/views/admin/adminReport.jsp";
				
	            request.setAttribute("rList", rList);
	            request.setAttribute("rpInfo", rpInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 신고 상세 페이지 조회 Controller ******************************************
			else if (command.equals("/reportPage.do")) {
				errorMsg = "신고 상세페이지 조회 중 오류 발생.";
				
				int reportNo = Integer.parseInt(request.getParameter("rn"));
				
				Report report = service.selectReport(reportNo);
				
				path = "/WEB-INF/views/admin/adminReportPage.jsp";
				
				request.setAttribute("report", report);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 블랙리스트 조회 Controller **************************************************
			else if (command.equals("/adminBlacklistInfo.do")) {
				errorMsg = "블랙리스트 조회 중 오류 발생.";
				
				String cp = request.getParameter("cp");
				
				BlacklistPageInfo bpInfo = service.BlacklistPageInfo(cp);
				
				List<Member> bkList = service.selectBlackList(bpInfo);
				
				path = "/WEB-INF/views/admin/adminBlacklistInfo.jsp";
				
	            request.setAttribute("bkList", bkList);
	            request.setAttribute("bpInfo", bpInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 기관(센터) 조회 Controller **************************************************
			else if (command.equals("/adminCenterInfo.do")) {
				errorMsg = "기관 목록 조회 중 오류 발생.";
				
				String cp = request.getParameter("cp");
				
				CenterPageInfo cpInfo = service.CenterPageInfo(cp);
				
				List<Center> cList = service.selectCenterList(cpInfo);
				
				path = "/WEB-INF/views/admin/adminCenterInfo.jsp";
				
	            request.setAttribute("cList", cList);
	            request.setAttribute("cpInfo", cpInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 기관(센터) 상세페이지 Controller **************************************************
			else if (command.equals("/centerPage.do")) {
				errorMsg = "기관 상세페이지 조회 중 오류 발생.";
				
				int centerNo = Integer.parseInt(request.getParameter("cn"));
				
				Center center = service.selectCenter(centerNo);
				
				path = "/WEB-INF/views/admin/adminCenterPage.jsp";
				
				request.setAttribute("center", center);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 기관(센터) 등록 페이지 Controller ***************************************************
			else if (command.equals("/centerAdd.do")) {
				errorMsg = "기관 등록 페이지 조회 중 오류 발생.";
				
				path = "/WEB-INF/views/admin/adminCenterAdd.jsp";

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 기관(센터) 기관명 중복 체크 Controller *******************************************
			else if(command.equals("/centerDubCheck.do")) {
				errorMsg = "기관명 중복 검사 과정에서 오류가 발생했습니다.";
				
				String centerName = request.getParameter("centerName");
						
				int result = service.centerNameDubCheck(centerName);
				response.getWriter().print(result);
			}
			
			// ********************* 기관(센터) 등록 Controller *********************
			else if(command.equals("/centerComplete.do")) {
				errorMsg = "센터등록 과정에서 오류가 발생했습니다.";
				
				path = "";
				String centerCla = request.getParameter("cla");
				String centerName = request.getParameter("centerName");
				String centerArea1 = request.getParameter("sido1");
				String centerArea2 = request.getParameter("gugun1");
				String postcode = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String centerAddrDtl = request.getParameter("address2");
				String centerAddr = postcode + "," + address1;
				String centerTel = request.getParameter("phone");
				String centerUrl = request.getParameter("homepage");

				Center center = new Center(centerCla, centerArea1, centerArea2, 
						centerName, centerTel, centerUrl, 
						centerAddr, centerAddrDtl);
				try {
					int result = service.centerAdd(center);
					
					if(result > 0) {
						
						swalIcon = "success";
						swalTitle = "기관 등록 성공";
						swalText = "";
						
						session.setAttribute("swalIcon", swalIcon);
						session.setAttribute("swalTitle", swalTitle);
						session.setAttribute("swalText", swalText);
						
						response.sendRedirect("adminCenterInfo.do");
						
					} else {
						
						swalIcon = "error";
						swalTitle = "기관 등록 실패";
						swalText = "";
						
						session.setAttribute("swalIcon", swalIcon);
						session.setAttribute("swalTitle", swalTitle);
						session.setAttribute("swalText", swalText);
						response.sendRedirect(request.getContextPath());
						
					}
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("errorMsg", errorMsg);
					path = "/WEB-INF/views/common/errorPage.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				}
			}
				

			// 기관(센터) 수정 페이지 Controller ***************************************************
			else if (command.equals("/centerUpdate.do")) {
				errorMsg = "기관 수정 페이지 조회 중 오류 발생.";
				
				int centerNo = Integer.parseInt(request.getParameter("no"));
				
				Center center = service.selectCenter(centerNo);
				
				path = "/WEB-INF/views/admin/adminCenterUpdate.jsp";

				request.setAttribute("center", center);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// ********************* 기관(센터) 수정 Controller *********************

			else if(command.equals("/updateAction.do")) {
				errorMsg = "센터 수정 중 오류가 발생했습니다.";
				
				int centerNo = Integer.parseInt(request.getParameter("no"));
				String centerArea1 = request.getParameter("sido1");
				String centerArea2 = request.getParameter("gugun1");
				String postcode = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String centerAddrDtl = request.getParameter("address2");
				String centerAddr = postcode + "," + address1;
				String centerTel = request.getParameter("phone");
				String centerUrl = request.getParameter("homepage");
				
				Center center = new Center();
				center.setCenterNo(centerNo);
				center.setCenterArea1(centerArea1);
				center.setCenterArea2(centerArea2);
				center.setCenterAddr(centerAddr);
				center.setCenterAddrDtl(centerAddrDtl);
				center.setCenterTel(centerTel);
				center.setCenterUrl(centerUrl);
				
				System.out.println(center);
				try {
					int result = service.updateCenter(center);
					
					if(result > 0) {
						swalIcon = "success";
						swalTitle = "수정 완료";
						swalText = "센터 정보가 수정되었습니다.";
						
					} else {
						swalIcon = "error";
						swalTitle = "수정 실패";
						swalText = "회원 정보 수정을 실패했습니다.";
					}
					
					session.setAttribute("swalIcon", swalIcon);
					session.setAttribute("swalTitle", swalTitle);
					session.setAttribute("swalText", swalText);
					
					response.sendRedirect("adminCenterInfo.do");
					
				} catch (Exception e) {
			         e.printStackTrace();
			         path = "/WEB-INF/views/common/errorPage.jsp";
			         request.setAttribute("errorMsg", errorMsg);
			         view = request.getRequestDispatcher(path);
			         view.forward(request, response);
				}
			}
			
			
			// 자유게시판 관리 (목록 조회) Controller **************************************************
			else if (command.equals("/adminBoard.do")) {
				errorMsg = "관리자 전용 자유게시판 조회 중 오류 발생.";
				
				String cp = request.getParameter("cp");
				
	            PageInfo pInfo = service.getPageInfo(cp);
	            
	            List<Board> aList = service.selectAdminList(pInfo);
	            
				path = "/WEB-INF/views/admin/adminBoard.jsp";
				
	            request.setAttribute("aList", aList);
	            request.setAttribute("pInfo", pInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);		
				
			}
			
			// 챌린지 게시판 조회(관리) Controller **************************************************
			else if (command.equals("/adminChall.do")) {
				errorMsg = "관리자 전용 챌린지 게시판 조회 중 오류 발생.";
				
				String cp = request.getParameter("cp");
				
				ChallPageInfo chpInfo = service.ChallPageInfo(cp);
				
				List<Challenge> chList = service.selectChallList(chpInfo);
				
				path = "/WEB-INF/views/admin/adminChall.jsp";
				
	            request.setAttribute("chList", chList);
	            request.setAttribute("chpInfo", chpInfo);
				

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			// 챌린지 인증게시판 조회(관리) Controller **************************************************
			else if (command.equals("/adminCrtfd.do")) {
				errorMsg = "관리자 전용 인증게시판 조회 중 오류 발생.";
				
				String cp = request.getParameter("cp");
				
				ChallCrtfdPageInfo crtpInfo = service.ChallCrtfdPageInfo(cp);
				
				List<ChallengeCrtfd> crtList = service.selectChallCrtfdList(crtpInfo);

				path = "/WEB-INF/views/admin/adminCrtfd.jsp";
				
	            request.setAttribute("crtList", crtList);
	            request.setAttribute("crtpInfo", crtpInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
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

