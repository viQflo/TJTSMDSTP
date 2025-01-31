<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, com.smhrd.model.CounselorDAO, com.smhrd.model.CounselorDTO"%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>상담사 연결</title>
	<link rel="stylesheet" href="common.css">
	<link rel="stylesheet" href="counselor.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
	<!-- 네비게이션 -->
	<nav class="navbar">
		<div class="logo" id="homeButton">
			<img src="vite.svg" alt="Logo" class="logo-img">
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

	<main class="counselor-container">
		<section class="counselor conn">
			<h2>상담사 연결</h2>
		</section>

		<div class="search-filter">
			<input type="text" id="search" placeholder="상담사 이름 또는 분야 검색">
			<select id="filter">
				<option value="all">전체</option>
				<option value="high-rating">평점 높은 순</option>
				<option value="low-rating">평점 낮은 순</option>
				<option value="counting">상담 건수 순</option>				
				<option value="new">신규 상담사 순</option>
			</select>
		</div>

		<div class="counselor-list" id="counselor-list">
			<!-- 상담사 정보가 동적으로 추가됨 -->
		</div>
	</main>

	<footer class="footer">
		<p>© 2025 MyTeamWebsite. All rights reserved.</p>
		<p>이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> | 고객센터: 1544-1234</p>
	</footer>

	<script>
	$(document).ready(function() {
	    var urlParams = new URLSearchParams(window.location.search);
	    var location = urlParams.get('location');
	    var csCharge = urlParams.get('cs_charge');

	    console.log("DEBUG: 가져온 location = ", location);
	    console.log("DEBUG: 가져온 csCharge = ", csCharge);

	    $.ajax({
	        url: 'GetCounselorServlet',
	        data: { location: location, cs_charge: csCharge },
	        type: 'GET',
	        success: function(data) {
	            console.log("DEBUG: 서버 응답 데이터 = ", data); // 데이터 구조 확인

	            var counselorList = $('#counselor-list');
	            counselorList.empty();

	            if (data && data.length > 0) {
	                data.forEach(function(counselor) {
	                    counselorList.append(
	                        '<div class="counselor-card">' +
	                        '<img src="https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2024%2F08%2F20%2Fjujutsu-kaisen-final-chapter-release-date-01.jpg?q=75&w=800&cbr=1&fit=max" alt="image">' +
	                        '<h3>' + counselor.name + '</h3>' +
	                        '<p>' + counselor.csCharge + '</p>' +
	                        '<p class="rating">⭐ 4.5</p>' +
	                        '</div>'
	                    );
	                });
	            } else {
	                counselorList.append('<p>해당 조건에 맞는 상담사가 없습니다.</p>');
	            }
	        },
	        error: function() {
	            alert('상담사 정보를 가져오는 데 실패했습니다.');
	        }
	    });
	});

	</script>

</body>
</html>
