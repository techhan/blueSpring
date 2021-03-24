<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${contextPath}/resources/css/board/boardMain.css">

</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    
    <c:set var = "loginMember" value="${loginMember}"/> 
    
    <!-- 로그인 체크(좋아요 때문에....) -->
    <c:choose>
    	<c:when  test="${null eq loginMember }">
    		<c:set var ="memberNo" value = "0"/>
    	</c:when>
    	<c:otherwise>
    		<c:set var ="memberNo" value = "${loginMember.memberNo}"/>
    	</c:otherwise>
    </c:choose>
    
    <h1 class="shadow">자유게시판</h1>       
    <div id="board-main">
        <ul id="board-category">
        		<li class="shadow"><a href="${contextPath}/board/list.do?cp=1">전체</a></li>
            <li class="shadow"><a href="${contextPath}/categorySearch.do?cn=정보">정보</a></li>
            <li class="shadow"><a href="${contextPath}/categorySearch.do?cn=일상">일상</a></li>
            <li class="shadow"><a href="${contextPath}/categorySearch.do?cn=취미">취미</a></li>
            <li class="shadow"><a href="${contextPath}/categorySearch.do?cn=고민">고민</a></li>
            <li class="shadow"><a href="${contextPath}/categorySearch.do?cn=취업">취업</a></li>
            <li class="shadow"><a href="${contextPath}/categorySearch.do?cn=자유">자유</a></li>
        </ul>
        
        
    
        <div id="board-box">	
            <table id="board">
                <tr>
                    <th>번호</th>
                    <th>카테고리</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                    <th>좋아요 수</th>
                </tr>

                <c:choose>
                    <c:when test="${empty bList}"> 
                        <tr>
                            <td colspan="6">존재하는 게시글이 없습니다.</td>
                        </tr>
                    </c:when>
    
                    <c:otherwise>                      
                        <c:forEach var="board" items="${bList}">
                            <tr>
                                <td>${board.boardNo}</td>
                                <td>${board.categoryName}</td>
                                <td>${board.boardTitle}</td>
                                <td>${board.memberNickname}</td>
                                <td>
                                    <fmt:formatDate var="createDate" value="${board.boardCreateDate}" pattern="yyyy-MM-dd"/>
                                    <fmt:formatDate var="today" value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd"/>
                                    
                                    <c:choose>
                                        <c:when test = "${createDate != today}">
                                            ${createDate}
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatDate value="${board.boardCreateDate}" pattern="HH:mm"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${board.readCount}</td>
                                <td>${board.likeCount}</td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>

    
    <div id="board-bottom">
    		<%-- 글쓰기 btn 영역 --%>
        <div id="board-write-area">
          <%-- 로그인 되어 있을 시 --%>
          <c:if test="${!empty loginMember}">
            <button type="button" id="board-write" onclick="location.href = '${contextPath}/board/boardWriteForm.do'">글쓰기</button>
        	</c:if>
        </div>

				<%-- 페이지 nav 영역 --%>
				<c:choose>
					<%-- 검색 후 생성된 페이지일 경우 --%>
					<c:when test="${!empty param.sk && !empty param.sv}">
						<c:url var="pageUrl" value="/search.do"/>
						<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}&cn=${param.cn}" />
					</c:when>
					<%-- 카테고리 선택 후 생성된 페이지일 경우 --%>
					<c:when test="${!empty param.cn}">
						<c:url var="pageUrl" value="/categorySearch.do"/>
						<c:set var="searchStr" value="&cn=${param.cn}" />
					</c:when>
					<%-- 검색을 하지 않았을 경우 --%>
					<c:otherwise>
						<c:url var ="pageUrl" value="/board/list.do"/>
					</c:otherwise>
				</c:choose>

				
					<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
					<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>

					<fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1) / pInfo.pageSize}" integerOnly="true"/>
					
					<fmt:parseNumber var="prev" value="${c1 * pInfo.pageSize}" integerOnly="true"/>
					<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}"/>
					
					<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / pInfo.pageSize}" integerOnly="true"/>
					<fmt:parseNumber var="next" value="${c2 * pInfo.pageSize + 1}" integerOnly="true"/>
					<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}"/>

        <ul id="board-nav">
					<%-- 현재 페이지가 10페이지 초과인 경우 --%>
					<c:if test="${pInfo.currentPage > 10}">			
						<li class="page-li"><a class="page-link" href="${firstPage}">&lt;&lt;</a></li>					
						<li class="page-li"><a class="page-link" href="${prevPage}">&lt;</a></li>				
					</c:if>				
					<!-- 페이지 목록 -->
					<c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">						
						<c:choose>
							<c:when test="${pInfo.currentPage == page}">
								<li class="page-li"><a class="page-link shadow">${page}</a></li>
							</c:when>			
							<c:otherwise>
								<li class="page-li"><a class="page-link shadow" href="${pageUrl}?cp=${page}${searchStr}">${page}</a></li>
							</c:otherwise>			
						</c:choose>						
					</c:forEach>				
					<%-- 다음 페이지가 마지막 페이지 이하인 경우 --%>
					<c:if test="${next <= pInfo.maxPage}">			
						<li class="page-li"><a class="page-link shadow" href="${nextPage}">&gt;</a></li>				
						<li class="page-li"><a class="page-link shadow" href="${lastPage}">&gt;&gt;</a></li>									
					</c:if>
        </ul>

				<%-- 검색 영역 --%>
        <div id="board-search">
        	<form action="${contextPath}/search.do" method="GET">
            <select name="sk" id="search-option">
                <option value="title">제목</option>
                <option value="content">내용</option>
                <option value="writer">작성자</option>
                <option value="titcont">제목 + 내용</option>
            </select>
            <input type="text" name="sv">
            <input id="hidden-cn" type="text" name="cn" value="${param.cn}">
            <button><i class="fas fa-search"></i></button> 
           </form>     
       	</div>
    </div>

    <jsp:include page="../common/footer.jsp"></jsp:include>
    
    <script>
				// 게시글 상세보기 기능 (jquery를 통해 작업)		
				$("#board td").on("click", function() {
					// 게시글 번호 얻어오기
					var boardNo = $(this).parent().children().eq(0).text();

					var url = "${contextPath}/board/view.do?cp=${pInfo.currentPage}&no=" + boardNo + "${searchStr}";
					// var url = "${contextPath}/board/view.do?cp=${pInfo.currentPage}&no=" + boardNo + "${searchStr}";
					location.href = url;
				});
				
				$("#board td").hover(function() {
                    $(this).parent().css("backgroundColor", "lightgray").css("transition", "0.3s");
                }, function(){
                    $(this).parent().css("backgroundColor", "white").css("transition", "0.3s");
                });
   
			 // 검색 내용이 있을 경우 검색창에 해당 내용을 작성해주는 기능 
				(function(){
					var searchKey = "${param.sk}";
					// 파라미터 중 sk가 있을 경우 ex) "49"
					// 파라미터 중 sk가 없을 경우 ex) "";
					
					var searchValue = "${param.sv}";
					
					var categoryName = "${param.cn}";
					
					// 검색창 select의 option을 반복 접근
					$("select[name=sk] > option").each(function(index, item) {
						// index : 현재 접근중인 요소(item)의 인덱스
						// item : 현재 접근중인 요소
						
						if($(item).val() == searchKey) {
							$(item).prop("selected", true);
						}
					});
					
					// 검색어 입력창에 searchValue 값 출력
					$("input[name=sv]").val(searchValue);
					
					// 검색어 입력창에 categoryName 값 출력
					$("input[name=cn]").val(categoryName);
                    //console.log(categoryName);
                    
                    // 현재 선택된 페이지 숫자 이벤트
                    $.each($(".page-link"), function(index, item){
                        if($(".page-link")[index].text == "${param.cp}") {
                            $(this).css("font-size", "24px");
                            $(this).parent().css("display", "inline-block").css("width", "30px").css("text-align", "center").css("color", " #283e69").css("font-weight", "bold");
                            $(this).parent().removeClass("page-li");
                        }
                    });
				})();
    </script>

</body>
</html>