<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지 인증 메인 페이지</title>
<link rel="stylesheet" href="${contextPath}/resources/css/challengeCr/ch_cr_list.css" type="text/css">
</head>
<body>

	<!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

    <div class="wrapper">
        
        <div class="ch">
            <div id="ch-a" class="ch-float">
                <h1>챌린져스 인증 게시판</h1>
            </div>
            <div id="ch-s" class="ch-float">
            <!-- 로그인 되어있고  -->   <!-- 로그인한 회원번호가 참여자 정보 테이블에 있다면  ???????????????????????????-->
                <c:if test="${!empty loginMember && !empty joinInfo}">
	                <ul>  
	                    <li><a href="${contextPath}/challengeCrtfd/insertForm.do">챌린지 <strong>인증</strong>하러가기 -></a></li>
	                </ul>
                </c:if>
            </div>
        </div>

        <!-- 카테고리 선택 -->
        <br><br>
        <hr>  
        <div class="cat-area">
			<div class="cat">
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfd/list.do?sort=${param.sort}">전체</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=건강&sort=${param.sort}">건강</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=관계&sort=${param.sort}">관계</a> 
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=생활&sort=${param.sort}">생활</a> 
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=역량&sort=${param.sort}">역량</a> 
            </div> 
            <div class="cat-2">
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=자산&sort=${param.sort}">자산</a> 
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=취미&sort=${param.sort}">취미</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=공부&sort=${param.sort}">공부</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=돈 관리&sort=${param.sort}">돈 관리</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCrtfdCategorySearch.do?cn=그 외&sort=${param.sort}">그 외</a>
            </div>
        </div>
            <hr>
        <br>

		<!-- 정렬 -->
		<div class="sort">
				<select id="skk" name="skk" class="form-control">
					<option value="new">최신순</option>
					<option value="view">조회순</option>
				</select>
		</div>

		<!-- 인증 글 목록 --> <!-- 번갈아가면서 색깔주는거 나중에 추가하기 -->
		<div class="list-wrapper">
			<table class="table" id="list-table">
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
						<th>작성일</th>
					</tr>
				</thead>
				
				<tbody>
					
					<!-- 조회된 목록이 없을 때   -->
					<c:choose>
						<c:when test="${empty list}">
							<tr>
								<td colspan="5">존재하는 인증글이 없습니다</td>
							</tr> 
						</c:when>
						<c:otherwise>
							<c:forEach var="challengeCrtfd" items="${list}"> 
								<tr>   
									<td>${challengeCrtfd.chlngBoardNo}</td>
									<td>${challengeCrtfd.chlngBoardTitle}</td>
									<td>${challengeCrtfd.memNickname}</td>
									<td>${challengeCrtfd.chlngBoardViews}</td>
									<td>
										<fmt:formatDate var="createDate" value="${challengeCrtfd.chlngBoardCrtDt}" pattern="yyyy-MM-dd"/>
										${createDate}
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		
		<%---------------------- Pagination ----------------------%>
	 	<c:choose>
			
			<c:when test="${!empty param.sk && !empty param.sv }">     
				<c:url var="pageUrl" value="/challengeCrtfdSearch.do"/>
				<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}&cn=${param.cn}&sort=${param.sort}"/>
			</c:when>
			<%-- 카테고리 선택 후 생성된 페이지일 경우 --%>
			<c:when test="${!empty param.cn}">
				<c:url var="pageUrl" value="/challengeCrtfdCategorySearch.do"/>
				<c:set var="searchStr" value="&cn=${param.cn}" />
			</c:when>
			<c:otherwise>
				<c:url var="pageUrl" value="/challengeCrtfd/list.do"/>
			</c:otherwise>
			
		</c:choose> 
		
		
		<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
		<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>
		
		<fmt:parseNumber  var="c1" value="${( pInfo.currentPage - 1) / 10 }" integerOnly="true" />    
		<fmt:parseNumber  var="prev" value="${ c1 * 10 }" integerOnly="true" />
		<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}" />
		
		<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / 10 }" integerOnly="true"/>
		<fmt:parseNumber var="next" value="${ c2 * 10 + 1 }" integerOnly="true" />     
		<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}" />
			
		<!-- 페이지 번호 목록 -->
        <div class="page-no-area">
			<ul>
			
				<c:if test="${pInfo.currentPage > 10}">
					<li><a href="${firstPage}">&lt;&lt;</a></li>
					<li><a href="${prevPage}">&lt;</a></li>
				</c:if>
				
				<c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
					<c:choose>
						<c:when test="${pInfo.currentPage == page }">     <!-- 만약 -->
							<li>
								<a class="page-link">${page}</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a class="page-link" href="${pageUrl}?cp=${page}${searchStr}">${page}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<c:if test="${next <= pInfo.maxPage}">
					<li><a href="${nextPage}">&gt;</a></li>
					<li><a href="${lastPage}">&gt;&gt;</a></li>
				</c:if>
				
			</ul>
        </div>
        
           <!-- 검색 -->
        <div class="search">
        	<form action="${contextPath}/challengeCrtfdSearch.do" method="GET" class="search-form">
        		<select name="sk" class="s-form-control1">
        			<option value="title">제목</option>
        			<option value="content">내용</option>
        			<option value="titcont">내용+제목</option>
        			<option value="writer">작성자</option>
        		</select>
        		<input type="text" name="sv" class="s-form-control2">
        		<input id="hidden-cn" type="text" name="cn" value="${param.cn}">
        		<input id="hidden-cn" type="text" name="sort" value="${param.sort}"> 
        		
        		<button class="s-form-control3">검색</button> 
        	</form>
        </div>
		
		
		

	</div>
	
	
	<!-- 푸터 영역 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
    
	<script>
	// 인증글 상세보기 기능 (jquery를 통해 작업)
	$("#list-table td").on("click", function(){
		
		// 게시글 번호 얻어오기
		var chlngBoardNo = $(this).parent().children().eq(0).text();
		
		var url = "${contextPath}/challengeCrtfd/view.do?cp=${pInfo.currentPage}&no=" + chlngBoardNo +"${searchStr}";
																																										
		var cn = "${param.cn}";
    	var sort = "${param.sort}";
    	    
    	if(cn != ""){
    		url += "&cn=" + cn;
    	}
    	
    	if(sort != ""){
    		url += "&sort=" + sort;
    	}
    	location.href = url;
		
	});
	

    

    
 	// 검색 내용이 있을 경우 검색창에 해당 내용을 작성해주는 기능
	(function(){
		var searchKey = "${param.sk}";
		var searchValue = "${param.sv}";
		var chlngCategoryNm = "${param.cn}"
		
		//select 부분에 선택되어있도록
		$("select[name=sk] > option").each(function(index, item){
			// index : 현재 접근중인 요소의 인덱스
			// item : 현재 접근중인 요소
			
						// title        title
			if(  $(item).val() == searchKey  ){
				$(item).prop("selected", true);
			}
		});
			
		// 검색어 입력창에 searchValue 값 출력
		$("input[name=sv]").val(searchValue);
			
		// 검색어 입력창에 카테고리네임
		$("input[name=cn]").val(chlngCategoryNm);
		//console.log(chlngCateNm);
		
		
	})();
    
	
 	
 	
 	
	// 최신순, 조회순
 	$("#skk").on("change", function(){
 		
 		var sort = $("#skk").val();  // new / view
 		//location.href = "list.do?sort=" + sort;    //cn=${param.cn}&???????????
 			
		console.log(sort);
 				
 		if("${param.cn}" != ""){      /* 카테고리 */
	 		location.href = "${contextPath}/challengeCrtfdCategorySearch.do?sort=" + sort + "&cn=${param.cn}"  ; 
 		}
 		else if("${param.sk}" != "" && "${param.sv}" != ""){   /* 검색  후 정렬 */
	 		location.href = "${contextPath}/challengeCrtfdSearch.do?sort=" + sort + "&sk=${param.sk}&sv=${param.sv}&cn=${param.cn}"  ; 
 		}
 		
 		
 		else{
	 		location.href = "list.do?sort=" + sort; 
 			
 		}
 		
 				
 	});
 	
 	// 정렬 방식 유지
 	(function(){
 		$("#skk > option").each(function(index, item){
 			// index : 현재 접근중인 요소의 인덱스
			// item : 현재 접근중인 요소
			if(  $(item).val() == "${param.sort}"  ){   
				$(item).prop("selected", true);
			}
		});
 	})();
 		
	
	</script>
	
</body>
</html>