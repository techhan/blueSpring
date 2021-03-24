package com.boss.blueSpring.main.model.vo;

import java.sql.Date;

public class MainSearch {
	
	private int no;				// 메인 검색용 번호
	private String title;		// 제목
	private String content;		// 내용
	private Date crtDt;			// 작성일
	private int type;			// 타입
	private String deleteFl;	// 삭제여부
	
	public MainSearch() {
	}

	public MainSearch(int no, String title, String content, Date crtDt, int type, String deleteFl) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.crtDt = crtDt;
		this.type = type;
		this.deleteFl = deleteFl;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCrtDt() {
		return crtDt;
	}

	public void setCrtDt(Date crtDt) {
		this.crtDt = crtDt;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDeleteFl() {
		return deleteFl;
	}

	public void setDeleteFl(String deleteFl) {
		this.deleteFl = deleteFl;
	}

	@Override
	public String toString() {
		return "MainSearch [no=" + no + ", title=" + title + ", content=" + content + ", crtDt=" + crtDt + ", type="
				+ type + ", deleteFl=" + deleteFl + "]";
	}
	
	
	

}
