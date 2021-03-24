<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 비밀번호 찾기</title>

<link href="${contextPath}/resources/css/member/findComplete.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<div class="content">
				<div class="idFind">
					<h1>푸른봄</h1>
					<div class="findBackground">
						<h2>비밀번호가 새롭게</h2>
						<h2>설정되었습니다.</h2>
						<div class="btn_area">
							<span>
								<button type="button" id="btn_main" class="btn"
								onclick="location.href='${contextPath}'">메인으로</button>
							</span> 
							<span>
								<button type="button" id="btn_login" class="btn"
								onclick="location.href ='${contextPath}/member/login.do'">로그인</button>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
</body>
</html>