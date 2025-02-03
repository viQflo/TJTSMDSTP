console.log("챗봇 스크립트 로드됨");  //
document.addEventListener("DOMContentLoaded", function () {
    const chatbotIcon = document.getElementById("chatbot-icon");
    const chatbotModal = document.getElementById("chatbot-modal");
    const chatbotClose = document.getElementById("chatbot-close");
    const chatbotMessages = document.getElementById("chatbot-messages");
    const chatbotInput = document.getElementById("chatbot-input");
    const chatbotSend = document.getElementById("chatbot-send");

    // 챗봇 아이콘 클릭 -> 모달 열기
    chatbotIcon.addEventListener("click", function () {
        chatbotModal.style.display = "flex";
    });

    // 닫기 버튼 클릭 -> 모달 닫기
    chatbotClose.addEventListener("click", function () {
        chatbotModal.style.display = "none";
    });

    // 메시지 전송
    chatbotSend.addEventListener("click", function () {
        sendMessage();
    });

    chatbotInput.addEventListener("keypress", function (e) {
        if (e.key === "Enter") sendMessage();
    });

	function sendMessage() {
	    const userMessage = chatbotInput.value.trim();
	    if (!userMessage) return;

	    chatbotMessages.innerHTML += `<div class='chatbot-message user-message'>${userMessage}</div>`;
	    chatbotInput.value = "";

	    console.log("✅ [디버깅] 서버로 메시지 전송: ", userMessage);

	    fetch("http://localhost:8081/TJTSMDS/chatbot", {
	        method: "POST",
	        headers: { "Content-Type": "application/x-www-form-urlencoded" },
	        body: "message=" + encodeURIComponent(userMessage)
	    })
	    .then(response => {
	        console.log("✅ [디버깅] 서버 응답 상태 코드:", response.status);
	        return response.json();
	    })
	    .then(data => {
	        console.log("✅ [디버깅] Gemini AI 응답:", data);
	        chatbotMessages.innerHTML += `<div class='chatbot-message bot-message'>${data.reply}</div>`;
	    })
	    .catch(error => {
	        console.error("❌ [오류] 서버와 통신 중 문제가 발생했습니다:", error);
	        chatbotMessages.innerHTML += `<div class='chatbot-message bot-message'>오류가 발생했습니다.</div>`;
	    });
	}
	
});
