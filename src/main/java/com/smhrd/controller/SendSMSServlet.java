package com.smhrd.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

@WebServlet("/SendSMSServlet")
public class SendSMSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(SendSMSServlet.class.getName()); // 🔹 로그 추가

    // Aligo API 정보
    private static final String USER_ID = "stork2178";  // Aligo 계정 ID
    private static final String API_KEY = "t4qrkux5oynwqjmu62s92t47uqzujey8"; // Aligo API Key
    private static final String SENDER = "01094102178"; // 발신번호 (사전에 등록된 번호)
    private static final String RECEIVER = "01098765432"; // 문자 받을 번호 (고정)

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("🔹 SendSMSServlet 호출됨 (POST 요청 수신)");

        // 요청에서 message 값 가져오기
        String message = request.getParameter("message");
        if (message == null || message.isEmpty()) {
            message = "상담 신청과 관련하여 메시지가 도착했습니다"; // 기본 메시지
        }

        logger.info("✅ 메시지 내용: " + message);

        // Aligo API 엔드포인트
        String sms_url = "https://apis.aligo.in/send/";

        // HTTP 요청 데이터 구성
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("user_id", USER_ID));
        params.add(new BasicNameValuePair("key", API_KEY));
        params.add(new BasicNameValuePair("sender", SENDER));
        params.add(new BasicNameValuePair("receiver", RECEIVER));
        params.add(new BasicNameValuePair("msg", message));
        params.add(new BasicNameValuePair("testmode_yn", "Y")); // 실제 발송: "N", 테스트: "Y"

        logger.info("🔹 API 요청 데이터 준비 완료");

        // ✅ HttpClient 객체 생성 (CloseableHttpClient 사용)
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(sms_url);
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            logger.info("🔹 HTTP POST 요청 생성 완료");

            // ✅ HTTP 요청 실행 및 응답 받기 (CloseableHttpResponse 사용)
            try (CloseableHttpResponse res = client.execute(post)) {
                logger.info("✅ Aligo API 요청 완료, 응답 수신");

                BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));

                StringBuilder result = new StringBuilder();
                String buffer;
                while ((buffer = in.readLine()) != null) {
                    result.append(buffer);
                }
                in.close();

                logger.info("✅ Aligo API 응답: " + result.toString());

                // JSON 응답
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"result\": \"" + result.toString() + "\"}");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ 오류 발생", e);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
