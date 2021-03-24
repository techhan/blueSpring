<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 내 정보 수정</title>
<link href="${contextPath}/resources/css/common/find.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
.container{
	padding : 133px 0 334px;
}

.text{
	display: inline-block;
	text-align: right;
}

.text > span {
	padding-right: 10px;
}
.content{width : 70%;}

.input_area {
    margin-left: 43px;
    margin-right: 43px;
}

.back{
	width : 600px;
}

.text_area >  h3 {
	 width: 600px;
}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<div class="content">

					<div class="back">
				<form action="${contextPath}/mypage/myInfoPwCheck.do" method="post" name="myInfoChangePw" id="changeInfoPwForm">
					
					<div class="findForm">
						<div class="text_area">
							<h3>정보 수정을 위해 비밀번호를 입력해주세요.</h3>
						</div>
							<div class="input_area">
								<div class="row_group">
									<div class="text"><span>비밀번호</span></div> 
									<input type="password" name="pwd" class="find_input" required>
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