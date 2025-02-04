<%@ page import="com.smhrd.model.BoardDTO"%>
<%@ page import="com.smhrd.model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>게시글 내용</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="common.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 네비게이션 -->
    <nav class="navbar">
        <div class="logo" id="homeButton">
            <img src="./videos/image_no_bg.png" alt="Logo" class="logo-img">
            <div>
                <p class="logo-text">누구나</p>
                <p class="sub-text">Look+na</p>
            </div>
        </div>
        <div class="nav-links">
            <button class="nav-button" data-path="voucher.html">바우처 정보</button>
            <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
            <button class="nav-button" data-path="Board.html">커뮤니티</button>
            <button class="nav-button" data-path="login.html">로그인</button>
        </div>
    </nav>

<%
    // 게시글 번호를 URL 파라미터에서 가져옴
    String postIdx = request.getParameter("idx");
    BoardDAO dao = new BoardDAO();
    BoardDTO result = dao.getBoardContent(idx);  // getBoardContent 메서드 호출

    if (result == null) {  // result가 null일 경우 처리
%>
    <div class="alert alert-danger">
        <strong>오류!</strong> 해당 게시글을 찾을 수 없습니다.
    </div>
<%
    } else {  // result가 null이 아닐 경우 게시글 내용 출력
%>
    <div class="container">
      <h2>게시글 내용</h2>
      <table class="table table-bordered">
        <tr>
          <td>번호</td>
          <td><%= result.getPostIdx() %></td>
        </tr>
        <tr>
          <td>제목</td>
          <td><%= result.getPostTitle() %></td>
        </tr>
        <tr>
          <td>작성자</td>
          <td><%= result.getEmail() %></td>
        </tr>
        <tr>
          <td>작성일</td>
          <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(result.getCreateDt()) %></td>
        </tr>
        <tr>
          <td>내용</td>
          <td><%= result.getPostContent() %></td>
        </tr>
      </table>
      <button class="btn btn-primary" onclick="history.back()">목록으로</button>
    </div>
<%
    }
%>

</body>

	<footer class="footer">
		<p>© 2025 MyTeamWebsite. All rights reserved.</p>
		<p>
			이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> |
			고객센터: 1544-1234
		</p>
	</footer>
</html>
