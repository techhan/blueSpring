# ⭐️Portfolio - blueSpring

<!-- contents -->
<details open="open">
  <summary>Contents</summary>
  <ol>
    <li>
      <a href="#개요">개요</a>
    </li>
    <li>
      <a href="#내용">내용</a>
    </li>
    <li><a href="#구현-기능">구현 기능</a>
      <ul>
        <li><a href="#전문가-등록-신청-목록-조회">전문가 등록 신청 목록 조회</a></li>
        <li><a href="#전문가-등록-신청-상세-조회">전문가 등록 신청 상세 조회</a></li>
        <li><a href="#mypage">전문가 마이페이지 수정, 리뷰 신고</a></li>
        <li><a href="#전문가-정보-상세-조회">전문가 정보 상세 조회</a></li>
        <li><a href="#review">수강 리뷰 작성, 수정, 삭제</a></li>
      </ul>
    </li>
  </ol>
</details>

------------

# 📝개요

* 프로젝트 명 : 푸른봄(blueSpring)

* 일정 : 2020년 12월 02일 ~ 2021년 01월 25일

* 개발 목적 : 코로나 장기화로 인한 젊은 청년들의 자살률이 급격히 증가하고 있다. 코로나 우울(블루)과 관련된 각종 정보 제공과 코로나 블루를 해소할 수 있도록 취미를 공유하는 커뮤니티 등을 기획했다.

* 개발 환경
  - O/S : Windows 10
  - Server : Apache-tomcat-8.5.61
  - Java EE IDE : Eclipse ( version 2020-06 (4.16.0) )
  - Database : Oracle SQL Developer ( version 20.2.0 )
  - Programming Language : JAVA, HTML, CSS, JavaScript, JSP, SQL
  - Client Framework : jQuery 3.5.1
  - API : kakao map API, Google SMTP API
  - Version management : Git

------------

# 📝내용

* 팀원별 역할
  - 공통 : 기획, 요구사항 정의, 와이어 프레임, DB설계
  - 강성혁 : 자유게시판, 기관찾기
  - 이진선 : 공지사항, 챌린지, 인증게시판
  - 이솔이 : 메인, 관리자
  - 이한솔 : 로그인, 회원가입, 마이페이지

* 구현 기능
  - 회원가입, 탈퇴
  - 로그인
  - 정보 수정
  - 아이디, 비밀번호 찾기
  - 마이페이지
    - 내가 쓴 글, 댓글 조회
    - 참여중인 챌린지 조회
    - 역대 챌린지 조회


* DB 설계 <br>
![DB](https://user-images.githubusercontent.com/70805241/112722494-49e61000-8f4d-11eb-8631-f4dedb9d06b8.png)


<br><br>

------------

# 📝구현 기능

## 로그인

![로그이인](https://user-images.githubusercontent.com/70805241/112722479-2d49d800-8f4d-11eb-8036-ef984f245cea.png)  <br><br>

* 구현 기능 설명
    - 아이디 저장을 누른 후 로그인 시 Cookie 기능으로 추후에 사용자의 아이디가 자동으로 입력된다.
    - 블랙리스트 회원일 경우 '접근이 불가능한 계정입니다'라는 모달창이 띄워지며 메인으로 이동한다.
    - 올바르지 않은 아이디와 비밀번호 입력 시 '로그인 실패' 모달창이 띄워진다.
    - 아이디 찾기, 비밀번호 찾기, 회원가입을 클릭하면 해당 화면으로 이동한다.

<br>
---------------------
<br>

## 아이디, 비밀번호 찾기

- 아이디 찾기 <br> ![아이디찾기](https://user-images.githubusercontent.com/70805241/112723261-ef4eb300-8f50-11eb-8f00-a174c12a82d0.png)
![아이디찾기완료](https://user-images.githubusercontent.com/70805241/112723263-efe74980-8f50-11eb-8349-21b4f44c08ec.png)

<br>

* 구현 기능 설명
    - 이름과 이메일 주소를 이용해 DB 존재 여부 조회 후 존재하면 두 번째 페이지로 이동, 실패하면 '아이디 찾기 실패' 모달창이 띄워진다.

<br>

- 비밀번호 찾기 <br> ![비밀번호찾기](https://user-images.githubusercontent.com/70805241/112723259-eeb61c80-8f50-11eb-91b1-64dceb5ef843.png)
![비밀번호찾기완료(수정)](https://user-images.githubusercontent.com/70805241/112723260-ef4eb300-8f50-11eb-88a9-753fc7cbf49e.png)


<br>

* 구현 기능 설명
    - 아이디와 이메일 주소 입력 후 인증번호 받기 버튼을 누르면 인증 번호가 발송된다.
    - 유효한 인증번호를 입력하면 새로운 비밀번호를 설정할 수 있는 페이지로 이동한다.
    - 비밀번호 유효성 검사를 거친 후 새로운 비밀번호가 설정된다.

<br>

------------------

<br>

## 회원가입

![회원가입1](https://user-images.githubusercontent.com/70805241/112723252-ecec5900-8f50-11eb-834c-d97b4a888e68.png)
![회원가입2](https://user-images.githubusercontent.com/70805241/112723253-ecec5900-8f50-11eb-83ed-c181691ff74b.png)

<br>

* 구현 기능 설명
    - 각 항목에 대해 유효성 검사와 중복 검사를 실시하며 유효성 검사에 부적합 시 input 태그 테두리가 빨간색으로 변하고, 유효성 검사 메세지가 출력된다.
    - 유효성 검사에 적합할 시 input 태그 테두리가 파란색으로 변경된다.
    - 다음 우편번호 API를 이용해 유효한 주소만을 입력받는다.
    - 이메일 입력 후 '인증번호 받기' 버튼을 누르면 인증 번호가 발송된다.

<br>

------------------

<br>


## 마이페이지 메인

![마이페이지메인](https://user-images.githubusercontent.com/70805241/112723258-ee1d8600-8f50-11eb-8a90-6abeed7d175f.png) <br>

* 구현 기능 설명
    - 내 정보 부분에는 Session에 저장되어 있던 회원들의 정보가 출력된다.
    - 정보 수정 버튼을 누르면 본인 확인을 위해 비밀번호를 입력하는 페이지로 이동한다.
    - 참여중인 챌린지에는 가장 최근 참여한 챌린지가 조회되며 썸네일과 제목, 달성률, 간략한 내용이 출력된다. 챌린지 썸네일을 클릭하면 해당 챌린지의 상세 조회 페이지로 이동한다.
    - 내가 쓴 게시글, 댓글, 인증 게시글이 최대 5개씩 최신순으로 조회되며 제목을 클릭하면 해당 게시글의 상세 페이지로 이동한다.

<br>

-----------

<br>

## 내 정보 수정

![정보수정1](https://user-images.githubusercontent.com/70805241/112723250-ec53c280-8f50-11eb-9e96-a968d4fb6032.png)
![정보수정2](https://user-images.githubusercontent.com/70805241/112723251-ec53c280-8f50-11eb-93fd-e9b165cd1cc6.png)

<br>

* 구현 기능 설명
    - 메인 내 정보 영역 내 정보수정 버튼이나 사이드 메뉴 회원 정보 수정 버튼을 누르면 비밀번호 입력 페이지로 이동한다.
    - 변경할 수 있는 칸에 새로운 값을 넣으면 유효성 검사가 실시된다.
    - 회원 정보가 수정되면 '수정 완료' 모달창이 띄워지며 마이페이지 메인으로 이동한다.

<br>

--------------------

<br>

## 역대 챌린지 조회

![역대챌린지조회](https://user-images.githubusercontent.com/70805241/112723265-efe74980-8f50-11eb-8059-7192592e1e14.JPG) <br>

* 구현 기능 설명 
    - 여태 참여했던 챌린지들을 앨범형으로 조회할 수 있다.
    - 챌린지 썸네일을 클릭하면 해당 챌린지의 상세 조회 페이지로 이동한다.

<br>

## 내가 쓴 게시글, 댓글, 인증 게시글 조회

![작성글조회](https://user-images.githubusercontent.com/70805241/112723249-eb229580-8f50-11eb-82bd-d0e3a19fdb3f.png) <br>
- 비슷한 형식이므로 댓글, 인증 게시글 조회 게시판 캡쳐 생략

<br>

* 구현 기능 설명
    - 페이지 하단에 게시글 20개 기준으로 페이징 처리가 되어있다.
    - 한 행을 클릭하면 해당 게시글의 상세 조회 페이지로 이동한다.

<br>

-------------

<br>

## 회원 탈퇴

![회원탈퇴](https://user-images.githubusercontent.com/70805241/112723256-ed84ef80-8f50-11eb-9c3a-cc8f31ff1ed0.JPG)
![회원탈퇴2](https://user-images.githubusercontent.com/70805241/112723257-ee1d8600-8f50-11eb-8a37-fa5e68e516e9.JPG) <br>


* 구현 기능 설명
    - 사이드 메뉴에서 '회원 탈퇴'를 클릭하면 비밀번호를 입력하는 페이지로 이동한다.
    - 올바른 비밀번호를 입력 시 탈퇴가 완료된다.

<br>
--------

<br>

감사합니다!
