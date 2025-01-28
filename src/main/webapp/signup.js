document.addEventListener("DOMContentLoaded", function() {
	const signupForm = document.getElementById("signup-form");
	const signupMessage = document.getElementById("signup-message");

	// API Base URL
	const API_BASE_URL = "http://localhost:8081/TJTSMDS/api";

	// 회원가입 폼 제출 이벤트 리스너
	signupForm.addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 제출 동작 방지

		// 폼 데이터 수집
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
			create_dt: new Date().toISOString().split("T")[0], // 현재 날짜
		};
		console.log("FormData:", formData);
		// 디버깅용 로그
		console.log("회원가입 데이터:", formData);

		// API 요청
		fetch(`${API_BASE_URL}/users`, {
			method: "POST",
			headers: { "Content-Type": "application/json; charset=UTF-8" },
			body: JSON.stringify(formData),
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
							throw new Error(errorData.message || "회원가입에 실패했습니다.");
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
				signupMessage.textContent = "회원가입이 완료되었습니다!";
				signupMessage.style.color = "green";

				// 2초 후 메인 페이지로 이동
				setTimeout(function() {
					window.location.href = "Main.html";
				}, 2000);
			})
			.catch(function(error) {
				// 에러 처리
				console.error("오류 발생:", error);
				signupMessage.textContent = `오류: ${error.message}`;
				signupMessage.style.color = "red";
			});
	});
});
