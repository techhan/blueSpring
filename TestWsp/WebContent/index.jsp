<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>푸른봄에 오신 것을 환영합니다.</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="resources/css/main.css" type="text/css">
    
    <!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	
	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>

</head>
<body>
	<div class="wrapper">
		<jsp:include page="WEB-INF/views/common/header.jsp"></jsp:include>

		<div class="title">
			<img id="mainImg" src="resources/img/mainImg.png">
			<form action="${contextPath}/mainSearch.do" method="get" name="search_input">
				<input type="text" id="search_input" class="input" name="sv" placeholder="관심있는 내용을 Enter로 검색해 보세요!" autocomplete="off">
			</form>
		</div>
		<div class="content">
			<div class="notice">
				<div class="board_item">
					<div class="board_title">정부정책</div>
					<div class="board_content">
						<table class="table table-sm table-borderless notice_table">
						  	<tbody>
								<c:choose>
									<c:when test="${empty nList}">
										<tr>
											<td colspan="4">존재하는 게시글이 없습니다.</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach var="notice" items="${nList}">
											<tr>
												<th scope="row" width="75">${notice.noticeNo}</th>
												<td colspan="3">${notice.noticeTitle}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="board_more">
						<a href="${contextPath}/notice/list.do">더보기</a>
					</div>
				</div>
				<div class="board_item">
					<div class="board_title">자유게시판</div>
					<div class="board_content">
						<table class="table table-sm table-borderless board_table">
						  	<tbody>
								<c:choose>
									<c:when test="${empty bList}">
										<tr>
											<td colspan="4\">존재하는 게시글이 없습니다.</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach var="board" items="${bList}">
											<tr id="b-${board.boardNo}">
												<th scope="row" width="75">[${board.categoryName}]</th>
												<td colspan="3">${board.boardTitle}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="board_more">
						<a href="${contextPath}/board/list.do">더보기</a>
					</div>
				</div>
				<div class="board_item">
					<div class="board_title">챌린지목록</div>
					<div class="board_content">
						<table class="table table-sm table-borderless challenge_table">
						  	<tbody>
								<c:choose>
									<c:when test="${empty cList}">
										<tr>
											<td colspan="4">존재하는 게시글이 없습니다.</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach var="challenge" items="${cList}">
											<tr id="b-${challenge.chlngNo}">
												<th scope="row" width="75">[${challenge.chlngCateNm}]</th>
												<td colspan="3">${challenge.chlngTitle}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="board_more">
						<a href="${contextPath}/challenge/list.do">더보기</a>
					</div>
				</div>
				<div class="board_item">
					<div class="board_title">인증게시판</div>
					<div class="board_content">
						<table class="table table-sm table-borderless challengeCrtfd_table">
						  	<tbody>
								<c:choose>
									<c:when test="${empty crtList}">
										<tr>
											<td colspan="4">존재하는 게시글이 없습니다.</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach var="challengeCrtfd" items="${crtList}">
											<tr id="b-${challengeCrtfd.chlngBoardNo}">
												<th scope="row" width="75">[${challengeCrtfd.chlngCateNm}]</th>
												<td colspan="3">${challengeCrtfd.chlngBoardTitle}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="board_more">
						<a href="${contextPath}/challengeCrtfd/list.do">더보기</a>
					</div>
				</div>
			</div>
		</div>
		<div style="clear: both;"></div>
		<jsp:include page="WEB-INF/views/common/footer.jsp"></jsp:include>
	</div>
	
	
	<script>
		// 정부 정책 상세 조회
		$(".notice_table tr > *").on("click", function(){
			var noticeNo = $(this).parent().children().eq(0).text();
			
			location.href = "notice/view.do?cp=1&no="+noticeNo;
		});
	
		// 자유 게시판 상세 조회
		$(".board_table tr > *").on("click", function(){
			var id = $(this).parent().attr("id");
			var boardNo = id.substring(id.lastIndexOf("-") + 1);
			
			location.href = "board/view.do?cp=1&no="+boardNo;
		});
		
		// 챌린지 게시판 상세 조회
		$(".challenge_table tr > *").on("click", function(){
			var id = $(this).parent().attr("id");
			var challengeNo = id.substring(id.lastIndexOf("-") + 1);
			
			location.href = "challenge/view.do?cp=1&no="+challengeNo;
		});
		
		// 챌린지 인증 게시판 상세 조회
		$(".challengeCrtfd_table tr > *").on("click", function(){
			var id = $(this).parent().attr("id");
			var challengeCrtfdNo = id.substring(id.lastIndexOf("-") + 1);
			
			location.href = "challengeCrtfd/view.do?cp=1&no="+challengeCrtfdNo;
		});
	
	</script>
	
	
</body>
</html>