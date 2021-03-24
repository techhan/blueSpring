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
//"/member/myPage.do", "/member/changePwd.do", "/member/secession.do","/member/updateMember.do", "/member/updatePwd.do", "/member/updateStatus.do"
@WebFilter(urlPatterns = {"/mypage/*"})
public class LoginFilter implements Filter {

    public LoginFilter() {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// ServletRequest 매개변수를 HttpServletRequest로 다운 캐스팅
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		// Session 얻어오기
		HttpSession session = req.getSession();
		
		// Session에 있는 로그인된 회원 정보를 얻어옴.
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		if(loginMember == null) { // 로그인이 되어있지 않은 경우
			// 메인 페이지로 강제 이동
			res.sendRedirect(req.getContextPath());
			
		} else {
			chain.doFilter(request, response);	
			// 다음 필터 호출
			// 다음 필터가 없으면 Servlet 또는 JSP로 이동
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
