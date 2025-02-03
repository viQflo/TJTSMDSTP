<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="com.smhrd.model.BoardDTO"%>
<%@ page import="java.util.List"%>
<%@ page import="com.smhrd.model.BoardDAO"%>

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
            <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
            <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
            <button class="nav-button" data-path="Board.html">커뮤니티</button>
            <button class="nav-button" data-path="login.html">로그인</button>
        </div>
    </nav>

    <div id="board-container" class="min-h-screen flex flex-col items-center justify-center bg-white p-6">
        <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">커뮤니티 게시판</h1>

        <!-- 게시글 목록 -->
        <div id="posts-list" class="w-full max-w-4xl">
            <%
                // 게시글 목록을 DB에서 가져오기
                BoardDAO dao = new BoardDAO();
                List<BoardDTO> list = dao.getList(); // DB에서 전체 게시글 리스트를 가져옵니다.
            %>

            <div class="container">
                <div class="panel panel-default">
                    <div class="panel-heading">게시판</div>
                    <div class="panel-body">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>번호</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성일</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- list는 DB에서 가져온 게시글 목록 -->
                                <%
                                for (BoardDTO b : list) {
                                %>
                                <tr>
                                    <td><%=b.getPostIdx()%></td>
                                    <td><a href="boardcontent.jsp?idx=<%=b.getPostIdx()%>"><%=b.getPostTitle()%></a></td>
                                    <td><%=b.getEmail()%></td>
                                    <td>
                                        <%
                                        // 날짜 값이 null일 경우 처리
                                        if (b.getCreateDt() != null) {
                                            String formattedDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(b.getCreateDt());
                                            out.print(formattedDate);
                                        } else {
                                            out.print("No date available"); // 기본값 또는 다른 표시
                                        }
                                        %>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- 글쓰기 버튼 -->
        <div>
            <button class="btn btn-sm btn-success" id="write-post-button">글작성</button>
        </div>

    </div>

    <!-- Footer -->
    <footer class="footer">
        <p>© 2025 MyTeamWebsite. All rights reserved.</p>
        <p>
            이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> |
            고객센터: 1544-1234
        </p>
    </footer>

    <script src="main.js"></script>

    <!-- 글 작성 버튼 클릭 시 로그인 상태 확인 -->
    <script>
    document.getElementById("write-post-button").addEventListener("click", function() {
        const token = localStorage.getItem("authToken");

        if (!token) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            alert("로그인이 필요합니다.");
            window.location.href = "login.html";
        } else {
            // 로그인한 경우 글쓰기 페이지로 이동
            window.location.href = "boardform.jsp";  // 글쓰기 페이지로 이동
        }
    });
    </script>
</body>
</html>
