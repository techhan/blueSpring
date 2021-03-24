<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${contextPath}/resources/css/board/boardUpdate.css">

</head>
<body>
		<jsp:include page="../common/header.jsp"></jsp:include>
    
    <h1>자유게시판</h1>

    <div id="write-wrapper">
        <h2>글수정</h2>

				<c:if test="${!empty param.sk && !empty param.sv}">
					<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
				</c:if>
				
				<form action="update.do?cp=${param.cp}&no=${param.no}${searchStr}" method="post" 
					  role="form" onsubmit="return updateValidate();">
            <div id="title-wrapper">
                <label>글 제목</label> 
                <input id="b-title" name="b-title" type="text" value="${board.boardTitle}"><br>
            </div>
  
 
            <div id="category-wrapper">
                <label>카테고리</label>      
             		<input type="radio" name="b-category" id="info" value="10"> <label class="radio-check">정보</label>               	
                <input type="radio" name="b-category" id="life" value="20"> <label class="radio-check">일상</label>
                <input type="radio" name="b-category" id="hobby" value="30"> <label class="radio-check">취미</label>
                <input type="radio" name="b-category" id="worry" value="40"> <label class="radio-check">고민</label>
                <input type="radio" name="b-category" id="employ" value="50"> <label class="radio-check">취업</label>
                <input type="radio" name="b-category" id="free" value="60"> <label class="radio-check">자유</label> <br>
            </div>

            <div id="file-wrapper">
                <label>첨부하기</label> <br>
            </div>

            <div id="content-wrapper">
                <label>내용</label> <br>    
                <textarea name="b-content" id="summernote" cols="3000" rows="1000"></textarea> <br>
            </div>

            <button type="submit" id="update-btn">수 정</button>
            <button type="button" id="back-board-main" onclick="location.href='${header.referer}'">취소</button>
        </form>
    </div>

    <jsp:include page="../common/footer.jsp"></jsp:include>
    
    <script>
		 // 유효성 검사 
			function updateValidate() {
				if ($("#b-title").val().trim().length == 0) {
					alert("제목을 입력해 주세요.");
					$("#b-title").focus();
					return false;
				}
	
				if ($("#b-content").val().trim().length == 0) {
					alert("내용을 입력해 주세요.");
					$("#b-content").focus();
					return false;
				}
			}
			
			// 화면 새로 고침 시 기존 글의 태그 체크
			$(document).ready(function() {
				
				$(".radio-check").each(function(index, item){
					
					if($(this).text() == '${board.categoryName}') {
						$(this).prev().prop("checked", true);
						console.log(1);
						
					} else {
						console.log(2);
					}
					
				});

			});
			
			
			$(document).ready(function() {
		     $('#summernote').summernote({
							 height: 300,                 // 에디터 높이
							 minHeight: null,             // 최소 높이
							 maxHeight: null,
		           focus: true, 
		           lang : 'ko-KR',
		           
					  callbacks: {
						  onImageUpload: function(files, eidto) {
								  uploadSummernoteImageFile(files[0], this);
						  }
					  }
		     });
		     // Summernote에 글 내용 추가하는 코드
		     $("#summernote").summernote('code', '${board.boardContent}');
		   });
				
			
			/**
			   * 이미지 파일 업로드
			   */
		function uploadSummernoteImageFile(file, el) {
			if($(':radio[name="b-category"]:checked').length < 1){
				alert('태그를 선택해주세요');                        
				event.preventDefault();
			}
			data = new FormData();
			data.append("file", file);
			$.ajax({
				data : data,
				type : "POST",
				url : "${contextPath}/board/insertImage.do",
				contentType : false,
				processData : false,
				success : function(data) {
					//항상 업로드된 파일의 url이 있어야 한다.
			$(el).summernote('editor.insertImage', data); 
						//		$(el).summernote('editor.insertImage', img_name);
				}
	      });
 		}   
    </script>
</body>
</html>