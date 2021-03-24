<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 마이페이지</title>

<link href="${contextPath}/resources/css/mypage/myPage.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>


<style>
.myInfoTable td > span {
	margin-left : 30px;
}

#cThumbnail_area  img {
	width : 190px;
	height : 200px;
	
}
.area{
	cursor: pointer;
}

#cTitle{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 210px;
    margin : auto;
}


.progress_area {
margin : auto;}

.progress {
	background-color: #d8d8d8;
	border-radius: 20px;
	position: relative;
	margin : auto;
	height: 30px;
	width: 260px;
	
}

.progress-done {
	background: linear-gradient(to left, #F2709C, #FF9472);
	box-shadow: 0 3px 3px -5px #F2709C, 0 2px 5px #F2709C;
	border-radius: 20px;
	color: #fff;
	display: flex;
	align-items: center;
	justify-content: center;
	height: 100%;
	width: 0;
	opacity: 0;
	transition: 1s ease 0.3s;
}

.myInfo_area {
	cursor: auto;
}

.myInfo_area button {
	cursor : pointer;
 }
 
 .chanllenge_in_progress_area > .back {
 	min-height: 207pxpx;
 }
 
#chanllenge_table {
 	margin : auto;
 }
 
 .area > h3 {
 	margin-top : 20px;
 }
 
 .list_wrapper {
 margin-top : 13px;
 }

</style>
</head>

<body>
	<c:set var="address" value="${fn:split(loginMember.memberAddr, ',')}" />
	
		<c:choose>
			<c:when test="${loginMember.memberGender == 'M'.charAt(0)}">
				<c:set var="gender" value="남자"/>
			</c:when>
			<c:otherwise>
				<c:if test="${loginMember.memberGender == 'F'.charAt(0)}">
					<c:set var="gender" value="여자"/>
				</c:if>
				<c:if test="${loginMember.memberGender == 'U'.charAt(0)}">
					<c:set var="gender" value="선택 안함"/>
				</c:if>
			</c:otherwise>
		</c:choose>


	<div class="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">

			<jsp:include page="../common/mypageSideMenu.jsp"></jsp:include>
			<div class="content">
				<div class="myInfo_area area">
					<h3>내 정보</h3>
					<div class="myInfo_back back">
						<table class="myInfoTable t_left">
							<tr>
								<th class="title">아이디</th>
								<td><span>${loginMember.memberId}</span></td>
							</tr>
							<tr>
								<th class="title">닉네임</th>
								<td><span>${loginMember.memberNickname}</span></td>
							</tr>
							<tr>
								<th class="title">이름</th>
								<td><span>${loginMember.memberNm}</span></td>
							</tr>
							<tr>
								<th class="title last">성별</th>
								<td><span>${gender}</span></td>
							</tr>
						</table>



						<table class="myInfoTable t_right">
							<tr>
								<th class="title">주소</th>
								<td><span>${address[1]}</span><br>
								<span>${address[2]}</span>	</td>
								
							</tr>
							<tr>
								<th class="title">이메일</th>
								<td><span>${loginMember.memberEmail}</span></td>
							</tr>
							<tr>
								<th class="title">생년월일</th>
								<td><span>${loginMember.memberBirth}</span></td>
							</tr>
							<tr>
								<th class="title last">휴대전화</th>
								<td><span>${loginMember.memberPhone}</span></td>
								<td>
										<button type="button" class="btn"
											onclick="location.href = '${contextPath}/mypage/myInfoChangePw.do'">정보
											수정</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<!-- // myInfo 끝 -->



				<!-- 챌린지 -->
				<div class="chanllenge_in_progress_area area">
					<h3>참여중인 챌린지</h3>
					<div class="back">
						<table id="chanllenge_table">
						<c:choose>
							<c:when test="${empty nc}">
								<tr>
									<td style="padding-top:80px;">
										참여중인 챌린지가 없습니다.
									</td>
								</tr>
							</c:when>
							<c:otherwise>
							
							<tr id="b-${nc.chlngNo}">
								<td rowspan="2">
									<!-- 챌린지 썸네일  -->
									<div id="cThumbnail_area">
										<c:set var="img"
											value="${contextPath}/resources/img/basicImg.JPG" />
										<c:if test="${thumbnail != null}">
											<c:if test="${nc.chlngNo == thumbnail.parentChNo}">
												<c:set var="img"
													value="${contextPath}/resources/uploadImages/challenge/${thumbnail.fileName}" />
											</c:if>
										</c:if>
										<img class="cThumbnail" src="${img}"></img>
									</div>
								</td>

								<!-- 챌린지 제목  -->
								<td class="cTitle_td">
									<h4 id="cTitle">${nc.chlngTitle}</h4>
								</td>
								
								
							<!-- 챌린지 달성률  -->
							<!-- <tr>-->
								<td class="progress_area">
									<h5>달성률</h5>
								<div class="progress">
									<div class="progress-done" id="progressV" data-done="50">
												
										</div>
									</div>
								</td>
								<td></td>
								<td></td>
							</tr>
							
							
							
								<!-- 인증게시글 -->
							<tr colspan="3" id="cboard">
									<td colspan="3" style="padding-left:40px;">${nc.chlngContent}</td>
							</tr>
							</c:otherwise>
						</c:choose>
							
						</table>
					</div>
				</div>


				<div class="list_wrapper">
					<!-- 내가 쓴 게시글 조회 -->
					<div class="myBoardlistAll area">
						<h3>내가 쓴 게시글</h3>
						<div class="list_area">
							<table id="board_list">
								<c:choose>
									<c:when test="${empty bList}">
									<tr  class="none">
										<td colspan="3">작성한 게시글이 없습니다.</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="board" items="${bList}">
											<tr id="b-${board.boardNo}">
												<th scope="row" width="65">[${board.categoryName}]</th>
												<td colspan="3">${board.boardTitle}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
							<a class="more" href="${contextPath}/mypage/myBoardList.do"><span>더보기</span></a>
					</div>



					<!-- 내가 쓴 댓글 조회 -->
					<div class="myReplylistAll area">
						<h3>내가 쓴 댓글</h3>
						<div class="list_area">
							<table id="comment_list">
								<c:choose>
									<c:when test="${empty cList}">
										<td colspan="3">작성한 댓글이 없습니다.</td>
									</c:when>
									<c:otherwise>
										<c:set var="idx" value="0"/>
										<c:forEach var="comment" items="${cList}">
											<tr id="b-${comment.parentBoardNo}">
												<th scope="row">${idx = idx+ 1}</th>
												<td  colspan="3" width="65">
													<c:set var="content" value="${comment.comContent}"/>
													<c:if test="${fn:indexOf(content, '<br>') != -1}" >
														<c:set var="content" value ="${fn:split(content,'<br>')[0]}"/>
													</c:if>
													${content}
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
							<a class="more" href="${contextPath}/mypage/myReplyList.do"><span>더보기</span></a>
					</div>


					<!-- 역대 챌린지 조회 -->
					<div class="allTimeChallenges_area area">
						<h3>역대 챌린지 조회</h3>
						<div class="list_area">
							<table id="allTimeChallenges_list">
								<c:choose>
									<c:when test="${empty acList}">
										<td colspan="3">참여한 챌린지가 없습니다.</td>
									</c:when>
									<c:otherwise>
										<c:forEach var="ac" items="${acList}">
											<tr id="b-${ac.chlngBoardNo}">
												<th scope="row">[${ac.chlngCateNm}]</th>
												<td  colspan="3" width="65">${ac.chlngBoardTitle}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
						<a class="more" href="${contextPath}/mypage/allTimeChallenge.do"><span>더보기</span></a>
					</div>
				</div>

			</div>

		</div>

		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
	
	
	<script>


	var memNo = ${loginMember.memberNo};
	var challengeNo =${nc.chlngNo};

	
 	$(document).ready(function(){
		$.ajax({
			url : "progressBar.do",
			data : {"challengeNo" : challengeNo,
					"memNo" : memNo},
			type : "post",
			success : function(result){
				if(result < 10 ){
					$("#progressV").attr("data-done", result);
				} else {
					$("#progressV").attr("data-done", result).text(result + '%')
				}
				const progress = document.querySelector('.progress-done');

				progress.style.width = progress.getAttribute('data-done') + '%';
				progress.style.opacity = 1;
			}
			
		}) 
	}); 
 
	
	
		// 자유 게시판 상세 조회
		$("#board_list tr > *").on("click", function(){
			var id = $(this).parent().attr("id");
			var boardNo = id.substring(id.lastIndexOf("-") + 1);
			location.href = "../board/view.do?cp=1&no="+boardNo;
		});
		
		$("#comment_list tr > *").on("click", function(){
			var id = $(this).parent().attr("id");
			var boardNo = id.substring(id.lastIndexOf("-") + 1);
			
			location.href = "../board/view.do?cp=1&no="+boardNo;
		});
		

		// 챌린지 인증 게시판 상세 조회
		$(".challengeCrtfd_table tr > *").on("click", function(){
			var id = $(this).parent().attr("id");
			var challengeCrtfdNo = id.substring(id.lastIndexOf("-") + 1);
			
			
		});
		
		
		$(".chanllenge_in_progress_area tr > *").on("click", function(){
			var id = $(this).parent().attr("id");
			var challengeCrtfdNo = id.substring(id.lastIndexOf("-") + 1);
			
			location.href = "../challenge/view.do?cp=1&no="+challengeCrtfdNo;
		});
	
		
		

	</script>

</body>
</html>