var validateCheck = {
    "id" : false,
	"nickname" : false,
    "pwd1" : false,
    "pwd2" : false,
    "name" : false,
    "email" : false,
	"birthyy" :  false,
	"birthmm" :  false,
	"birthdd" : false,
	"gender" : false,
	"phone" : false,
	"address" : false,
	"emadilNum" : false
}


// 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)
$("#id").on("input", function(){
    var regExp = /^[a-zA-z\d-_]{5,20}$/;

    var value = $("#id").val();
    if(!regExp.test(value)){
		if(value.trim().length == 0){
				$("#idMsg").text("필수 정보입니다.").css("color", "red");
			} else{
      		  	$("#idMsg").text("5~20자의 영문 소문자, 숫자와 _, -만 사용 가능합니다.").css("color", "red");
			}
		$("#id").css("border", "1px solid red");
		validateCheck.id = false;
    } else{
        $.ajax({
            url : "idDupCheck.do",
            data : {"id" : value},
            type : "post",
            success : function(result){
                    if(result == 0){ // 중복되지 않은 경우
                    $("#idMsg").text("");
					$("#id").css("border", "1px solid #8cb0f7");
                    validateCheck.id = true;
                }else{
                    $("#idMsg").text("이미 사용 중인 아이디입니다.").css("color", "red");
					$("#id").css("border", "1px solid red");
                    validateCheck.id = false;
                }
            },
            error : function(){
                console.log("아이디 중복 검사 실패");
            }
        });
    }
});


// 닉네임 유효성 검사
$("#nickName").on("input", function(){
	 var regExp = /^[a-zA-z\d-_가-힣]{2,20}$/;
	 var value = $("#nickName").val();
	
	if(!regExp.test(value)){
		if(value.trim().length == 0){
			$("#nickNameMsg").text("필수 정보입니다.").css("color", "red");
		} else{
        	$("#nickNameMsg").text("2~20자의 소문자, 한글, 숫자와 _, -만 사용 가능합니다.").css("color", "red");
		}
		$("#nickName").css("border", "1px solid red");
		validateCheck.nickname = false;
	} else {
		$.ajax({
			url : "nicknameDubCheck.do",
			data : {"nickname" : value},
			type : "post",
			success : function(result){
				if(result == 0){
					$("#nickNameMsg").text("");
					$("#nickName").css("border", "1px solid #8cb0f7");
					validateCheck.nickname = true;
				}else{
					$("#nickNameMsg").text("이미 사용 중인 닉네임입니다.").css("color", "red");
					$("#nickName").css("border", "1px solid red");
				}
			}
			
		});
	}
});



// 비밀번호 유효성 검사
// 영어 대, 소문자 + 숫자, 총 6~12글자
$("#pswd1, #pswd2").on("input", function(){
    // 비밀번호 유효성 검사
    var regExp = /^[a-zA-Z\d]{6,12}$/;

    var v1 = $("#pswd1").val();
    var v2 = $("#pswd2").val();

    if(!regExp.test(v1)){
		if(v1.trim().length == 0) {
			 $("#pswd1Msg").text("필수 정보입니다.").css("color", "red");
		} else {
			 $("#pswd1Msg").text("영어 대, 소문자 + 숫자, 총 6~12글자만 사용 가능합니다.").css("color", "red");
		}
		$("#pswd1").css("border", "1px solid red");
        validateCheck.pwd1 = false;
    } else{
        $("#pswd1Msg").text("");
		$("#pswd1").css("border", "1px solid #8cb0f7");
        validateCheck.pwd1 = true;
    }


    // 비밀번호가 유효하지 않은 상태에서 비밀번호 확인 작성 시
    if(!validateCheck.pwd1 && v2.length > 0){
        swal("유효한 비밀번호를 먼저 작성해주세요.");
        $("#pswd2").val(""); // 비밀번호 확인에 입력한 값 삭제
		$("#pswd1").val("").focus();

    }else {
        // 비밀번호, 비밀번호 확인의 일치 여부
       if(v1.length == 0 || v2.length == 0){
			$("#pswd2Msg").text("");
		}else if(v1 == v2 && v2.length != 0){
            $("#pswd2Msg").text("");
			$("#pswd2").css("border", "1px solid #8cb0f7");
            validateCheck.pwd2 = true;
        }else{
            $("#pswd2Msg").text("비밀번호가 일치하지 않습니다.").css("color", "red");
			$("#pswd2").css("border", "1px solid red");
            validateCheck.pwd2 = false;
        }
    }
});






/*이름*/ 
$("#name").on("input", function(){
    var regExp = /^[가-힣]{2,}$/;

    var value = $("#name").val();
    if(!regExp.test(value)){
		if(value.trim().length == 0) {
			$("#nameMsg").text("필수 정보입니다.").css("color", "red");
		} else{ 
			$("#nameMsg").text("이름 형식이 유효하지 않습니다.").css("color", "red"); }
		$("#name").css("border", "1px solid red");
            validateCheck.name = false;
    }else {
        $("#nameMsg").text("");
		$("#name").css("border", "1px solid #8cb0f7");
            validateCheck.name = true;
    }
});


function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("address3").value = extraAddr;
            
            } else {
                document.getElementById("address3").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("address2").focus();
        }
    }).open();
	
	validateCheck.address = true;

}


function maxLengthCheck(object) {
	if(object.value.length > object.maxLength){
		object.value = object.value.slice(0, object.maxLength);
	}
}


$("#birth_yy, #birth_mm, #birth_dd").on("input", function(){
	var regExp = /^[\d]{4}$/;
	//var regExp2 = /^[\d]{2}$/;
	var regExp2 = /^(0[1-9]|1[0-9]|2[0-9]|3[01])$/;
	
	var value = $("#birth_yy").val();
	var value2 = $("#birth_mm").val();
	var value3 = $("#birth_dd").val();
	
	if(!regExp.test(value)){
		if(value.trim().length == 0){
			$("#birthdayMsg").text("필수 정보입니다.").css("color", "red");
		} else{
			$("#birthdayMsg").text("태어난 년도 4자리를 정확하게 입력하세요.").css("color", "red");	
		}
		
		$("#birth_yy").css("border", "1px solid red").text("");
	 	validateCheck.birthyy = false;
	} else {
		$("#birthdayMsg").text("");
		$("#birth_yy").css("border", "1px solid #8cb0f7");
            validateCheck.birthyy = true;
	}
	
	if(value2 == '월'){
		$("#birthdayMsg").text("태어난 월을 선택하세요.").css("color", "red");
		$("#birth_mm").css("border", "1px solid red");
		validateCheck.birthmm = false;
	} else {
		$("#birthdayMsg").text("");
		$("#birth_mm").css("border", "1px solid #8cb0f7");
		validateCheck.birthmm = true;
	}
	
	if(!regExp2.test(value3)) {
		console.log(value3);
		
		if(value3.trim().length == 0){
			$("#birthdayMsg").text("필수 정보입니다.").css("color", "red");
			$("#birth_dd").css("border", "1px solid red").text("");
			validateCheck.birthdd = false;
		}else if(Number(value3) < 0 || Number(value3) > 31) {
			
			$("#birthdayMsg").text("태어난 일 2자리를 정확하게 입력하세요.").css("color", "red");	
			validateCheck.birthdd = false;
		}else if(Number(value3) <= 9){
			$("#birthdayMsg").text("10일 미만인 경우 앞에 0을 붙여주세요.").css("color", "red");	
			validateCheck.birthdd = false;
		}
		} else{
		$("#birthdayMsg").text("");
		$("#birth_dd").css("border", "1px solid #8cb0f7");
		validateCheck.birthdd = true;
	}
	
});


$("#gender").on("change", function(){
	var value = $("#gender").val();
	if(value != '성별') {
		$("#gender").css("border", "1px solid #8cb0f7");
		validateCheck.gender = true;
	} else{
		$("#gender").css("border", "1px solid red");
		validateCheck.gender = false;
	}
});


$("#phone").on("input", function(){
	var regExp = /^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$/;
	var value = $("#phone").val();
	
	if(!regExp.test(value)){
		if(value.trim().length == 0){
			$("#phoneMsg").text("필수 정보입니다.").css("color", "red");
		} else{
			$("#phoneMsg").text("올바른 전화번호를 입력해주세요('-'포함)").css("color", "red");
		}
		$("#phone").css("border", "1px solid red");
		validateCheck.phone = false;
	} else{
		$("#phoneMsg").text("");
		$("#phone").css("border", "1px solid #8cb0f7");
		validateCheck.phone = true;
	}
});


var eCheck = true;
$("#email").on("input", function(){
	var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
	var value = $("#email").val();
	
	if(!regExp.test(value)){
		if(value.trim().length == 0) {
			$("#emailMsg").text("필수 정보입니다.").css("color", "red");
		} else{
			$("#emailMsg").text("올바른 이메일를 입력해주세요").css("color", "red");
		}
		validateCheck.email = false;
		eCheck = false;
	}  else{
        $.ajax({
            url : "emailDupCheck.do",
            data : {"email" : value},
            type : "post",
            success : function(result){
                    if(result == 0){ // 중복되지 않은 경우
                    $("#emailMsg").text("");
					$("#email").css("border", "1px solid #8cb0f7");
                    validateCheck.email = true;
					eCheck = true;
                }else{
                    $("#emailMsg").text("이미 사용 중인 이메일입니다.").css("color", "red");
					$("#email").css("border", "1px solid red");
                    validateCheck.email = false;
					eCheck = false;
                }
            },
            error : function(){
                console.log("이메일 중복 검사 실패");
            }
        });
    }
});

// 이메일 인증 번호 
var sendKey;

$("#email_btn").on("click",function(){
	$.ajax({
		url : "../sendMail",
		data : {toEmail : $("#email").val()},
		type : "post",
		async : false,
		success : function(result){
			if(eCheck && result != undefined){
				swal({icon : "success", title : "이메일이 전송되었습니다.", text : $("#email").val() + " 에서 확인해주세요."});
				sendKey = result;
				validateCheck.emadilNum = true;
			}else{
				swal({icon : "error", title : "이메일 전송 실패", text : "이메일을 다시 확인해주세요."});
			}
		}
		, error : function(){
			swal({icon : "error", title : "이메일 전송 실패", text : "이메일을 다시 확인해주세요."});
			validateCheck.emadilNum = false;
			console.log("error");
		}
		
	});
});


$("#cNum").on("input",function(){
	
	var inputKey = $("#cNum").val();
	console.log(sendKey == inputKey);
	if(sendKey == inputKey){
		$("#cNum").css("border", "1px solid #8cb0f7");
		$("#emailMsg").text("");
	}else{
		$("#emailMsg").text("인증 번호가 일치하지 않습니다.");
		$("#cNum").css("border", "1px solid red");
	}
});


function validate(){
	for(var key in validateCheck){
		if(!validateCheck[key]){
			var msg;
			switch(key) {
				case  "id" : msg="아이디가"; break;
				case	"nickname" :  msg="닉네임이"; break;
				case    "pwd1" :  
				case    "pwd2" :  msg="비밀번호가"; break;
				case    "name" :  msg="이름이"; break;
				case    "email" :  msg="이메일이"; break;
				case	"birthyy" :  
				case	"birthmm" :  
				case	"birthdd" :  msg="생년월일이"; break;
				case	"gender" :  msg="성별이"; break;
				case	"phone" :  msg="전화번호가"; break;
				case	"address" :  msg="주소가"; break;
				case 	"emadilNum" : msg="인증번호가"; break;
			}
			swal(msg+" 유효하지 않습니다.");
			
			$("#" + key).focus();
			return false;
		}
	}
}






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
	


// 회원 정보 수정--------------------------------------
// 회원 정보 수정 유효성 검사
	var saveNickname = $("#changeNickName").val();
   var updateCheck = {
		"nickname2":false,
		"address2" : false,
		"phone2" : false
	};

$("#changeNickName").on("input", function(){
	 var regExp = /^[a-zA-z\d-_가-힣]{2,20}$/;
	 var value = $("#changeNickName").val();
	
	if(!regExp.test(value)){
		if(value.trim().length == 0){
			$("#nickNameMsg").text("입력해주세요.").css("color", "red");
		} else{
        	$("#nickNameMsg").text("2~20자 내  소문자, 한글, 숫자,  _, - 사용").css("color", "red");
		}
		$("#changeNickName").css("border", "1px solid red");
		updateCheck.nickname2 = false;
	} 
	else {
		$.ajax({
			url : "nicknameDubCheck.do",
			data : {"nickname" : value},
			type : "post",
			success : function(result){
				if(result == 0){
					$("#nickNameMsg").text("");
					$("#changeNickName").css("border", "1px solid #8cb0f7");
					updateCheck.nickname2 = true;
				} else{
					if(value == saveNickname){
					updateCheck.nickname2 = true;
					}
					$("#nickNameMsg").text("이미 사용 중인 닉네임입니다.").css("color", "red");
					$("#changeNickName").css("border", "1px solid red");
					updateCheck.nickname2 = false;
				}
			}
			
		});
	}
});



function memberUpdateValidate(){
	
	    var regExp1 = /^[a-zA-z\d-_가-힣]{2,20}$/; // 닉네임
   var regExp2 = /^(010|011|016|017|018|019)-[^0][0-9]{3,4}-[0-9]{4}/; // 핸드폰 번호
    // 전화번호 유효성 검사
    var p = $("#changeTel").val();
    if(!regExp2.test(p)){
        updateCheck.phone2 = false;
    }else{
        updateCheck.phone2 = true;
    }
	
	var post = $("#post").val();
	var addr1 = $("#address1").val();
	var addr2 = $("#address2").val();
	 
	if(post == null || addr1 == null || add2 == null){
		updateCheck.address2 = true;
	} else{
		updateCheck.address2 = false;
	}

	
    for(var key in updateCheck){
        if(updateCheck[key] == false){
            swal(key + " 값이 유효하지 않습니다.");
            return false;
        }
    }
}





