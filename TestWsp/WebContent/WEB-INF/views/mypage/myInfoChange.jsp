<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 회원 정보 수정</title>

<link href="${contextPath}/resources/css/mypage/myInfoChange.css" rel="stylesheet" type="text/css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style>



#postcode, #address1, #address2, #address3  {
/*	border : 1px solid #b8b9b9c2; */
	border-radius : 0;
}

#postcode:focus, #address1:focus, #address2:focus, #address3:focus {
	outline: none;
	border : solid 1px #283e69;
}

.row{
margin : 8px 0 8px;
}

#postcodify_search_button{
	width : 74px;
	height: 29px;
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
			<form action="${contextPath}/mypage/myInfoChangeAction.do" method="post" id="changeForm"  onsubmit="return memberUpdateValidate();">
				<div class="changeWrap">
					<h3>내 정보 수정</h3>
					<hr>
					<div class="group">
						<div class="text_area">
							<span>아이디</span>
						</div>
						<div class="input_area readonly_area">
							<input type="text" id="changeId" name="changeId" value="${loginMember.memberId}" readonly> 
						</div>
					</div>
					<hr>
					
					<div class="group">
						<div class="text_area">
							<label for="changeNickName"><span>닉네임</span></label>
						</div>
						<div class="input_area">
							<input type="text" id="changeNickName" name="changeNickName" value="${loginMember.memberNickname}" autocomplete="off" placeholder="2~20자 소문자, 한글, 숫자와 _, -만 사용"> 
						</div>
						<span class="error_area" id="nickNameMsg"></span>
					</div>
					<hr>
					
					<div class="group">
						<div class="text_area">
							<span>비밀번호</span>
						</div>
						<div class="input_area" style="border:0;">
							<button type="button" class="pwChangeBtn" id="pwBtn" 
								onclick="location.href='${contextPath}/mypage/changePw.do'">
								비밀번호 변경
							</button>
						</div>
						
					</div>
					<hr>

						<div class="group">
 							<span class="text_area"> 
									<label for="address" style="margin-bottom: 5px;">주소</label>
							</span> 
							<div class="row mb-3 form-row" style="display:flex;" >
							<div class="col-md-3 text_area" id="addr1_1">
								<label for="postcodify_search_button">우편번호</label>
							</div>
							<div class="col-md-3 post input_area" id="addr1_2" style="margin-left: 3px;">
								<input type="text" name="post" class="form-control" id="postcode"  autocomplete="off" value="${address[0]}">
							</div>
							<div class="col-md-3" id="addr1_3" >
																				
								<button type="button" class="btn btn-primary" id="postcodify_search_button" onclick="sample6_execDaumPostcode()"
									>검색</button> 
							</div>
						</div>
	
						<div class="row mb-3 form-row"  style="display:flex;">
							<div class="col-md-3 text_area">
								<label for="address1">도로명 주소</label>
							</div>
							<div class="col-md-9 input_area"  style="margin-left: 3px;">
								<input type="text" class="form-control postcodify_address" name="address1" id="address1"  autocomplete="off" value="${address[1]}" >
							</div>
						</div>
	
						<div class="row mb-3 form-row" style="display:flex;" >
							<div class="col-md-3 text_area">
								<label for="address2">상세주소</label>
							</div>
							<div class="col-md-9 input_area"  style="margin-left: 3px;">
								<input type="text" class="form-control postcodify_details" name="address2" id="address2"  autocomplete="off" value="${address[2]}">
							</div>
						</div> 
						<div class="row mb-3 form-row" style="display:flex;">
							<div class="col-md-3 text_area"  >
								<label for="address3"></label>
							</div>
							<div class="col-md-9 input_area" style="margin-left: 3px;">
								<input type="text" class="form-control postcodify_details" name="address3" id="address3" placeholder="참고항목" autocomplete="off">
							</div>
						</div> 
						<span class="error_area" id="addrMsg"></span>
					</div>	
						
						
						
						<hr>

						<div class="group">
							<div class="text_area">
								<span>이름</span>
							</div>
							<div class="input_area readonly_area">
								<input type="text" id="changeName" name="changeName" value="${loginMember.memberNm}" readonly>
							</div>
						</div>
						<hr>


						<div class="group">
							<div class="text_area">
								<span>이메일</span>
							</div>
							<div class="input_area readonly_area">
								<input type="text" id="changeEmail" name="changeEmail"  value="${loginMember.memberEmail}" readonly>
							</div>
						</div>
						<hr>

						<div class="group">
							<div class="text_area">
								<span>생년월일</span>
							</div>
							<div class="input_area readonly_area">
								<input type="text" id="changeBirth" name="changeBirth"  value="${loginMember.memberBirth}" readonly>
							</div>
						</div>
						<hr>

						<div class="group">
							<div class="text_area">
								<span>성별</span>
							</div>
							<div class="input_area readonly_area">
								<input type="text" id="changeGender" name="changeGender"  value="${gender}" readonly>
							</div>
						</div>
						<hr>

						<div class="group">
							<div class="text_area">
								<span>휴대전화</span>
							</div>
							<div class="input_area">
								<input type="text" id="changeTel" name="changeTel" placeholder='"-"포함 번호를 입력하세요.'  value="${loginMember.memberPhone}" autocomplete="off">
							</div>
							<span class="error_area" id="phoneMsg"></span>
						</div>
						<hr>
					</div>
					<button type="submit" class="btn submitBtn">완료</button>
					
			</form>
			
			</div>

		</div>
		<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script src="${contextPath}/resources/js/member_signUp.js"></script>
		<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
	
</body>
</html>