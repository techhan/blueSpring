package com.boss.blueSpring.report.model.vo;

import java.sql.Date;

public class Report {
	private int reportNo; 			// 신고글 번호
	private String reportType;  	// 신고 유형(댓글, 게시판)
	private int boardNo; 			// 신고 대상 글
	private int memberNo; 			// 신고자
	private int reportCategoryNo;   // 신고 유형
	private Date reportCreateDate;  // 신고접수일
	private String reportDeleteFl;  // 신고글 삭제 유무
	private String memberId;		// 신고 회원 아이디
	private String targetId;		// 신고당한 회원
	private String memberBlacklist;	// 블랙리스트 회원
	private String reportContent;	// 신고 접수 내용
	
	
	public Report() {
	}
	
	public Report(int reportNo, String reportType, int boardNo, int memberNo, int reportCategoryNo,
			Date reportCreateDate, String reportDeleteFl, String memberId, String targetId, String memberBlacklist, String reportContent) {
		super();
		this.reportNo = reportNo;
		this.reportType = reportType;
		this.boardNo = boardNo;
		this.memberNo = memberNo;
		this.reportCategoryNo = reportCategoryNo;
		this.reportCreateDate = reportCreateDate;
		this.reportDeleteFl = reportDeleteFl;
		this.memberId = memberId;
		this.targetId = targetId;
		this.memberBlacklist = memberBlacklist;
		this.reportContent = reportContent;
	}
	
	// admin 신고 목록 조회 생성자
	public Report(int reportNo, String reportType, int boardNo, int reportCategoryNo, String memberId, String targetId, String memberBlacklist) {
		super();
		this.reportNo = reportNo;
		this.reportType = reportType;
		this.boardNo = boardNo;
		this.reportCategoryNo = reportCategoryNo;
		this.memberId = memberId;
		this.targetId = targetId;
		this.memberBlacklist = memberBlacklist;
	}
	
	// admin 신고 상세 페이지 조회 생성자
	public Report(int reportNo, String reportType, int boardNo, String memberId, int reportCategoryNo, String reportContent) {
		super();
		this.reportNo = reportNo;
		this.reportType = reportType;
		this.boardNo = boardNo;
		this.memberId = memberId;
		this.reportCategoryNo = reportCategoryNo;
		this.reportContent = reportContent;
	}

	public int getReportNo() {
		return reportNo;
	}


	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getReportCategoryNo() {
		return reportCategoryNo;
	}

	public void setReportCategoryNo(int reportCategoryNo) {
		this.reportCategoryNo = reportCategoryNo;
	}

	public Date getReportCreateDate() {
		return reportCreateDate;
	}

	public void setReportCreateDate(Date reportCreateDate) {
		this.reportCreateDate = reportCreateDate;
	}

	public String getReportDeleteFl() {
		return reportDeleteFl;
	}

	public void setReportDeleteFl(String reportDeleteFl) {
		this.reportDeleteFl = reportDeleteFl;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberBlacklist() {
		return memberBlacklist;
	}

	public void setMemberBlacklist(String memberBlacklist) {
		this.memberBlacklist = memberBlacklist;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	@Override
	public String toString() {
		return "Report [reportNo=" + reportNo + ", reportType=" + reportType + ", boardNo=" + boardNo + ", memberNo="
				+ memberNo + ", reportCategoryNo=" + reportCategoryNo + ", reportCreateDate=" + reportCreateDate
				+ ", reportDeleteFl=" + reportDeleteFl + ", memberId=" + memberId + ", targetId=" + targetId
				+ ", memberBlacklist=" + memberBlacklist + ", reportContent=" + reportContent + "]";
	}
	
	
	
		
}
