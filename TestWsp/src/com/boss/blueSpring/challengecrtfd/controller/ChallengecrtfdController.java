package com.boss.blueSpring.challengecrtfd.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challengecrtfd.model.service.ChallengeCrtfdService;
import com.boss.blueSpring.challengecrtfd.model.vo.Attachment;
import com.boss.blueSpring.challengecrtfd.model.vo.ChallengeCrtfd;
import com.boss.blueSpring.challengecrtfd.model.vo.PageInfo;
import com.boss.blueSpring.common.MyFileRenamePolicy;
import com.boss.blueSpring.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/challengeCrtfd/*")
public class ChallengecrtfdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI(); // 요청이 들어오는 주소 
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/challengeCrtfd").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		String errorMsg = null;
		
		try {
			ChallengeCrtfdService service = new ChallengeCrtfdService();
			
			String cp = request.getParameter("cp");
			
			//챌린지 인증 목록 페이지 이동**********************************************************************
			if(command.equals("/list.do")) {
				String sort = request.getParameter("sort");
				PageInfo pInfo = service.getPageInfo(cp);
				HttpSession session = request.getSession(); 
				Member member = (Member)session.getAttribute("loginMember");	
				 
				List<ChallengeCrtfd> list = service.selectList(pInfo, sort);

				
				if(member != null) { //로그인한 멤버가 있으면
					int memberNo = member.getMemberNo(); 
//				참여자 정보 테이블 
					List<Map<String, Object>> joinInfo = service.selectjoinInfo( memberNo);
					request.setAttribute("joinInfo", joinInfo);
				}
				
				
				
				path = "/WEB-INF/views/challengeCrtfd/challengeCrtfdList.jsp";
				request.setAttribute("list", list);
				request.setAttribute("pInfo", pInfo);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 챌린지 인증 상세조회 ********************************************************************
			else if(command.equals("/view.do")) {
				HttpSession session = request.getSession(); 
				int challengeCrtfdNo = Integer.parseInt(request.getParameter("no"));
				int memberNo = 0;
				
				Member member = (Member)session.getAttribute("loginMember");	
				
				//좋아요
				if(member != null) { //로그인한 멤버가 있으면
					memberNo = member.getMemberNo(); 
				}
				
				ChallengeCrtfd challengeCrtfd = service.selectChallengeCrtfd(challengeCrtfdNo);
				
				// 이미지 파일 조회 부분
				List<Attachment> fList = service.selectChallengeCrtfdFiles(challengeCrtfdNo);
				
				if(!fList.isEmpty()) {
					request.setAttribute("fList", fList);
				}
				
				path="/WEB-INF/views/challengeCrtfd/challengeCrtfdView.jsp";
				request.setAttribute("challengeCrtfd", challengeCrtfd);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 등록 폼********************************************************************
			else if(command.equals("/insertForm.do")) {
				
				Member member = (Member)request.getSession().getAttribute("loginMember");	
				
				int memberNo = member.getMemberNo(); 
				List<Map<String, Object>> joinInfo = service.selectjoinInfo( memberNo);
				request.setAttribute("joinInfo", joinInfo);
				
				
				path="/WEB-INF/views/challengeCrtfd/challengeCrtfdInsert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 등록********************************************************************
			else if(command.equals("/insert.do")) {
				
				errorMsg = "챌린지 인증 글 삽입 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 20MB == 20 * 1024KB == 20 * 1024 * 1024Byte
				
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/uploadImages/challengeCr/";
					
				MultipartRequest multiRequest 
				= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
		
				// 전달 받은 파일 정보를 저장
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = multiRequest.getFileNames();
				
				while(files.hasMoreElements()) { // 다음 요소가 있다면
					String name = files.nextElement(); //img0
					
					if(multiRequest.getFilesystemName(name) != null) {
						Attachment temp = new Attachment();
						
						temp.setFileName(multiRequest.getFilesystemName(name));
						temp.setFilePath(filePath);
						
						// name 속성에 따라 fileLevel 지정
						int fileLevel = 0;
						switch(name) {
						case "img0" : fileLevel = 0; break;
						case "img1" : fileLevel = 1; break;
						case "img2" : fileLevel = 2; break;
						}
						
						temp.setFileLevel(fileLevel);
						
						// cList에 추가
						fList.add(temp);
					}
				} // end while
				
				//사진, 소감
				String chlngNo = multiRequest.getParameter("chSelect");
				String chlngCrContent = multiRequest.getParameter("chlngCrContent");
				String chlngCrTitle = multiRequest.getParameter("chlngCrTitle");

				HttpSession session = request.getSession();
			    Member loginMember = (Member)session.getAttribute("loginMember");
			    int chlngeWriter = loginMember.getMemberNo();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("chlngNo", chlngNo);
				map.put("chlngCrContent", chlngCrContent);
				map.put("chlngeWriter", chlngeWriter);
				map.put("chlngCrTitle", chlngCrTitle);
				
				int result = service.insertChallengeCrtfd(map);
				
				if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장되어있다.
					swalIcon = "success";
					swalTitle = "챌린지 인증글 등록 성공";
					path = "view.do?cp=1&no=" + result;
				}
					else {
					swalIcon = "error";
					swalTitle = "챌린지 인증글 등록 실패";
					path = "list.do";
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
								
				response.sendRedirect(path);
				
			}
			
			// 수정 폼********************************************************************
			else if(command.equals("/updateForm.do")) {
				errorMsg = "챌린지 수정 이동 과정에서 오류 발생";
				
				int chlngBoardNo = Integer.parseInt(request.getParameter("no"));
				
				ChallengeCrtfd ChallengeCrtfd = service.updateView(chlngBoardNo);
				
				// 업데이트 화면 출력용 게시글 조회가 성공한 경우
				if(ChallengeCrtfd != null) {
					// 해당 게시글에 작성된 이미지(파일) 목록 정보 조회
					List<Attachment> fList = service.selectChallengeCrtfdFiles(chlngBoardNo);
					
					if(!fList.isEmpty()) {  // 조회됐다면
						request.setAttribute("fList", fList);
					}
					request.setAttribute("ChallengeCrtfd", ChallengeCrtfd);
					path = "/WEB-INF/views/challengeCrtfd/challengeCrtfdUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				
				} else {
					
					response.sendRedirect(request.getHeader("referer"));
					//  상세 조회 -> 수정 화면
				}
			}
			
			// 수정********************************************************************
			else if(command.equals("/update.do")) {
				errorMsg = "챌린지 인증 글 수정 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 20MB == 20 * 1024KB == 20 * 1024 * 1024Byte
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/uploadImages/challengeCr/";
				
				MultipartRequest multiRequest 
				= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
		
				int chlngNo = Integer.parseInt(multiRequest.getParameter("no"));
				
				String chlngCrContent = multiRequest.getParameter("chlngCrContent");
				String chlngCrTitle = multiRequest.getParameter("chlngCrTitle");
				
				HttpSession session = request.getSession();
			    Member loginMember = (Member)session.getAttribute("loginMember");
			    int chlngeWriter = loginMember.getMemberNo();
				
			    Map<String, Object> map = new HashMap<String, Object>();
				map.put("chlngNo", chlngNo);
				map.put("chlngCrContent", chlngCrContent);
				map.put("chlngeWriter", chlngeWriter);
				map.put("chlngCrTitle", chlngCrTitle);
				
				
				
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = multiRequest.getFileNames();
				
				while(files.hasMoreElements()) { // 다음 요소가 있다면
					String name = files.nextElement(); //img0
					
					if(multiRequest.getFilesystemName(name) != null) {
						Attachment temp = new Attachment();
						
						temp.setFileName(multiRequest.getFilesystemName(name));
						temp.setFilePath(filePath);
						
						// name 속성에 따라 fileLevel 지정
						//int fileLevel = 0;
						switch(name) {
						case "img0" : temp.setFileLevel(0); break;
						case "img1" : temp.setFileLevel(1); break;
						case "img2" : temp.setFileLevel(2); break;
						}
						
						//temp.setFileLevel(fileLevel);
						
						// cList에 추가
						fList.add(temp);
					}
				} // end while
				
				map.put("fList", fList);
				
				
				
				int result = service.udpateChallengeCrtfd(map);
				
				//path = "view.do?cp=" + cp + "&no=" + chlngNo; 
				
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "챌린지 인증글 수정 성공";
					path = "view.do?cp=" + cp + "&no=" + chlngNo;
					
				} else {
					swalIcon = "error";
					swalTitle = "챌린지 인증글 수정 실패";
					path = "list.do";
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
			    
				response.sendRedirect(path);
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			// 삭제 ********************************************************************
			else if(command.equals("/delete.do")) {
				errorMsg = "챌린지 인증 삭제 과정에서 오류 발생";
				
				int chlngBoardNo = Integer.parseInt(request.getParameter("no"));
				
				int result = service.updateChCrFl(chlngBoardNo);
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "챌린지 인증글 삭제 성공";
					path = "list.do";
				}else {
					swalIcon = "error";
					swalTitle = "챌린지 인증글 삭제 실패";
					path = request.getHeader("referer");
				}
				response.sendRedirect(path);
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
