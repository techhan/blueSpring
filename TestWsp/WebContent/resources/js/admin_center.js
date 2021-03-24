var validateCheck = {
	"centerName" : false, // 기관명
	"address" : false, // 주소
	"phone" : false, // 전화번호
}

// 기관명 중복 검사
$("#centerName").on("input", function(){
	 var regExp = /^[가-힣\d]{2,30}$/;
	 var value = $("#centerName").val();
	
	console.log(value);
	
	if(!regExp.test(value)){
		if(value.trim().length == 0){
			$("#centerNameMsg").text("필수 정보입니다.").css("color", "red");
			$("#centerName").css("border", "1px solid red");
			validateCheck.centerName = false;
		}
	} else {
		$.ajax({
			url : "centerDubCheck.do",
			data : {"centerName" : value},
			type : "post",
			success : function(result){
				console.log(result);
				
				if(result == 0){
					$("#centerNameMsg").text("");
					$("#centerName").css("border", "1px solid #8cb0f7");
					validateCheck.centerName = true;
				}else{
					$("#centerNameMsg").text("이미 사용 중인 기관명입니다.").css("color", "red");
					$("#centerName").css("border", "1px solid red");
				}
			},error : function(){
				console.log("error");
			}
		});
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


// 전화번호
$("#phone").on("input", function(){
	//var regExp = /^([0-9]{2,3})-?([0-9]{3,4})-?([0-9]{4})$/;
	var regExp = /^([0-9]{2,3})-([0-9]{3,4})-([0-9]{4})$/;
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


function validate(){
	for(var key in validateCheck){
		if(!validateCheck[key]){
			var msg;
			switch(key) {
				case	"centerName" :  msg="기관명이"; break;
				case	"address" :  msg="주소가"; break;
				case	"phone" :  msg="전화번호가"; break;
			}
			swal(msg+" 유효하지 않습니다.");
			
			$("#" + key).focus();
			return false;
		}
	}
}


// 기관 정보 수정--------------------------------------
// 기관 정보 수정 유효성 검사

   var updateCheck = {
		"phone" : false,	// 전화번호
		"address" : false, 	// 주소
	};


function centerUpdateValidate(){

   var regExp = /^([0-9]{2,3})-?([0-9]{3,4})-?([0-9]{4})$/;

    // 전화번호 유효성 검사
    var p = $("#phone").val();
    if(!regExp.test(p)){
        updateCheck.phone = false;
    }else{
        updateCheck.phone = true;
    }
	
	var post = $("#post").val();
	var addr1 = $("#address1").val();
	var addr2 = $("#address2").val();
	 
	if(post == "" || addr1 == "" || add2 == ""){
		updateCheck.address = true;
	} else{
			updateCheck.address = false;
	}
	
    for(var key in updateCheck){
        if(!updateCheck[key]){
            swal("일부 값이 유효하지 않습니다.");
            return false;
        }
    }
}
