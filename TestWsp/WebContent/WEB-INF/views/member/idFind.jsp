<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 아이디 찾기</title>
<link href="${contextPath}/resources/css/common/find.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
.container{
	padding : 105px 0 298px;
}

.input_area {
margin-left : 40px;
}

</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<div class="content">
				<form action="${contextPath}/member/idFindComplete.do" method="post" name="idFind">
					<div class="findForm">
						<div class="text_area">
							<h1>푸른봄</h1>
						</div>
						<div class="back">
						
							<h3 style="margin-bottom: 0px;">아이디 찾기</h3>
							<h3 style="margin-top: 5px;">본인확인 이메일 작성</h3>
							<div class="input_area">
								<div class="row_group">
									<div class="text">이름</div> 
									<input type="text" id="name" name="name" class="find_input" required>
								</div>
								<div class="row_group">
									<div class="text">이메일 주소</div> 
									<input type="email" id="email" name="email" class="find_input" required>
							<!-- 		<button type="button" class="btn" id="emailNumBtn">인증번호 받기</button>
									<input type="text" name="emailNum" class="find_input" id="emailNum"
										placeholder="인증번호를 입력해주세요." required> -->
								</div>
								</div>
								<div class="row_group">
									<div class="nextBtn_area">
										<button type="submit" class="btn" id="nextBtn">다음</button>
									</div>
								</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
</body>
</html>