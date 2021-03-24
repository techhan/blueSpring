<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>

.sideMenu_area{
	width: 200px;
	margin : 0 0 0 20px;
	padding : 0;
	display : inline-block;
	float : left;
}

 
.sideMenu > ul{
	padding : 0;
	margin : 0;
	border-top : solid 1px #3C3735;
	overflow : hidden;
}

.sideMenu li {
	border-bottom : solid 1px #3C3735;
	position : relative;
	overflow: hidden;
}

.sideMenu a {
    padding: 1.1em 0;
    color: black;
    font: 400 1.125em ;
    text-align: center;
    text-transform: lowercase;
    
    display: block;
    position: relative;
    z-index: 1;
    transition : .35s ease color;
}

.sideMenu_list a:hover{
	color : rgb(249 155 67);
}





</style>

<div class="sideMenu_area">
	<div class="sideMenu">
		<ul>
			<li class="sideMenu_list">
				<a id="changeInfo" class="sideMenu_title" href="${contextPath}/mypage/myInfoChangePw.do">회원 정보 수정</a>
			</li>
			
			<li class="sideMenu_list">
				<a class="sideMenu_title" href="${contextPath}/mypage/myBoardList.do">작성한 게시글</a>
			</li>
			
			<li class="sideMenu_list">
				<a class="sideMenu_title" href="${contextPath}/mypage/myReplyList.do">작성한 댓글</a>
			</li>
			
			<li class="sideMenu_list">
				<a class="sideMenu_title" href="${contextPath}/mypage/allTimeChallenge.do">역대 챌린지</a>
			</li>
			
			<li class="sideMenu_list">
				<a class="sideMenu_title" href="${contextPath}/mypage/challengeCrtfd.do">인증 게시글</a>
			</li>
			
			<li class="sideMenu_list">
				<a class="sideMenu_title" href="${contextPath}/mypage/deleteAccount.do">회원 탈퇴</a>
			</li>
		</ul>
	</div>
</div>


