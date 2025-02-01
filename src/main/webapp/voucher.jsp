<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
if (request.getAttribute("vouchers") == null) {
    response.sendRedirect("Voucher"); // 🚀 /Voucher로 자동 리디렉트
    return;
}
%>
<%@ page import="java.util.List"%>
<%@ page import="com.smhrd.model.VoucherDTO"%>

<%
    List<VoucherDTO> vouchers = (List<VoucherDTO>) request.getAttribute("vouchers");
    int itemsPerPage = 15; // ✅ 한 페이지에 표시할 개수
    int totalItems = (vouchers != null) ? vouchers.size() : 0;
    int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>바우처 사이트</title>
<link rel="stylesheet" href="common.css">
<link rel="stylesheet" href="voucher.css">
</head>
<body>

<!-- ✅ Navbar -->
<nav class="navbar">
    <div class="logo" id="homeButton">
        <img src="vite.svg" alt="Logo" class="logo-img">
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

<div class="container">
<br><br><br><br><br>
    <h2>바우처 사이트</h2>
<br><br>
<!-- ✅ 검색창 -->
<div class="search-container">
    <input type="text" id="searchInput" placeholder="원하는 바우처 정보를 입력하세요.">
    <button id="searchButton">
        <img src="search-icon.png" alt="검색" width="20">
    </button>
</div>

<br><br>
    <%
    if (vouchers == null) {
        out.println("<p style='color:red;'>🚨 서버에서 데이터를 받지 못했습니다.</p>");
    } else if (vouchers.isEmpty()) {
        out.println("<p style='color:orange;'>⚠️ DB에 저장된 바우처가 없습니다.</p>");
    }
    %>

    <div id="portfolioGrid">
        <%
        if (vouchers != null && !vouchers.isEmpty()) {
            for (int i = 0; i < vouchers.size(); i++) {
                VoucherDTO voucher = vouchers.get(i);
        %>
        <div class="portfolio-item" data-index="<%= i %>">
            <img src="<%= voucher.getImg() %>" alt="바우처 이미지">
            <div class="content">
                <h3><%= voucher.getName() %></h3>
                <p>#전국민 #바우처 #지원금</p>
                <a href="<%= voucher.getUrl() %>" target="_blank">사이트 방문하기</a>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p>등록된 바우처가 없습니다.</p>
        <%
        }
        %>
    </div>
<br><br>
    <!-- ✅ 간결한 페이지네이션 UI -->
    <div class="pagination">
        <button id="prevPage" class="page-btn">이전</button>
        <span id="pageNumbers"></span>
        <button id="nextPage" class="page-btn">다음</button>
    </div>
</div>

<!-- ✅ Footer -->
<footer class="footer">
    <p>© 2025 MyTeamWebsite. All rights reserved.</p>
    <p>
        이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> |
        고객센터: 1544-1234
    </p>
</footer>

<script>
    let itemsPerPage = <%= itemsPerPage %>;
    let currentPage = 1;
    let items = document.querySelectorAll(".portfolio-item");
    let totalPages = <%= totalPages %>;

    function showPage(page) {
        let start = (page - 1) * itemsPerPage;
        let end = start + itemsPerPage;

        items.forEach((item, index) => {
            item.style.display = (index >= start && index < end) ? "block" : "none";
        });

        document.getElementById("prevPage").disabled = (page === 1);
        document.getElementById("nextPage").disabled = (page === totalPages);

        updatePaginationUI();
    }

    function updatePaginationUI() {
        let pageNumbers = document.getElementById("pageNumbers");
        pageNumbers.innerHTML = "";

        for (let i = 1; i <= totalPages; i++) {
            let button = document.createElement("button");
            button.textContent = i;
            button.classList.add("page-btn");
            if (i === currentPage) {
                button.classList.add("active");
            }
            button.addEventListener("click", () => changePage(i));
            pageNumbers.appendChild(button);
        }
    }

    function changePage(page) {
        if (page < 1 || page > totalPages) return;
        currentPage = page;
        showPage(currentPage);
    }

    document.getElementById("prevPage").addEventListener("click", () => changePage(currentPage - 1));
    document.getElementById("nextPage").addEventListener("click", () => changePage(currentPage + 1));

    // ✅ 초기 페이지 로딩
    showPage(currentPage);
</script>

<script src="main.js"></script>
<script src="voucher.js"></script>
</body>
</html>
