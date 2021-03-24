package com.boss.blueSpring.member.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.MembershipService;

import com.boss.blueSpring.member.model.service.MemberService;
import com.boss.blueSpring.member.model.vo.Member;
import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI(); // 요청이 들어오는 주소 
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/member").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		String errorMsg = null; 
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		MemberService mService = new MemberService();
		
		
		
		try {
			request.setCharacterEncoding("UTF-8");
			
			
			// ****************************************************************** 로그인 페이지를 보여주는 Controller ******************************************************************
			if(command.equals("/login.do")) {
				path="/WEB-INF/views/member/loginForm.jsp";				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			
			else if(command.equals("/error.do")) {
				errorMsg = "에러페이지 테스트";			
				 path = "/WEB-INF/views/common/errorPage.jsp";
		         request.setAttribute("errorMsg", errorMsg);
		         view = request.getRequestDispatcher(path);
		         view.forward(request, response);
			}
			
			// ****************************************************************** 로그인  Controller ******************************************************************
			else if(command.equals("/loginAction.do")) {
				errorMsg = "로그인 중 오류가 발생했습니다.";
				
				String memberId = request.getParameter("id_input");
				String memberPwd = request.getParameter("pw_input");
				String idSave = request.getParameter("id_chk");
				
				Member member = new Member();
				member.setMemberId(memberId);
				member.setMemberPwd(memberPwd);
				
				Member loginMember = mService.loginMember(member);
				
				//response.setContentType("text/html; charset=UTF-8");
				
				HttpSession session = request.getSession();
				
				if(loginMember != null && loginMember.getMemberBlackList() == 'N') {
					
					session.setMaxInactiveInterval(60*60);
					
					session.setAttribute("loginMember", loginMember);
					
					Cookie cookie = new Cookie("saveId", memberId);
					
					if(idSave != null) {
						cookie.setMaxAge(60 * 60 * 24 * 7);
					} else {
						cookie.setMaxAge(0);
					}
					
					cookie.setPath(request.getContextPath());
					
					response.addCookie(cookie);
					
					response.sendRedirect(request.getContextPath());
					
				} else if(loginMember != null && loginMember.getMemberBlackList() == 'Y') {
					swalIcon = "error";
					swalTitle = "로그인 실패";
					swalText = "접근이 불가능한 계정입니다.";
					
					response.sendRedirect(request.getContextPath());
				}
				
				else {
					swalIcon = "error";
					swalTitle = "로그인 실패";
					swalText = "아이디와 비밀번호를 확인해주세요.";
					
					response.sendRedirect(request.getHeader("referer"));
					 
				}
				session.setAttribute("swalIcon", swalIcon);
				session.setAttribute("swalTitle", swalTitle);
				session.setAttribute("swalText", swalText);
						
				}
			
			
			// ****************************************************************** 로그아웃 Controller ******************************************************************
			else if(command.equals("/logout.do")) {
				request.getSession().invalidate();
				response.sendRedirect(request.getHeader("referer"));
			}
			
			
			
			// ****************************************************************** 회원가입 Controller ******************************************************************
			else if(command.equals("/signup.do")) {
				path="/WEB-INF/views/member/signUp.jsp";
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			
			// ****************************************************************** 아이디 중복체크 Controller ******************************************************************
			else if(command.equals("/idDupCheck.do")) {
				errorMsg = "아이디 중복 검사 과정에서 오류가 발생했습니다.";
				
				String id = request.getParameter("id");
				int result = mService.idDupCheck(id);
				response.getWriter().print(result);
				
			}
			
			// ****************************************************************** 닉네임 중복체크 Controller ******************************************************************
			else if(command.equals("/nicknameDubCheck.do")) {
				errorMsg = "닉네임 중복 검사 과정에서 오류가 발생했습니다.";
				
				String nickname = request.getParameter("nickname");
				int result = mService.nicknameDubCheck(nickname);
				response.getWriter().print(result);
			}
			
			
			// ****************************************************************** 이메일 중복체크  Controller ******************************************************************
			else if(command.equals("/emailDupCheck.do")) {
				errorMsg = "이메일 중복 검사 과정에서 오류가 발생했습니다.";
				String email = request.getParameter("email");
				int result = mService.emailDupCheck(email);
				response.getWriter().print(result);
			}
			
			
			
			// ****************************************************************** 회원가입 Controller ******************************************************************
			else if(command.equals("/signupComplete.do")) {
				errorMsg = "회원가입 과정에서 오류가 발생했습니다.";
				
				path = "";
				String memberId = request.getParameter("id");
				String memberNickname = request.getParameter("nickName");
				String memberPwd = request.getParameter("pswd1");
				String memberNm = request.getParameter("name");
				
				String postcode = request.getParameter("post");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				String memberAddr = postcode + "," + address1 + "," + address2;
				
				String birthYY = request.getParameter("birth_yy");
				String birthMM = request.getParameter("birth_mm");
				String birthDD = request.getParameter("birth_dd");
				
				String memberBirthday = birthYY + "-" + birthMM + "-" + birthDD;
				java.sql.Date memberBirth = java.sql.Date.valueOf(memberBirthday);
				
				
				char memberGender = request.getParameter("gender").charAt(0);
				String memberPhone = request.getParameter("phone");
				String memberEmail = request.getParameter("email");
				
				
				Member member = new Member(memberId, memberPwd, memberNm, 
											memberBirth, memberGender, memberPhone, 
										   memberAddr, memberEmail, memberNickname);
				
		
					int result = mService.signUp(member);
					
					if(result > 0) {
						path="/WEB-INF/views/member/signUpComplete.jsp";
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					} else {
						
						HttpSession session = request.getSession();
						
						swalIcon = "error";
						swalTitle = "회원가입 실패";
						swalText = "";
						
						session.setAttribute("swalIcon", swalIcon);
						session.setAttribute("swalTitle", swalTitle);
						session.setAttribute("swalText", swalText);
						response.sendRedirect(request.getContextPath());
					}
			}
			
			// ****************************************************************** 아이디 찾기 페이지 Controller ******************************************************************
			else if(command.equals("/idFind.do")) {				
				path="/WEB-INF/views/member/idFind.jsp";
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			
			// ****************************************************************** 아이디 찾기 Controller ******************************************************************
			else if(command.equals("/idFindComplete.do")) {
				errorMsg = "아이디 찾기 과정에서 오류가 발생했습니다.";
				String name = request.getParameter("name");
				String email = request.getParameter("email");

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				map.put("email", email);

				String memberIdFind = mService.idFind(map);
				HttpSession session = request.getSession();
				if (memberIdFind != null) {

					session.setAttribute("memberIdFind", memberIdFind);

					path = "/WEB-INF/views/member/idFindComplete.jsp";

					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				} else {
					swalIcon = "error";
					swalTitle = "아이디 찾기 실패";
					swalText = "이름과 이메일을 다시 확인해주세요.";

					session.setAttribute("swalIcon", swalIcon);
					session.setAttribute("swalTitle", swalTitle);
					session.setAttribute("swalText", swalText);

					response.sendRedirect(request.getHeader("referer"));
				}
			}
			
			
			// ****************************************************************** 비밀번호 찾기 폼 Controller ****************************************************************** 
			else if(command.equals("/pwFind.do")) {
				path="/WEB-INF/views/member/pwFind.jsp";
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			
			
			//  ****************************************************************** 새로운 비밀번호 설정  ******************************************************************
			else if(command.equals("/idEmaliChk.do")) {
				errorMsg = "비밀번호 찾기 과정에서 오류가 발생했습니다.";
				String id = request.getParameter("id");
				String email = request.getParameter("email");

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);
				map.put("email", email);
				
				System.out.println(id);
				System.out.println(email);
				int result = mService.pwFind(map);
				
			//	request.setAttribute("memNO", result);
				System.out.println(result);
				response.getWriter().print(result);
			}
			else if (command.equals("/changePw.do")) {
				errorMsg = "비밀번호 찾기 과정에서 오류가 발생했습니다.";
				

				
				
				HttpSession session = request.getSession();

				String check = request.getParameter("ck");
				int nm = Integer.parseInt(request.getParameter("mN"));
				System.out.println(check);
				System.out.println(nm);
				if (check.equals("true")) {
					

					session.setAttribute("memNo", nm);
					
					path = "/WEB-INF/views/common/newPwForm.jsp";

					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				} else {

					swalIcon = "error";
					swalTitle = "비밀번호 찾기 실패";
					swalText = "아이디와 이메일을 다시 확인해주세요.";
					session.setAttribute("swalIcon", swalIcon);
					session.setAttribute("swalTitle", swalTitle);
					session.setAttribute("swalText", swalText);
				
				}

			}
			
			
			
			
			// 비밀번호 변경 완료 페이지
			else if(command.equals("/changePwComplete.do")) {
				String newPw = request.getParameter("newPw1");
				
				int memNo = Integer.parseInt(request.getParameter("num"));		
				
				int result = mService.changePw(newPw, memNo); 
				
				if(result > 0) {
					
					path="/WEB-INF/views/member/pwFindNewPwComplete.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				} else {
					swalIcon = "error";
					swalTitle = "비밀번호 변경 실패";
					swalText = "비밀번호 형식에 맞게 작성해주세요.";
					path="/WEB-INF/views/member/pwFind.jsp";
					response.sendRedirect(path);
				}
				
			}

			
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", errorMsg);
			path = "/WEB-INF/views/common/errorPage.jsp";
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
