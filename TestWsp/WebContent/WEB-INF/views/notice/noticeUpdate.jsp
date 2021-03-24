<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사할 글 작성</title>
<link rel="stylesheet" href="${contextPath}/resources/css/notice/no_insert.css" type="text/css">
</head>
<body>
	<!-- 해더 영역 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

	
	<div class="wrapper">
		
		<h2>공지사항 작성</h2>
		<hr>
		
		<c:if test="${!empty param.sk && !empty param.sv}">
			<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
		</c:if>
		
		<form action="update.do?cp=${param.cp}&no=${param.no}${searchStr}" method="post" 
			  enctype="multipart/form-data" role="form" onsubmit="return updateValidate();">
		
		
				<!-- 조금 안으로 들여쓰기 위해서  -->
				<div class="input">
					
					<!-- 제목 -->		
					<label class="input-area">제목</label>

					<input type="text" name="noticeTitle"  class="title-input" value="${notice.noticeTitle}">

													<!-- value=""   에   el로 저장되어있던 값을 가져온다 --> 
														
					<!-- 이미지 파일 업로드 -->
					<div class="form-line">
						<label class="title-img">대표 이미지 </label>
						<div class="ch-board-Img noticeImg" id="titleImgArea">
							<img id="titleImg" width="200" height="200">
						</div>
					</div>
					
					<div class="form-line-sb">
						<label class="t-img">업로드<br>이미지</label>
						<div class="ch-board-Img noticeImg" id="contentImgArea1">
							<img id="contentImg1" width="150" height="150">
						</div>
		
						<div class="ch-board-Img noticeImg" id="contentImgArea2">
							<img id="contentImg2" width="150" height="150">
						</div>
					</div>
					
					<div id="fileArea">
						<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)">     
						<input type="file" id="img1" name="img1" onchange="LoadImg(this,1)"> 
						<input type="file" id="img2" name="img2" onchange="LoadImg(this,2)">
					</div>
				
					<div>내용</div>

					<textarea class="form-control" id="noticeContent" name="noticeContent" rows="15" style="resize: none;">${notice.noticeContent}</textarea>
		
		
				</div>
	
			<hr>
			
			<!-- 수정, 이전으로 버튼 -->
			<div class="text-center">
				<button type="submit" class="btn">수정</button>
				<button type="button" class="btn" onclick="location.href='${header.referer}'">이전</button>
			</div>

		</form>
	</div>

	
	<!-- 푸터 영역 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
    

	<script>
	// 유효성 검사 
	function noticeValidate() {
		if ($("#noticeTitle").val().trim().length == 0) {
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
		
		$(".noticeImg").on("click", function(){ //이미지 영역이 클릭이 되었을 때 
			
			// 클릭한 이미지 영역 인덱스 얻어오기
			var index = $(".noticeImg").index(this);
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

				$(".noticeImg").eq(num).children("img").attr("src",
						e.target.result); //num은 0,1,2,3 중 하나임
				// e.target.result : 파일 읽기 동작을 성공한 요소가 읽어들인 파일 내용
			}
		}
	}
	
	// 이미지 배치        여기서 오류나는거 신경x 스트립트
	<c:forEach var="file" items="${fList}">
		$(".noticeImg").eq( ${file.fileLevel} ).children("img")
				.attr("src", "${contextPath}/resources/uploadImages/notice/${file.fileName}");
	</c:forEach>  
	
	
	</script>
</body>
</html>