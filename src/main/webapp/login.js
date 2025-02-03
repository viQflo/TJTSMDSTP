
document.getElementById("google-login").addEventListener("click", function() {
   window.location.href = "https://accounts.google.com/o/oauth2/auth?client_id=YOUR_GOOGLE_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code&scope=email profile";
});

document.getElementById("google-login").addEventListener("click", function() {
   window.location.href = "https://accounts.google.com/o/oauth2/auth?client_id=YOUR_GOOGLE_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code&scope=email profile";
});

document.getElementById("kakao-login").addEventListener("click", function() {
   const KAKAO_CLIENT_ID = "8a582482509b48121de75cdf6846ab30";
   const REDIRECT_URI = "http://localhost:8081/TJTSMDS/api/kakao/callback";

   const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
   window.location.href = kakaoAuthUrl;
});

// ✅ 로그인 상태 확인 및 유지
function checkLoginStatus() {
   const token = localStorage.getItem("authToken");
   if (token) {
      console.log("✅ 로그인 상태 유지 중");
      document.getElementById("login-button").style.display = "none"; // 로그인 버튼 숨기기
   }
}

// ✅ URL에서 JWT 토큰 가져오기
function getTokenFromURL() {
   const urlParams = new URLSearchParams(window.location.search);
   return urlParams.get("token");
}

// ✅ 로그인 후 JWT 저장 및 페이지 이동
document.addEventListener("DOMContentLoaded", function() {
   checkLoginStatus();

   const token = getTokenFromURL();
   if (token) {
      console.log("🔵 카카오 로그인 완료, 토큰 저장 중:", token);
      localStorage.setItem("authToken", token);
      window.location.href = "Main.html"; // ✅ 로그인 후 메인 페이지 이동
   }
});

document.getElementById("naver-login").addEventListener("click", function() {
   window.location.href = "https://nid.naver.com/oauth2.0/authorize?client_id=YOUR_NAVER_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code&state=RANDOM_STATE";
});


/*document.getElementById("google-login").addEventListener("click", function() {

   window.location.href = "https://accounts.google.com/o/oauth2/auth?client_id=YOUR_GOOGLE_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code&scope=email profile";

});



document.getElementById("kakao-login").addEventListener("click", function() {

   window.location.href = "https://kauth.kakao.com/oauth/authorize?client_id=YOUR_KAKAO_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code";

});



document.getElementById("naver-login").addEventListener("click", function() {

   window.location.href = "https://nid.naver.com/oauth2.0/authorize?client_id=YOUR_NAVER_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code&state=RANDOM_STATE";

});
*/



document.addEventListener("DOMContentLoaded", function() {

   const loginForm = document.getElementById("login-form");

   const errorMessage = document.getElementById("error-message");



   //const API_BASE_URL = "http://localhost:8081/TJTSMDS/api";
   const API_BASE_URL = window.location.origin+"/" + window.location.pathname.split('/')[1]+"/api"; 


   loginForm.addEventListener("submit", function(event) {

      event.preventDefault(); // 기본 제출 동작 방지



      // 로그인 데이터 수집

      const loginData = {

         email: document.getElementById("email").value,

         pw: document.getElementById("password").value,

      };



      console.log("LoginData:", loginData);



      fetch(`${API_BASE_URL}/login`, {

         method: "POST",

         headers: { "Content-Type": "application/json; charset=UTF-8" },

         body: JSON.stringify(loginData),

      })

         .then(function(response) {

            console.log("응답 상태 코드:", response.status);



            if (response.ok) {

               return response.json();

            } else {

               return response.text().then(function(errorText) {

                  try {

                     const errorData = JSON.parse(errorText);

                     console.error("에러 데이터:", errorData);

                     throw new Error(errorData.message || "로그인에 실패했습니다.");

                  } catch (e) {

                     console.error("HTML 에러:", errorText);

                     throw new Error("서버에서 HTML 응답을 반환했습니다.");

                  }

               });

            }

         })

         .then(function(data) {

            console.log("응답 데이터:", data);

            localStorage.setItem("authToken", data.token); // 토큰을 로컬 스토리지에 저장

            errorMessage.textContent = "로그인이 성공적으로 완료되었습니다!";

            errorMessage.style.color = "green";



            setTimeout(function() {

               window.location.href = "Main.html";

            }, 1000);

         })

         .catch(function(error) {

            console.error("오류 발생:", error);

            errorMessage.textContent = `오류: ${error.message}`;

            errorMessage.style.color = "red";

         });

   });

});