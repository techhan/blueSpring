package com.boss.blueSpring.challengecrtfd.model.vo;

public class PageInfo {  

	// 얻어올 값
	private int currentPage;  // 현재 페이지 번호를 저장할 변수
	private int listCount;    // 전체 게시글 수를 저장할 변수
	
	// 설정할 값
	private int limit = 10;    // 한 페이지에 보여질 게시글 목록 수 
	private int pageSize = 10; // 페이징바에 표시될 페이지 수 
	
	// 계산할 값
	private int maxPage;		// 전체 목록 페이지의 수 == 마지막 페이지
	private int startPage;      // 페이징바 시작 페이지 번호
	private int endPage; 		// 페이징바 끝 페이지 번호
	

	// 기본 생성자 사용 x

	public PageInfo(int currentPage, int listCount) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		
		// 전달받은 값과 명시적으로 선언된 값을 이용하여
		//  makePageInfo() 수행
		makePageInfo();
	}

	public PageInfo(int currentPage, int listCount, int limit, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.limit = limit;
		this.pageSize = pageSize;
		
		makePageInfo(); //혹시라도 다른 값이 바뀌게된는경우가 잇을 수 있으니
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {  // 이 값이 바뀌면 다시 계산해야된다
		this.listCount = listCount;
		makePageInfo();
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) { // 이 값이 바뀌면 다시 계산해야된다
		this.limit = limit;
		makePageInfo();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) { // 이 값이 바뀌면 다시 계산해야된다
		this.pageSize = pageSize;
		makePageInfo();
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", listCount=" + listCount + ", limit=" + limit + ", pageSize="
				+ pageSize + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
	private void makePageInfo() {
		
		maxPage =  (int)Math.ceil((double)listCount / limit);
		
		startPage = (currentPage -1) / pageSize * pageSize + 1;
		endPage = startPage + pageSize - 1;
		
		// 총 페이지의 수가 end 페이지 보다 작을 경우
		if(maxPage <= endPage) {   //maxPage 데이터가 있는 부분까지
			endPage = maxPage;
		}
				
	
	
	
	
	
	}
	
	
}
