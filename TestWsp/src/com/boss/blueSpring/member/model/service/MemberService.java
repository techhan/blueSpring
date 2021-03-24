package com.boss.blueSpring.member.model.service;

import com.boss.blueSpring.member.model.dao.MemberDAO;
import com.boss.blueSpring.member.model.vo.Member;

import java.sql.Connection;
import java.util.Map;

import static com.boss.blueSpring.common.JDBCTemplate.*;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();
	
	
	

	/** 로그인 Service
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member loginMember(Member member) throws Exception {
		Connection conn = getConnection();
		Member loginMember = dao.loginMember(conn, member);
		close(conn);
		return loginMember;
	}




	/** 아이디 중복 체크 Service
	 * @param id
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String id) throws Exception {
		Connection conn = getConnection();
		int result = dao.idDupCheck(conn, id);
		close(conn);
		return result;
	}




	/** 닉네임 중복 체크 Service
	 * @param nickname
	 * @return result
	 * @throws Exception
	 */
	public int nicknameDubCheck(String nickname) throws Exception {
		Connection conn = getConnection();
		int result = dao.nicknameDubCheck(conn, nickname);
		close(conn);
		return result;
	}




	/** 이메일 중복 체크 Service
	 * @param email
	 * @return result 
	 * @throws Exception
	 */
	public int emailDupCheck(String email) throws Exception{
		Connection conn = getConnection();
		int result = dao.emailDupCheck(conn, email);
		close(conn);
		return result;
	}




	/** 회원가입 Service
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception {
		Connection conn = getConnection();
		int result = dao.signUp(conn, member);
		if(result > 0) commit(conn);
		else commit(conn);
		close(conn);
		return result;
	}




	/** 아이디 찾기 Service
	 * @param map
	 * @return memberIdFind
	 * @throws Exception
	 */
	public String idFind(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		String memberIdFind = dao.idFind(conn, map);
		close(conn);
		return memberIdFind;
	}




	/** 비밀번호 찾기 Service
	 * @param map
	 * @return result 
	 * @throws Exception
	 */
	public int pwFind(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		int result = dao.pwFind(conn, map);
		close(conn);		
		return result;
	}




	/** 비밀변호 변경 Service
	 * @param newPw
	 * @return result
	 * @throws Exception
	 */
	public int changePw(String newPw, int memNo) throws Exception {
		Connection conn = getConnection();
		int result = dao.changePw(conn, newPw, memNo);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}
}
