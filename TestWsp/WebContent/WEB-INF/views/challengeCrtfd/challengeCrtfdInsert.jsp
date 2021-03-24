<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지 인증 등록 페이지</title>
<link rel="stylesheet" href="${contextPath}/resources/css/challengeCr/ch_cr_insert.css" type="text/css">
</head>
<body>

	<!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

	<div class="wrapper">
		<h2>챌린지 인증하기</h2>
		<hr>
		
		<form action="${contextPath}/challengeCrtfd/insert.do?no=${param.no}&cp=${param.cp}" method="post" 
			enctype="multipart/form-data" role="form" onsubmit="return challengeCrtfdValidate();">
			<!-- 개설 정보 입력 -->
			<div class="input">
			
				<label class="input-area">챌린지 선택</label>
				<select name="chSelect" id="chSelect" class="ch-c-cat">
					<!-- <option>DB에서 정보 챌린지 정보 가져올 거</option> -->
					<!--DB에서 회원이 참여하고 있는 챌린지들은 가져온다 -->
					<!-- 된다면 첼린지 제목 / 시작일 ~ 끝나는 날 -->
					
					<c:forEach var="challenge" items="${joinInfo }">
						<option value="${challenge.challengeNo}">${challenge.challengeTitle} &nbsp;&nbsp;&nbsp;&nbsp;
							<fmt:formatDate var="startDate" value="${challenge.chlngStartDt}" pattern="yyyy-MM-dd"  />
							${startDate}
							&nbsp;&nbsp;-&nbsp;&nbsp;
							<fmt:formatDate var="endDate" value="${challenge.chlngEndDt}" pattern="yyyy-MM-dd"  />
							${endDate}	
						</option>
					
					</c:forEach>
				</select>
				
				<div class="input-top-margin input-area">
					<label  class="title-area">제목 </label>
					<input class="input-title" id="chlngCrTitle" name="chlngCrTitle">
					
				</div>
				
				<div class="input-top-margin">
					<label  class="input-area">작성자 </label>
					<%-- ${challenge.memNickname} --%>
					${loginMember.memberNickname }
				</div>
				
				<div class="input-top-margin">
					<label class="input-area">작성일</label>
					<span id="today"></span>
				</div>
			
				<!-- 파일 업로드 -->
				<div class="input-top-margin">
					<div class="form-line">
						<label>인증 사진 파일</label>
						<div class="ch-c-board-Img chCrImg" id="titleImgArea">
							<img id="titleImg" width="350px" height="250px">
						</div>
					</div>
				</div>
				
				<div id="fileArea">
					<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)">     
				</div>
				
				
				<!-- 참가 소감 작성 -->
				<div class="input-top-margin">오늘의 참가 소감 작성</div>
				<textarea class="form-control" id="chlngCrContent" name="chlngCrContent" rows="20" style="resize: none;"></textarea>
				
			</div>
			
			<hr>
			
			<!-- 완료, 목록 버튼 -->
			<div class="text-center">
				<button type="submit" class="btn">완료</button>
				<button type="button" class="btn" onclick="location.href='${header.referer}'">목록</button>
			</div>
			
		</form>
	</div>








	<!-- 푸터 영역 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>


    <script>
    //오늘 ㅁ날짜
		(function printToday(){
			// 오늘 날짜 출력 
	 		var today = new Date();
			var month = (today.getMonth()+1);
			var date = today.getDate();
	
	  	var str = today.getFullYear() + "-"
	        		+ (month < 10 ? "0"+month : month) + "-"
	        		+ (date < 10 ? "0"+date : date);
			$("#today").html(str);
		})();

    
 	// 유효성 검사
    function challengeCrtfdValidate() {
		if ($("#challengeTitle").val().trim().length == 0) {
			alert("제목을 입력해 주세요.");
			$("#challengeTitle").focus();
			return false;
		}

		if ($("#challengeContent").val().trim().length == 0) {
			alert("내용을 입력해 주세요.");
			$("#challengeContent").focus();
			return false;
		}
	}
 	
 	// 이미지 영역을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수    //래디함수 : 페이지가 ㅎ로딩이 끝난후 실행
	$(function(){
		$("#fileArea").hide(); // #fileArea 요소를 감춤
		
		$(".chCrImg").on("click", function(){ //이미지 영역이 클릭이 되었을 때 
			
			// 클릭한 이미지 영역 인덱스 얻어오기
			var index = $(".chCrImg").index(this);
					// -> 클릭된 요소가 .noticeImg 중 몇번재 인덱스인지 반환
			console.log(index);
					
			// 클린된 영역 인덱스에 맞는 input file 태그 클릭
			$("#img" + index).click();
		});
	});
 	
 	
	// 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
	function LoadImg(value, num) {

		if (value.files && value.files[0]) { //해당 요소에 업로드된 파일이 있을 경우

			var reader = new FileReader();
			reader.readAsDataURL(value.files[0]);

			reader.onload = function(e) {

				$(".chCrImg").eq(num).children("img").attr("src", e.target.result);
			}
		}
	}
 	
 	
 	
 	
    </script>

</body>
</html>