<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원정보 조회 페이지</title>
    <!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	
	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
	
    <style>
	.admin_content {
		width: 70%;
		height: 100%;
		margin: auto;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.admin_title {
		margin: 3% 0 3% 0;
		color: rgb(40, 62, 105);
	}
	
	.admin_board {
		width: 100%;
		height: 100%;
	}
	
	.page_area { margin-top: 2%; }
	
	.userInfo_search { text-align: center; }
	
	.pagination > li > a, .pagination > li > a:hover{ color: black; }
	
	#userInfo_btn {
		background-color: #343a40;
		color: snow;
	}
	</style>
</head>
<body>
    <div class="wrapper">
    	<jsp:include page="../common/header.jsp"></jsp:include>

          <jsp:include page="../common/adminMenu.jsp"></jsp:include>

        <div class="admin_content">
            <div class="admin_title">
                <h4>회원정보 조회</h4>
            </div>
            <div class="admin_board">
                <table class="table table-sm">
                    <thead class="thead-dark">
                      <tr>
                        <th scope="col">회원번호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">닉네임</th>
                        <th scope="col">이름</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">성별</th>
                        <th scope="col">전화번호</th>
                        <th scope="col">주소</th>
                        <th scope="col">가입일</th>
                        <th scope="col">탈퇴여부</th>
                        <th scope="col">블랙리스트여부</th>
                        <th scope="col">회원등급</th>
                      </tr>
                    </thead>
                    <tbody>
                   	<c:choose>
	                    <c:when test="${empty mList}">
	                        <tr>
	                            <td colspan="12">존재하는 회원정보가 없습니다.</td>
	                        </tr>
	                    </c:when>
	                    
                    <c:otherwise>
                        <c:forEach var="member" items="${mList}">
                            <tr>
		                        <td>${member.memberNo}</td>
		                        <th>${member.memberId}</th>
		                        <td>${member.memberNickname}</td>
		                        <td>${member.memberNm}</td>
		                        <td>${member.memberBirth}</td>
		                        <td>${member.memberGender}</td>
		                        <td>${member.memberPhone}</td>
		                        <td>${fn:split(member.memberAddr,',')[1]}</td>
		                        <td>${member.memberJoined}</td>
		                        <td>${member.memberScsnFl}</td>
		                        <td>${member.memberBlackList}</td>
		                        <td>${member.memberLevel}</td>
	          				</tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                    </tbody>
                  </table>
            </div>

            <%---------------------- Pagination ----------------------%>
			<%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
			<c:choose>
				<%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
				<c:when test="${!empty param.sk && !empty param.sv }">
					<c:url var="pageUrl" value="/adminSearch/member.do"/>
					
					<%-- 쿼리스트링으로 사용할 내용을 변수에 저장 --%>
					<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
				</c:when>
				
				<%-- 검색을 하지 않았을 경우 --%>
				<c:otherwise>
					<c:url var="pageUrl" value="/admin/adminMemberInfo.do"/>		
				</c:otherwise>
			</c:choose>

			<!-- 화살표에 들어갈 주소를 변수로 생성 -->
			 
			<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
			<c:set var="lastPage" value="${pageUrl}?cp=${mpInfo.maxPage}${searchStr}"/>
			 
			 <fmt:parseNumber var="c1" value="${(mpInfo.currentPage - 1) / 10 }" integerOnly="true" />
			 <fmt:parseNumber var="prev" value="${ c1 * 10 }" integerOnly="true" />
			 <c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}" />
			 
			 <fmt:parseNumber var="c2" value="${(mpInfo.currentPage + 9) / 10 }" integerOnly="true" />
			 <fmt:parseNumber var="next" value="${ c2 * 10 + 1 }" integerOnly="true" />
			 <c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}" />



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
									href="${pageUrl}?cp=${page}${searchStr}">${page}</a></li>
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


            <div class="userInfo_search">
            <form action="${contextPath}/adminSearch/member.do" method="GET">
            <select id="userInfo_search" name="sk">
                <option value="memNo">회원번호</option>
                <option value="memId">아이디</option>
                <option value="memNick">닉네임</option>
            </select>
            <input type="text" name="sv">
            <button type="button" id="userInfo_btn">검색</button>
			</form>
        </div>
    </div>
    <div style="clear: both;"></div>
    <jsp:include page="../common/footer.jsp"></jsp:include>
    </div>
    
    <script>
	// 검색 내용이 있을 경우 검색창에 해당 내용을 작성해두는 기능
	(function(){
		var searchKey = "${param.sk}";
		var searchValue = "${param.sv}";
		$("select[name=sk] > option").each(function(index, item){
			if( $(item).val() == searchKey ){
				$(item).prop("selected", true);
			}
		});
		$("input[name=sv]").val(searchValue);
	})();
	
	</script>
</body>
</html>