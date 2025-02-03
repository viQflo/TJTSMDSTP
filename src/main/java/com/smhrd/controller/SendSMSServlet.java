package com.smhrd.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Instant;

@WebServlet("/SendSMSServlet")
public class SendSMSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String API_KEY = "NCS9KPNWCKAU3AEV";
    private static final String API_SECRET = "MAQOHKYFON17MEBXPABNAGUE2XROUQ2M";
    private static final String SENDER_PHONE = "01094102178"; // ✅ 하이픈 제거
    private static final String RECEIVER_PHONE = "01036019957"; // ✅ 하이픈 제거

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String counselorName = request.getParameter("counselorName");
        String csCharge = request.getParameter("csCharge");

        System.out.println("DEBUG: SMS 요청 받음 - 상담사: " + counselorName + ", 분야: " + csCharge);

        String message = "[알림] " + counselorName + " 상담사가 배정되었습니다. 분야: " + csCharge;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            System.out.println("DEBUG: SMS API 요청 시작");

            URL url = new URL("https://api.coolsms.co.kr/messages/v4/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // ✅ CoolSMS는 HMAC-SHA256 방식의 인증을 요구함
            String timestamp = String.valueOf(Instant.now().getEpochSecond());
            String signature = generateSignature(timestamp);

            conn.setRequestProperty("Authorization", "HMAC-SHA256 " + signature);
            conn.setRequestProperty("Date", timestamp);
            conn.setRequestProperty("Api-Key", API_KEY);
            conn.setDoOutput(true);

            // ✅ 필수 필드 추가
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("app_version", "JAVA-API-1.0"); // 필수 필드
            jsonPayload.put("type", "SMS");

            JSONArray messagesArray = new JSONArray();
            JSONObject messageObject = new JSONObject();
            messageObject.put("to", RECEIVER_PHONE);
            messageObject.put("from", SENDER_PHONE);
            messageObject.put("text", message);
            messagesArray.put(messageObject);

            jsonPayload.put("messages", messagesArray);

            // ✅ JSON 데이터 전송
            OutputStream os = conn.getOutputStream();
            os.write(jsonPayload.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            // ✅ 응답 코드 확인
            int responseCode = conn.getResponseCode();
            System.out.println("DEBUG: SMS API 응답 코드 = " + responseCode);

            // ✅ 응답 본문 확인
            String responseBody;
            if (responseCode == 200) {
                try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                    responseBody = scanner.useDelimiter("\\A").next();
                }
            } else {
                try (Scanner scanner = new Scanner(conn.getErrorStream(), StandardCharsets.UTF_8)) {
                    responseBody = scanner.useDelimiter("\\A").next();
                }
            }
            System.out.println("DEBUG: SMS API 응답 본문 = " + responseBody);

            // ✅ JSON 응답을 클라이언트로 반환
            JSONObject jsonResponse = new JSONObject(responseBody);
            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: SMS 전송 중 오류 발생 - " + e.getMessage());

            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "SMS 전송 중 오류 발생: " + e.getMessage());

            response.getWriter().write(errorResponse.toString());
        }
    }

    // ✅ CoolSMS HMAC-SHA256 Signature 생성 함수
    private String generateSignature(String timestamp) throws Exception {
        String message = timestamp + API_KEY;
        Mac hasher = Mac.getInstance("HmacSHA256");
        hasher.init(new SecretKeySpec(API_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] hash = hasher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
