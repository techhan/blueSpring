<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   <!-- 날짜 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지 업데이트 페이지</title>
<link rel="stylesheet" href="${contextPath}/resources/css/challenge/ch_update.css" type="text/css">
</head>

<body>
	<!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

	<div class="wrapper">
		
		<h2>챌린지 수정하기</h2>
		<hr>
		
		
		<form action="${contextPath}/challenge/update.do?no=${param.no}&cp=${param.cp}" method="post" 
			enctype="multipart/form-data" role="form" onsubmit="return challengeValidate();">
			<!-- 개설 정보 입력 -->
			<div class="input">
				<label class="input-area">제목</label>
				<input type="text" name="chlngTitle" id="chlngTitle" class="title-input" value="${challenge.chlngTitle}"> <br><br>
				
				
				<fmt:formatDate var="srtDt" value="${challenge.chlngStartDt}" pattern="yyyy-MM-dd"/>
				<label class="input-area">시작일</label>
				<input type="date" name="strDt" id="strDt" class="date str-date" value="${srtDt}"> 
				
				<fmt:formatDate var="endDt" value="${challenge.chlngEndDt}" pattern="yyyy-MM-dd"/>
				<label class="input-area">종료일</label>
				<input type="date" name="endDt"  id="endDt" class="date end-date" value="${endDt}"> 
				
				<label class="input-area">카테고리</label>
				<select name="cate" id="cate" class="ch-cat">
	        			<option>건강</option>
	        			<option>관계</option>
	        			<option>생활</option>
	        			<option>역량</option>
	        			<option>자산</option>
	        			<option>취미</option>
	        			<option>공부</option>
	        			<option>돈 관리</option>
	        			<option>그 외</option>
	        	</select>
				
				<br><br>
				
				<!-- 이미지 업로드 부분 업데이트 -->
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
				
				
				<br>
				<div>소개하기</div>
				<textarea class="form-control" id="chlngContent" name="chlngContent" rows="15" style="resize: none;">${challenge.chlngContent}</textarea>
			</div>
			
			<hr>
			
			<!-- 수정, 이전으로 버튼 -->
			<div class="text-center">
				<button type="submit" class="btn">수정</button>
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
			$("#noticeTitle").focus();
			return false;
		}

		if ($("#noticeContent").val().trim().length == 0) {
			alert("내용을 입력해 주세요.");
			$("#noticeContent").focus();
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

		if (value.files && value.files[0]) { //해당 요소에 업로드된 파일이 있을 경우

			var reader = new FileReader();

			reader.readAsDataURL(value.files[0]);

			reader.onload = function(e) {

				$(".chImg").eq(num).children("img").attr("src",e.target.result); //num은 0,1,2,3 중 하나임
			}
		}
	}
	
	// 이미지 배치        여기서 오류나는거 신경x 스트립트
	<c:forEach var="file" items="${fList}">
		$(".chImg").eq( ${file.fileLevel} ).children("img")
				.attr("src", "${contextPath}/resources/uploadImages/challenge/${file.fileName}");
	</c:forEach>             
	
	
	
	// 카테고리 초기값 지정             //즉시 실행 함수
	(function(){
		$("#cate > option").each(function(index, item){
			
			if( $(item).text() == "${challenge.chlngCateNm}"){
				$(item).prop("selected", true);
			}
		});
	})();
	
	

	

    
    
    
	
    	
    </script>
</body>
</html>