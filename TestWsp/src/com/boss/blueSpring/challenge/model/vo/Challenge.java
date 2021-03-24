package com.boss.blueSpring.challenge.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Challenge {
	
	private int chlngNo;			// 챌린지 번호
	private String chlngTitle;		// 챌린지 제목
	private String chlngContent;	// 챌린지 내용
	private Timestamp chlngStartDt;		// 챌린지 시작일
	private Timestamp chlngEndDt;		// 챌린지 종료일
	private int currNumMem;			// 챌린지 현재 참가 인원
	private int accNumMem;			// 챌린지 누적 참가 인원
	private char chlngFl;			// 챌린지 삭제 여부
	private Timestamp chlngCreateDt;		// 챌린지 미션 작성일
	private Timestamp chlngUpdateDt;		// 챌린지 미션 수정일
	private int memberNo;			// 회원번호
	private String chlngCateNm;	// 챌린지 카테고리 이름
	private int chlngCateNo;	// 챌린지 카테고리 번호
	private String memberId;		// 회원 아이디
	private int likeCount;
	private String memNickname; //작성자 닉네임
	

	public Challenge() {
	}

	public Challenge(int chlngNo, String chlngTitle, String chlngContent, Timestamp chlngStartDt, Timestamp chlngEndDt,
			int currNumMem, int accNumMem, char chlngFl, Timestamp chlngCreateDt, Timestamp chlngUpdateDt, int memberNo,
			String chlngCateNm, String memberId, int likeCount, String memNickname, int chlngCateNo) {
		super();
		this.chlngNo = chlngNo;
		this.chlngTitle = chlngTitle;
		this.chlngContent = chlngContent;
		this.chlngStartDt = chlngStartDt;
		this.chlngEndDt = chlngEndDt;
		this.currNumMem = currNumMem;
		this.accNumMem = accNumMem;
		this.chlngFl = chlngFl;
		this.chlngCreateDt = chlngCreateDt;
		this.chlngUpdateDt = chlngUpdateDt;
		this.memberNo = memberNo;
		this.chlngCateNm = chlngCateNm;
		this.chlngCateNo = chlngCateNo;
		this.memberId = memberId;
		this.likeCount = likeCount;
		this.memNickname = memNickname;
	}

	
	
	
	public Challenge(int chlngNo, String chlngTitle, String chlngContent, Timestamp chlngStartDt, Timestamp chlngEndDt,
			String chlngCateNm, int likeCount) {
		super();
		this.chlngNo = chlngNo;
		this.chlngTitle = chlngTitle;
		this.chlngContent = chlngContent;
		this.chlngStartDt = chlngStartDt;
		this.chlngEndDt = chlngEndDt;
		this.chlngCateNm = chlngCateNm;
		this.likeCount = likeCount;
	}

	public Challenge(int chlngNo, String chlngTitle, char chlngFl, String chlngCateNm, String memberId) {
		super();
		this.chlngNo = chlngNo;
		this.chlngTitle = chlngTitle;
		this.chlngFl = chlngFl;
		this.chlngCateNm = chlngCateNm;
		this.memberId = memberId;
	}
	
	// admin 챌린지 조회 용
	// CHLNG_NO, CHLNG_TITLE, MEM_ID, CHLNG_FL
	public Challenge(int chlngNo, String chlngTitle, String memberId, char chlngFl) {
		super();
		this.chlngNo = chlngNo;
		this.chlngTitle = chlngTitle;
		this.memberId = memberId;
		this.chlngFl = chlngFl;
	}

	public int getChlngNo() {
		return chlngNo;
	}

	public void setChlngNo(int chlngNo) {
		this.chlngNo = chlngNo;
	}

	public String getChlngTitle() {
		return chlngTitle;
	}

	public void setChlngTitle(String chlngTitle) {
		this.chlngTitle = chlngTitle;
	}

	public String getChlngContent() {
		return chlngContent;
	}

	public void setChlngContent(String chlngContent) {
		this.chlngContent = chlngContent;
	}

	public Timestamp getChlngStartDt() {
		return chlngStartDt;
	}

	public void setChlngStartDt(Timestamp chlngStartDt) {
		this.chlngStartDt = chlngStartDt;
	}

	public Timestamp getChlngEndDt() {
		return chlngEndDt;
	}

	public void setChlngEndDt(Timestamp chlngEndDt) {
		this.chlngEndDt = chlngEndDt;
	}

	public int getCurrNumMem() {
		return currNumMem;
	}

	public void setCurrNumMem(int currNumMem) {
		this.currNumMem = currNumMem;
	}

	public int getAccNumMem() {
		return accNumMem;
	}

	public void setAccNumMem(int accNumMem) {
		this.accNumMem = accNumMem;
	}

	public char getChlngFl() {
		return chlngFl;
	}

	public void setChlngFl(char chlngFl) {
		this.chlngFl = chlngFl;
	}

	public Timestamp getChlngCreateDt() {
		return chlngCreateDt;
	}

	public void setChlngCreateDt(Timestamp chlngCreateDt) {
		this.chlngCreateDt = chlngCreateDt;
	}

	public Timestamp getChlngUpdateDt() {
		return chlngUpdateDt;
	}

	public void setChlngUpdateDt(Timestamp chlngUpdateDt) {
		this.chlngUpdateDt = chlngUpdateDt;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getchlngCateNm() {
		return chlngCateNm;
	}

	public void setchlngCateNm(String chlngCateNm) {
		this.chlngCateNm = chlngCateNm;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getMemNickname() {
		return memNickname;
	}

	public void setMemNickname(String memNickname) {
		this.memNickname = memNickname;
	}

	
	
	public int getChlngCateNo() {
		return chlngCateNo;
	}

	public void setChlngCateNo(int chlngCateNo) {
		this.chlngCateNo = chlngCateNo;
	}

	@Override
	public String toString() {
		return "Challenge [chlngNo=" + chlngNo + ", chlngTitle=" + chlngTitle + ", chlngContent=" + chlngContent
				+ ", chlngStartDt=" + chlngStartDt + ", chlngEndDt=" + chlngEndDt + ", currNumMem=" + currNumMem
				+ ", accNumMem=" + accNumMem + ", chlngFl=" + chlngFl + ", chlngCreateDt=" + chlngCreateDt
				+ ", chlngUpdateDt=" + chlngUpdateDt + ", memberNo=" + memberNo + ", chlngCateNm=" + chlngCateNm
				+ ", chlngCateNo=" + chlngCateNo + ", memberId=" + memberId + ", likeCount=" + likeCount
				+ ", memNickname=" + memNickname + "]";
	}


}