package com.boss.blueSpring.member.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import com.boss.blueSpring.member.model.vo.Member;
import static com.boss.blueSpring.common.JDBCTemplate.*;

public class MemberDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public MemberDAO() {
		try {
			String filePath
			= MemberDAO.class.getResource("/com/boss/blueSpring/sql/member/member-query.xml").getPath();
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	/** 로그인용 DAO
	 * @param conn
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member loginMember(Connection conn, Member member) throws Exception {
		Member loginMember = null;
		
		String query = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			rset = pstmt.executeQuery();

			if(rset.next()) {
				loginMember = new Member(rset.getInt("MEM_NO"),
						rset.getString("MEM_ID"),
						rset.getString("MEM_NM"),
						rset.getDate("MEM_BIRTH"),
						rset.getString("MEM_GENDER").charAt(0),
						rset.getString("MEM_PHONE"),
						rset.getString("MEM_ADDR"),
						rset.getString("MEM_EMAIL"),
						rset.getDate("MEM_JOINED"),
						rset.getString("MEM_SCSN_FL").charAt(0),
						rset.getString("MEM_BLACKLIST").charAt(0),
						rset.getString("MEM_LEVEL").charAt(0),
						rset.getString("MEM_NICKNAME"));
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return loginMember;
	}



	/** 아이디 중복 체크 DAO
	 * @param conn
	 * @param id
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String id) throws Exception{
		int result = 0;
		String query = prop.getProperty("idDupCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}


	
	/** 닉네임 중복 체크 DAO
	 * @param conn
	 * @param nickname
	 * @return result
	 * @throws Exception
	 */
	public int nicknameDubCheck(Connection conn, String nickname) throws Exception {
		int result = 0;
		String query = prop.getProperty("nicknameDupCheck");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nickname);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}



	/** 이메일 중복 체크 DAO
	 * @param conn
	 * @param email
	 * @return result
	 * @throws Exception
	 */
	public int emailDupCheck(Connection conn, String email) throws Exception {
		int result = 0;
		String query = prop.getProperty("emailDupCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}



	/** 회원가입 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Member member) throws Exception {
		int result = 0;
		String query = prop.getProperty("signUp");
		
		try {
			pstmt = conn.prepareStatement(query);
			System.out.println(member.getMemberPwd());
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberNm());
			pstmt.setDate(4, member.getMemberBirth());
			pstmt.setString(5, member.getMemberGender()+"");
			pstmt.setString(6, member.getMemberPhone());
			pstmt.setString(7, member.getMemberAddr());
			pstmt.setString(8, member.getMemberEmail());
			pstmt.setString(9, member.getMemberNickname());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}



	/** 아이디 찾기 DAO
	 * @param conn
	 * @param map
	 * @return memberIdFind
	 * @throws Exception
	 */
	public String idFind(Connection conn, Map<String, Object> map) throws Exception {
		String memberIdFind = null;
		
		String query = prop.getProperty("idFind");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("name"));
			pstmt.setString(2, (String)map.get("email"));
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				memberIdFind = rset.getString(1);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return memberIdFind;
	}



	/** 비밀번호 찾기 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int pwFind(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		String query = prop.getProperty("pwFind");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("id"));
			pstmt.setString(2, (String)map.get("email"));
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
			System.out.println("DAO 테스트 : " + result);
		} finally {
			close(rset);
			close(conn);
		}
		return result;
	}



	/** 비밀번호 변경 DAO
	 * @param conn
	 * @param newPw
	 * @param memNo
	 * @return result
	 * @throws Exception
	 */
	public int changePw(Connection conn, String newPw, int memNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("changePw");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newPw);
			pstmt.setInt(2, memNo);
			
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
}













