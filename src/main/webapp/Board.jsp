<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.smhrd.model.BoardDTO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // 게시글 데이터를 가져오는 로직 (Servlet에서 전달된 데이터)
    List<BoardDTO> posts = (List<BoardDTO>) request.getAttribute("posts");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>커뮤니티 게시판</title>
<link rel="stylesheet" href="common.css">
<link rel="stylesheet" href="Board.css">
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
      <div class="nav-links"></div>
   </nav>

   <div id="board-container" class="min-h-screen flex flex-col items-center bg-white p-6">
      <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">커뮤니티 게시판</h1>
      
      <!-- 검색창 -->
      <div class="search-bar flex justify-center items-center mb-8 gap-2">
         <select id="search-filter">
            <option value="제목">제목</option>
            <option value="내용">내용</option>
            <option value="제목+내용">제목+내용</option>
            <option value="작성자">작성자</option>
         </select>
         <input id="search-query" type="text" placeholder="검색어를 입력하세요" />
         <button id="search-button">검색</button>
      </div>
      
      <!-- 게시글 목록 -->
      <div id="posts-list">
         <c:forEach var="post" items="${posts}">
            <div class="post-item" onclick="location.href='BoardController?action=view&postIdx=${post.postIdx}'">
                <h2 class="post-title">${post.postTitle}</h2>
                <p class="post-meta">작성자: ${post.email} | 조회수: ${post.postViews} | 작성일: ${post.createDt}</p>
            </div>
         </c:forEach>
      </div>

      <!-- 글쓰기 버튼 -->
      <button id="write-post-button">글쓰기</button>
   </div>

   <!-- Footer -->
   <footer class="footer">
      <p>© 2025 MyTeamWebsite. All rights reserved.</p>
      <p>이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> | 고객센터: 1544-1234</p>
   </footer>

   <script src="main.js"></script>
   <script>
      document.addEventListener("DOMContentLoaded", function() {
         const writePostButton = document.getElementById("write-post-button");
         writePostButton.addEventListener("click", function() {
            const isLoggedIn = localStorage.getItem("authToken");
            if (!isLoggedIn) {
               alert("로그인이 필요한 서비스입니다.");
               window.location.href = "login.html";
            } else {
               window.location.href = "BoardController?action=write";
            }
         });
      });
   </script>
</body>
</html>
