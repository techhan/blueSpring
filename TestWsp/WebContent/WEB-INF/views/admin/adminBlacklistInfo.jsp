<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>블랙리스트 조회 페이지</title>
    <!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	
	<!-- Bootstrap core JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
	
    <style>
	.admin_content{ 
	    width: 70%;
	    height: 100%;
	    margin: auto;
	
	    overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	
	 }
	
	.admin_black_title{
	    margin: 3% 0 3% 0;
	    color:rgb(40, 62, 105);
	    
	}
	
	.admin_black_board{ 
	    width: 100%;
	    height: 100%;
	}
	
	.page_area{ margin-top: 2%; }
	
	.black_search{ text-align: center; }
	
	.pagination > li > a, .pagination > li > a:hover{ color: black; }
	
	#black_btn { 
	    background-color: #343a40;
	    color: snow;
	}
	
	#black_btn2 { 
		float: right;
	    background-color: #dc3545;
	    color: snow;
	    border : none;
	}
	</style>
</head>
<body>
    <div class="wrapper">
    	<jsp:include page="../common/header.jsp"></jsp:include>
    	
    	<jsp:include page="../common/adminMenu.jsp"></jsp:include>

        <div class="admin_content">
            <div class="admin_black_title">
                <h4>블랙리스트 조회</h4>
            </div>
            <div class="admin_black_board">
                <table class="table table-sm">
                    <thead class="thead-dark">
                      <tr>
                        <th scope="col"><input type="checkbox" id="ck_all"></th>
                        <th scope="col">블랙리스트여부</th>
                        <th scope="col">회원번호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">닉네임</th>
                        <th scope="col">이름</th>
                        <th scope="col">생년월일</th>
                        <th scope="col">성별</th>
                        <th scope="col">전화번호</th>
                        <th scope="col">가입일</th>
                        <th scope="col">탈퇴여부</th>
                        <th scope="col">회원등급</th>
                      </tr>
                    </thead>
                    <tbody>
                   	<c:choose>
	                    <c:when test="${empty bkList}">
	                        <tr>
	                            <td colspan="13">존재하는 블랙리스트 회원이 없습니다.</td>
	                        </tr>
	                    </c:when>
    
                    <c:otherwise>                      
                        <c:forEach var="member" items="${bkList}">
                            <tr>
                            	<td><input type="checkbox" name="selectClick" value="${member.memberNo}"></td>
                            	<td>${member.memberBlackList}</td>
		                        <td>${member.memberNo}</td>
		                        <th>${member.memberId}</th>
		                        <td>${member.memberNickname}</td>
		                        <td>${member.memberNm}</td>
		                        <td>${member.memberBirth}</td>
		                        <td>${member.memberGender}</td>
		                        <td>${member.memberPhone}</td>
		                        <td>${member.memberJoined}</td>
		                        <td>${member.memberScsnFl}</td>
		                        <td>${member.memberLevel}</td>
	          				</tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                    </tbody>
                  </table>
            </div>

            <%---------------------- Pagination ----------------------%>
			<%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
			<c:choose>
				<%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
				<c:when test="${!empty param.sk && !empty param.sv }">
					<c:url var="pageUrl" value="/adminSearch/blacklist.do"/>
					
					<%-- 쿼리스트링으로 사용할 내용을 변수에 저장 --%>
					<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
				</c:when>
				
				<%-- 검색을 하지 않았을 경우 --%>
				<c:otherwise>
					<c:url var="pageUrl" value="/admin/adminBlacklistInfo.do"/>		
				</c:otherwise>
			</c:choose>

			<!-- 화살표에 들어갈 주소를 변수로 생성 -->
			 
			<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
			<c:set var="lastPage" value="${pageUrl}?cp=${bpInfo.maxPage}${searchStr}"/>
			 
			 <fmt:parseNumber var="c1" value="${(bpInfo.currentPage - 1) / 10 }" integerOnly="true" />
			 <fmt:parseNumber var="prev" value="${ c1 * 10 }" integerOnly="true" />
			 <c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}" />
			 
			 <fmt:parseNumber var="c2" value="${(bpInfo.currentPage + 9) / 10 }" integerOnly="true" />
			 <fmt:parseNumber var="next" value="${ c2 * 10 + 1 }" integerOnly="true" />
			 <c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}" />



			<div class="page_area">
				<ul class="pagination justify-content-center">

					<%-- 현재 페이지가 10페이지 초과인 경우 --%>
					<c:if test="${bpInfo.currentPage > 10}">
						<li>
							<!-- 첫 페이지로 이동(<<) --> <a class="page-link" href="${firstPage}">&lt;&lt;</a>
						</li>

						<li>
							<!-- 이전 페이지로 이동 (<) --> <a class="page-link" href="${prevPage}">&lt;</a>
						</li>
					</c:if>

					<!-- 페이지 목록 -->
					<c:forEach var="page" begin="${bpInfo.startPage}"
						end="${bpInfo.endPage}">
						<c:choose>
							<c:when test="${bpInfo.currentPage == page }">
								<li><a class="page-link">${page}</a></li>
							</c:when>
							<c:otherwise>
								<li><a class="page-link"
									href="${pageUrl}?cp=${page}${searchStr}">${page}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<%-- 다음 페이지가 마지막 페이지 이하인 경우 --%>
					<c:if test="${next <= bpInfo.maxPage}">
						<li>
							<!-- 다음 페이지로 이동 (>) --> <a class="page-link" href="${nextPage}">&gt;</a>
						</li>
						<li>
							<!-- 마지막 페이지로 이동(>>) --> <a class="page-link" href="${lastPage}">&gt;&gt;</a>
						</li>

					</c:if>

				</ul>
			</div>


            <div class="black_search">
            <form action="${contextPath}/adminSearch/blacklist.do" method="GET">
            <select id="black_search" name="sk">
                <option value="bkNo">회원번호</option>
                <option value="bkId">아이디</option>
                <option value="bkNick">닉네임</option>
            </select>
            <input type="text" name="sv">
            <button type="button" id="black_btn">검색</button>
			</form>
			<button id="black_btn2">블랙리스트 삭제</button>
        </div>
    </div>
    <div style="clear: both;"></div>
    <jsp:include page="../common/footer.jsp"></jsp:include>
    </div>
    
    <script>
 	// 검색 내용이 있을 경우 검색창에 해당 내용을 작성해두는 기능
	(function(){
		var searchKey = "${param.sk}";
		var searchValue = "${param.sv}";
		$("select[name=sk] > option").each(function(index, item){
			if( $(item).val() == searchKey ){
				$(item).prop("selected", true);
			}
		});
		$("input[name=sv]").val(searchValue);
	})();
 	
    // 체크박스 전체 선택&해제
    $('#ck_all').click(function(){
         if($("#ck_all").prop("checked")){
            $("input[type=checkbox]").prop("checked",true); 
        }else{
            $("input[type=checkbox]").prop("checked",false); 
        }
    });
    
    // 삭제
    var clicks = new Array();
	$("#black_btn2").on("click", function() {			
		
        $("input:checkbox[name='selectClick']:checked").each(function(){
        	clicks.push($(this).val());       	
        });
        
	    if(confirm("정말 삭제하시겠습니까?")){
	    	$.ajaxSettings.traditional = true;
			$.ajax({
				url : "${contextPath}/adminDelete/blacklist.do",
				data : {"black" : clicks},
				type : "get",
	            success : function(result) {

	            	if(result > 0) {
	            		swal({"icon" : "success" , "title" : "블랙리스트 삭제 성공"})
	            		.then(function(){location.reload()});
	            	}
                                           
	             }, error : function(request, status, error) {
	                  alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
	              }
			});
		}
	});
	
	
	
	
	
	</script>
	
</body>
</html>