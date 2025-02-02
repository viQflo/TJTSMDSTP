document.addEventListener("DOMContentLoaded", function() {
	const navbar = document.querySelector(".nav-links");

	// 로고 클릭 시 메인 페이지(main.html)로 이동
	document.getElementById("homeButton").addEventListener("click", () => {
		window.location.href = "Main.html"; // main.html 경로로 이동
	});

	// 로컬 스토리지에서 토큰 가져오기
	const token = localStorage.getItem("authToken");

	if (token) {
		// window.location.origin+"/" + window.location.pathname.split('/')[1]+"/api/"; 
		// 토큰이 있는 경우 JWT 인증을 통해 세션 상태 확인
		fetch("http://localhost:8081/TJTSMDS/api/session-check", {
			method: "GET",
			headers: {
				"Authorization": `Bearer ${token}`, // JWT 토큰을 Authorization 헤더에 포함
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
					// 로그인 상태일 경우: 네비게이션 바 변경
					navbar.innerHTML = `
                        <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
                        <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
                        <button class="nav-button" data-path="Board.html">커뮤니티</button>
                        <button class="nav-button" data-path="mypage.jsp">마이페이지</button>
                        <button class="nav-button" id="logout-button">로그아웃</button>
                    `;
					// 로그아웃 버튼 클릭 시 토큰 삭제 후 로그아웃 처리
					document.getElementById("logout-button").addEventListener("click", () => {
						localStorage.removeItem("authToken"); // 로컬 스토리지에서 토큰 삭제
						alert("로그아웃되었습니다.");
						window.location.href = "Main.html";
					});
				} else {
					// 비로그인 상태일 경우: 네비게이션 바 변경
					navbar.innerHTML = `
                        <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
                        <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
                        <button class="nav-button" data-path="Board.html">커뮤니티</button>
                        <button class="nav-button" data-path="login.html">로그인</button>
                    `;
				}

				// 네비게이션 바 버튼 클릭 시 해당 경로로 이동 (이벤트 위임)
				navbar.addEventListener("click", (event) => {
					const button = event.target.closest(".nav-button");
					if (button) {
						const path = button.getAttribute("data-path");
						if (path) {
							window.location.href = path; // data-path 경로로 이동
						}
					}
				});
			})
			.catch((error) => {
				console.error("세션 확인 실패:", error.message);
				navbar.innerHTML = `
                    <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
                    <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
                    <button class="nav-button" data-path="Board.html">커뮤니티</button>
                    <button class="nav-button" data-path="login.html">로그인</button>
                `;
			});
	} else {
		// 토큰이 없으면 비로그인 상태로 처리
		navbar.innerHTML = `
            <button class="nav-button" data-path="voucher.jsp">바우처 정보</button>
            <button class="nav-button" data-path="counsel.html">상담 연결서비스</button>
            <button class="nav-button" data-path="Board.html">커뮤니티</button>
            <button class="nav-button" data-path="login.html">로그인</button>
        `;
	}

	// 회원가입 및 로그인 버튼 처리 (메인 콘텐츠 부분)
	const redirectButtonHandler = (url) => {
		alert(`${url} 페이지로 이동합니다.`);
		window.location.href = `${url}.html`;
	};

	const signupButton = document.getElementById("signupButton");
	if (signupButton) {
		signupButton.addEventListener("click", () => redirectButtonHandler("signup"));
	}

	const loginButton = document.getElementById("loginButton");
	if (loginButton) {
		loginButton.addEventListener("click", () => redirectButtonHandler("login"));
	}

	// 네비게이션 바 버튼 클릭 시 이동 처리 (이벤트 위임)
	document.querySelectorAll(".nav-button").forEach((button) => {
		button.addEventListener("click", () => {
			const path = button.getAttribute("data-path"); // data-path 속성 읽기
			if (path) {
				window.location.href = path; // data-path 경로로 이동
			}
		});
	});
});