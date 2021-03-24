<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<link href="${contextPath}/resources/css/member/login.css" rel="stylesheet" type="text/css">


  
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>푸른봄: 로그인</title>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	
</head>
<body>

	<div id="wrap">
	
		<jsp:include page="../common/header.jsp"></jsp:include>
		<jsp:include page="../common/login_header.jsp"></jsp:include>


		<div id="container">
			<form action="${contextPath}/member/loginAction.do" id="login_form" method="POST">
				<fieldset>
					<legend class="blind">로그인</legend>
					<div id="login_middle">
						<div id="id_wrap">
							<div id="id_input_area">
								<input id="id_input" name="id_input" type="text" placeholder="아이디" value="${cookie.saveId.value}" required>
							</div>
						</div>
						<div id="pw_wrap">
							<div id="pw_input_area">
								<input id="pw_input" name="pw_input" type="password" placeholder="비밀번호" required>
							</div>
						</div>

						<div id="id_save_area">
							<input type="checkbox" id="id_chk" name="id_chk"
							 <c:if test="${!empty cookie.saveId.value}">checked</c:if>
							> <label for="id_chk">
								<span>아이디 저장</span>
							</label>
						</div>
						<br>
						<div id="login_btn_area">
							<button type="submit" id="login_btn">로그인</button>
						</div>
						<hr>
					</div>
				</fieldset>
			</form>

			<div id="find_area">
				<div id="find_info">
					<a id="idFind" href="${contextPath}/member/idFind.do">아이디 찾기</a> 
					<span class="bar">|</span> 
					<a id="pwFind" href="${contextPath}/member/pwFind.do">비밀번호 찾기</a>
					<span class="bar">|</span> 
					<a id="signUp" href="${contextPath}/member/signup.do">회원가입</a>
				</div>
			</div>
	
	
			<div id="login_banner_area">
				<img id="login_banner" src="${contextPath}/resources/img/login_banner.jpg">
			</div>
			
		<!-- container 끝 -->
		</div>
	<!-- wrap 끝 -->	
	<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
	
	
	
</body>
</html>