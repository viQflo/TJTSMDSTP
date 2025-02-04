<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.smhrd.model.BoardDAO"%>
<%@ page import="com.smhrd.model.BoardDTO"%>

<%
int pageNum = 1; // 기본 페이지 번호
int pageSize = 5; // 페이지당 게시글 개수

// 사용자가 지정한 페이지 값 가져오기
if (request.getParameter("pageNum") != null) {
	pageNum = Integer.parseInt(request.getParameter("pageNum"));
}

// DAO 인스턴스 생성
BoardDAO boardDAO = new BoardDAO();

// 게시글 개수 가져오기
int totalPosts = boardDAO.getTotalPostCount();
int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

// 현재 페이지의 게시글 리스트 가져오기
List<BoardDTO> postList = boardDAO.getPostsByPage(pageNum, pageSize);

// 페이지네이션 범위 계산
int paginationSize = 5; // 한 번에 보여줄 페이지 개수
int startPage = ((pageNum - 1) / paginationSize) * paginationSize + 1;
int endPage = Math.min(startPage + paginationSize - 1, totalPages);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>커뮤니티 게시판</title>

<!-- CSS 적용 -->
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
		<div class="nav-links">
			<button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
			<button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
			<button class="nav-button" data-path="Board.jsp">커뮤니티</button>
			<button class="nav-button" data-path="login.html">로그인</button>
		</div>
	</nav>

	<!-- 커뮤니티 게시판 페이지 -->
	<div id="board-container"
		class="min-h-screen flex flex-col items-center justify-center bg-white p-6">
		<h1 class="text-3xl font-bold text-center text-gray-800 mb-6">커뮤니티
			게시판</h1>

		<!-- 검색창 -->
		<div class="flex justify-center items-center mb-8 gap-2">
			<select id="search-filter"
				class="border border-gray-300 rounded-l-lg px-4 py-2">
				<option value="제목">제목</option>
				<option value="내용">내용</option>
				<option value="제목+내용">제목+내용</option>
				<option value="작성자">작성자</option>
			</select> <input id="search-query" type="text" placeholder="검색어를 입력하세요"
				class="border border-gray-300 px-4 py-2 w-80" />
			<button id="search-button"
				class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">검색</button>
		</div>

		<!-- 게시글 목록 -->
		<div id="posts-list" class="w-full max-w-4xl">
			<%
			for (BoardDTO post : postList) {
				String postFile = (post.getPostFile() != null && !post.getPostFile().isEmpty())
				? post.getPostFile()
				: "https://cdn-icons-png.flaticon.com/512/3282/3282224.png";
			%>
			<div class="post-item"
				onclick="location.href='BoardDetail.jsp?postIdx=<%=post.getPostIdx()%>'; ">
				<img src="<%=postFile%>" alt="Thumbnail" class="post-thumbnail">
				<div class="post-info">
					<div class="post-title"><%=post.getPostTitle()%></div>
					<div class="post-meta">
						작성자:
						<%=post.getEmail()%>
						| 조회수:
						<%=post.getPostViews()%>
						| 좋아요:
						<%=post.getPostLikes()%>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>

		<!-- 페이지네이션 -->
		<div id="pagination-buttons">
			<%
			if (startPage > 1) {
			%>
			<a href="Board.jsp?pageNum=<%=startPage - 1%>" class="page-button">이전</a>
			<%
			}
			%>

			<%
			for (int i = startPage; i <= endPage; i++) {
			%>
			<a href="Board.jsp?pageNum=<%=i%>"
				class="page-button <%=(i == pageNum) ? "active" : ""%>"> <%=i%>
			</a>
			<%
			}
			%>

			<%
			if (endPage < totalPages) {
			%>
			<a href="Board.jsp?pageNum=<%=endPage + 1%>" class="page-button">다음</a>
			<%
			}
			%>
		</div>

		<!-- 글쓰기 버튼: 페이지네이션과 간격 추가 -->
		<div class="text-center">
			<button id="write-post-button">
				글쓰기</button>
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
	<script>
    document.addEventListener('DOMContentLoaded', function() {
        // 글쓰기 버튼 클릭 시 글 작성 페이지로 이동
        document.getElementById('write-post-button').addEventListener('click', function() {
            window.location.href = 'writePost.jsp';
        });

        // 검색 기능 구현
        document.getElementById('search-button').addEventListener('click', function() {
            const filter = document.getElementById('search-filter').value;
            const query = document.getElementById('search-query').value.trim().toLowerCase();
            const postItems = document.querySelectorAll('.post-item');

            postItems.forEach(post => {
                const title = post.querySelector('.post-title').textContent.toLowerCase();
                const meta = post.querySelector('.post-meta').textContent.toLowerCase();

                if ((filter === '제목' && title.includes(query)) ||
                    (filter === '내용' && meta.includes(query)) ||
                    (filter === '제목+내용' && (title.includes(query) || meta.includes(query))) ||
                    (filter === '작성자' && meta.includes(query))) {
                    post.style.display = 'block';
                } else {
                    post.style.display = 'none';
                }
            });
        });
    });
    </script>

</body>
</html>
