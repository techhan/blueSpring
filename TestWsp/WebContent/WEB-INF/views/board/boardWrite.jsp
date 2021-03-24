<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${contextPath}/resources/css/board/boardWrite.css">



</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    
    <h1>자유게시판</h1>

    <div id="write-wrapper">
        <h2>글작성</h2>

        <form action="${contextPath}/board/write.do" method="post" 
				  		role="form" onsubmit="return boardValidate();">
            <div id="title-wrapper">
                <label>글 제목</label> 
                <input id="b-title" name="b-title" type="text"> <br>
            </div>
            
<%--             <div class="form-inline mb-2">
							<label class="input-group-addon mr-3 insert-label">작성자</label>
							<h5 class="my-0" id="writer">${loginMember.memberNo}</h5>
						</div> --%>
  
            <div id="category-wrapper">
                <label>카테고리</label>
                <input type="radio" name="b-category" id="info" value="10"> <label class="radio-check">정보</label>
                <input type="radio" name="b-category" id="life" value="20"> <label class="radio-check">일상</label>
                <input type="radio" name="b-category" id="hobby" value="30"> <label class="radio-check">취미</label>
                <input type="radio" name="b-category" id="worry" value="40"> <label class="radio-check">고민</label>
                <input type="radio" name="b-category" id="employ" value="50"> <label class="radio-check">취업</label>
                <input type="radio" name="b-category" id="free" value="60"> <label class="radio-check">자유</label> <br>
            </div>

<!--             <div id="file-wrapper">
                <label for="File">첨부하기&nbsp;&nbsp;</label> 
                <input type="file" name="file" id="file-btn"/>
                <i class="fas fa-file-image"></i>
            </div> -->

            <div id="content-wrapper">
                <label>내용</label> <br>    
                <textarea id="b-content" name="b-content" class="summernote"></textarea>
                
            </div>

            <button type="submit" id="add-btn" onClick="uploadSummernoteImageFile();">등 록</button>
            <button type="button" id="back-board-main" onclick="location.href='${header.referer}'">목록으로</button>
        </form>
    </div>

    <jsp:include page="../common/footer.jsp"></jsp:include>
    
    <script>
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
			function boardValidate() {
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
			
			
			// 이미지 업로드
 			$(document).ready(function() {
				//여기 아래 부분
				$('#b-content').summernote({
					  height: 300,                 // 에디터 높이
					  minHeight: null,             // 최소 높이
					  maxHeight: null,             // 최대 높이
					  focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
					  lang: "ko-KR",					// 한글 설정
					  placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
			          
					  callbacks: {
						  onImageUpload: function(files, eidto) {
								  uploadSummernoteImageFile(files[0], this);
						  }
					  }
				});
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