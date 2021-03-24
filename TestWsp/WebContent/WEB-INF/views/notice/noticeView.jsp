<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지글 조회</title>
<link rel="stylesheet" href="${contextPath}/resources/css/notice/no_view.css" type="text/css">

</head>
<body>
	<!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	
	<div class="wrapper">
		<h2>공지사항</h2>
		<hr>
		
		<div class="input">
			<div class="oone">제목
				<span class="input-padding">${notice.noticeTitle}</span>
			</div>
			
			<div class="one">
				<div class="writer">작성자
					<span class="input-padding">${notice.memberId }</span>
				</div>
				
				<div class="writer-dt">작성일
					<span class="input-padding">
					<fmt:formatDate var="createDate" value="${notice.noticeCrtDt}" pattern="yyyy-MM-dd"/>
										${createDate}
					</span>
				</div>
				
				<div class="lokup">조회수
					<span class="input-padding">${notice.noticeViews }</span>
				</div>
			</div>
			
					<!-- 이미지 출력 -->
			<c:if test="${!empty fList}">
				<!-- 이미지가 없으면 그 슬라이드 공간을 차지하지 않음 -->
				<div>
				
					<div>
						<c:forEach var="file" items="${fList}" varStatus="vs">
							<div class="imgFile" align="center">
								<img class="d-block w-100 boardImg" id="${file.fileNo}" 
											src="${contextPath}/resources/uploadImages/notice/${file.fileName}">
							</div>
						</c:forEach>
					</div>
					
				</div>
				
				
			</c:if>
 			
 			
 			<!-- 내용 부분 -->
			<div class="introduce">${notice.noticeContent }</div>
				
		</div>

		<hr>

		<!-- 목록, 수정, 삭제  버튼 -->
		<div class="text-center">
			<c:if test="${!empty loginMember && (notice.memberId == loginMember.memberId) }">
				<button id="deleteBtn" class="btn">삭제</button>
				
				<c:if test="${!empty param.sv && !empty param.sk}">
							<%-- 검색을 통해 들어온 상세 조회 페이지인 경우 --%>
							<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
				</c:if>

				<a href="updateForm.do?cp=${param.cp}&no=${param.no}${searchStr}" class="btn btn-update">수정</a>

			
			</c:if>
			
			
			<c:choose>
				<c:when test="${!empty param.sk && !empty param.sv}">
					<c:url var="goToList" value="../noticeSearch.do">
						<c:param name="cp">${param.cp}</c:param>
						<c:param name="sk">${param.sk}</c:param>
						<c:param name="sv">${param.sv}</c:param>
					</c:url>
				</c:when>
				
				<c:otherwise>
					<c:url var="goToList" value="list.do">  <!-- 상대경로 방식 -->
						<c:param name="cp">${param.cp}</c:param>
					</c:url>
				</c:otherwise>
			</c:choose>
			
			<a href="${goToList}" class="btn btn-update">목록</a>
		</div>

		
	</div>
	
	
	


	<!-- 푸터 영역 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
    <script>
  	//삭제 버튼 이벤트
	$("#deleteBtn").on("click", function(){                     // window.confirm 윈도우에서 제공하는 내장객체
		
		if(window.confirm("정말 삭제 하시겠습니까?")){
			location.href = "delete.do?no=${notice.noticeNo}";
		}
		
	});
    </script>

</body>
</html>