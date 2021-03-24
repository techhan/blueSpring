<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>푸른봄 - 전체 검색 결과</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    
    <!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	
	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
	
	<style>
	.search_content {
		width: 70%;
		height: 100%;
		margin: auto;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.search_title {
		margin: 3% 0 3% 0;
		color: rgb(40, 62, 105);
	}
	
	.admin_board {
		width: 100%;
		height: 100%;
	}
	
	.table td>a {
		text-decoration: none;
		color: black;
	}
	
	.page-item>a, .page-item>a:hover {
		color: black;
	}
	
	#search-table > tbody > tr:hover {
		cursor: pointer;
		background-color: #eee;
	}
	
	.pagination > li > a, .pagination > li > a:hover { color: black; }
	
	</style>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../common/header.jsp"></jsp:include>

        <div class="search_content">
            <div class="search_title">
                <h4>"${param.sv}" 검색 결과</h4>
            </div>

            <div class="adminBoard_board">
                <table class="table table-sm" id="search-table">
                
                    <thead class="thead-dark">
                      <tr>
                        <th scope="col">게시판명</th>
                        <th scope="col">제목</th>
                        <th scope="col">내용</th>
                        <th scope="col">작성일</th>
                      </tr>
                    </thead>
                    
                    
                    <tbody>
                    
                    <c:choose>
                    <c:when test="${empty mList}">
                        <tr>
                            <td colspan="4">검색결과가 없습니다.</td>
                        </tr>
                    </c:when>
    
                    <c:otherwise>                      
                        <c:forEach var="search" items="${mList}">
                            <tr id="${search.type}-${search.no}">
                                <th>
                                	<c:choose>
                                		<c:when test="${search.type == 1}">자유게시판</c:when>
                                		<c:when test="${search.type == 2}">정부정책</c:when>
                                		<c:when test="${search.type == 3}">챌린지</c:when>
                                		<c:when test="${search.type == 4}">챌린지인증</c:when>
                                	</c:choose>
                                </th>
                                <td>${search.title}</td>
                                
                                <td>
                                
                                
                                	<c:set var="content" value="${fn:split(search.content,'<br>')}" />

									<c:forEach var="cont" items="${content}" varStatus="vs">
							     		<c:if test="${vs.count <= 2}">
									     	
									     	<c:set var="c" value="${cont}"/>
									     	
									     	<c:if test="${fn:length(c) > 30 }">
									     		<c:set var="c" value="${fn:substring(c,0,30)}..."/>
									     	</c:if>
									     
									     	${c}
									     	
									     	<c:if test="${vs.count == 1}"><br></c:if>
								    	</c:if>
									</c:forEach> 

                                </td>
                                
                                
                                <td>${search.crtDt}</td>
							</tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                
                    </tbody>
                  </table>
            </div>

            <%---------------------- Pagination ----------------------%>
			<%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
			<c:url var="pageUrl" value="mainSearch.do">
				<c:param name="sv">${param.sv}</c:param>
			</c:url>

			<!-- 화살표에 들어갈 주소를 변수로 생성 -->
			 
			<c:set var="firstPage" value="${pageUrl}&cp=1"/>
			<c:set var="lastPage" value="${pageUrl}&cp=${mpInfo.maxPage}"/>
			 
			 <fmt:parseNumber var="c1" value="${(mpInfo.currentPage - 1) / 10 }" integerOnly="true" />
			 <fmt:parseNumber var="prev" value="${ c1 * 10 }" integerOnly="true" />
			 <c:set var="prevPage" value="${pageUrl}&cp=${prev}" />
			 
			 <fmt:parseNumber var="c2" value="${(mpInfo.currentPage + 9) / 10 }" integerOnly="true" />
			 <fmt:parseNumber var="next" value="${ c2 * 10 + 1 }" integerOnly="true" />
			 <c:set var="nextPage" value="${pageUrl}&cp=${next}" />



			<div class="page_area">
				<ul class="pagination justify-content-center">

					<%-- 현재 페이지가 10페이지 초과인 경우 --%>
					<c:if test="${mpInfo.currentPage > 10}">
						<li>
							<!-- 첫 페이지로 이동(<<) --> <a class="page-link" href="${firstPage}">&lt;&lt;</a>
						</li>

						<li>
							<!-- 이전 페이지로 이동 (<) --> <a class="page-link" href="${prevPage}">&lt;</a>
						</li>
					</c:if>

					<!-- 페이지 목록 -->
					<c:forEach var="page" begin="${mpInfo.startPage}"
						end="${mpInfo.endPage}">
						<c:choose>
							<c:when test="${mpInfo.currentPage == page }">
								<li><a class="page-link">${page}</a></li>
							</c:when>
							<c:otherwise>
								<li><a class="page-link"
									href="${pageUrl}&cp=${page}">${page}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<%-- 다음 페이지가 마지막 페이지 이하인 경우 --%>
					<c:if test="${next <= mpInfo.maxPage}">
						<li>
							<!-- 다음 페이지로 이동 (>) --> <a class="page-link" href="${nextPage}">&gt;</a>
						</li>
						<li>
							<!-- 마지막 페이지로 이동(>>) --> <a class="page-link" href="${lastPage}">&gt;&gt;</a>
						</li>

					</c:if>

				</ul>
			</div>
        </div>
		<div style="clear: both;"></div>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
	
	
	<script>
		$("#search-table > tbody > tr *").on("click",function(){
			var id = $(this).parent().attr("id").split("-");
			var type = id[0];
			var no = id[1];
			
			var url;
			switch(type){
			case '1' : url = "board/view.do?cp=1&no="+no; break;
			case '2' : url = "notice/view.do?cp=1&no="+no; break;
			case '3' : url = "challenge/view.do?cp=1&no="+no; break;
			case '4' : url = "challengeCrtfd/view.do?cp=1&no="+no; break;
			}
			
			location.href = url;
		});
	</script>
	
</body>
</html>