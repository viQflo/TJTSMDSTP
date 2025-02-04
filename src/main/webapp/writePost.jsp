<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글 작성</title>
    <link rel="stylesheet" href="common.css">
    <link rel="stylesheet" href="writePost.css">
</head>
<body>

    <!-- ✅ 네비게이션 바 -->
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

    <!-- ✅ 게시글 작성 폼 -->
    <div id="write-post-container" class="min-h-screen flex flex-col items-center justify-center bg-white p-6">
        <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">글 작성</h1>
        
        <form id="postForm" enctype="multipart/form-data" class="write-post-form">
            <!-- 제목 입력 -->
            <div class="mb-4">
                <label for="postTitle" class="label">제목</label>
                <input type="text" id="postTitle" name="postTitle" class="input-text" required>
            </div>

            <!-- 내용 입력 -->
            <div class="mb-4">
                <label for="postContent" class="label">내용</label>
                <textarea id="postContent" name="postContent" rows="10" class="textarea" required></textarea>
            </div>

            <!-- 첨부파일 (선택 사항) -->
            <div class="mb-4">
                <label for="postFile" class="label">첨부파일</label>
                <input type="file" id="postFile" name="postFile" class="input-file">
            </div>

            <!-- 등록 / 취소 버튼 -->
            <div class="flex-between">
                <button type="button" id="submitPost" class="button button-primary">등록</button>
                <button type="button" class="button button-secondary" onclick="window.location.href='Board.jsp'">취소</button>
            </div>
        </form>
    </div>

    <!-- ✅ Footer -->
    <footer class="footer">
        <p>© 2025 MyTeamWebsite. All rights reserved.</p>
        <p>이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> | 고객센터: 1544-1234</p>
    </footer>

    <!-- ✅ 메인 JavaScript 파일 -->
    <script src="main.js"></script>

    <!-- ✅ JavaScript (AJAX 요청) -->
    <script>
        document.getElementById("submitPost").addEventListener("click", function (event) {
            event.preventDefault(); // 폼 기본 제출 방지

            const form = document.getElementById("postForm");
            const formData = new FormData(form);
            const token = localStorage.getItem("authToken"); // 로컬스토리지에서 토큰 가져오기

            fetch("writePostAction.jsp", {
                method: "POST",
                body: formData,
                headers: {
                    "Authorization": "Bearer "+token // 🔹 JWT 토큰을 Authorization 헤더에 포함
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("게시글 작성 성공!");
                    window.location.href = "Board.jsp";
                } else {
                    alert("게시글 작성 실패: " + data.message);
                }
            })
            .catch(error => console.error("Error:", error));
        });
    </script>

</body>
</html>
