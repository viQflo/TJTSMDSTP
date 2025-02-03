<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>아이디 찾기</title>
<!-- CSS 파일 -->
<link rel="stylesheet" href="common.css">
<link rel="stylesheet" href="signup.css">
<style>
    /* 모달 스타일 중앙 정렬 */
    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .modal-content {
        background: white;
        padding: 20px;
        border-radius: 10px;
        text-align: center;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        width: 300px;
    }
    #close-modal {
        margin-top: 10px;
        padding: 10px 20px;
        border: none;
        background-color: #4a90e2;
        color: white;
        border-radius: 5px;
        cursor: pointer;
    }
    #close-modal:hover {
        background-color: #357ab7;
    }
    #find-id-button {
    width: 100%;
    padding: 10px;
    background-color: #4a90e2;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}
</style>
</head>
<body>

	<!-- Navbar -->
	<nav class="navbar">
		<div class="logo" id="homeButton">
			<img src="./videos/image_no_bg.png" alt="Logo" class="logo-img">
			<div>
				<p class="logo-text">누구나</p>
				<p class="sub-text">Look+na</p>
			</div>
		</div>
		<div class="nav-links">
			<button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
			<button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
			<button class="nav-button" data-path="Board.html">커뮤니티</button>
			<button class="nav-button" data-path="login.html">로그인</button>
		</div>
	</nav>

	<!-- 메인 컨텐츠 -->
	<div class="container">
		<div class="signup-box">
			<h2>아이디 찾기</h2>
			<form id="find-id-form" action="FindIdController" method="POST">
				<div class="form-group">
					<label for="name">이름</label> 
					<input type="text" id="name" name="name" required>
				</div>
				<div class="form-group">
					<label for="phone">전화번호</label> 
					<input type="tel" id="phone" name="phone" required>
				</div>
				<button type="submit" id="find-id-button">아이디 찾기</button>
				<p id="find-id-message" class="message"></p>
			</form>
		</div>
	</div>

	<!-- 모달 창 -->
	<% String foundEmail = (String) request.getAttribute("foundEmail"); %>
	<% if (foundEmail != null) { %>
	<div id="id-modal" class="modal" style="display: flex;">
		<div class="modal-content">
			<h2>아이디 찾기 결과</h2>
			<p id="found-email"><%= foundEmail %></p>
			<button id="close-modal">확인</button>
		</div>
	</div>
	<% } %>

	<!-- Footer -->
	<footer class="footer">
		<p>© 2025 MyTeamWebsite. All rights reserved.</p>
		<p>
			이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> |
			고객센터: 1544-1234
		</p>
	</footer>

	<script>
		document.addEventListener("DOMContentLoaded", function() {
			const closeModal = document.getElementById("close-modal");
			if (closeModal) {
				closeModal.addEventListener("click", function() {
					window.location.href = "login.html";
				});
			}
		});
	</script>
	<script src="main.js"></script>

</body>
</html>
