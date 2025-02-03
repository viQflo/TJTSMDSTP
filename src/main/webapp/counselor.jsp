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
            <img src="./videos/메인 로고.png" alt="Logo" class="logo-img">
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
        <!-- 상담사 연결 -->
        <section class="counselor conn">
            <h2>상담사 연결 선택창</h2>
        </section>

        <!-- 검색 필터 -->
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

        <!-- 상담사 리스트 -->
        <div class="counselor-list" id="counselor-list">
            <!-- 상담사 정보가 동적으로 추가됨 -->
        </div>

        <!-- ✅ 입장 시 안내 모달 -->
        <div id="welcomeModal" class="modal">
            <div class="modal-content">
                <span class="close-welcome">&times;</span>
                <h3>상담 예약</h3>
                <p>상담사를 선택하고 예약을 진행해주세요.</p>
                <button id="closeWelcomeModal" class="confirm-btn">네, 알겠습니다!</button>
            </div>
        </div>

        <!-- ✅ 예약 버튼 클릭 시 나오는 모달 -->
        <div id="reservationModal" class="modal" style="display: none;">
            <div class="modal-content">
                <span class="close-reservation">&times;</span>
                <h3 id="modalTitle"></h3>
                <p id="modalInfo"></p>
                <button id="confirmReservation" class="confirm-btn">예약 신청</button>
            </div>
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
            console.log("DEBUG: 서버 응답 데이터 = ", data);

            var counselorList = $('#counselor-list');
            counselorList.empty();

            if (data && data.length > 0) {
                data.forEach(function(counselor) {
                    let randomRating = (Math.random() * (4.9 - 4.1) + 4.1).toFixed(1);

                    var counselorCard = $('<div class="counselor-card">')
                        .append('<img src="https://example.com/sample-image.jpg" alt="image">')
                        .append('<h3>' + counselor.name + '</h3>')
                        .append('<p>' + counselor.csCharge + '</p>')  
                        .append('<p class="rating">⭐ ' + randomRating + '</p>') 
                        .append('<button class="book-btn">예약</button>');

                    // ✅ 예약 버튼 이벤트 추가
                    counselorCard.find(".book-btn").click(function() {
                        console.log("DEBUG: 예약 버튼 클릭됨 - 상담사:", counselor.name, "분야:", counselor.csCharge);
                        openReservationModal(counselor.name, counselor.csCharge);
                    });

                    counselorList.append(counselorCard);
                });
            } else {
                console.warn("DEBUG: 조건에 맞는 상담사가 없음");
                counselorList.append('<p>해당 조건에 맞는 상담사가 없습니다.</p>');
            }
        },
        error: function(xhr, status, error) {
            console.error("ERROR: 상담사 정보를 가져오는 데 실패했습니다.", status, error);
            alert('상담사 정보를 가져오는 데 실패했습니다.');
        }
    });

    // ✅ 안내 모달 관련 요소
    var welcomeModal = $("#welcomeModal");

    $("#closeWelcomeModal, .close-welcome").click(function() {
        console.log("DEBUG: '네, 알겠습니다!' 버튼 클릭됨 → 모달 닫기");
        welcomeModal.hide();
    });

    $(window).on("load", function() {
        console.log("DEBUG: 페이지 로드됨 → 안내 모달 표시");
        welcomeModal.show();
    });

    // ✅ 예약 모달 관련 요소
    const reservationModal = $("#reservationModal");
    const modalTitle = $("#modalTitle");
    const modalInfo = $("#modalInfo");
    const confirmReservation = $("#confirmReservation");

    let isReservationMode = false;

    function openReservationModal(name, field) {
        console.log("DEBUG: 예약 모달 오픈 - 상담사:", name, "분야:", field);
        modalTitle.text(name + " 상담사와 예약 신청");
        modalInfo.text("분야: " + field);
        isReservationMode = true;
        reservationModal.show();
    }

    confirmReservation.click(function() {
        if (isReservationMode) {
            var counselorName = modalTitle.text().replace(" 상담사와 예약 신청", "");
            var csCharge = modalInfo.text().replace("분야: ", "");

            console.log("DEBUG: SMS 요청 전송 - 상담사:", counselorName, "분야:", csCharge);

            $.ajax({
                url: 'SendSMSServlet',
                type: 'POST',
                data: { counselorName: counselorName, csCharge: csCharge },
                dataType: "json",  // ✅ JSON 응답을 자동으로 파싱
                success: function(response) {
                    console.log("DEBUG: SMS 전송 응답 - ", response);
                    
                    if (response.status === "success") {
                        alert(response.message); // ✅ 정상적인 메시지 출력
                    } else {
                        alert("오류 발생: " + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("ERROR: 문자 발송 실패 -", status, error);
                    alert("문자 발송에 실패했습니다.");
                }
            });

            alert("예약 신청 완료!");
        }
        reservationModal.hide();
        isReservationMode = false;
    });

});
</script>
<script src="main.js"></script>
</body>
</html>
