document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("login-form");
    const errorMessage = document.getElementById("error-message");
    const API_BASE_URL = "http://127.0.0.1:8000/api";

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const pw = document.getElementById("password").value;

        const loginData = { email, pw };

        try {
            const response = await fetch(`${API_BASE_URL}/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(loginData),
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem("token", data.token); // JWT 토큰 저장
                window.location.href = "main.html"; // 로그인 성공 시 main 페이지로 이동
            } else {
                throw new Error("이메일 또는 비밀번호가 잘못되었습니다.");
            }
        } catch (error) {
            errorMessage.textContent = error.message;
        }
    });
});
