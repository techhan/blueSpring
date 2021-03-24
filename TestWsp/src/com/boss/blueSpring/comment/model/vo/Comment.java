package com.boss.blueSpring.comment.model.vo;

import java.sql.Timestamp;

public class Comment {
	private int comNo; 		 			// 댓글 번호
	private String comContent; 			// 댓글 내용
	private Timestamp comCreateDate;	// 댓글 작성일
	private int parentBoardNo;			// 댓글이 작성된 게시글 번호
	private String memberId;			// 댓글 작성 회원
	private String comStatus;			// 댓글 상태
	private String memberNickName;			// 댓글 상태

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(int comNo, String comContent, Timestamp comCreateDate, int parentBoardNo, String memberId,
			String comStatus) {
		super();
		this.comNo = comNo;
		this.comContent = comContent;
		this.comCreateDate = comCreateDate;
		this.parentBoardNo = parentBoardNo;
		this.memberId = memberId;
		this.comStatus = comStatus;
	}
	
	public Comment(int comNo, String comContent, Timestamp comCreateDate, int parentBoardNo, String memberId,
			String comStatus, String memberNickName) {
		super();
		this.comNo = comNo;
		this.comContent = comContent;
		this.comCreateDate = comCreateDate;
		this.parentBoardNo = parentBoardNo;
		this.memberId = memberId;
		this.comStatus = comStatus;
		this.memberNickName = memberNickName;
	}

	public int getComNo() {
		return comNo;
	}

	public void setComNo(int comNo) {
		this.comNo = comNo;
	}

	public String getComContent() {
		return comContent;
	}

	public void setComContent(String comContent) {
		this.comContent = comContent;
	}

	public Timestamp getComCreateDate() {
		return comCreateDate;
	}

	public void setComCreateDate(Timestamp comCreateDate) {
		this.comCreateDate = comCreateDate;
	}

	public int getParentBoardNo() {
		return parentBoardNo;
	}

	public void setParentBoardNo(int parentBoardNo) {
		this.parentBoardNo = parentBoardNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getComStatus() {
		return comStatus;
	}

	public void setComStatus(String comStatus) {
		this.comStatus = comStatus;
	}
	
	public String getMemberNickName() {
		return memberNickName;
	}

	public void setMemberNickName(String memberNickName) {
		this.memberNickName = memberNickName;
	}

	@Override
	public String toString() {
		return "Comment [comNo=" + comNo + ", comContent=" + comContent + ", comCreateDate=" + comCreateDate
				+ ", parentBoardNo=" + parentBoardNo + ", memberId=" + memberId + ", comStatus=" + comStatus
				+ ", memberNickName=" + memberNickName + "]";
	}

	
	
}
