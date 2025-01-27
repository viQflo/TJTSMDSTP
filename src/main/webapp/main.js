// 로고 클릭 시 메인 페이지(main.html)로 이동
document.getElementById("homeButton").addEventListener("click", () => {
    window.location.href = "Main.html"; // main.html 경로로 이동
});

// 각 네비게이션 버튼 클릭 시 이동 처리
document.querySelectorAll(".nav-button").forEach((button) => {
    button.addEventListener("click", () => {
        const path = button.getAttribute("data-path"); // data-path 속성 읽기
        if (path) {
            window.location.href = path; // data-path 경로로 이동
        }
    });
});

// 회원가입 버튼 클릭 시
const signupButton = document.getElementById("signupButton");
if (signupButton) {
    signupButton.addEventListener("click", () => {
        alert("회원가입 페이지로 이동합니다.");
        window.location.href = "signup.html";
    });
}

// 로그인 버튼 클릭 시
const loginButton = document.getElementById("loginButton");
if (loginButton) {
    loginButton.addEventListener("click", () => {
        alert("로그인 페이지로 이동합니다.");
        window.location.href = "login.html";
    });
}

// 로그인/로그아웃 버튼 처리
const authButton = document.getElementById("authButton");
if (authButton) {
    authButton.addEventListener("click", () => {
        const isLoggedIn = false; // 로그인 여부를 확인하는 변수 (추후 백엔드와 연동 필요)
        if (isLoggedIn) {
            alert("로그아웃 되었습니다.");
            // 로그아웃 처리 로직 추가 (예: 세션 삭제)
            window.location.href = "/";
        } else {
            alert("로그인 페이지로 이동합니다.");
            window.location.href = "/login";
        }
    });
}
