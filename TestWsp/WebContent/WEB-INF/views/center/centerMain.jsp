<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	
	h1 {
		text-align: center;
		text-shadow: 1px 1px 2px black;
	}

	#searchWrapper {
		min-height: 400px;		
	}

	#selectArea {
		border-image: linear-gradient(to right,lightgray 100%, lightgray 100%);
		border-image-slice: 1;
		border-style: solid;
		border-image-width: 5px;
		background: #f7f7f7;
		width: 10%;	
		padding: 15px;
	}
    
   #sidoSelect {
   	margin-bottom: 20px;
   }
    
	#gugunSelectArea {
		text-align: left;
		width: 80%;
		height: 70px;
	}
	
	#centerSelectArea {
		overflow-x: hidden;
		overflow-y: scroll;
		width: 100%;
		height: 800px;
		padding: 0;
	}


	#listMap {
		display: flex;
	}
	
	#centerListArea {
		border-image: linear-gradient(to right, lightgray 100%,  rgb(146, 161, 230)100%);
		border-image-slice: 1;
		border-style: solid;
		border-image-width: 5px;
		background: #f7f7f7;
		width: 25%;	
	}

	#mapArea {
		width: 65%;
		height: 800px;
	}
  
	label {
		width: 100px;
		display: block;
	}

	#centerList {

	}

	#centerList > li {
		width: 100%;
		border-bottom: 2px solid lightgray;
	}

    
</style>
<link rel="stylesheet" href="${contextPath}/resources/css/common/btnStyle.css">
</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
    
    <h1>기관찾기</h1>
		    
			<!-- 기관 검색 -->	
		
		<div id="listMap">
			<!-- 기관 목록 -->
			<div id ="selectArea">
				시/도 : <select name="sidoSelect" id="sidoSelect"></select>
				<button id="search-btn" class="btn-style1">찾기</button> 
					<div id="gugunSelectArea">
						
					</div>   
					<br>
			</div>	
			<div id="centerListArea">
				
				<div id="centerSelectArea">
						<ul id="centerList">
						
						</ul>
				</div>  
			</div>	

			<!-- 지도 -->
			<div id="mapArea">

			</div>
		</div>
			
			

<!-- 	</div> -->


     
        
   <jsp:include page="../common/footer.jsp"></jsp:include>

	<script>	
		var userAddress = "${loginMember.memberAddr}";
		var sidoArea = ["시/도 선택","서울특별시","인천광역시","대전광역시","광주광역시","대구광역시","울산광역시","부산광역시","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도"];
			
		var gugun1 = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"];
		var gugun2 = ["계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"];
		var gugun3 = ["대덕구","동구","서구","유성구","중구"];
		var gugun4 = ["광산구","남구","동구","북구","서구"];
		var gugun5 = ["남구","달서구","동구","북구","서구","수성구","중구","달성군"];
		var gugun6 = ["남구","동구","북구","중구","울주군"];
		var gugun7 = ["강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"];
		var gugun8 = ["고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시","가평군","양평군","여주군","연천군"];
		var gugun9 = ["강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군","양구군","양양군","영월군","인제군","정선군","철원군","평창군","홍천군","화천군","횡성군"];
		var gugun10 = ["제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군","음성군","증평군","진천군","청원군"];
		var gugun11 = ["계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군","당진군","부여군","서천군","연기군","예산군","청양군","태안군","홍성군"];
		var gugun12 = ["군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군","부안군","순창군","완주군","임실군","장수군","진안군"];
		var gugun13 = ["광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군","구례군","담양군","무안군","보성군","신안군","영광군","영암군","완도군","장성군","장흥군","진도군","함평군","해남군","화순군"];
		var gugun14 = ["경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시","영천시","포항시","고령군","군위군","봉화군","성주군","영덕군","영양군","예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군"];
		var gugun15 = ["거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시","창원시","통영시","거창군","고성군","남해군","산청군","의령군","창녕군","하동군","함안군","함양군","합천군"];
		var gugun16 = ["서귀포시","제주시","남제주군","북제주군"];
		
		var address = "${loginMember.memberAddr}"; // 주소 담을 변수
		var centerNameAry = new Array(); // 센터 명 배열에 담기
		var centerName = ""; // 센터명 담을 변수
		var flagNum = 0; //
		var flagNum2 = 0; //
		var positionX = new Array(); // x좌표 담기
		var positionY = new Array(); // y좌표 담기
		
		var searchOn = 0;

		
		// 최초 지도 표시
		$(document).ready(function(){
			var mapContainer = document.getElementById('mapArea'), // 지도를 표시할 div 
		    mapOption = { 
		        center: new kakao.maps.LatLng(37.56811677175753, 126.98300361156419), // 지도의 중심좌표
		        level: 3 // 지도의 확대 레벨
		    };
		
			// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
			var map = new kakao.maps.Map(mapContainer, mapOption);			
		});



		// 찾기 버튼 클릭 시
		$("#search-btn").on("click", function() {
			if($(':checkbox[name="gugunSelect"]:checked').length < 1){
				alert('지역을 선택해주세요');                        
				event.preventDefault();
			} 
			else {
				var sido = $("#sidoSelect option:selected").val();
			var gugun = new Array();

			$("input:checkbox[name='gugunSelect']:checked").each(function(){
				gugun.push($(this).val());
			}) 		
			
 			$.ajaxSettings.traditional = true;
			
			$.ajax({
				url : "${contextPath}/center/selectCenterList.do", 
				data : {"sido" : sido, 
								"gugun" : gugun}, 
				type : "get",
				dataType : "JSON",
				success : function(cList) {
					$("#centerList").html("");
					address = "";
					addressAry = new Array();
					
					$.each(cList, function(index, item){
						var li = $("<li>").addClass("center-row");
						var placeSearchBtn = $("<button>").text("위치찾기").addClass("placeSearchBtn btn-style1").attr("onclick", "placeSearch(this)");
						
						var div = $("<div>");
						var br = $("<br>");					
						
						var centerTitle = $("<h2>").addClass("centerName").text(item.centerName);
						var centerCla = $("<p>").addClass("centerCla").text(item.centerCla);
						var centerName = $("<p>").addClass("centerName").text(item.centerName);
						var centerPhone = $("<p>").addClass("centerPhone").text(item.centerTel);
						var centerUrl = $("<a>").addClass("centerUrl").attr("href", "http://"+item.centerUrl).text(item.centerUrl);
						var centerAddr = $("<p>").addClass("centerAddr").text(item.centerAddr);
						var centerAddr2 = $("<p>").addClass("centerAddr2").text(item.centerAddrDtl);
						// 
						console.log(item.centerUrl);
						
						div.append(centerTitle).append(centerCla).append(centerPhone).append(centerUrl).append(centerAddr).append(centerAddr2).append(placeSearchBtn);
						
						li.append(div);
						
						$("#centerList").append(li);
						
						address = item.centerAddr;						
						centerName = item.centerName;
						searchAddress(centerName);		

						flagNum = flagNum + 1;						
					
					});
 						
				}, error : function(request, status, error) {
			  	   alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
			 	}	
			}); 		
		}
			
		});
		
		
		// 위치찾기 버튼 클릭 시
		function placeSearch(ele){
				var thisAddr = $(ele).prev().prev().text()
				centerName = $(ele).prev().prev().prev().prev().prev().prev().prev().text();
				placeSearchMarker(thisAddr);
		};

			
		

		
		// 구/군 선택 시 위도 경도 배열에 담기
		function searchAddress(centerName) {
			centerNameAry = new Array();
			searchOn = 0;
			positionX = new Array(); // x좌표 담기
			positionY = new Array(); // y좌표 담기
			// =================================== 위도 경도 =====================================
			$.ajax({
         url:'https://dapi.kakao.com/v2/local/search/address.json?query='+encodeURIComponent(address),
         type:'GET',
         headers: {'Authorization' : 'KakaoAK 95bd3f68d02962b41f5efde3edf2ad26'},
			   success:function(data){
				   	 try {
					   	 flagNum2 = flagNum2 + 1;
					       positionY.push(data['documents'][0]['y']); 
				  			 positionX.push(data['documents'][0]['x']);
						   	 centerNameAry.push(centerName);
					       if(flagNum2 == flagNum) {
					    	   pickMarkers();
					       }			   		 
				   	 } catch(error) {
				   		 
				   	 }

			   },			   
			   error : function(e){
			       console.log(e);
			   },						   				
			});									
		}
		
		// 위치 찾기 시 위도 경도 담기
		function placeSearchMarker(thisAddr) {
			searchOn = 0;
			$.ajax({
         url:'https://dapi.kakao.com/v2/local/search/address.json?query='+encodeURIComponent(thisAddr),
         type:'GET',
         headers: {'Authorization' : 'KakaoAK 95bd3f68d02962b41f5efde3edf2ad26'},
			   success:function(data){
				   	 searchOn = 1;
				   	 var x = data['documents'][0]['x'];
				   	 var y = data['documents'][0]['y']; 
				   	 pickMarkers(x, y);
			   },			   
			   error : function(e){
			       console.log(e);
			   },						   				
			});									
		}      
		
		// =================================== 지도 =====================================	
		function pickMarkers(x, y) {
			var container = document.getElementById('mapArea'); //지도를 담을 영역의 DOM 레퍼런스
			var options = { //지도를 생성할 때 필요한 기본 옵션  
				center: new kakao.maps.LatLng(positionY[0], positionX[0]), //지도의 중심좌표.
				level: 3 //지도의 레벨(확대, 축소 정도)
			};
			
			var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴		
			
	 		var points = [];
			
			for(var i = 0; i < positionY.length; ++i) {
				points[i] = new kakao.maps.LatLng(positionY[i], positionX[i]);
			}
			
			// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
			var bounds = new kakao.maps.LatLngBounds();    

			var i, marker;
			for (i = 0; i < points.length; i++) {
			    // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
			    marker =     new kakao.maps.Marker({ position : points[i] });
			    marker.setMap(map);
			    
			    // 마커마다 윈도우 표시하기 //////////////////////////
			    var iwContent = '<div style="padding:5px;">' + centerNameAry[i] + '</div>',
			    iwPosition = new kakao.maps.LatLng(positionY[i], positionX[i]);
		    
			    var infowindow = new kakao.maps.InfoWindow({
			        position : iwPosition, 
			        content : iwContent 
			    });
			      
			    // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
			    infowindow.open(map, marker); 
			    ////////////////////////////////////////////
			    
			    
			    // LatLngBounds 객체에 좌표를 추가합니다
			    bounds.extend(points[i]);

			}

			function setBounds() {
			    // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
			    // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
			    map.setBounds(bounds);
			}
			
			// 만약 위치 찾기 버튼 클릭하면
				if(searchOn == 1) {
										
				var moveLatLon = new kakao.maps.LatLng(y, x);
				    
			    // 지도 중심을 부드럽게 이동시킵니다
			    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
				  map.panTo(moveLatLon);  
				}
		     
		}	
		
		

	// 초기 select 설정
	$(function(){  	  
		$("#sidoSelect").each(function() {
				for(var i = 0; i < sidoArea.length; ++i) {
					$(this).append("<option value='"+sidoArea[i]+"'>"+sidoArea[i]+"</option>");
				}		
			});	  	 
	
	});
	
	// 시 도 체크시
		$("#sidoSelect").on("change", function(){
//			console.log($("#sidoSelect option:selected").val());
			$("#gugunSelectArea").empty();
			
			var sidoIndex = $("#sidoSelect option").index($("#sidoSelect option:selected"));
			
//			console.log(sidoIndex);

			// 서울
			if(sidoIndex == 1) {
				for(var j = 0; j < gugun1.length; ++j) {  				
					$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun1[j] + ">" + gugun1[j] +"</label>");	 
					// if(j > 1 && j % 10 == 0) {
					// 	$("#gugunSelectArea").append("<br>");
					// }	  			
				}  
				  		
			}
			
			// 인천
			if(sidoIndex == 2) {
				for(var j = 0; j < gugun2.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	  */ 				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun2[j] + ">" + gugun2[j] +"</label>");	  			
					}    		
			}
			
			// 대전
			if(sidoIndex == 3) {
				for(var j = 0; j < gugun3.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun3[j] + ">" + gugun3[j] +"</label>");	  			
					}    		
			}
			
			// 광주
			if(sidoIndex == 4) {
				for(var j = 0; j < gugun4.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun4[j] + ">" + gugun4[j] +"</label>");	  			
					}    		
			}
			
			// 대구
			if(sidoIndex == 5) {
				for(var j = 0; j < gugun5.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	  */ 				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun5[j] + ">" + gugun5[j] +"</label>");	  			
					}    		
			}
			
			// 울산
			if(sidoIndex == 6) {
				for(var j = 0; j < gugun6.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	  */ 				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun6[j] + ">" + gugun6[j] +"</label>");	  			
					}    		
			}
			
			
			// 부산
			if(sidoIndex == 7) {
				for(var j = 0; j < gugun7.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun7[j] + ">" + gugun7[j] +"</label>");	  			
					}    		
			}
			
			
			// 경기도
			if(sidoIndex == 8) {
				for(var j = 0; j < gugun8.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	  */ 				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun8[j] + ">" + gugun8[j] +"</label>");	  			
					}    		
			}
			
			
			// 강원도
			if(sidoIndex == 9) {
				for(var j = 0; j < gugun9.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun9[j] + ">" + gugun9[j] +"</label>");	  			
					}    		
			}
			
			
			// 충청북도
			if(sidoIndex == 10) {
				for(var j = 0; j < gugun10.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun10[j] + ">" + gugun10[j] +"</label>");	  			
					}    		
			}
			
			
			// 충청남도
			if(sidoIndex == 11) {
				for(var j = 0; j < gugun11.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	  */ 				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun11[j] + ">" + gugun11[j] +"</label>");	  			
					}    		
			}
			
			
			// 전라북도
			if(sidoIndex == 12) {
				for(var j = 0; j < gugun12.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun12[j] + ">" + gugun12[j] +"</label>");	  			
					}    		
			}
			
			
			// 전라남도
			if(sidoIndex == 13) {
				for(var j = 0; j < gugun13.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun13[j] + ">" + gugun13[j] +"</label>");	  			
					}    		
			}
			
			
			// 경상북도
			if(sidoIndex == 14) {
				for(var j = 0; j < gugun14.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun14[j] + ">" + gugun14[j] +"</label>");	  			
					}    		
			}
			
			
			// 경상남도
			if(sidoIndex == 15) {
				for(var j = 0; j < gugun15.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				} */	  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun15[j] + ">" + gugun15[j] +"</label>");	  			
					}    		
			}
			
			
			// 제주도
			if(sidoIndex == 16) {
				for(var j = 0; j < gugun16.length; ++j) {
	/* 	 				if(j % 2 == 0) {
							$("#gugunSelectArea").append("<br>");
				}	 */  				
				$("#gugunSelectArea").append("<label><input type='checkbox' name='gugunSelect' class='gugunSelect' value=" + gugun16[j] + ">" + gugun16[j] +"</label>");	  			
					}    		
			}
			
 			
	
		});
    
    // 시 도 체크시 
	</script>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=78d99faa53b972374c1a1fb343c1f1a3&libraries=services"></script>
</body>
</html>