<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지 메인 페이지</title>
<link rel="stylesheet" href="${contextPath}/resources/css/challenge/ch_list.css" type="text/css">
</head>

<body>
    <!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

    <div class="wrapper">
        
        <div class="ch">
            <div id="ch-a" class="ch-float">
                <h1>챌린져스</h1>
                <h4>: 작은 습관으로 하루를 바꿔보세요.</h4>
            </div>
            <div id="ch-s" class="ch-float">
                <ul>
                <!-- 로그인한 회원만 챌린지 등록, 인증글 작성 가능    -- 나중에 눌렀을 때 로그인된 회원이 아니면 로그인 창으로 이동 -->
                <c:if test="${!empty loginMember}">
                    <li><a href="${contextPath}/challenge/insertForm.do" >챌린지 <strong>개설</strong>하러 가기 -></a></li>
                    <li><a href="${contextPath}/challengeCrtfd/list.do">챌린지 <strong>인증 게시판</strong> 가기 -></a></li>
                </c:if>
                </ul>
            </div>
        </div>

        <!-- 카테고리 선택 -->
        <br><br>
        <hr>  <!-- 차후 이미지로 교체 예정  -->
        <div class="cat-area">
            <div class="cat">
                <a id="cat-mg" class="cat-float" href="${contextPath}/challenge/list.do?sort=${param.sort}">전체</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=건강&sort=${param.sort}">건강</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=관계&sort=${param.sort}">관계</a> 
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=생활&sort=${param.sort}">생활</a> 
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=역량&sort=${param.sort}">역량</a> 
            </div> 
            <div class="cat-2">
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=자산&sort=${param.sort}">자산</a> 
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=취미&sort=${param.sort}">취미</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=공부&sort=${param.sort}">공부</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=돈 관리&sort=${param.sort}">돈 관리</a>
                <a id="cat-mg" class="cat-float" href="${contextPath}/challengeCategorySearch.do?cn=그 외&sort=${param.sort}">그 외</a>
            </div>
        </div>
            <hr>
        <br>

		<!-- 정렬 -->
		<div class="sort">
			<!-- <form action="#" > -->
				<select id="skk" name="skk" class="form-control">
					<option value="new">최신순</option>
					<option value="like">좋아요순</option>
				</select>
			<!-- </form> -->
		</div>



		<c:choose>
			<c:when test="${empty list}">
			없을때
			
			</c:when>
			
			<c:otherwise>
				<!-- 챌린지 목록들의 전체 영역 -->
					<div class="ch-sb-aarea">
						<c:forEach var="challenge" items="${list}">

						<!-- 1챌린지 선택 -->
						<!-- 데이터가 있는만큼 보여지기 위해서 데이터가 있는만큼 반복 -->
						<div class="ch-sb-area" id="${challenge.chlngNo}">
							
							<!-- 이미지 들어갈 부분 -->
							<!-- 해당 챌린지 상세페이지로  -->
							<div class="ch-img">
								<c:forEach var="file" items="${fmList}">
									<c:if test="${challenge.chlngNo == file.parentChNo}">
											<img src="${contextPath}/resources/uploadImages/challenge/${file.fileName}">
									</c:if>
									
								</c:forEach>
							</div>
							
							
						
							<!-- 정보 부분 -->
							<div class="ch-sb">

								<div class="ch-title">${challenge.chlngTitle}</div>
								<div class="ch-dt">
									<fmt:formatDate var="startDate" value="${challenge.chlngStartDt}" pattern="yyyy-MM-dd"  />
									${startDate}
									~
									<fmt:formatDate var="endDate" value="${challenge.chlngEndDt}" pattern="yyyy-MM-dd"  />
									${endDate}
									
								</div>

								<div class="ch-sh-like">
									<div id="like-img" class="ch-s-float">
										<img src="${contextPath}/resources/img/ch_main_like.png">
									</div>
									<div id="like" class="ch-s-float">${challenge.likeCount}
									</div>
								</div>
							</div>
							<!-- 정부 부분 -->
							
						</div>

						</c:forEach>
					</div>
				</c:otherwise>
       	</c:choose>
        
        
        
		<%---------------------- Pagination ----------------------%>
	 	<c:choose>
			 <%-- 검색 후 생상된 페이지일 경우 --%>   
			<c:when test="${!empty param.sk && !empty param.sv }">     
				<c:url var="pageUrl" value="/challengeSearch.do"/>
				<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}&cn=${param.cn}&sort=${param.sort}"/>
			</c:when>
			<%-- 카테고리 선택 후 생성된 페이지일 경우 --%>
			<%-- <c:when test="${!empty param.cn}"> 
				<c:url var="pageUrl" value="/challengeCategorySearch.do"/>
				<c:set var="searchStr" value="&cn=${param.cn}"/>
			</c:when> --%>
			<%-- 정렬  --%>
			<%-- 카테고리 선택 후 생성된 페이지일 경우 --%>
			<c:when test="${!empty param.cn}">
				<c:url var="pageUrl" value="/challengeCategorySearch.do"/>
				<c:set var="searchStr" value="&cn=${param.cn}" />
			</c:when>
			
			<c:otherwise>
				<c:url var="pageUrl" value="/challenge/list.do"/>
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
        	<form action="${contextPath}/challengeSearch.do" method="GET" class="search-form">
        		<select name="sk" class="s-form-control1">
        			<option value="title">제목</option>
        			<option value="content">내용</option>
        			<option value="titcont">내용+제목</option>
        			<option value="writer">작성자(닉네임)</option>
        		</select>
        		<input type="text" name="sv" class="s-form-control2">
        		<input id="hidden-cn" type="text" name="cn" value="${param.cn}">
        		<input id="hidden-cn" type="text" name="sort" value="${param.sort}"> 
        		
        		<!--  -->
        		
        		<button class="s-form-control3">검색</button> 
        	</form>
        </div>
		
		
        

    </div>
    
    <!-- 푸터 영역 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
    
    <script>
    
    $(".ch-img").on("click", function(){
    	var chlngNo = $(this).parent().attr("id");
    	
    	var url = "${contextPath}/challenge/view.do?cp=${pInfo.currentPage}&no=" + chlngNo +"${searchStr}";
    	
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
		//console.log(chlngCategoryNm);
		
		
	})();
    
    
 	// 조회순, 좋아요순
 	$("#skk").on("change", function(){
 		
 		var sort = $("#skk").val();  // new / like
 		//location.href = "list.do?sort=" + sort;    //cn=${param.cn}&???????????
 			
 		if("${param.cn}" != ""){
	 		location.href = "${contextPath}/challengeCategorySearch.do?sort=" + sort + "&cn=${param.cn}"  ; 
 		}
 		else if("${param.sk}" != "" && "${param.sv}" != ""){   /* 검색  후 정렬 */
	 		location.href = "${contextPath}/challengeSearch.do?sort=" + sort + "&sk=${param.sk}&sv=${param.sv}&cn=${param.cn}"  ; 
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