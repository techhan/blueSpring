package com.boss.blueSpring.challenge.controller;

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

import com.boss.blueSpring.challenge.model.service.ChallengeService;
import com.boss.blueSpring.challenge.model.vo.Attachment;
import com.boss.blueSpring.challenge.model.vo.Challenge;
import com.boss.blueSpring.challenge.model.vo.Like;
import com.boss.blueSpring.challenge.model.vo.PageInfo;
import com.boss.blueSpring.common.MyFileRenamePolicy;
import com.boss.blueSpring.member.model.vo.Member;
import com.boss.blueSpring.notice.model.vo.Notice;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/challenge/*")
public class ChallengeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI(); // 요청이 들어오는 주소 
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/challenge").length()); 
		
		String path = null;
		RequestDispatcher view = null;
		
		String errorMsg = null;
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		try {
			ChallengeService service = new ChallengeService();
			
			String cp = request.getParameter("cp");
			
			
			//챌린지 목록 페이지 이동 **********************************************************
			if(command.equals("/list.do")) {
				
				String sort = request.getParameter("sort");
				PageInfo pInfo = service.getPageInfo(cp);
				
				
//				if(request.getParameter("cn") != null) {
//				}
//				List<Challenge> list = service.selectList(pInfo, sort, cn);
				List<Challenge> list = service.selectList(pInfo, sort);
				
				
				/*대표 이미지 조회 관련 코드 부분*/
				if(list != null) {
					// 대표 이미지 조회 부분
					List<Attachment> fmList = service.selectThumbFiles(pInfo);
					
					if(!fmList.isEmpty()) {
						request.setAttribute("fmList", fmList);
					}
				
				}
				
				path="/WEB-INF/views/challenge/challengeList.jsp";
				request.setAttribute("list", list);
				request.setAttribute("pInfo", pInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			else if(command.equals("/view.do")) {
				HttpSession session = request.getSession(); 
				int challengeNo = Integer.parseInt(request.getParameter("no"));
				int memberNo = 0;
				
				Member member = (Member)session.getAttribute("loginMember");	
				
				//좋아요
				if(member != null) { //로그인한 멤버가 있으면
					memberNo = member.getMemberNo(); 
				}
				
				Challenge challenge = service.selectChallenge(challengeNo);
				//좋아요 목록 담기 위한 리스트
				Like likeInfo = service.selectLike(challengeNo, memberNo);
				
				// 챌린지 참여 여부 확인
				int check = service.check(challengeNo, memberNo);
				
				
				// 이미지 파일 조회 부분
				List<Attachment> fList = service.selectChallengeFiles(challengeNo);
				
				if(!fList.isEmpty()) {
					request.setAttribute("fList", fList);
				}
				
				path="/WEB-INF/views/challenge/challengeView.jsp";
				request.setAttribute("challenge", challenge);
				request.setAttribute("likeInfo", likeInfo);
				request.setAttribute("check", check);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 챌린지 삽입 폼 컨트롤러 ********************************************ㄴ
			else if(command.equals("/insertForm.do")) {
				path="/WEB-INF/views/challenge/challengeInsert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 챌린지 등록 컨트롤러***************************************************************************
			else if(command.equals("/insert.do")) {
				
				errorMsg = "챌린지 삽입 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 20MB == 20 * 1024KB == 20 * 1024 * 1024Byte
				
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/uploadImages/challenge/";
				
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
				
				
				//insert.jsp에서 입력받은거를 얻어와!1!!!!!!!
				String chlngTitle = multiRequest.getParameter("chlngTitle");
				String chlngContent = multiRequest.getParameter("chlngContent");
				String chlngStartDt = multiRequest.getParameter("strDt");         
				String chlngEndDt = multiRequest.getParameter("endDt");          
				int chlngCateNo = Integer.parseInt(multiRequest.getParameter("cate"));

//				System.out.println(chlngTitle);
//				System.out.println(chlngContent);
//				System.out.println(chlngStartDt);
//				System.out.println(chlngEndDt);
//				System.out.println(chlngCateNo);
				
				// 세션에서 로그인한 회원의 번호를 얻어옴
				//Member loginMember = (Member)request.getSession().getAttribute("loginMember"); 
				
				HttpSession session = request.getSession();
			    Member loginMember = (Member)session.getAttribute("loginMember");
			    int chlngeWriter = loginMember.getMemberNo();
			    //System.out.println(loginMember);
				
				//System.out.println(chlngeWriter);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("chlngTitle", chlngTitle);
				map.put("chlngContent", chlngContent);
				map.put("chlngStartDt", chlngStartDt);
				map.put("chlngEndDt", chlngEndDt);
				map.put("chlngCateNo", chlngCateNo);
				map.put("chlngeWriter", chlngeWriter);
				
				//System.out.println(map);
				
				int result = service.insertChallenge(map);
				//System.out.println("result : " + result);
				
				if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장되어있다.
					swalIcon = "success";
					swalTitle = "챌린지 등록 성공";
					path = "view.do?cp=1&no=" + result;
				}
					else {
					swalIcon = "error";
					swalTitle = "챌린지 등록 실패";
					path = "list.do";
				}

				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);
			}
			
			
			// 챌린지 수정 폼****************************************************
			else if(command.equals("/updateForm.do")) {
				errorMsg = "챌린지 수정 이동 과정에서 오류 발생";
				
				int chlngNo = Integer.parseInt(request.getParameter("no"));
				
				Challenge challenge = service.updateView(chlngNo);
				
				// 업데이트 화면 출력용 게시글 조회가 성공한 경우
				if(challenge != null) {
					// 해당 게시글에 작성된 이미지(파일) 목록 정보 조회
					List<Attachment> fList = service.selectChallengeFiles(chlngNo);
					
					if(!fList.isEmpty()) {  // 조회됐다면
						request.setAttribute("fList", fList);
					}
					request.setAttribute("challenge", challenge);
					path = "/WEB-INF/views/challenge/challengeUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				
				} else {
					
					response.sendRedirect(request.getHeader("referer"));
					//  상세 조회 -> 수정 화면
				}
			}
			
			// 챌린지 수정 ******************************************************
			else if(command.equals("/update.do")) {
				errorMsg = "챌린지 수정 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 20MB == 20 * 1024KB == 20 * 1024 * 1024Byte
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/uploadImages/challenge/";
				
				
				MultipartRequest multiRequest 
				= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
		
				int cureentPage = Integer.parseInt(multiRequest.getParameter("cp"));
				
				int chlngNo = Integer.parseInt(multiRequest.getParameter("no"));
				String chlngTitle = multiRequest.getParameter("chlngTitle");
				String chlngContent = multiRequest.getParameter("chlngContent");
				String chlngStartDt = multiRequest.getParameter("strDt");         
				String chlngEndDt = multiRequest.getParameter("endDt");          
				String chlngCateNm = multiRequest.getParameter("cate");
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("chlngNo", chlngNo);
				map.put("chlngTitle", chlngTitle);
				map.put("chlngContent", chlngContent);
				map.put("chlngStartDt", chlngStartDt);
				map.put("chlngEndDt", chlngEndDt);
				map.put("chlngCateNm", chlngCateNm);
				
				System.out.println(map);
				
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
				
				map.put("fList", fList);
	
				
				int result = service.updatetChallenge(map);
				
				if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장되어있다.
					swalIcon = "success";
					swalTitle = "챌린지 수정 성공";
					path = "view.do?cp=" + cp + "&no=" + chlngNo;
				}
					else {
					swalIcon = "error";
					swalTitle = "챌린지 수정 실패";
					path = "list.do";
					}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
	//			System.out.println("path : " + path);
				
				response.sendRedirect(path);
				
			}
			
			
			// 챌린지 삭제 ****************************************************
			else if(command.equals("/delete.do")) {
				
				errorMsg = "챌린지 미션 삭제 과정에서 오류 발생";
				
				int chlngNo = Integer.parseInt(request.getParameter("no"));
				
				int result = service.updateChFl(chlngNo);
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "챌린지 삭제 성공";
					path = "list.do";
				}else {
					swalIcon = "error";
					swalTitle = "챌린지 삭제 실패";
					path = request.getHeader("referer");
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);
			}
				
			
			// 챌린지 좋아요
			else if(command.equals("/challengeLike.do")) {
				errorMsg = "좋아요 등록 과정에서 오류 발생.";
				
				int chlngNo = Integer.parseInt(request.getParameter("chlngNo"));
				int memberNo = Integer.parseInt(request.getParameter("memberNo"));
			
				
				int likeFlag = service.challengeLike(chlngNo, memberNo);
				
				response.getWriter().print(likeFlag);
			}
			
			
			// 챌린지 참여자 조인...!!!***************************************************************
			else if(command.equals("/join.do")) {
				errorMsg = "챌린지 참여 과정에서 오류 발생.";
				
				int chlngNo = Integer.parseInt(request.getParameter("chlngNo"));
				int memberNo = Integer.parseInt(request.getParameter("memberNo"));
			
				int join = service.join(chlngNo, memberNo);
				//System.out.println(join);
				
				
				if(join > 0) { 
					swalIcon = "success";
					swalTitle = "챌린지 참여 되었습니다.";
					//path = "view.do?cp="+ cp + "&no=" + chlngNo;				
					path = request.getHeader("referer");
				} else { 					
					swalIcon = "error";
					swalTitle = "챌린지 참여 실패";
					path = request.getHeader("referer");
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
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
