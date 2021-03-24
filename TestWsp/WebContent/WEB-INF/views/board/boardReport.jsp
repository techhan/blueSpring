<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
    body {
        background-color: rgb(220, 229, 235);
    }

    h1 {
        text-align: center;
        text-shadow: 1px 1px 2px black;
    }

    #board-report-area {
        text-align: center;
    }

    label{
        display: inline-block;
        width: 19%;
        text-align: center;
        text-shadow: 1px 1px 2px black;
    }

    #report-content {
        width: 80%;
        height: 200px;
        margin: auto;
        /* background-color: rgb(245, 230, 216); */
    }

    #btn-area {
        display: flex;
        justify-content: space-around;
    }
    
    .hidden-form {
        visibility: hidden;
    }
</style>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>
<body>

    <div id="board-report-area">
        <h1>게시글 신고</h1>
    
    <!-- <form action="${contextPath}/boardReport.do" method="get" id="report-form"> -->
        <label><input type="radio" name="reportCategory" value="10">욕설</label>
        <label><input type="radio" name="reportCategory" value="20">광고</label>
        <label><input type="radio" name="reportCategory" value="30">비방</label>
        <label><input type="radio" name="reportCategory" value="40">허위</label>
        <label><input type="radio" name="reportCategory" value="50">기타</label>
        <br>
        <br>

        <input type="text" class="hidden-form" name="brdNo" value="${boardNo}"/>
        <input type="text" class="hidden-form" name="memNo" value="${memberId}"/>
        <input type="text" class="hidden-form" name="target" value="${target}"/>

        <textarea name="reportContent" id="report-content"></textarea>
    
        <br>
        <br>

        <div id="btn-area">
            <button>취소</button>
            <input type="button" id="report-btn" value="신고하기"> 
        </div>
<!--             
    </form> -->
    </div>

<script>
    var brdNo = "${boardNo}";
    var memNo = "${memberId}";
    var target = "${target}";
    var reportContent = "";
    var reportCategory;
    $("#report-btn").on("click", function() {
        reportCategory = $('input[name=reportCategory]:checked').val();
        reportContent = $("#report-content").val().trim()
        console.log(brdNo);
        console.log(memNo);
        console.log(target);
        console.log(reportContent);
        console.log(reportCategory);
        if($(':radio[name="reportCategory"]:checked').length < 1){
            alert('신고유형을 선택해주세요');                        
            event.preventDefault();
        } else if(window.confirm("보고 있는 게시글을 신고하시겠습니까?")){
            $.ajax({
    		url : "${contextPath}/boardReport.do",
            data : {"reportCategory" : reportCategory ,
                    "reportContent" : reportContent,
                    "brdNo" : brdNo,
                    "memNo" : memNo,
                    "target" : target},
    		type : "post",
    		success : function(result) {    			
    			if(result > 0) {
                    swal({"icon" : "success" , "title" : "신고가 접수되었습니다."}).then(function(){ 
                    	opener.parent.location.href = "${contextPath}/board/list.do?cp=1"; // 주소 메인으로 넘기기 
                    	window.close(); 
                    });
                   
    			} else {
                    swal({"icon" : "error" , "title" : "신고 접수 실패."});
                }		
    		}, 
    		error : function(request, status, error) {
    	      alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
    		}
    		
    	});
        }
    });
</script>

</body>
</html>