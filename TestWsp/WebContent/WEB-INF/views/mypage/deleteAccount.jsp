<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 회원탈퇴</title>
<link href="${contextPath}/resources/css/common/find.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
.container{
	padding : 133px 0 242px;
}

.text{
	display: inline-block;
	text-align: right;
}

.text > span {
	padding-right: 10px;
}


.content > h1 {
	text-align: center;
}


.back{
	width : 600px;
}

.findForm{width:410px;}

.text_area{
 width :  400px;
}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<div class="content">
			<h1>푸른봄</h1>
			<div class="back">
				<form action="${contextPath}/mypage/deleteAccountComplete.do" method="post" name="myInfoChangePw">
					<div class="findForm">
						<div class="text_area">
							<h3>회원 탈퇴를 위해 비밀번호를 입력해주세요.</h3>
						</div>
							<div class="box">
								<div class="row_group">
									<div class="input_area"><span style="margin-right: 15px;">비밀번호</span>
									<input type="password" name="deletePwd" class="delete_input find_input" required></div> 
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
		</div>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
</body>
</html>