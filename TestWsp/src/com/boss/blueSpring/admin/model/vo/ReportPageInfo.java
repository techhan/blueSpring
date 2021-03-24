package com.boss.blueSpring.admin.model.vo;

public class ReportPageInfo {
	
	// 얻어올 값
	private int currentPage; // 현재 페이지 번호를 저장할 변수
	private int listCount;   // 전체 게시글 수를 저장할 변수
	
	// 설정할 값
	private int limit = 20;  // 한 페이지에 보여질 게시글 목록 수
	private int pageSize = 10;  // 페이징바에 표시될 페이지 수
	
	// 계산할 값
	private int maxPage;  // 전체 목록 페이지의 수 == 마지막 페이지
	private int startPage;  // 페이징바 시작 페이지 번호
	private int endPage;  // 페이징바 끝 페이지 번호
	
	
	// 기본 생성자 사용 X
	
	
	public ReportPageInfo(int currentPage, int listCount) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		
		// 전달받은 값과 명시적으로 선언된 값을 이용하여 
		// makePageInfo() 수행
		makePageInfo();
	}

	public ReportPageInfo(int currentPage, int listCount, int limit, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
		this.limit = limit;
		this.pageSize = pageSize;
		
		makePageInfo();
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


	public void setListCount(int listCount) {
		this.listCount = listCount;	
		makePageInfo();
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
		makePageInfo();
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
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
		return "ReportPageInfo [currentPage=" + currentPage + ", listCount=" + listCount + ", limit=" + limit
				+ ", pageSize=" + pageSize + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + "]";
	}

	
	// 페이징 처리에 필요한 값을 계산하는 메소드
	private void makePageInfo() {
		// maxPage : 총 페이지 수 == 마지막 페이지
		// 총 게시글 수 100개, 한 페이지에 보여지는 게시글 수 10개
		// -> 총 페이지 수는 ? 100 / 10 = 10페이지
		
		// 총 게시글 수 101개, 한 페이지에 보여지는 게시글 수 10개
		// -> 총 페이지 수는 ? 101 / 10 = 10.1페이지(올림 처리) -> 11페이지
		maxPage = (int)Math.ceil((double)listCount / limit);
		
		// StartPage : 페이징바 시작 번호
		// 페이징바에 페이지를 10개씩 보여줄 경우 
		// 1, 11, 21, 31, ...
		// 현제페이지: 11페이지 -> 시작페이지 11
		// 현제페이지: 15페이지 -> 시작페이지 11
		// 현제페이지: 20페이지 -> 시작페이지 11
		startPage = (currentPage - 1) / pageSize * pageSize + 1;
		//				     (11 - 1) /	   10    *   10     + 1;           
		//				     (15 - 1) /	   10    *   10     + 1;  !주의! int와 int의 계산이다! 1.4같은 결과는 나오지 않음
		//				     (20 - 1) /	   10    *   10     + 1;                
		
		// endPage : 페이징바 끝 번호
		// 페이징바에 페이지를 10개씩 보여줄 경우
		// 10, 20, 30, 40, ...
		// 현제페이지: 11페이지 -> 끝페이지 20
		// 현제페이지: 15페이지 -> 끝페이지 20
		// 현제페이지: 20페이지 -> 끝페이지 20
		endPage = startPage + pageSize - 1;
		//           11     +     10    - 1;
		//           11     +     10    - 1;
		//           11     +     10    - 1;
		
		// 총 페이지의 수가 end페이지 보다 작을 경우
		// maxPage가 55이고 끝페이지가 60이라면?
		// 55가 60보다 작거나 같다라는 조건에 맞기 때문에 60은 55가 된다.(즉, 끝페이지는 55가 됨)
		if(maxPage <= endPage) {
			endPage = maxPage;
		}
	}

}
