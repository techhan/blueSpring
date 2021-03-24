<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지 인증하기 상세</title>
<link rel="stylesheet" href="${contextPath}/resources/css/challengeCr/ch_cr_view.css" type="text/css">
</head>
<body>

	<!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

	<div class="wrapper">
		<h2>챌린지 인증</h2>
		<hr>

		
			<!-- 개설 정보 입력 -->
			<div class="input">

				<div class="one">
					<!-- 챌린지 이름 -->
					<label class="input-area">챌린지</label>
					<span class="input-area2">${challengeCrtfd.chlngBoardTitle} &nbsp;&nbsp;  
						<fmt:formatDate var="startDate" value="${challengeCrtfd.chlngStartDt}" pattern="yyyy-MM-dd"  />
						${startDate}
						&nbsp;&nbsp;~&nbsp;&nbsp;
						<fmt:formatDate var="endDate" value="${challengeCrtfd.chlngEndDt}" pattern="yyyy-MM-dd"  />
						${endDate}							
					</span>
					<!--DB에서 회원이 참여하고 있는 챌린지들은 가져온다 -->
					<!-- 된다면 첼린지 제목 / 시작일 ~ 끝나는 날 -->
					<div class="lokup">
						<label class="input-area">조회수</label> ${challengeCrtfd.chlngBoardViews}
					</div>
				</div>

				
				<div class="one">
					<!-- 작성자 -->
					<label class="input-area">작성자</label> 
					<span class="input-area2">${challengeCrtfd.memNickname}</span>
		
		
					<div class="cate">
						<label class="input-area">카테고리</label> ${challengeCrtfd.chlngCateNm}
					</div>
				</div>

				<div class="input-top-margin">
					<label class="input-area">제목 </label> ${challengeCrtfd.chlngBoardTitle}
				</div>

				<!-- -------------------------------------------------- -->

				<div class="input-top-margin">
					<label class="input-area">작성일</label> ${challengeCrtfd.chlngBoardCrtDt}
				</div>
				
				
				
				<!-- 파일 가져오기 -->

			<!-- 이미지 출력 -->
			<c:if test="${!empty fList}">
				<!-- 이미지가 없으면 그 슬라이드 공간을 차지하지 않음 -->
				
				<div>
					<c:forEach var="file" items="${fList}" varStatus="vs">
						<div class="imgFile" align="center">
							<img class="d-block w-100 chImg" id="${file.fileNo}" 
										src="${contextPath}/resources/uploadImages/challengeCr/${file.fileName}">
						</div>
					</c:forEach>
				</div>
				
			</c:if> 
				
				

			<!-- 참가 소감 작성 -->
			<div class="introduce">${challengeCrtfd.chlngBoardContent}</div>
			


			</div>

			<hr>

			<!-- 목록, 수정, 삭제  버튼 -->
			<div class="text-center">
				<c:if test="${!empty loginMember && (challengeCrtfd.memberId == loginMember.memberId) }">
					<button id="deleteBtn" class="btn">삭제</button>
					
					<c:if test="${!empty param.sv && !empty param.sk}">
							<%-- 검색을 통해 들어온 상세 조회 페이지인 경우 --%>
							<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
					</c:if>
	
					<a href="updateForm.do?cp=${param.cp}&no=${param.no}${searchStr}" class="btn btn-update">수정</a>
				</c:if>
				
				
				<c:choose>
					<c:when test="${!empty param.sk && !empty param.sv}">
						<c:url var="goToList" value="${contextPath}/challengeCrtfdSearch.do">
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
			location.href = "delete.do?no=${challengeCrtfd.chlngBoardNo}";
		}
		
	});
</script>
</body>
</html>