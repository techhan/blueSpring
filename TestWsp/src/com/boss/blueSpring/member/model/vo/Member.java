package com.boss.blueSpring.member.model.vo;

import java.sql.Date;

public class Member {
	private int memberNo; // 번호
	private String memberId; // 아이디
	private String memberPwd; // 비밀번호
	private String memberNm; // 이름
	private Date memberBirth; // 생년월일
	private char memberGender; // 성별
	private String memberPhone; // 전화번호
	private String memberAddr; // 주소
	private String memberEmail; // 이메일
	private Date memberJoined; // 가입일
	private char memberScsnFl; // 탈퇴여부
	private char memberBlackList; // 블랙리스트 여부
	private char memberLevel; // 등급
	private String memberNickname; //닉네임
	
	
	
	public Member() {
	}

	
	// 로그인 생성자
	public Member(int memberNo, String memberId, String memberPwd, String memberNm, Date memberBirth, char memberGender,
			String memberPhone, String memberAddr, String memberEmail, Date memberJoined, char memberScsnFl,
			char memberBlackList, char memberLevel, String memberNickname) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberNm = memberNm;
		this.memberBirth = memberBirth;
		this.memberGender = memberGender;
		this.memberPhone = memberPhone;
		this.memberAddr = memberAddr;
		this.memberEmail = memberEmail;
		this.memberJoined = memberJoined;
		this.memberScsnFl = memberScsnFl;
		this.memberBlackList = memberBlackList;
		this.memberLevel = memberLevel;
		this.memberNickname = memberNickname;
	}
	
	
	
	
	public Member(int memberNo, String memberId, String memberNm, Date memberBirth, char memberGender,
			String memberPhone, String memberAddr, String memberEmail, Date memberJoined, char memberScsnFl,
			char memberBlackList, char memberLevel, String memberNickname) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberNm = memberNm;
		this.memberBirth = memberBirth;
		this.memberGender = memberGender;
		this.memberPhone = memberPhone;
		this.memberAddr = memberAddr;
		this.memberEmail = memberEmail;
		this.memberJoined = memberJoined;
		this.memberScsnFl = memberScsnFl;
		this.memberBlackList = memberBlackList;
		this.memberLevel = memberLevel;
		this.memberNickname = memberNickname;
	}

	// 회원가입 생성자
	
	public Member(String memberId, String memberPwd, String memberNm, Date memberBirth, char memberGender,
			String memberPhone, String memberAddr, String memberEmail, String memberNickname) {
		super();
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberNm = memberNm;
		this.memberBirth = memberBirth;
		this.memberGender = memberGender;
		this.memberPhone = memberPhone;
		this.memberAddr = memberAddr;
		this.memberEmail = memberEmail;
		this.memberNickname = memberNickname;
	}


	// admin 회원정보 조회 용
	public Member(int memberNo, String memberId, String memberNickname, String memberNm, Date memberBirth, char memberGender,
			String memberPhone, String memberAddr, Date memberJoined, char memberScsnFl, char memberBlackList,
			char memberLevel) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberNickname = memberNickname;
		this.memberNm = memberNm;
		this.memberBirth = memberBirth;
		this.memberGender = memberGender;
		this.memberPhone = memberPhone;
		this.memberAddr = memberAddr;
		this.memberJoined = memberJoined;
		this.memberScsnFl = memberScsnFl;
		this.memberBlackList = memberBlackList;
		this.memberLevel = memberLevel;
	}
	
	// admin 블랙리스트 조회용
	public Member(int memberNo, String memberId, String memberNickname, String memberNm, Date memberBirth, char memberGender,
			String memberPhone, Date memberJoined, char memberScsnFl, char memberBlackList,
			char memberLevel) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberNickname = memberNickname;
		this.memberNm = memberNm;
		this.memberBirth = memberBirth;
		this.memberGender = memberGender;
		this.memberPhone = memberPhone;
		this.memberJoined = memberJoined;
		this.memberScsnFl = memberScsnFl;
		this.memberBlackList = memberBlackList;
		this.memberLevel = memberLevel;
	}



	public int getMemberNo() {
		return memberNo;
	}



	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberNm() {
		return memberNm;
	}

	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}

	public Date getMemberBirth() {
		return memberBirth;
	}

	public void setMemberBirth(Date memberBirth) {
		this.memberBirth = memberBirth;
	}

	public char getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(char memberGender) {
		this.memberGender = memberGender;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberAddr() {
		return memberAddr;
	}

	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public Date getMemberJoined() {
		return memberJoined;
	}

	public void setMemberJoined(Date memberJoined) {
		this.memberJoined = memberJoined;
	}

	public char getMemberScsnFl() {
		return memberScsnFl;
	}

	public void setMemberScsnFl(char memberScsnFl) {
		this.memberScsnFl = memberScsnFl;
	}

	public char getMemberBlackList() {
		return memberBlackList;
	}

	public void setMemberBlackList(char memberBlackList) {
		this.memberBlackList = memberBlackList;
	}

	public char getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(char memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getMemberNickname() {
		return memberNickname;
	}


	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}


	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPwd=" + memberPwd + ", memberNm="
				+ memberNm + ", memberBirth=" + memberBirth + ", memberGender=" + memberGender + ", memberPhone="
				+ memberPhone + ", memberAddr=" + memberAddr + ", memberEmail=" + memberEmail + ", memberJoined="
				+ memberJoined + ", memberScsnFl=" + memberScsnFl + ", memberBlackList=" + memberBlackList
				+ ", memberLevel=" + memberLevel + ", memberNickname=" + memberNickname + "]";
	}
}
