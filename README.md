# project : JSP ajax를 이용한 실시간 채팅사이트

개인실습 프로젝트 <br>
언어 : JSP, HTML/CSS, JavaScript, jquery, ajax, bootstrap <br>
개발도구 : Tomcat 8.5, Eclipse, my-SQL, <br>
<br>
# 소개

수업진행 내용을 참고로 ajax를 활용한 실시간 채팅사이트입니다.<br>
채팅 기능으로 친구찾기, 메세지함, 안읽은 메세지 개수 표시, 실시간 채팅 서비스를 구현하였습니다.
게시판 기능 복습을 위해 게시판 목록, 상세내용, 글쓰기, 글수정, 글삭제, 답글, 파일 업로드, 파일 다운로드, 페이지네이션 기능을 구현했습니다. <br>
이외에 유저 프로필 사진 등록, 수정, 로그인, 회원가입, 회원정보 수정, 모달창 기능을 구현하였습니다.
JDBC와 연동하기 위해서 Connection pool(커넥션 풀)을 사용하였습니다. <br>
<br>


# 실행 방법

### 1. 이클립스에 프로젝트를 import 하세요.

### 2. connection pool이 작동하는지 connect 여부를 확인해주세요.
- META-INF 폴더에 context.xml 파일에서 설정 값을 확인해주세요.

### 3. 아래와 같이 테이블을 만들어 주세요. 
<br>
- chatBoard
<pre><code>
CREATE TABLE `chatBoard` (
  `userID` varchar(20),
  `boardID` int(11),
  `boardTitle` varchar(50),
  `boardContent` varchar(2048),
  `boardDate` datetime,
  `boardHit` int(11),
  `boardFile` varchar(100),
  `boardRealFile` varchar(100),
  `boardGroup` int(11),
  `boardSequence` int(11),
  `boardLevel` int(11),
  `boardAvailable` int(11),
  PRIMARY KEY (`boardID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
</code></pre>
<br>
<br>
- chatUser
<pre><code>
CREATE TABLE `chatUser` (
  `userID` varchar(20),
  `userPASS` varchar(20),
  `userName` varchar(20),
  `userAge` int(11),
  `userGender` varchar(20),
  `userEmail` varchar(50),
  `userProfile` varchar(50),
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
</code></pre>
<br>
<br>
- chatting
<pre><code>
CREATE TABLE `chatting` (
  `chatID` int(11) NOT NULL AUTO_INCREMENT,
  `fromID` varchar(20),
  `toID` varchar(20),
  `chatContent` varchar(100),
  `chatTime` datetime,
  `chatRead` int(11),
  PRIMARY KEY (`chatID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8
</code></pre>

<br>
- table은 총 3개 입니다 (chatBoard - 게시판DB / chatUser - 회원DB / chatting - 채팅DB )
<br>

### 4. 메인 test는  WebContent 폴더에 'index.jsp'를 run하시면 됩니다.
- 메인 페이지의 상단 메뉴를 이용해 기능을 테스트해보실 수 있습니다.
