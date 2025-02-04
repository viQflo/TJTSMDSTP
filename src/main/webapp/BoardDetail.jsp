	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="com.smhrd.model.BoardDAO"%>
	<%@ page import="com.smhrd.model.BoardDTO"%>
	
	<%
	    // 1️⃣ 게시글 번호 가져오기
	    String postIdxParam = request.getParameter("postIdx");
	    int postIdx = 0;
	
	    if (postIdxParam != null && !postIdxParam.isEmpty()) {
	        postIdx = Integer.parseInt(postIdxParam);
	    } else {
	        out.println("<h2>❌ 오류: 게시글 번호가 전달되지 않았습니다.</h2>");
	        return; // 여기서 종료
	    }
	
	    // 2️⃣ DAO 생성 후 특정 게시글 정보 가져오기
	    BoardDAO boardDAO = new BoardDAO();
	    boardDAO.incrementViewCount(postIdx); // 조회수 증가
	    BoardDTO post = boardDAO.getPostById(postIdx);
	
	    // 3️⃣ 이전 게시글과 다음 게시글 번호 가져오기
	    int previousPostIdx = boardDAO.getPreviousPostIdx(postIdx);
	    int nextPostIdx = boardDAO.getNextPostIdx(postIdx);
	
	    // 4️⃣ 게시글이 존재하지 않을 경우 예외 처리
	    if (post == null) {
	        out.println("<h2>❌ 오류: 해당 게시글을 찾을 수 없습니다.</h2>");
	        return; // 여기서 종료
	    }
	%>
	
	<!DOCTYPE html>
	<html lang="ko">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>게시글 상세보기</title>
	
	    <link rel="stylesheet" href="common.css">
	    <link rel="stylesheet" href="BoardDetail.css">
	</head>
	<body>
	
	    <!-- Navbar -->
	    <nav class="navbar">
	        <div class="logo" id="homeButton">
	            <img src="./videos/메인 로고.png" alt="Logo" class="logo-img">
	            <div>
	                <p class="logo-text">누구나</p>
	                <p class="sub-text">&nbsp;Look+我(나)</p>
	            </div>
	        </div>
	        <div class="nav-links">
	            <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
	            <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
	            <button class="nav-button" data-path="Board.jsp">커뮤니티</button>
	            <button class="nav-button" data-path="login.html">로그인</button>
	        </div>
	    </nav>
	
	    <!-- 게시글 상세보기 -->
	    <div id="post-detail-container">
	        <h2><%= post.getPostTitle() %></h2>
	        <p class="post-meta">작성자: <%= post.getEmail() %> | 조회수: <%= post.getPostViews() %> | 좋아요: <span id="like-count"><%= post.getPostLikes() %></span></p>
	        
	        <hr>
	
	        <% if (post.getPostFile() != null && !post.getPostFile().isEmpty()) { %>
	            <img src="<%= post.getPostFile() %>" alt="게시글 이미지">
	        <% } %>
	
	        <p><%= post.getPostContent() %></p>
	
	        <!-- 버튼 그룹 -->
	        <div class="button-group">
	            <% if (previousPostIdx != -1) { %>
	                <button onclick="location.href='BoardDetail.jsp?postIdx=<%= previousPostIdx %>';">이전 글</button>
	            <% } else { %>
	                <button disabled>첫 번째 게시글입니다.</button>
	            <% } %>
	
	            <button id="like-button">좋아요 ♥ (<span id="like-count"><%= post.getPostLikes() %></span>)</button>
	
	            <% if (nextPostIdx != -1) { %>
	                <button onclick="location.href='BoardDetail.jsp?postIdx=<%= nextPostIdx %>';">다음 글</button>
	            <% } else { %>
	                <button disabled>마지막 게시글입니다.</button>
	            <% } %>
	        </div>
	    </div>
	
	    <!-- Footer -->
	    <footer class="footer">
	        <p>© 2025 MyTeamWebsite. All rights reserved.</p>
	        <p>이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> | 고객센터: 1544-1234</p>
	    </footer>
	
	    <script>
	        // 좋아요 버튼 기능 (AJAX 사용)
	        document.getElementById('like-button').addEventListener('click', function() {
	            fetch('UpdateLikes.jsp?postIdx=<%= postIdx %>')
	                .then(response => response.text())
	                .then(newLikes => {
	                    alert('게시글에 좋아요를 눌렀습니다!');
	                    window.location.reload();

	                });
	        });
	    </script>
	
	    <script src="main.js"></script>
	</body>
	</html>
