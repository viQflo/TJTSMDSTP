package com.smhrd.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NaverLoginUtil {
    private static final String CLIENT_ID = "Eicmp9EZTakx68iOqOQQ";
    private static final String CLIENT_SECRET = "oCJSQIYtxj";
    private static final String REDIRECT_URI = "http://localhost:8081/TJTSMDS/api/naver/callback";

    // ✅ Access Token 요청
    public static JsonNode getNaverAccessToken(String authCode, String state) throws IOException {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        String postParams = "grant_type=authorization_code"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&code=" + authCode
                + "&state=" + state;

        return sendPostRequest(tokenUrl, postParams);
    }

    // ✅ 사용자 정보 요청
    public static JsonNode getNaverUserInfo(String accessToken) throws IOException {
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
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
        int responseCode = con.getResponseCode();
        InputStream stream = (responseCode >= 200 && responseCode < 300) ? con.getInputStream() : con.getErrorStream();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String response = in.readLine();
            System.out.println("🔵 [NaverLoginUtil] HTTP 응답 본문: " + response);
            return mapper.readTree(response);
        }
    }
}
