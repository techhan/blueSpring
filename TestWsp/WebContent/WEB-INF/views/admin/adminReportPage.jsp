<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>신고 접수 상세 페이지</title>
    
    <style>
    .admin_content { 
        width: 90%; 
        height: 100%;
        margin: auto;
    }
    .reportPage_category { 
        width: 100%;
        height: 10%;
        font-weight: bold;
    }
    .reportPage_title{
        border-top: 2px solid gray;
        border-bottom : 2px solid gray;
        width: 100%;
        height: 30%;
    }
    .reportPage_content{
        width: 100%;
        height: 60%;
    }
	</style>
</head>
<body>
    <div class="wrapper">
        <div class="admin_content">
            <div class="reportPage_view">
                <div class="reportPage_category">
                    <h3>신고접수번호 : ${report.reportNo}</h3>
                </div>
                <div class="reportPage_title">
                    <h4>(카테고리 10:욕설 20:광고 30:비방 40:허위 50:기타)</h4>
                    <h3>카테고리 번호 : ${report.reportCategoryNo}</h3>
                </div>
                <div class="reportPage_content">
                    <p></p>
                    신고타입 (1:게시글 2:댓글) : ${report.reportType}<br>
                    원글번호 : ${report.boardNo}<br>
                    신고한회원 : ${report.memberId}<br>
                    접수내용 : <br>
                    ${report.reportContent}
                </div>
            </div>
        </div>
    </div>
</body>
</html>