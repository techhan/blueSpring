<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 역대 챌린지 조회</title>

<link href="${contextPath}/resources/css/mypage/allTimeChallenge.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style>


#cTable > *{
cursor: pointer;
}

/* 페이지 번호 목록 */
 .page-no-area{
   width: 100%;
   height: 45px;
   margin-top : 43px;
}

.page-no-area ul{  /* 중앙에 두는 방법 */
   width : 70%;
   height : 100%;
   margin : auto;
    text-align: center;
}
.page-no-area ul li{
   width : 7%;
   height : 100%;
    display: inline-block;
    margin-right: -6px;
    list-style-type: none;
    text-align: center;
}
.page-no-area li a{
   width : 100%;
   height : 100%;
    text-decoration: none;
    font-size: 20px;
    color: black;
    line-height: 50px;
    display: block;
}
.page-no-area  a:hover{
   color : #283e69;
   border-bottom: 1px solid rgb(249 155 67);
   transition : .35s ease color;
}

.page-no-area a:before{
 transition: .35s ease left;}
 
 #cTable{
margin : auto;
}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<jsp:include page="../common/mypageSideMenu.jsp"></jsp:include>
			<div class="content">
			<h3>역대 챌린지</h3>
				<div class="cBack">
					<table id="cTable">
					<c:choose>
						<c:when test="${empty list}">
							<tr>
								<td colspan="3"> 참여한 챌린지가 없습니다. </td>
							</tr>
						</c:when>
				
						<c:otherwise> 
							
							<c:forEach var="challenge" items="${list}" varStatus="vs">
								<c:if test="${vs.index == 0  || vs.index == 3 }">
									<tr id="b-${challenge.chlngNo}">
								</c:if>
								<td>
							
									<div class="cThumbnail_area">
									
									<c:set var="img" value="${contextPath}/resources/img/basicImg.JPG"/>
									
									<c:if test="${!empty fList}">
										<c:forEach var="thumbnail" items="${fList}">
											<c:if test="${challenge.chlngNo == thumbnail.parentChNo}">
												<c:set var="img" value="${contextPath}/resources/uploadImages/challenge/${thumbnail.fileName}"/>
												<c:set var="flag" value="false"/>
											
											</c:if>
										</c:forEach>
									</c:if>
									
									<img class="cThumbnail" src="${img}"></img>
									
									</div>
									<div class="cTitle_area">
										<h4 class="title">${challenge.chlngTitle}</h4>
									</div>
									<div class="cPeriod_area">
										 <fmt:formatDate var="chlngStartDt" value="${challenge.chlngStartDt}" pattern="yyyy-MM-dd"/>
										 <fmt:formatDate var="chlngEndDt" value="${challenge.chlngEndDt}" pattern="yyyy-MM-dd"/>
										<span class="period">${chlngStartDt} - ${chlngEndDt}</span>
									</div>
								<%-- 	<div class="check_area">
										<img class="checkImg" src="${contextPath}/resources/img/challenge_check.png">
									</div> --%>
								</td> 
						
							<c:if test="${vs.index == 2  || vs.last }">
								<tr>
							</c:if>
						
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
				
				
		<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
		<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>
		
		<fmt:parseNumber  var="c1" value="${( pInfo.currentPage - 1) / 10 }" integerOnly="true" />    
		<fmt:parseNumber  var="prev" value="${ c1 * 10 }" integerOnly="true" />
		<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}" />
		
		<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / 10 }" integerOnly="true"/>
		<fmt:parseNumber var="next" value="${ c2 * 10 + 1 }" integerOnly="true" />     
		<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}" />
			
				
				
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
				
				
				
			</div>
		</div>
		
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
	
	 <script>
			$("#cTable tr > *").on("click", function(){
				var id = $(this).parent().attr("id");
				var challengeNo = id.substring(id.lastIndexOf("-") + 1);
				
				location.href = "../challenge/view.do?cp=1&no="+challengeNo;
			});
			
			
			
    </script>
</body>
</html>