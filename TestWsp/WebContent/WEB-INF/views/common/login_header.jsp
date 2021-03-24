<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>WebServer Project</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<!-- Bootstrap core JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<!-- sweetalert : alert창을 꾸밀 수 있게 해주는 라이브러리 https://sweetalert.js.org/ -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>

#logo_area{
	padding-top : 30px;
	margin-bottom: 30px;
}

#login_top{
	margin-bottom: 20px;
	width : 30%;
	margin : auto;
}



#login_img{
   display: block;
	margin : auto;
	width : 150px;
}

</style>


</head>

<body>
	<div id="header">
		<div id="logo_area">
			<div id="login_top">
				<img id="login_img" src="${contextPath}/resources/img/logo.png">
			</div>
		</div>
	</div>
</body>

</html>