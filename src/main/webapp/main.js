document.addEventListener("DOMContentLoaded", function () {
    const navbar = document.querySelector(".nav-links");
    const darkModeToggle = document.getElementById("dark-button");
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
/*맨 아래 커뮤니티 소개창 애니메이션*/
document.addEventListener("DOMContentLoaded", function () {
    const magicCards = document.querySelectorAll(".zoom_card");

    const magicObserver = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("magic_active");
            }
        });
    }, { threshold: 0.2 });

    magicCards.forEach(card => magicObserver.observe(card));
});
/* 바우처 정보 사이트로 이동*/
document.addEventListener("DOMContentLoaded", function () {
    
    const portalButton = document.querySelector(".btn-1");

    // 버튼이 존재하는지 확인 후 클릭 이벤트 추가
    if (portalButton) {
        portalButton.addEventListener("click", function () {
            window.location.href = "voucher.jsp"; // 이동할 페이지
        });
    } else {
        console.warn("btn-1 요소를 찾을 수 없습니다!");
    }
});
/* 상담 연결 사이트로 이동*/
document.addEventListener("DOMContentLoaded", function () {
    
    const portalButton = document.querySelector(".btn-2");

    // 버튼이 존재하는지 확인 후 클릭 이벤트 추가
    if (portalButton) {
        portalButton.addEventListener("click", function () {
            window.location.href = "counsel.html"; // 이동할 페이지
        });
    } else {
        console.warn("btn-2 요소를 찾을 수 없습니다!");
    }
});
/* 커뮤니티 사이트로 이동*/
document.addEventListener("DOMContentLoaded", function () {
    
    const portalButton = document.querySelector(".portal_button");

    // 버튼이 존재하는지 확인 후 클릭 이벤트 추가
    if (portalButton) {
        portalButton.addEventListener("click", function () {
            window.location.href = "Board.jsp"; // 이동할 페이지
        });
    } else {
        console.warn("portal_button 요소를 찾을 수 없습니다!");
    }
});
document.addEventListener("DOMContentLoaded", function () {
    const cosmicElements = document.querySelectorAll(".fade-effect");

    const cosmicObserver = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("starlit-show");
            }
        });
    }, { threshold: 0.3 });

    cosmicElements.forEach(element => cosmicObserver.observe(element));
});