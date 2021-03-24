<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>기관 상세 페이지</title>
    <style>
    .admin_content { 
        width: 90%; 
        height: 100%;
        margin: auto;
    }
    .centerPage_category { 
        width: 100%;
        height: 10%;
        font-weight: bold;
    }
    .centerPage_title{
        border-top: 2px solid gray;
        border-bottom : 2px solid gray;
        width: 100%;
        height: 30%;
    }
    .centerPage_content{
        width: 100%;
        height: 60%;
    }
	</style>
</head>
<body>
    <div class="wrapper">
        <div class="admin_content">
            <div class="centerPage_view">
                <div class="centerPage_category">
                    <h3>분류 : ${center.centerCla}</h3>
                </div>
                <div class="centerPage_title">
                    <h4>기관명 : ${center.centerName}</h4>
                </div>
                <div class="centerPage_content">
                    <p></p>
                    센터번호 : ${center.centerNo}<br>
                    지역(시/도) : ${center.centerArea1}<br>
                    지역(구/군) : ${center.centerArea2}<br>
                    전화번호 : ${center.centerTel}<br>
                    홈페이지 : ${center.centerUrl}<br>
                    주소: ${center.centerAddr}<br>
               ${center.centerAddrDtl}<br>
                </div>
            </div>
        </div>
    </div>
</body>
</html>