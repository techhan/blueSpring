<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지 등록 페이지</title>
<link rel="stylesheet" href="${contextPath}/resources/css/challenge/ch_insert.css" type="text/css">
</head>

<body>
	<!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

	<div class="wrapper">
		
		<h2>챌린지 개설하기</h2>
		<hr>
		
		
		<form action="${contextPath}/challenge/insert.do" method="post" 
			enctype="multipart/form-data" role="form" onsubmit="return challengeValidate();">
			<!-- 개설 정보 입력 -->
			<div class="input">
				<label class="input-area">제목</label>
				<input type="text" class="title-input" id="chlngTitle" name="chlngTitle" placeholder="챌린지 제목을 입력해주세요."> <br><br>
				
				<label class="input-area">시작일</label>
				<input type="date" class="date str-date strDt" id="strDt" name="strDt"> 
				
				<label class="input-area">종료일</label>
				<input type="date" class="date end-date endDt" id="endDt" name="endDt"> 
				
				<label class="input-area">카테고리</label>
				<select name="cate" id="cate" class="ch-cat">
	        			<option value="10">건강</option>
	        			<option value="20">관계</option>
	        			<option value="30">생활</option>
	        			<option value="40">역량</option>
	        			<option value="50">자산</option>
	        			<option value="60">취미</option>
	        			<option value="70">공부</option>
	        			<option value="80">돈 관리</option>
	        			<option value="90">그 외</option>
	        	</select>
				
				<br><br>
				
				<!-- 이미지 파일 업로드 -->
				<div class="form-line">
					<label class="title-img">대표 이미지 </label>
					<div class="ch-board-Img chImg" id="titleImgArea">
						<img id="titleImg" width="200" height="200">
					</div>
				</div>
				
				<div class="form-line-sb">
					<label class="t-img">추가 설명<br>이미지</label>
					<div class="ch-board-Img chImg" id="contentImgArea1">
						<img id="contentImg1" width="150" height="150">
					</div>
	
					<div class="ch-board-Img chImg" id="contentImgArea2">
						<img id="contentImg2" width="150" height="150">
					</div>
				</div>
				
				<div id="fileArea">
					<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)">     
					<input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
					<input type="file" id="img2" name="img2" onchange="LoadImg(this,2)">
				</div>
				
			
				<!-- <div class="rule-area">
					<label class="input-area">인증방법</label>
					<input type="text" class="rule-input">
				</div> -->
				
				<br>
				<div>소개하기</div>
				<textarea class="form-control" id="chlngContent" name="chlngContent" rows="15" style="resize: none;"
				 			placeholder="인증방법과 챌린지에 대해서 설명해주세요!"></textarea>
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
    // 유효성 검사
    function challengeValidate() {
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
		
		$(".chImg").on("click", function(){ //이미지 영역이 클릭이 되었을 때 
			
			// 클릭한 이미지 영역 인덱스 얻어오기
			var index = $(".chImg").index(this);
					// -> 클릭된 요소가 .noticeImg 중 몇번재 인덱스인지 반환
			console.log(index);
					
			// 클린된 영역 인덱스에 맞는 input file 태그 클릭
			$("#img" + index).click();
		});
	});
		
	
 	// 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
	function LoadImg(value, num) {
		// value.files : 파일이 선택되어 있으면 true
		// value.files[0] : 여러 파일 중 첫번째 파일이 업로드 되어 있으면 true

		if (value.files && value.files[0]) { //해당 요소에 업로드된 파일이 있을 경우

			var reader = new FileReader();
			// 자바스크립트 FileReader
			// 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 
			// 읽을 파일을 가리키는 File 혹은 Blob객체를 이용해 
			// 파일의 내용을 읽고 사용자의 컴퓨터에 저장하는 것을 가능하게 해주는 객체

			reader.readAsDataURL(value.files[0]);
			// FileReader.readAsDataURL()
			// 지정된의 내용을 읽기 시작합니다. 
			// lob완료되면 result속성 data:에 파일 데이터를 나타내는 URL이 포함 됩니다.

			reader.onload = function(e) {
				// FileReader.onload
				// load 이벤트의 핸들러. 이 이벤트는 읽기 동작이 성공적으로 완료 되었을 때마다 발생합니다.

				// 읽어들인 내용(이미지 파일)을 화면에 출력

				$(".chImg").eq(num).children("img").attr("src",
						e.target.result); //num은 0,1,2,3 중 하나임
				// e.target.result : 파일 읽기 동작을 성공한 요소가 읽어들인 파일 내용
			}
		}
	}
	
    	
    </script>
</body>
</html>