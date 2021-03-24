<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" scope="application" value="${pageContext.servletContext.contextPath}"></c:set>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="${contextPath}/resources/css/header.css" type="text/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script src="${contextPath}/resources/summernote/summernote-lite.js"></script>
<script src="${contextPath}/resources/summernote/summernote-ko-KR.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/summernote/summernote-lite.css">
<title>header</title>
<style>
#nav { margin-top : 0px;}

.content{
min-height : 649px;
}
</style>
</head>
<body>
   
      <c:if test="${!empty sessionScope.swalTitle }">
      <script>
         swal({
            icon : "${swalIcon}",
            title : "${swalTitle}",
            text : "${swalText}"
         });
      </script>

      <%-- 2) 한번 출력한 메세지를 Session에서 삭제 --%>
      <c:remove var="swalIcon" />
      <c:remove var="swalTitle" />
      <c:remove var="swalText" />
   </c:if>


   <div class="header_wrap">
      <div class="header_menu">
         <a id="logo_link" href="${contextPath}"><img id="logo" src="${contextPath}/resources/img/bluespringlogo.png"></a>
         <ul id="nav">
            <li>
               <div class="notice nav_title">공지사항</div>
               <div class="sub">
                  <ul>
                     <li><a href="${contextPath}/notice/list.do">정부정책</a></li>
                  </ul>
               </div>
            </li>
            <li>
               <div class="community nav_title">커뮤니티</div>
               <div class="sub">
                  <ul>
                     <li><a href="${contextPath}/board/list.do?cp=1">자유게시판</a></li>
                  </ul>
               </div>
            </li>
            <li>
               <div class="challengers nav_title">챌린지</div>
               <div class="sub">
                  <ul>
                     <li><a href="${contextPath}/challenge/list.do">챌린지</a></li>
                     <li><a href="${contextPath}/challengeCrtfd/list.do">인증게시판</a></li>
                  </ul>            
               </div>
            </li>
            <li>
               <div class="center nav_title">기관찾기</div>
               <div class="sub">
                  <ul>
                     <li><a href="${contextPath}/center/centerForm.do">센터/병원찾기</a></li>
                  </ul>
               </div>
            </li>
         </ul>
      </div>
   
   
   <c:choose>
      <c:when test="${empty sessionScope.loginMember}">
         <div class="login">
            <button type="button" id="header_login" onclick="location.href = '${contextPath}/member/login.do'">로그인</button>
                <button type="button" id="header_join" onclick="location.href = '${contextPath}/member/signup.do'">회원가입</button>
         </div>
      </c:when>
      <c:otherwise>
         <div class="login logging">
         <script>
            console.log('${loginMember.memberId}');
         </script>
            <ul>
         
      
            <c:if test="${!empty loginMember && loginMember.memberLevel == 'A'.charAt(0)}">
               <li>
                  <span><a href="${contextPath}/admin/adminMain.do">${loginMember.memberNickname}</a></span>
               </li>
            </c:if>
            
            
            <c:if test="${!empty loginMember && loginMember.memberLevel == 'M'.charAt(0)}">
               <li>
                  <span><a href="${contextPath}/mypage/main.do">${loginMember.memberNickname}</a></span>
               </li>
            </c:if>
       
                 
                <li><button type="button" id="header_logout" 
               onclick="location.href='${contextPath}/member/logout.do'">로그아웃</button></li>
            </ul>
         </div></c:otherwise>
            


     
   </c:choose>
      
   </div>

</body>

</html>