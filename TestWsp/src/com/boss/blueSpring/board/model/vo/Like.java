package com.boss.blueSpring.board.model.vo;

public class Like {
	private int memberNo;
	private int boardNo;
	
	public Like() {
		// TODO Auto-generated constructor stub
	}

	public Like(int memberNo, int boardNo) {
		super();
		this.memberNo = memberNo;
		this.boardNo = boardNo;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	@Override
	public String toString() {
		return "Like [memberNo=" + memberNo + ", boardNo=" + boardNo + "]";
	}
	
	
}
