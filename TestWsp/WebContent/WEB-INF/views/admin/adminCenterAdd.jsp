<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸른봄: 센터 등록</title>

<link href="${contextPath}/resources/css/member/signUp.css" rel="stylesheet" type="text/css">


<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap" rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
#postcode, #address1, #address2, #address3  {
	border : 1px solid #b8b9b9c2;
	border-radius : 0;
}

#postcode:focus, #address1:focus, #address2:focus, #address3:focus {
	outline: none;
	border : solid 1px #283e69;
}
</style>
</head>
<body>
	<div id="wrap">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<jsp:include page="../common/login_header.jsp"></jsp:include>
		
		
		<div id="container">
			<form id="centerAdd" method="post" action="centerComplete.do" name="centerAdd" onsubmit="return validate();">
				<div id="content">
					<div id="signUp_form">
						<!-- 분류 -->
						<div class="row_group">
							<span class="signUp_text">
								<label for="cla" style="margin-bottom: 5px;">분류</label>
							</span>
							<span class="sign_input input_id">
								<input type="text" id="cla" name="cla" class="signUp_int" maxlength="20" autocomplete="off" required>
							</span>
							<span class="error_area" id="claMsg"></span>
						</div>
						
						<!-- 기관명  -->
						<div class="row_group">
							<span class="signUp_text"> 
								<label for="centerName" style="margin-bottom: 5px;">기관명</label>
							</span> 
							<span class="sign_input input_centerName"> 
							<input type="text" id="centerName" name="centerName" class="signUp_int" maxlength="30" autocomplete="off" required>
							</span> 
							<span class="error_area" id="centerNameMsg"></span>
						</div>
						
						
						<!-- 지역 -->
						<div class="row_group">
							<span class="signUp_text"> 
								<label for="area" style="margin-bottom: 5px;">지역</label>
							</span> 
							<span class="sign_input input_name"> 
							<select name="sido1" id="sido1"></select>
							<select name="gugun1" id="gugun1"></select>
							</span> 
							<span class="error_area" id="areaMsg"></span>
						</div>
						
	
						<!-- 주소 -->
						<div class="row_group">
 							<span class="signUp_text"> 
									<label for="address" style="margin-bottom: 5px;">주소</label>
							</span> 
												<div class="row mb-3 form-row">
							<div class="col-md-3" id="addr1_1">
								<label for="postcodify_search_button">우편번호</label>
							</div>
							<div class="col-md-3" id="addr1_2">
								<input type="text" name="post" class="form-control" id="postcode"  autocomplete="off" required>
							</div>
							<div class="col-md-3" id="addr1_3">
																				
								<button type="button" class="btn btn-primary" id="postcodify_search_button" onclick="sample6_execDaumPostcode()"
									>검색</button> 
							</div>
						</div>
	
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<label for="address1">도로명 주소</label>
							</div>
							<div class="col-md-9">
								<input type="text" class="form-control postcodify_address" name="address1" id="address1"  autocomplete="off" required>
							</div>
						</div>
	
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<label for="address2">상세주소</label>
							</div>
							<div class="col-md-9">
								<input type="text" class="form-control postcodify_details" name="address2" id="address2"  autocomplete="off" required>
							</div>
						</div> 
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<label for="address3"></label>
							</div>
							<div class="col-md-9">
								<input type="text" class="form-control postcodify_details" name="address3" id="address3" placeholder="참고항목" autocomplete="off">
							</div>
						</div> 
						
						<span class="error_area" id="addressMsg"></span> 
					</div>	
				
					<!-- 전화번호  -->
					<div class="row_group">
						<span class="signUp_text"> 
							<label for="phone" style="margin-bottom: 5px;">전화번호</label>
						</span> 
						<span class="sign_input input_phone"> 
						<input type="tel" id="phone" name="phone" class="signUp_int" maxlength="40" 
							autocomplete="off"  placeholder='"-"를 포함해서 입력해주세요.' required>
						</span> 
						<span class="error_area" id="phoneMsg"></span>
					</div>
					
					<!-- 홈페이지  -->
					<div class="row_group">
						<span class="signUp_text"> 
							<label for="homepage" style="margin-bottom: 5px;">홈페이지</label>
						</span> 
						<span class="sign_input input_homepage"> 
						<input type="text" id="homepage" name="homepage" class="signUp_int" maxlength="40" 
							autocomplete="off">
						</span> 
						<span class="error_area" id="homepageMsg"></span>
					</div>
					
					<!-- 등록하기 버튼 -->
					<div class="btn_area">
						<button type="submit" id="signBtn" class="btn">등록하기</button>
					</div>
					</div>
				</div>
			</form>

			<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script src="${contextPath}/resources/js/admin_center.js"></script>
		</div>
	
	<jsp:include page="../common/footer.jsp"></jsp:include>
	</div>
	
	
<script>
$('document').ready(function () {
    var area0 = ["시/도 선택", "서울특별시", "인천광역시", "대전광역시", "광주광역시", "대구광역시", "울산광역시", "부산광역시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도"];
    var area1 = ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"];
    var area2 = ["계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군"];
    var area3 = ["대덕구", "동구", "서구", "유성구", "중구"];
    var area4 = ["광산구", "남구", "동구", "북구", "서구"];
    var area5 = ["남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군"];
    var area6 = ["남구", "동구", "북구", "중구", "울주군"];
    var area7 = ["강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"];
    var area8 = ["고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시", "가평군", "양평군", "여주군", "연천군"];
    var area9 = ["강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", "양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군", "횡성군"];
    var area10 = ["제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "증평군", "진천군", "청원군"];
    var area11 = ["계룡시", "공주시", "논산시", "보령시", "서산시", "아산시", "천안시", "금산군", "당진군", "부여군", "서천군", "연기군", "예산군", "청양군", "태안군", "홍성군"];
    var area12 = ["군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군", "부안군", "순창군", "완주군", "임실군", "장수군", "진안군"];
    var area13 = ["광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", "구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"];
    var area14 = ["경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", "영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군"];
    var area15 = ["거제시", "김해시", "마산시", "밀양시", "사천시", "양산시", "진주시", "진해시", "창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군", "함안군", "함양군", "합천군"];
    var area16 = ["서귀포시", "제주시", "남제주군", "북제주군"];


    // 시/도 선택 박스 초기화

    $("select[name^=sido]").each(function () {
        $selsido = $(this);
        $.each(eval(area0), function () {
            $selsido.append("<option value='" + this + "'>" + this + "</option>");
        });
        $selsido.next().append("<option value=''>구/군 선택</option>");
    });

    // 시/도 선택시 구/군 설정

    $("select[name^=sido]").change(function () {
        var area = "area" + $("option", $(this)).index($("option:selected", $(this))); // 선택지역의 구군 Array
        var $gugun = $(this).next(); // 선택영역 군구 객체
        $("option", $gugun).remove(); // 구군 초기화

        if (area == "area0")
            $gugun.append("<option value=''>구/군 선택</option>");
        else {
            $.each(eval(area), function () {
                $gugun.append("<option value='" + this + "'>" + this + "</option>");
            });
        }
    });

});
</script>
	
</body>
</html>