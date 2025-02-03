package com.smhrd.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/SendSMSServlet")
public class SendSMSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String API_KEY = "NCSGAD0D5KNDW9OR"; 
    private static final String API_SECRET = "PEYPCQMBHEFGQUY4A46AZAOREMWQXCBZ"; 
    private static final String SENDER_PHONE = "01094102178"; // 발신번호 (등록된 번호)
    private static final String RECEIVER_PHONE = "01036019957"; // 수신번호

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String counselorName = request.getParameter("counselorName"); 
        String csCharge = request.getParameter("csCharge"); 

        System.out.println("DEBUG: SMS 요청 받음 - 상담사: " + counselorName + ", 분야: " + csCharge);

        String message = "[알림] " + counselorName + " 상담사가 배정되었습니다. 분야: " + csCharge;

        response.setCharacterEncoding("UTF-8"); // ✅ 응답 인코딩을 UTF-8로 설정
        response.setContentType("application/json"); // ✅ JSON 응답 설정

        try {
            System.out.println("DEBUG: SMS API 요청 시작");

            // ✅ 최신 CoolSMS API 엔드포인트
            URL url = new URL("https://api.coolsms.co.kr/messages/v4/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Authorization", "user " + API_KEY);
            conn.setDoOutput(true);

            // ✅ JSON 데이터 올바른 형식으로 생성
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("apiKey", API_KEY); // ✅ 필수 필드

            JSONArray messagesArray = new JSONArray();
            JSONObject messageObject = new JSONObject();
            messageObject.put("to", RECEIVER_PHONE);
            messageObject.put("from", SENDER_PHONE);
            messageObject.put("text", message);
            messageObject.put("type", "LMS"); // ✅ 문자 길이 초과 방지 (SMS → LMS)

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

            JSONObject jsonResponse = new JSONObject();
            if (responseCode == 200) {
                System.out.println("DEBUG: SMS 전송 성공!");
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "SMS 전송 성공!");
            } else {
                System.err.println("ERROR: SMS 전송 실패! 응답 코드: " + responseCode);
                byte[] errorStream = conn.getErrorStream().readAllBytes();
                String errorMessage = new String(errorStream, StandardCharsets.UTF_8);
                System.err.println("ERROR: 응답 메시지 - " + errorMessage);
                
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "SMS 전송 실패! 응답 코드: " + responseCode + ", 오류 메시지: " + errorMessage);
            }

            response.getWriter().write(jsonResponse.toString()); // ✅ JSON 형식으로 응답

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: SMS 전송 중 오류 발생 - " + e.getMessage());

            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "SMS 전송 중 오류 발생: " + e.getMessage());
            
            response.getWriter().write(errorResponse.toString()); // ✅ JSON 응답 반환
        }
    }
}
