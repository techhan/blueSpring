package com.boss.blueSpring.center.model.vo;

public class Center {

	private int centerNo;			// 센터번호
	private String centerCla;		// 센터분류
	private String centerArea1;		// 지역(시)
	private String centerArea2;		// 지역(구)
	private String centerName;		// 센터이름
	private String centerTel;		// 센터 전화번호
	private String centerUrl;		// 센터 홈페이지
	private String centerAddr;		// 센터 주소
	private String centerAddrDtl;	// 센터 상세주소
	private char centerDeleteFl;	// 센터 삭제여부
	
	public Center() {
	}
	
	public Center(int centerNo, String centerCla, String centerArea1, String centerArea2, String centerName,
			String centerTel, String centerUrl, String centerAddr, String centerAddrDtl, char centerDeleteFl) {
		super();
		this.centerNo = centerNo;
		this.centerCla = centerCla;
		this.centerArea1 = centerArea1;
		this.centerArea2 = centerArea2;
		this.centerName = centerName;
		this.centerTel = centerTel;
		this.centerUrl = centerUrl;
		this.centerAddr = centerAddr;
		this.centerAddrDtl = centerAddrDtl;
		this.centerDeleteFl = centerDeleteFl;
	}

	public Center(int centerNo, String centerCla, String centerArea1, String centerArea2, String centerName,
			String centerTel, String centerUrl, String centerAddr) {
		super();
		this.centerNo = centerNo;
		this.centerCla = centerCla;
		this.centerArea1 = centerArea1;
		this.centerArea2 = centerArea2;
		this.centerName = centerName;
		this.centerTel = centerTel;
		this.centerUrl = centerUrl;
		this.centerAddr = centerAddr;
	}

	// admin 센터 목록 조회용 생성자
	public Center(int centerNo, String centerCla, String centerArea1, String centerArea2, String centerName,
			String centerTel, String centerUrl, char centerDeleteFl) {
		super();
		this.centerNo = centerNo;
		this.centerCla = centerCla;
		this.centerArea1 = centerArea1;
		this.centerArea2 = centerArea2;
		this.centerName = centerName;
		this.centerTel = centerTel;
		this.centerUrl = centerUrl;
		this.centerDeleteFl = centerDeleteFl;
	}
	
	// admin 센터 등록하기 생성자
	public Center(String centerCla, String centerArea1, String centerArea2, String centerName, String centerTel,
			String centerUrl, String centerAddr, String centerAddrDtl) {
		super();
		this.centerCla = centerCla;
		this.centerArea1 = centerArea1;
		this.centerArea2 = centerArea2;
		this.centerName = centerName;
		this.centerTel = centerTel;
		this.centerUrl = centerUrl;
		this.centerAddr = centerAddr;
		this.centerAddrDtl = centerAddrDtl;
	}
	
	
	public int getCenterNo() {
		return centerNo;
	}

	public void setCenterNo(int centerNo) {
		this.centerNo = centerNo;
	}

	public String getCenterCla() {
		return centerCla;
	}

	public void setCenterCla(String centerCla) {
		this.centerCla = centerCla;
	}

	public String getCenterArea1() {
		return centerArea1;
	}

	public void setCenterArea1(String centerArea1) {
		this.centerArea1 = centerArea1;
	}

	public String getCenterArea2() {
		return centerArea2;
	}

	public void setCenterArea2(String centerArea2) {
		this.centerArea2 = centerArea2;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterTel() {
		return centerTel;
	}

	public void setCenterTel(String centerTel) {
		this.centerTel = centerTel;
	}

	public String getCenterUrl() {
		return centerUrl;
	}

	public void setCenterUrl(String centerUrl) {
		this.centerUrl = centerUrl;
	}

	public String getCenterAddr() {
		return centerAddr;
	}

	public void setCenterAddr(String centerAddr) {
		this.centerAddr = centerAddr;
	}

	public String getCenterAddrDtl() {
		return centerAddrDtl;
	}

	public void setCenterAddrDtl(String centerAddrDtl) {
		this.centerAddrDtl = centerAddrDtl;
	}

	public char getCenterDeleteFl() {
		return centerDeleteFl;
	}

	public void setCenterDeleteFl(char centerDeleteFl) {
		this.centerDeleteFl = centerDeleteFl;
	}

	@Override
	public String toString() {
		return "Center [centerNo=" + centerNo + ", centerCla=" + centerCla + ", centerArea1=" + centerArea1
				+ ", centerArea2=" + centerArea2 + ", centerName=" + centerName + ", centerTel=" + centerTel
				+ ", centerUrl=" + centerUrl + ", centerAddr=" + centerAddr + ", centerAddrDtl=" + centerAddrDtl
				+ ", centerDeleteFl=" + centerDeleteFl + "]";
	}


	
}
