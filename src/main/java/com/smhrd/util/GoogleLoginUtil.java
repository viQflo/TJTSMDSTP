package com.smhrd.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleLoginUtil {
    private static final String CLIENT_ID = "74295089266-1kqp4bt7r1u1d4t04eiuc3k5d07cammr.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8081/TJTSMDS/api/google/callback";

    public static JsonNode getGoogleAccessToken(String authCode) throws IOException {
        String tokenUrl = "https://oauth2.googleapis.com/token";
        String postParams = "grant_type=authorization_code"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&code=" + authCode;

        System.out.println("🔵 [GoogleLoginUtil] Google Access Token 요청 시작");
        System.out.println("🔵 [GoogleLoginUtil] 요청 URL: " + tokenUrl);
        System.out.println("🔵 [GoogleLoginUtil] 요청 본문: " + postParams);

        return sendPostRequest(tokenUrl, postParams);
    }
    public static JsonNode getGoogleUserInfo(String accessToken) throws IOException {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
        return sendGetRequest(userInfoUrl, accessToken);
    }

    private static JsonNode sendPostRequest(String url, String params) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()))) {
            writer.write(params);
            writer.flush();
        }
        
        // ✅ 응답 디버깅
        int responseCode = con.getResponseCode();
        System.out.println("🔴 Google OAuth 응답 코드: " + responseCode);
        
        if (responseCode != 200) {
            System.out.println("⚠️ Google OAuth 요청 실패! 에러 응답 받음");
            return null;
        }


        return readResponse(con);
    }

    private static JsonNode sendGetRequest(String url, String accessToken) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);

        return readResponse(con);
    }

    private static JsonNode readResponse(HttpURLConnection con) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream;
        
        int responseCode = con.getResponseCode();
        System.out.println("🔵 [GoogleLoginUtil] HTTP 응답 코드: " + responseCode);

        if (responseCode >= 200 && responseCode < 300) {
            stream = con.getInputStream();
        } else {
            stream = con.getErrorStream();
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();

            // ✅ 실제 응답을 콘솔에 출력하여 확인
            System.out.println("🔵 [GoogleLoginUtil] HTTP 응답 본문: " + response);

            // ✅ 응답이 빈 값인지 확인
            if (response.isEmpty()) {
                System.out.println("❌ [GoogleLoginUtil] 응답이 비어 있음!");
                return null;
            }

            return mapper.readTree(response); // JSON 변환
        } catch (Exception e) {
            System.out.println("❌ [GoogleLoginUtil] 응답 처리 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
}
