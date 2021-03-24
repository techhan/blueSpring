<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
.container{
	padding : 93px 0 310px;
}

#findCNum{
	margin-top : 9px;
	margin-left : 103px;
}


#findEmail_btn{
	margin-left : 10px;
	width : 96px;
	height : 42px;
	background-color : rgb(241, 135, 35);
}

.back {
background-color: #fbf9f9;
padding : 40px 50px 50px 50px;
margin : auto;
width : 600px;
}

.text_area{
	margin : auto;
	width : 700px;
	text-align: center;
}
</style>


</head>
<body>
	<div class="wrap">
	<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<div class="content">
				<form method="post" name="pwFind" action="${contextPath}/member/changePw.do" onsubmit="return pwdValidate();">
					<div class="findForm">
						<div class="text_area">
							<h1>푸른봄</h1>
						</div>
							<div class="back">
								<h3 style="margin-bottom: 0px;">비밀번호 찾기</h3>
								<h3 style="margin-top: 5px;">본인확인 이메일 작성</h3>
							<div class="input_area">
								<div class="row_group">
									<div class="text">아이디</div> 
									<input type="text" id="id" name="id" class="find_input" required>
								</div>
								<div class="row_group">
									<div class="text">이메일 주소</div> 
									<input type="email" id="email" name="email" class="find_input" required>
								<button type="button" class="btn" id="findEmail_btn">인증번호 받기</button>
									<input type="text" name="findCNum" class="find_input" id="findCNum"
										placeholder="인증번호를 입력해주세요." autocomplete="off" required>
								</div>
								<div class="row_group">
									<div class="nextBtn_area">
										<button type="button" class="btn" id="nextBtn">다음</button>
									</div>
								</div>
								</div>
						</div>
					</div>
				</form>
			</div>
		</div>

		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
	
	
	<script>

	var sendKey; 
	var eCheck = true;
	var numCheck = null;
	var mN = null;
	
	$("#email, #id").on("input", function(){
		$.ajax({
			url : "idEmaliChk.do",
			data : {email : $("#email").val(),
					id : $("#id").val()},
			type : "post",
			async : false,
			success : function(result){
				mN = result+"";
				console.log(mN)
				eCheck = true;
			}, error : function(){
				eCheck = false;
				console.log("error");
			}
	});
	});
	
	/* function validate(){
			$.ajax({
				url : "idEmaliChk.do",
				data : {email : $("#email").val(),
						id : $("#id").val()},
				type : "post",
				async : false,
				success : function(result){
					mNO = result+"";
					eCheck = true;
				}, error : function(){
					eCheck = false;
					console.log("error");
				}
		});
	} */
	
$("#findEmail_btn").on("click", function(){
	$.ajax({
		url : "../sendMail",
		data : {toEmail : $("#email").val()},
		type : "post",
		async : false,
		success : function(result){
			if(eCheck && result != undefined){
				swal({icon : "success", title : "이메일이 전송되었습니다.", text : $("#email").val() + " 에서 확인해주세요."});
				sendKey = result;
				
			}else{
				swal({icon : "error", title : "이메일 전송 실패", text : "이메일을 다시 확인해주세요."});
			}
		}
		, error : function(){
			swal({icon : "error", title : "이메일 전송 실패", text : "이메일을 다시 확인해주세요."});
			console.log("error");
		}
		});
	});

	var check = true;
	$("#findCNum").on("input",function(){
		
		var inputKey = $("#findCNum").val();
		if(sendKey == inputKey){
			check = true;
			numCheck = true;
			$("#findCNum").css("border", "1px solid #8cb0f7");
		}else{
			check = false;
			numCheck = false;
			$("#findCNum").css("border", "1px solid red");
		}

	});
	
	
	// 비밀번호 변경 js
	function pwdValidate(){

	    var regExp = /^[a-zA-Z\d]{6,12}$/; // 영어 대,소문자 + 숫자, 총 6~12글자

	    if(!regExp.test( $("#newPw1").val() ) ){
	        swal("비밀번호 형식이 유효하지 않습니다.");
	        $("#newPw1").focus();

	        return false; 
	    }

	    // 새로운 비밀번호와 확인이 일치하지 않을 때
	    if( $("#newPw1").val() != $("#newPw2").val() ){
	        swal("새로운 비밀번호가 일치하지 않습니다.");
	        
	        $("#newPw1").focus(); // 포커스 이동
	        $("#newPw1").val(""); // newPwd1에 값 지우기 
	        $("#newPw2").val(""); // newPwd2에 값 지우기 

	        return false;
	    }
	}
	
	$("#nextBtn").on("click", function(){
		location.href = "changePw.do?ck="+numCheck + "&mN=" + mN ;
		
	}); 
	</script>
</body>
</html>