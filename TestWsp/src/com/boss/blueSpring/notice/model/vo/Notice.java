package com.boss.blueSpring.notice.model.vo;


import java.sql.Timestamp;

public class Notice {

	private int noticeNo; // 공지글 번호
	private String noticeTitle; // 공지글 제목
	private Timestamp noticeCrtDt; // 작성일
	private String noticeContent; //공지글 내용
	private String noticeDelFl; // 삭제여부(N,Y)
	private Timestamp noticeUpdateDt; // 수정날짜
	private String memberId; // 작성자 아이디
	private int noticeViews; //조회수
	
	public Notice() {}

	public Notice(int noticeNo, String noticeTitle, Timestamp noticeCrtDt, String noticeContent, String noticeDelFl,
			Timestamp noticeUpdateDt, String memberId, int noticeViews) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeCrtDt = noticeCrtDt;
		this.noticeContent = noticeContent;
		this.noticeDelFl = noticeDelFl;
		this.noticeUpdateDt = noticeUpdateDt;
		this.memberId = memberId;
		this.noticeViews = noticeViews;
	}
	
	// main 공지사항 조회용 생성자
	public Notice(int noticeNo, String noticeTitle) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
	}
	

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public Timestamp getNoticeCrtDt() {
		return noticeCrtDt;
	}

	public void setNoticeCrtDt(Timestamp noticeCrtDt) {
		this.noticeCrtDt = noticeCrtDt;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeDelFl() {
		return noticeDelFl;
	}

	public void setNoticeDelFl(String noticeDelFl) {
		this.noticeDelFl = noticeDelFl;
	}

	public Timestamp getNoticeUpdateDt() {
		return noticeUpdateDt;
	}

	public void setNoticeUpdateDt(Timestamp noticeUpdateDt) {
		this.noticeUpdateDt = noticeUpdateDt;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getNoticeViews() {
		return noticeViews;
	}

	public void setNoticeViews(int noticeViews) {
		this.noticeViews = noticeViews;
	}

	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeCrtDt=" + noticeCrtDt
				+ ", noticeContent=" + noticeContent + ", noticeDelFl=" + noticeDelFl + ", noticeUpdateDt="
				+ noticeUpdateDt + ", memberId=" + memberId + ", noticeViews=" + noticeViews + "]";
	}

	
	
	
	
}
