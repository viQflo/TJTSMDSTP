document.addEventListener("DOMContentLoaded", function () {
    const navbar = document.querySelector(".nav-links");
    const darkModeToggle = document.getElementById("dark-mode-toggle");
    const homeButton = document.getElementById("homeButton");
    const signupButton = document.getElementById("signupButton");
    const loginButton = document.getElementById("loginButton");

    // ✅ homeButton이 존재할 때만 이벤트 리스너 추가
    if (homeButton) {
        homeButton.addEventListener("click", () => {
            window.location.href = "Main.html";
        });
    } else {
        console.warn("homeButton 요소를 찾을 수 없습니다!");
    }

    // ✅ 다크 모드 토글 버튼이 존재할 때만 실행
    if (darkModeToggle) {
        if (localStorage.getItem("theme") === "dark") {
            document.body.classList.add("dark-mode");
        }

        darkModeToggle.addEventListener("click", function () {
            document.body.classList.toggle("dark-mode");

            if (document.body.classList.contains("dark-mode")) {
                localStorage.setItem("theme", "dark");
            } else {
                localStorage.setItem("theme", "light");
            }
        });
    } else {
        console.warn("dark-mode-toggle 요소를 찾을 수 없습니다!");
    }

    // ✅ 회원가입 및 로그인 버튼이 존재할 때만 실행
    if (signupButton) {
        signupButton.addEventListener("click", () => {
            alert("signup 페이지로 이동합니다.");
            window.location.href = "signup.html";
        });
    }
    if (loginButton) {
        loginButton.addEventListener("click", () => {
            alert("login 페이지로 이동합니다.");
            window.location.href = "login.html";
        });
    }

    // ✅ 네비게이션 바 버튼 클릭 이벤트 (이벤트 위임)
    if (navbar) {
        navbar.addEventListener("click", (event) => {
            const button = event.target.closest(".nav-button");
            if (button) {
                const path = button.getAttribute("data-path");
                if (path) {
                    window.location.href = path;
                }
            }
        });
    } else {
        console.warn("nav-links 요소를 찾을 수 없습니다!");
    }

    // ✅ 로컬 스토리지에서 JWT 토큰 확인
    const token = localStorage.getItem("authToken");

    if (token) {
        fetch("http://localhost:8081/TJTSMDS/api/session-check", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            }
        })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error("세션 확인 실패");
        })
        .then((data) => {
            if (data.isLoggedIn) {
                // ✅ 로그인 상태: 네비게이션 바 변경
                navbar.innerHTML = `
                    <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
                    <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
                    <button class="nav-button" data-path="Board.jsp">커뮤니티</button>
                    <button class="nav-button" data-path="mypage.jsp">마이페이지</button>
                    <button class="nav-button" id="logout-button">로그아웃</button>
                `;

                const logoutButton = document.getElementById("logout-button");
                if (logoutButton) {
                    logoutButton.addEventListener("click", () => {
                        localStorage.removeItem("authToken");
                        alert("로그아웃되었습니다.");
                        window.location.href = "Main.html";
                    });
                }
            } else {
                setDefaultNavbar(navbar);
            }
        })
        .catch((error) => {
            console.error("세션 확인 실패:", error.message);
            setDefaultNavbar(navbar);
        });
    } else {
        setDefaultNavbar(navbar);
    }
});

// ✅ 기본 네비게이션 바 설정 함수
function setDefaultNavbar(navbar) {
    if (navbar) {
        navbar.innerHTML = `
            <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
            <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
            <button class="nav-button" data-path="Board.jsp">커뮤니티</button>
            <button class="nav-button" data-path="login.html">로그인</button>
        `;
    }
}
