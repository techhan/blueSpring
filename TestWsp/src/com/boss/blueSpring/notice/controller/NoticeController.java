package com.boss.blueSpring.notice.controller;

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

import com.boss.blueSpring.common.MyFileRenamePolicy;
import com.boss.blueSpring.member.model.vo.Member;
import com.boss.blueSpring.notice.model.service.NoticeService;
import com.boss.blueSpring.notice.model.vo.Attachment;
import com.boss.blueSpring.notice.model.vo.Notice;
import com.boss.blueSpring.notice.model.vo.PageInfo;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI(); // 요청이 들어오는 주소
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/notice").length());

		String path = null;
		RequestDispatcher view = null;

		String errorMsg = null;

		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		try {
			NoticeService service = new NoticeService();

			// 현재 페이지를 얻어옴
			String cp = request.getParameter("cp"); // 처음은 null

			// 공지사항 목록 조회 **********************************************
			if (command.equals("/list.do")) {
				PageInfo pInfo = service.getPageInfo(cp);

				List<Notice> list = service.selectList(pInfo);

				/* 썸네일 관련 부분 */

				path = "/WEB-INF/views/notice/noticeList.jsp";

				request.setAttribute("list", list);
				request.setAttribute("pInfo", pInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			// 공지사항 상세보기 **********************************************
			else if (command.equals("/view.do")) {
				int noticeNo = Integer.parseInt(request.getParameter("no")); // 파라미터 다 String형임

				// 상세조회 비지니스 로직 수행 후 결과 반환 받기
				Notice notice = service.selectNotice(noticeNo);
				
				// 해당 게시글에 포함된 이미지 파일 목록 조회 서비스 호출
				List<Attachment> fList = service.selectNoticeFiles(noticeNo);
				
				if(!fList.isEmpty()) { // 해당 게시글 이미지 정보가 DB에 있을 경우
					request.setAttribute("fList", fList);
				}

				path = "/WEB-INF/views/notice/noticeView.jsp";
				request.setAttribute("notice", notice);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}

			// 공지사항 등록 이동 **********************************************
			else if (command.equals("/insertForm.do")) {
				path = "/WEB-INF/views/notice/noticeInsert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			
			// 공지글 등록 Controller (+파일 업로드) *************************
			else if(command.equals("/insert.do")) {
				
				errorMsg = "게시글 삽입 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 20MB == 20 * 1024KB == 20 * 1024 * 1024Byte
			
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/uploadImages/notice/";
				

				MultipartRequest multiRequest 
					= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = multiRequest.getFileNames();

				while(files.hasMoreElements()) { // 다음 요소가 있다면
					
					// 현재 접근한 요소 값 반환
					String name = files.nextElement(); //img0
					
					// 제출받은 file 태그 요소 중 업로드된 파일이 있을 경우
					if(multiRequest.getFilesystemName(name) != null) {
						
						// Attachment 객체에 파일 정보 저장
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
						
						// fList에 추가
						fList.add(temp);
					}
				} // end while
				
				// 3. 파일 정보를 제외한 게시글 정보를 얻어와 저장하기
				String noticeTitle = multiRequest.getParameter("noticeTitle");
				String noticeContent = multiRequest.getParameter("noticeContent");

				
				// 세션에서 로그인한 회원의 번호를 얻어옴
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");   //그냥 가져로면 Object 임 . 형변환 필요
				int noticeWriter = loginMember.getMemberNo();
				// fList, boardTitle, boardContent, categoryCode, boardWriter
				
				// Map 객체를 생성하여 얻어온 정보들을 모두 저장
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("noticeTitle", noticeTitle);
				map.put("noticeContent", noticeContent);
				map.put("noticeWriter", noticeWriter);

				//System.out.println(map);
				
				// 4. 공지글 등록 비지니스 로직 수행 후 결과 반환받기
				int result = service.insertNotice(map);
				
				if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장되어있다.
					swalIcon = "success";
					swalTitle = "공지글 등록 성공";
					path = "view.do?cp=1&no=" + result;
				}
					else {
					swalIcon = "error";
					swalTitle = "공지글 등록 실패";
					path = "list.do"; //게시글 목록
				}
//
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			
			
			// 게시글 수정 화면 전환 Controller ***********************************************
			else if(command.equals("/updateForm.do")) {
					
				errorMsg = "게시글 수정 화면 전환 과정에서 오류 발생";
				
				// 수정화면이 미리 이전 내용을 작성될 수 있게
				// 글 번호를 이용하여 이전 내용을 조회해옴
				int noticeNo = Integer.parseInt(request.getParameter("no"));
				
				Notice notice = service.updateView(noticeNo);
				
				// 업데이트 화면 출력용 게시글 조회가 성공한 경우
				if(notice != null) {
					// 해당 게시글에 작성된 이미지(파일) 목록 정보 조회
					List<Attachment> fList = service.selectNoticeFiles(noticeNo);
					
					if(!fList.isEmpty()) {  // 조회됐다면
						request.setAttribute("fList", fList);
					}
					request.setAttribute("notice", notice);
					path = "/WEB-INF/views/notice/noticeUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				
				} else {
					
					response.sendRedirect(request.getHeader("referer"));
					//  상세 조회 -> 수정 화면
				
				}
			}

			

			// 공지글 수정Controller ***********************************************

			else if(command.equals("/update.do")) {
				errorMsg = "공지글 수정 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 20MB == 20 * 1024KB == 20 * 1024 * 1024Byte
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/uploadImages/notice/";
				
				MultipartRequest mRequest 
					= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				String noticeTitle = mRequest.getParameter("noticeTitle");
				String noticeContent = mRequest.getParameter("noticeContent");
				int noticeNo = Integer.parseInt(mRequest.getParameter("no"));
				
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = mRequest.getFileNames();

				while(files.hasMoreElements()) { // 다음 요소가 있다면
					
					// 현재 접근한 요소 값 반환
					String name = files.nextElement(); //img0
					
					// 제출받은 file 태그 요소 중 업로드된 파일이 있을 경우
					if(mRequest.getFilesystemName(name) != null) {
						
						// Attachment 객체에 파일 정보 저장
						Attachment temp = new Attachment();
						
						temp.setFileName(mRequest.getFilesystemName(name));
						temp.setFilePath(filePath);
						temp.setParentNoticeNo(noticeNo);
						
						// name 속성에 따라 fileLevel 지정

						//int fileLevel = 0;
						switch(name) {
						case "img0" : temp.setFileLevel(0); break;
						case "img1" : temp.setFileLevel(1); break;
						case "img2" : temp.setFileLevel(2); break;
						}
						
						//temp.setFileLevel(fileLevel);

						
						// fList에 추가
						fList.add(temp);
					}
				} 
				

				int noticeWriter = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo(); 

				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("noticeTitle", noticeTitle);
				map.put("noticeContent", noticeContent);
				map.put("noticeWriter", noticeWriter);
				map.put("noticeNo", noticeNo);

				int result = service.updateNotice(map);
				
				path = "view.do?cp=" + cp + "&no=" + noticeNo; 
				
				
				if(result > 0) {
//					swalIcon = "success";
//					swalTitle = "게시글 수정 성공";
					
					String sk = mRequest.getParameter("sk");
					String sv = mRequest.getParameter("sv");
					
					if(sk != null && sv != null) {
						path += "&sk=" + sk + "&sv=" + sv;
					}
					swalIcon = "success";
					swalTitle = "공지사항 수정 성공";
					
				} else {
					swalIcon = "error";
					swalTitle = "공지사항 수정 실패";
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);
				
			}
			  
			// 공지글 삭제 Controller ******************************************
			else if(command.equals("/delete.do")) {
				errorMsg = "공지글 삭제 과정에서 오류 발생";
				
				//게시글 번호 얻어오기
				int noticeNo = Integer.parseInt(request.getParameter("no"));
				
				// 게시글 삭제(게시글 상태 -> 'N') 비지니스 로직 수행 후 결과 반환
				int result = service.updateNoticeStatus(noticeNo);
				
				
				// 비즈니스 로직 결과에 따라
				// "게시글 삭제 성공" / "게시글 삭제 실패" 매세지를 전달
				// 삭제 성공 시 : 게시글 목록 redirect
				// 삭제 실패 시 : 삭제 시도한 게시글 상세조회 페이지
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "공지글 삭제 성공";
					path = "list.do";
				}else {
					swalIcon = "error";
					swalTitle = "공지글 삭제 실패";
					path = request.getHeader("referer"); // 삭제전의 상세조회 페이지
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
				
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
