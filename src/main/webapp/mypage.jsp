<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="main.js"></script>
    <link rel="stylesheet" href="common.css">
    <link rel="stylesheet" href="mypage.css">
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
            <button class="nav-button" data-path="voucher.html">바우처 정보</button>
            <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
            <button class="nav-button" data-path="Board.html">커뮤니티</button>
            <button class="nav-button" data-path="mypage.jsp">마이페이지</button>
            <button class="nav-button" id="logout-btn">로그아웃</button>
        </div>
    </nav>

    <div class="container">
        <h2 class="title">마이페이지</h2>

        <!-- 프로필 섹션 -->
        <div class="profile">
            <img src="https://cdn-icons-png.flaticon.com/512/3282/3282224.png" alt="Profile">
            <h3 id="user-name">로드 중...</h3>
            <p id="user-job">로드 중...</p>
        </div>

        <!-- 회원 정보 섹션 -->
        <div class="info-section">
            <h3>회원 정보</h3>
            <div class="info-item">
                <label>이름</label> <input type="text" id="name" value="" disabled>
            </div>
            <div class="info-item">
                <label>이메일</label> <input type="email" id="email" value="" disabled>
            </div>
            <div class="info-item">
                <label>전화번호</label> <input type="text" id="phone" value="">
            </div>
        </div>

        <!-- 버튼 섹션 -->
        <div class="buttons">
            <button id="edit-btn">정보 수정</button>
            <button id="logout-btn">로그아웃</button>
        </div>

        <!-- 비밀번호 변경 섹션 -->
        <div class="password-section">
            <h3>비밀번호 변경</h3>
            <input type="password" id="new-password" placeholder="새 비밀번호 입력">
            <button id="change-password-btn">변경</button>
        </div>
    </div>

    <!-- ✅ 상담 신청 현황 -->
    <div class="counsel-section">
        <h3>상담 신청 현황</h3>
        <table>
            <thead>
                <tr>
                    <th>신청 날짜</th>
                    <th>상담 유형</th>
                    <th>상담자 이름</th>
                    <th>진행 상태</th>
                </tr>
            </thead>
            <tbody id="counsel-list">
                <!-- JavaScript에서 동적으로 추가됨 -->
            </tbody>
        </table>
    </div>

    <footer class="footer">
        <p>© 2025 MyTeamWebsite. All rights reserved.</p>
        <p>
            이메일: <a href="mailto:wayawaya@smhrd.com">wayawaya@smhrd.com</a> |
            고객센터: 1544-1234
        </p>
    </footer>

    <script>
    $(document).ready(function() {
        let isEditing = false;

        // ✅ 로컬 스토리지에서 토큰 가져오기
        var token = localStorage.getItem("authToken");

        if (!token) {
            alert("로그인이 필요합니다.");
            window.location.href = "login.html";
            return;
        }

        console.log("✅ 저장된 토큰:", token);

        // ✅ 사용자 정보 불러오기
        fetch("http://localhost:8081/TJTSMDS/api/getUserInfo", {
            method: "POST",
            credentials: "include",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log("✅ 서버 응답 데이터:", data);
            $("#user-name").text(data.name);
            $("#user-job").text(data.job);
            $("#name").val(data.name);
            $("#email").val(data.email);
            $("#phone").val(data.phone);
        })
        .catch(error => {
            console.error("세션 확인 실패:", error);
            alert("세션이 만료되었습니다. 다시 로그인하세요.");
            window.location.href = "login.html";
        });

        // ✅ 상담 신청 내역 불러오기
        fetch("http://localhost:8081/TJTSMDS/GetCounselList", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                const counselList = data.counselList;
                const tableBody = $("#counsel-list");

                tableBody.empty(); // 기존 목록 초기화

                counselList.forEach(item => {
                    const row = `<tr>
                        <td>${item.date}</td>
                        <td>${item.type}</td>
                        <td>${item.name}</td>
                        <td class="status">${item.status}</td>
                    </tr>`;
                    tableBody.append(row);
                });
            } else {
                console.error("상담 내역 불러오기 실패:", data.message);
            }
        })
        .catch(error => console.error("오류 발생:", error));

        // ✅ 정보 수정 버튼
        $("#edit-btn").click(function() {
            isEditing = !isEditing;
            $("#phone").prop("disabled", !isEditing);

            if (!isEditing) {
                let newPhone = $("#phone").val();
                $.post("UpdateMember", { email: $("#email").val(), phone: newPhone }, function(response) {
                    alert("전화번호가 업데이트되었습니다.");
                });
            }
            $(this).text(isEditing ? "수정 완료" : "정보 수정");
        });

        // ✅ 로그아웃 버튼
        $("#logout-btn").click(function() {
            localStorage.removeItem("authToken");
            alert("로그아웃되었습니다.");
            window.location.href = "Main.html";
        });

        // ✅ 비밀번호 변경 버튼
        $("#change-password-btn").click(function() {
            let newPassword = $("#new-password").val();
            if (newPassword.trim() === "") {
                alert("새 비밀번호를 입력하세요.");
                return;
            }

            $.post("UpdatePassword", { email: $("#email").val(), password: newPassword }, function(response) {
                alert("비밀번호가 변경되었습니다.");
                $("#new-password").val("");
            });
        });
    });
    </script>

</body>
</html>
