document.addEventListener("DOMContentLoaded", function() {
	const loginForm = document.getElementById("login-form");
	const errorMessage = document.getElementById("error-message");

	// API Base URL
	const API_BASE_URL = "http://localhost:8081/TJTSMDS/api";

	// 로그인 폼 제출 이벤트 리스너
	loginForm.addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 제출 동작 방지

		// 로그인 데이터 수집
		const loginData = {
			email: document.getElementById("email").value,
			pw: document.getElementById("password").value,
		};

		console.log("LoginData:", loginData); // 디버깅용 로그

		// API 요청
		fetch(`${API_BASE_URL}/login`, {
			method: "POST",
			headers: { "Content-Type": "application/json; charset=UTF-8" },
			body: JSON.stringify(loginData),
		})
			.then(function(response) {
				console.log("응답 상태 코드:", response.status);

				// 요청 성공 여부 판단
				if (response.ok) {
					// JSON 데이터 반환
					return response.json();
				} else {
					// 실패 시 HTML 또는 JSON 응답 처리
					return response.text().then(function(errorText) {
						try {
							// JSON 응답 처리
							const errorData = JSON.parse(errorText);
							console.error("에러 데이터:", errorData);
							throw new Error(errorData.message || "로그인에 실패했습니다.");
						} catch (e) {
							// HTML 에러 응답 처리
							console.error("HTML 에러:", errorText);
							throw new Error("서버에서 HTML 응답을 반환했습니다.");
						}
					});
				}
			})
			.then(function(data) {
				// 요청 성공 처리
				console.log("응답 데이터:", data);
				localStorage.setItem("token", data.token); // JWT 토큰 저장
				errorMessage.textContent = "로그인이 성공적으로 완료되었습니다!";
				errorMessage.style.color = "green";

				// 2초 후 메인 페이지로 이동
				setTimeout(function() {
					window.location.href = "Main.html";
				}, 1000);
			})
			.catch(function(error) {
				// 에러 처리
				console.error("오류 발생:", error);
				errorMessage.textContent = `오류: ${error.message}`;
				errorMessage.style.color = "red";
			});
	});
});
