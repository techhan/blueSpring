package com.boss.blueSpring.board.model.vo;

import java.sql.Timestamp;

public class Board {
	// DB에서 만든 V_BOARD의 모양 그대로 필드 선언
	private int boardNo;
	private String boardTitle;
	private Timestamp boardCreateDate;
	private String boardContent;
	private int readCount;
	private String boardStatus;
	private Timestamp boardModifyDate;
	private String memberId;
	private String categoryName;
	private int likeCount;
	private String memberNickname;
	
	public Board() {
		// TODO Auto-generated constructor stub
	}

	
	public Board(int boardNo, String boardTitle, String memberId, int readCount, String categoryName,
			Timestamp boardCreateDate, int likeCount) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.memberId = memberId;
		this.readCount = readCount;
		this.categoryName = categoryName;
		this.boardCreateDate = boardCreateDate;
		this.likeCount = likeCount;
	}
	
	// 게시글 목록 조회용 생성자	
	public Board(int boardNo, String boardTitle, String memberId, int readCount, String categoryName,
			Timestamp boardCreateDate, String memberNickname, int likeCount) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.memberId = memberId;
		this.readCount = readCount;
		this.categoryName = categoryName;
		this.boardCreateDate = boardCreateDate;
		this.memberNickname = memberNickname;
		this.likeCount = likeCount;
	}

	// admin 자유게시판 관리 (게시글 조회)
	public Board(int boardNo, String boardTitle, String memberId, String boardStatus) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.memberId = memberId;
		this.boardStatus = boardStatus;

	}

	public Board(int boardNo, String boardTitle, Timestamp boardCreateDate, String boardContent, int readCount,
			String boardStatus, Timestamp boardModifyDate, String memberId, String categoryName, int likeCount,
			String memberNickname) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardCreateDate = boardCreateDate;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.boardStatus = boardStatus;
		this.boardModifyDate = boardModifyDate;
		this.memberId = memberId;
		this.categoryName = categoryName;
		this.likeCount = likeCount;
		this.memberNickname = memberNickname;
	}


	public int getBoardNo() {
		return boardNo;
	}


	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}


	public String getBoardTitle() {
		return boardTitle;
	}


	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}


	public Timestamp getBoardCreateDate() {
		return boardCreateDate;
	}


	public void setBoardCreateDate(Timestamp boardCreateDate) {
		this.boardCreateDate = boardCreateDate;
	}


	public String getBoardContent() {
		return boardContent;
	}


	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}


	public int getReadCount() {
		return readCount;
	}


	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}


	public String getBoardStatus() {
		return boardStatus;
	}


	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}


	public Timestamp getBoardModifyDate() {
		return boardModifyDate;
	}


	public void setBoardModifyDate(Timestamp boardModifyDate) {
		this.boardModifyDate = boardModifyDate;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public int getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	public String getMemberNickname() {
		return memberNickname;
	}


	public void setMemberNickname(String boardNickname) {
		this.memberNickname = boardNickname;
	}
	

	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardCreateDate=" + boardCreateDate
				+ ", boardContent=" + boardContent + ", readCount=" + readCount + ", boardStatus=" + boardStatus
				+ ", boardModifyDate=" + boardModifyDate + ", memberId=" + memberId + ", categoryName=" + categoryName
				+ ", likeCount=" + likeCount + ", memberNickname=" + memberNickname + "]";
	}



	
}
