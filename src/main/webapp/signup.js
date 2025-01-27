document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signup-form");
    const signupMessage = document.getElementById("signup-message");
    const API_BASE_URL = "http://localhost:8000/api";

    signupForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const formData = {
            email: document.getElementById("email").value,
            pw: document.getElementById("pw").value,
            name: document.getElementById("name").value,
            nick: document.getElementById("nick").value,
            birthdate: document.getElementById("birthdate").value,
            gender: document.getElementById("gender").value,
            job: document.getElementById("job").value,
            phone: document.getElementById("phone").value,
            user_type: document.getElementById("user_type").value,
            create_dt: new Date().toISOString().split("T")[0]
        };

        try {
            const response = await fetch(`${API_BASE_URL}/users`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                signupMessage.textContent = "회원가입이 완료되었습니다!";
                signupMessage.style.color = "green";
                setTimeout(() => {
                    window.location.href = "index.html"; // 회원가입 후 메인 페이지로 이동
                }, 2000);
            } else {
                throw new Error("회원가입에 실패했습니다.");
            }
        } catch (error) {
            signupMessage.textContent = error.message;
            signupMessage.style.color = "red";
        }
    });
});
