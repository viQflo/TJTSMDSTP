package com.smhrd.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/chatbot")
public class GeminiChatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String GEMINI_API_KEY = "AIzaSyA0VBYvD2gt33QR8bG3HemZuezSOIM_htU"; // 🔹 본인의 API 키 입력
    private static final String GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + GEMINI_API_KEY;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        request.setCharacterEncoding("UTF-8"); // ✅ 한글 깨짐 방지
        String userMessage = request.getParameter("message");
        System.out.println("✅ [서버 로그] 사용자 메시지: " + userMessage);

        JsonObject jsonResponse = new JsonObject();

        if (userMessage == null || userMessage.trim().isEmpty()) {
            jsonResponse.addProperty("reply", "무엇이든 질문해주세요! 😊");
        } else {
            try {
                String aiReply = sendToGemini(userMessage);
                System.out.println("✅ [서버 로그] Gemini AI 응답: " + aiReply);
                jsonResponse.addProperty("reply", aiReply);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResponse.addProperty("reply", "❌ 서버 오류 발생: " + e.getMessage());
            }
        }

        response.getWriter().write(jsonResponse.toString());
    }

    private String sendToGemini(String userMessage) throws IOException {
        // ✅ 최신 Google Gemini API JSON 구조 적용
        JsonObject userMessagePart = new JsonObject();
        userMessagePart.addProperty("text", 
            "너는 친절하고 공감 능력이 뛰어난 상담사야.\n" +
            "사용자의 고민을 잘 들어주고, 따뜻한 말투로 위로해 주며, 적절한 조언을 해줘.\n" +
            "단호한 해결책보다는 감정을 존중하며 부드러운 방식으로 응답해줘.\n" +
            "사용자가 기분이 나아지도록 편안한 대화 분위기를 만들어줘.\n" +
            "사용자: " + userMessage
        );

        JsonObject userMessageRole = new JsonObject();
        userMessageRole.addProperty("role", "user");
        userMessageRole.add("parts", userMessagePart);

        JsonObject requestJson = new JsonObject();
        requestJson.add("contents", userMessageRole); // ✅ 최신 JSON 구조 적용

        URL url = new URL(GEMINI_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        System.out.println("🔄 [디버깅] Gemini API에 요청 보내는 중...");

        // ✅ JSON 데이터 전송
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestJson.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // ✅ HTTP 응답 코드 확인
        int responseCode = conn.getResponseCode();
        System.out.println("✅ [디버깅] Gemini API 응답 코드: " + responseCode);

        if (responseCode != 200) {
            System.out.println("❌ [오류] Gemini API 요청 실패. 응답 코드: " + responseCode);
            return "❌ 오류 발생: Gemini API 응답 코드 " + responseCode;
        }

        // ✅ API 응답 받기
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        // ✅ 응답 원본 출력
        System.out.println("✅ [디버깅] Gemini API 응답 원본: " + response.toString());

        // ✅ JSON 응답 파싱
        JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
        if (responseJson.has("candidates")) {
            JsonObject firstCandidate = responseJson.getAsJsonArray("candidates").get(0).getAsJsonObject();
            if (firstCandidate.has("content")) {
                JsonObject content = firstCandidate.getAsJsonObject("content");
                if (content.has("parts")) {
                    return content.getAsJsonArray("parts").get(0).getAsJsonObject().get("text").getAsString();
                }
            }
        }

        return "❌ AI가 응답을 생성하지 못했습니다.";
}
}
