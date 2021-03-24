<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 회원가입</title>
<link href="${contextPath}/resources/css/member/signUpComplete.css" rel="stylesheet" type="text/css">

<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>

</style>

</head>
<body>
	<div class="wrapper">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<div class="content">
					<h1 class="text">푸른봄</h1>
					<div class="back">
				<div class="text_area">
					<h1 class="text" style="margin-bottom : 2px;">회원가입이 </h1>
					<h1 class="text" style="margin-top : 2px;">완료되셨습니다.</h1>
				</div>

				<div class="btn_area">
					<span>
						<button type="button" id="btn_main" class="btn" 
						onclick="location.href='${contextPath}'">메인으로</button>
					</span> <span>
						<button type="button" id="btn_login" class="btn"
						onclick="location.href ='${contextPath}/member/login.do'">로그인</button>
					</span>
				</div>
				</div>
			</div>
		</div>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
</body>
</html>