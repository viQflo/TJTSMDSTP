<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>비밀번호 찾기</title>
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
    #find-password-button {
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
			<h2>비밀번호 찾기</h2>
			<form id="find-password-form" action="FindPasswordController" method="POST">
				<div class="form-group">
					<label for="email">이메일</label> 
					<input type="email" id="email" name="email" required>
				</div>
				<button type="submit" id="find-password-button">비밀번호 찾기</button>
				<p id="find-password-message" class="message"></p>
			</form>
		</div>
	</div>

	<!-- 모달 창 -->
	<% String foundPassword = (String) request.getAttribute("foundPassword"); %>
	<% if (foundPassword != null) { %>
	<div id="password-modal" class="modal" style="display: flex;">
		<div class="modal-content">
			<h2>비밀번호 찾기 결과</h2>
			<p id="found-password">비밀번호: <%= foundPassword %></p>
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
