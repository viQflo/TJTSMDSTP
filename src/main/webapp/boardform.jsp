<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="boardform.css">
<!-- CSS 링크 -->
<link rel="stylesheet" href="common.css">
<!-- CSS 링크 -->
<title>게시글 작성</title>
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

	<h2>게시글 작성</h2>

	<!-- 게시글 작성 폼 -->
	<form action="write.do" method="post" enctype="multipart/form-data">
		<label for="post_title">제목:</label> <input type="text"
			name="post_title" id="post_title" required><br> <label
			for="post_content">내용:</label><br>
		<textarea name="content" id="post_content" rows="10" cols="30"
			required></textarea>
		<br>

		<!-- 이메일 필드 (hidden) -->
		<input type="hidden" name="email" id="email"><br> <label
			for="post_file">파일 업로드:</label> <input type="file" name="post_file"
			id="post_file"><br>

		<button type="submit">작성</button>
	</form>

	<%
	if (request.getParameter("error") != null) {
	%>
	<div class="error-message">
		<%
		String errorType = request.getParameter("error");
		if (errorType.equals("title")) {
			out.println("제목을 입력해주세요.");
		} else if (errorType.equals("content")) {
			out.println("내용을 입력해주세요.");
		} else if (errorType.equals("writeFailed")) {
			out.println("게시글 작성에 실패했습니다. 다시 시도해주세요.");
		}
		%>
	</div>
	<%
	}
	%>

	<!-- 푸터 -->
	<footer class="footer">
		<p>© 2025 MyTeamWebsite. All rights reserved.</p>
		<p>
			이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> |
			고객센터: 1544-1234
		</p>
	</footer>

	<script>
    // JWT 토큰 디코딩 함수
    function decodeToken(token) {
        const base64Url = token.split('.')[1]; 
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); 
        const decoded = atob(base64); 
        
        // 디코딩된 데이터를 콘솔에 출력하여 확인합니다.
        const data = JSON.parse(decoded);
        console.log("디코딩된 데이터:", data);  // 데이터를 로그로 출력해 보세요.
        
        return data.sub; // 이메일이 'sub' 필드에 있기 때문에 data.sub로 반환
    }

    // 페이지가 로드되면 URL에서 token을 가져와서 디코딩 후 이메일 자동 입력
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        let token = urlParams.get("token"); // URL에서 JWT 토큰을 가져옵니다.

        if (!token) {
            token = localStorage.getItem("authToken"); // 로컬 스토리지에서 토큰 가져오기
        }

        console.log("토큰:", token);  // 디버깅을 위해 토큰을 로그로 출력

        if (token) {
            const email = decodeToken(token); // JWT 토큰을 디코딩하여 이메일 추출
            console.log("디코딩된 이메일:", email);  // 이메일 디버깅
            if (email) {
                document.getElementById("email").value = email; // 이메일 값을 hidden input에 채워넣음
            } else {
                alert("이메일 정보를 확인할 수 없습니다. 로그인 후 다시 시도해주세요.");
                window.location.href = "login.html"; // 로그인 페이지로 리다이렉트
            }
        } else {
            alert("로그인 후 게시글을 작성해 주세요.");
            window.location.href = "login.html"; // 로그인 페이지로 리다이렉트
        }
    }
</script>



	<script src="main.js"></script>

</body>
</html>
