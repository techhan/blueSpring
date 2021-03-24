package com.boss.blueSpring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.boss.blueSpring.member.model.vo.Member;
// "/notice/insert.do",
//"/notice/insertForm.do", "/notice/updateForm.do","/notice/update.do", "/notice/delete.do"
@WebFilter(urlPatterns= { "/admin/adminMain.do", "/admin/adminMemberInfo.do", "/admin/adminReport.do",
		"/admin/reportPage.do", "/admin/adminBlacklistInfo.do", "/admin/adminCenterInfo.do",
		"/admin/centerPage.do", "/admin/centerAdd.do", "/admin/centerDubCheck.do", 
		"/admin/centerComplete.do", "/admin/centerUpdate.do", "/admin/updateAction.do", 
		"/admin/adminBoard.do", "/admin/adminChall.do", "/admin/adminCrtfd.do" }
)
public class AdminFilter implements Filter {

    public AdminFilter() {}
	public void destroy() {}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		HttpSession session = req.getSession();
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		if(loginMember == null || loginMember.getMemberLevel() != 'A') { //  Member VO 수정 --> 오류 발생
			// 로그인이 되어있지 않거나 회원 등급이 "A"가 아닌 경우
			
			// 메인 페이지로 강제 이동
			res.sendRedirect(req.getContextPath());
			
		} else {
			chain.doFilter(request, response);
		}
	}

	
	
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
