<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 참여중인 챌린지</title>

<link href="${contextPath}/resources/css/mypage/progressChallenge.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="container">
			<jsp:include page="../common/mypageSideMenu.jsp"></jsp:include>
			<div class="content">
				<div class="challenge_wrap">
				<h3>참여중인 챌린지</h3>
					<div class="challenge_area left">
						<div class="challenge_Info">
						<div class="cTitle">
							<h4>물 3L씩 마시기</h4>
							</div>
							<div class="challenge_thumbnail">
								<img src="${contextPath}/resources/img/test.jpeg" id="cThumbnail">
							</div>
						</div>
					</div>
					<div class="challenge_area right">
						<div class="cCertification">
						<div class="tableTitle">
						<h4>인증게시판</h4>
						</div>
							<table class="table" id="list-table">
								<thead>
									<tr>
										<th>번호</th>
										<th>제목</th>
										<th>작성자</th>
										<th>조회수</th>
										<th>작성일</th>
									</tr>
								</thead>

								<tbody>

									<!-- 조회된 목록이 없을 때   -->
									<!-- <tr>
                  <td colspan="5">존재하는 인증글이 없습니다</td>
               </tr> -->
									<tr>
										<td>112</td>
										<td>제목 출력</td>
										<td>작성자 출력</td>
										<td>5</td>
										<td>날짜 출력</td>
									</tr>
									<tr>
										<td>113</td>
										<td>제목 출력</td>
										<td>작성자 출력</td>
										<td>5</td>
										<td>날짜 출력</td>
									</tr>
									<tr>
										<td>114</td>
										<td>제목 출력</td>
										<td>작성자 출력</td>
										<td>5</td>
										<td>날짜 출력</td>
									</tr>
									<tr>
										<td>115</td>
										<td>제목 출력</td>
										<td>작성자 출력</td>
										<td>5</td>
										<td>날짜 출력</td>
									</tr>
									<tr>
										<td>116</td>
										<td>제목 출력</td>
										<td>작성자 출력</td>
										<td>5</td>
										<td>날짜 출력</td>
									</tr>

								</tbody>
							</table>
						</div>


						<div class="cProgress">
							<h4>달성률</h4>
							<div class="progress_area">
								<div class="progress2 progress-moved">
									<div class="progress-bar2"></div>
								</div>
							</div>
						</div>
						
						<div class="cDday">
							<h4>종료일</h4>
							<h4 id="Dday">2021-01-05</h4>
						</div>
						
						<div class="abandonment_area">
							<button type="button" id="abandonmentBtn">중도 포기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
</body>
</html>