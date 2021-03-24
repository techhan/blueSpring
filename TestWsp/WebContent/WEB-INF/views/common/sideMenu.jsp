<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<style>
	* {
	font-family: 'Noto Sans KR', sans-serif;
	}
	
	.sideMenu_area{
		width: 200px;
		margin : 0 0 0 3px;
		padding : 0;
		display : inline-block;
		border : 1px solid rgb(166 167 169 / 89%);
		float : left;
	}
	
	.sideMenu {
		margin : auto;
		width : 90%;
		
	}
	
	.sideMenu > ul {
		margin : 0;
		padding : 0;
	}
	
	.sideMenu_list {
		background-color: rgb(243 243 243 / 76%);
	}
	
	.sideMenu > ul > li {
		text-align: center;
		padding : 5px 0 5px;
		margin : 8px 0 8px;
		cursor : pointer;
	}
	
	.sideMenu_title {
		text-decoration: none;
		color : black;
		margin : auto;
	}
	
	</style>
<div class="sideMenu_area">
	<div class="sideMenu">
		<ul>
			<li class="sideMenu_list">
				<div class="sideMenu_title_area">
					<a id="changeInfo" class="sideMenu_title" href="${contextPath}/mypage/myInfoPwCheck.do">회원 정보 수정</a>
				</div>
			</li>
			<li class="sideMenu_list">
				<div class="sideMenu_title_area">
					<a class="sideMenu_title" href="${contextPath}/mypage/myboardlist.do">작성한 게시글</a>
				</div>
			</li>
			<li class="sideMenu_list">
				<div class="sideMenu_title_area">
					<a class="sideMenu_title" href="${contextPath}/mypage/myreplylist.do">작성한 댓글</a>
				</div>
			</li>
			<li class="sideMenu_list">
				<div class="sideMenu_title_area">
					<a class="sideMenu_title" href="${contextPath}/mypage/progresschallenge.do">참여중인 챌린지</a>
				</div>
			</li>
			<li class="sideMenu_list">
				<div class="sideMenu_title_area">
					<a class="sideMenu_title" href="${contextPath}/mypage/alltimechallenge.do">역대 챌린지</a>
				</div>
			</li>
			<li class="sideMenu_list">
				<div class="sideMenu_title_area">
					<a class="sideMenu_title" href="#">인증게시글</a>
				</div>
			</li>
			<li class="sideMenu_list">
				<div class="sideMenu_title_area">
					<a class="sideMenu_title" href="#">회원 탈퇴</a>
				</div>
			</li>
		</ul>
	</div>
</div>