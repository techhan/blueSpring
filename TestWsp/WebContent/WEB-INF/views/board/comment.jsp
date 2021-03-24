<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	.cWriter, .cDate {
    	text-shadow: 1px 1px 2px black;
	}
    #comment-area {
        border-top: 1px solid lightgray;
        border-bottom: 1px solid lightgray;
        margin-top: 50px;
        margin-bottom: 20px;
        min-height: 400px;
    }

    table {
        width: 100%;
    }

    #comment-content-area {
        width: 90%;
    }

    .comment-content-box {
        resize: none;
        width: 90%;
    }
    
    #comment-content {
    		resize: none;
        width: 90%;
    }
    
    #add-comment {
        width: 100px;
	    	text-align: center;
    }

    li {
        list-style: none;
    }
    
    #commentListArea {
    		margin : 0;
    		padding-left: 0;
    		margin-bottom: 20px;
    }
    
    #commentReportBtn {
    		float: right;
    }

</style>
<link rel="stylesheet" href="${contextPath}/resources/css/common/btnStyle.css">
</head>
<body>
    <c:set var = "loginMember" value="${loginMember}"/> 

    <c:choose>
    	<c:when  test="${null eq loginMember }">
    		<c:set var ="loginMemberNo" value = "0"/>
    		<c:set var ="loginMemberId" value = ""/>
    	</c:when>
    	<c:otherwise>
    		<c:set var ="loginMemberNo" value = "${loginMember.memberNo}"/>
    		<c:set var ="loginMemberId" value = "${loginMember.memberId}"/>
    	</c:otherwise>
    </c:choose>


    <div id="comment-area">
        <h4>댓글</h4>
        
        <table>
            <tr>
                <td id="comment-content-area">
                    <textarea rows="3" id="comment-content" class="comment-content-box" placeholder="댓글을 작성하세요."></textarea>
                </td>
                <td>
                    <button id="add-comment" class="btn-style1">
						댓글<br>등록
					</button>
                </td>
            </tr>
        </table>
        
        	<!-- 댓글 출력 부분 -->
			<div class="commentList">
				<ul id="commentListArea">
			
				</ul>
			</div>
        
    </div>
    
    
		<!-- 스크립트 영역 -->
    <script> /*"${loginMember.memberId}"*/
				var loginMemberId = '${loginMember.memberId}'; 
				var loginMemberNo = ${loginMemberNo};
        var parentBoardNo = ${board.boardNo};
        
        // 페이지 로딩 완료 시 댓글 목록 호출
        $(function(){
            selectCommentList();
        });
        
        
        // 해당 게시글 댓글 목록 조회 함수(ajax)
        function selectCommentList(){
        
            $.ajax({
                url : "${contextPath}/comment/selectList.do",
                data : {"parentBoardNo" : parentBoardNo}, 
                type : "post", 
                dataType : "JSON", 
                success : function(cList) {
                    $("#commentListArea").html("");                   
                    
                    $.each(cList, function(index, item){
            		
                        var li = $("<li>").addClass("comment-row");
                        var reportBtn = $("<button>").text("신고하기").addClass("btn-style3").attr("id", "commentReportBtn").attr("onclick", "reportComment("+item.comNo+", '"+item.memberId+"' )");
                        																																																	
                        var cWriter = $("<p>").addClass("cWriter").text(item.memberNickName);
                        var cDate = $("<p>").addClass("cDate").text("작성일 : " + item.comCreateDate);
                        
                        var div = $("<div>");
                        //div.append(cWriter).append(cDate);
                        
                       	div.append(cWriter);
                        div.append(cDate);
                       	
                        if(item.memberId != loginMemberId && loginMemberId != "") {
                        	div.append(reportBtn);
                        }
                        
                        
                        var cContent = $("<p>").addClass("cContent").html(item.comContent);
                        
                        li.append(div).append(cContent);                     
                        
                        // 현재 댓글의 작성자와 로그인한 멤버의 아이디가 같을 때 버튼 추가
/*                      console.log(item.memberId);
                        console.log(loginMemberId); */
                        if(item.memberId == loginMemberId){
                            // console.log(item.memberId);
                            // 댓글, 수정, 삭제 버튼 영역
                            var commentBtnArea = $("<div>").addClass("commentBtnArea");
                            
                            // ** 추가되는 댓글에 onclick 이벤트를 부여하여 버튼 클릭 시 수정, 삭제를 수행할 수 있는 함수를 이벤트 핸들러로 추가함. 
                            var showUpdate = $("<button>").addClass("").text("수정").addClass("btn-style2").attr("onclick", "showUpdateComment("+item.comNo+", this)");
                            var deleteComment = $("<button>").addClass("").text("삭제").addClass("btn-style3").attr("onclick", "deleteComment("+item.comNo+")");
                            
                            commentBtnArea.append(showUpdate).append(deleteComment);
                            
                            li.append(commentBtnArea);
                        }
        
                        
                        $("#commentListArea").append(li).append($("<hr>"));
                        
                        
                    });
                    
                }, 
                error : function(request, status, error) {
                	alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
                }		
            })           
        }
        
    // 댓글 등록 함수
  	$("#add-comment").on("click", function() {
  		var commentContent = $("#comment-content").val().trim();
  		
  		// 로그인 안되있으면
  		if(loginMemberId == "") {
  			alert("로그인이 되어있어야 댓글을 작성할 수 있습니다.");
  			
  		} else { // 로그인이 되어있다면
  			
  			if(commentContent.length == 0) {
  				alert("한 글자 이상 입력하셔야 합니다.")
  				
  			} else {
  				// ${loginMember.memberNo}
  				var commentWriter = loginMemberNo;
  				
  				$.ajax({
  					url : "${contextPath}/comment/insertComment.do",
  					data : {"commentWriter" : commentWriter,
  									"commentContent" : commentContent,
  									"parentBoardNo" : parentBoardNo},
  					type : "post", 
  					success : function(result) {
  						
  						if(result > 0) {
  							$("#comment-content").val("");
  							
  							swal({"icon" : "success", "title" : "댓글 등록 성공"});
  							
  							selectCommentList();
  						}
  						
  					},
  					error : function(request, status, error) {
  	          alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
  	        }	
  				});
  			}
  			
  		}
  		
  	});
    
    // 댓글 수정 영역 관련(수정 창이 중복으로 뜨지 않게)
    var beforeCommentRow; // 원래의 댓글 내용
    
    function showUpdateComment(commentNo, element) {
    	if($(".commentUpdateContent").length > 0) { // 배열의 개수를 나타내는구나
    		$(".commentUpdateContent").eq(0).parent().html(beforeCommentRow); //eq(0) : 배열의 0번째 있는 애
    	}
    	
    	beforeCommentRow = $(element).parent().parent().html(); // 부모의 내용 전체 담아놓기
    	
    	var beforeContent = $(element).parent().prev().html(); // 부모의 바로 앞에 있는 요소의 html 담기 (즉, 댓글 내용 담기)
    	
    	// 이전 댓글 내용의 크로스사이트 스크립트 처리 해제, 개행문자 변경
    	// -> 자바스크립트에는 replaceAll() 메소드가 없으므로 정규 표현식을 이용하여 변경
    	beforeContent = beforeContent.replace(/&amp;/g, "&");	
    	beforeContent = beforeContent.replace(/&lt;/g, "<");	
    	beforeContent = beforeContent.replace(/&gt;/g, ">");	
    	beforeContent = beforeContent.replace(/&quot;/g, "\"");	
    	
    	beforeContent = beforeContent.replace(/<br>/g, "\n");	
    	
    	// prev()는 선택, bofore()는 추가!
    	$(element).parent().prev().remove();
    	var textarea = $("<textarea>").addClass("commentUpdateContent comment-content-box").attr("rows", "3").text(beforeContent);
    	$(element).parent().before(textarea);
    	
    	var updateComment = $("<button>").addClass("btn-style2").text("수정 하기").attr("onclick", "updateComment(" + commentNo + ", this)");    	
    	var cancelUpdate = $("<button>").addClass("btn-style3").text("수정 취소").attr("onclick", "updateCancel(this)");
    	
    	var commentBtnArea = $(element).parent();
    	
    	$(commentBtnArea).empty();
    	$(commentBtnArea).append(updateComment); 
    	$(commentBtnArea).append(cancelUpdate);
    	
    }
    
    // 수정 폼에서 수정 하기 클릭 시
    function updateComment(commentNo, element) {
    	var afterCommentContent = $(element).parent().prev().val();
    	
    	$.ajax({
    		url : "${contextPath}/comment/updateComment.do",
    		data : {"commentNo" : commentNo ,
    						"afterCommentContent" : afterCommentContent},
    		type : "post",
    		success : function(result) {
    			
    			if(result > 0) {
    				selectCommentList(parentBoardNo); // 질문하기
    				
    				swal({"icon" : "success" , "title" : "댓글이 수정되었습니다."});
    			}    			
    		}, 
    		error : function(request, status, error) {
    	      alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
    		}
    		
    	});
    }
    
    // 수정 폼에서 수정 취소 클릭 시
    function updateCancel(element) {
    	$(element).parent().parent().html(beforeCommentRow);
    }
    
    // 댓글 삭제
    function deleteComment(commentNo) {
    	if(confirm("댓글을 정말로 삭제하시겠습니까?")) {
    		
    		$.ajax({
    			url : "${contextPath}/comment/deleteComment.do",
    			data : {"commentNo": commentNo}, 
					success : function(result) {
						
						if(result > 0) {
							selectCommentList(parentBoardNo);
							
							swal({"icon" : "success" , "title" : "댓글이 삭제되었습니다."});
						}
						
					}, 
      		error : function(request, status, error) {
       	      alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
       		}    			
    			
    		});
    	}
    }
    
    // 댓글 신고 
    function reportComment(commentNo, commentWriter){
			if(window.confirm("보고 있는 댓글을 신고하시겠습니까?")) {
				/* $(board.memberId); */
				var memberNo = ${loginMemberNo};
				var target = commentWriter;
				var comNo = commentNo;
				
				console.log(target);
				
				var url = "${contextPath}/commentReportForm.do?comNo=" + comNo + "&memNo=" + memberNo + "&target=" + target;
				var title = "신고하기";
				var option = "width = 700, height = 400, top = 300, left = 600, location = no";
				
				window.open(url, title, option);
			}    	
    };
    
    </script>
</body>
</html>