<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 비밀번호 찾기</title>

<link href="${contextPath}/resources/css/common/find.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
.container > .content{
	padding : 104px 0 213px;
}

.row_group{
	margin-right: 30px;
}

.backgroundColor{
	width: 400px;
	margin: auto;
}
</style>

</head>
<body>
		<c:set var="memNo" scope="request" value="${memNo}"></c:set>
	<div class="wrap">
	<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<div class="content">
				<form action="
					<c:if test="${empty loginMember}">${contextPath}/member/changePwComplete.do?num=${memNo}</c:if>
					<c:if test="${!empty loginMember}">${contextPath}/mypage/changePwComplete.do</c:if>
				" method="post" name="newPwForm" onsubmit="return pwdValidate();">
				
					<div class="idFindForm">
						<div class="text_area">
							<h1>푸른봄</h1>
						</div>
						<div class="back">
							<h3>새로운 비밀번호 설정</h3>
							<div class="input_area">
								<div class="row_group">
									<div class="text">비밀번호</div> 
									<input type="password" id="newPw1" name="newPw1" class="find_input" required>
									<span class="errorMsg" id="pw1_error"></span>
								</div>
								<div class="row_group">
									<div class="text">비밀번호 확인</div> 
									<input type="password" id="newPw2" name="newPw2" class="find_input" required>
									<span class="errorMsg" id="pw2_error"></span>
								</div> 
								<div class="row_group">
									<div class="nextBtn_area">
										<button type="submit" class="btn" id="nextBtn">비밀번호 재설정</button>
									</div>
								</div>
						</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<script src="${contextPath}/resources/js/member_signUp.js"></script>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
</body>
</html>