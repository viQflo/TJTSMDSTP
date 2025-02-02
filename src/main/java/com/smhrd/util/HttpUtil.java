package com.smhrd.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtil {

    private static final String KAKAO_CLIENT_ID = "8a582482509b48121de75cdf6846ab30";
    private static final String KAKAO_REDIRECT_URI = "http://localhost:8081/TJTSMDS/api/kakao/callback";

    public static JsonNode getKakaoAccessToken(String authCode) throws IOException {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        String postParams = "grant_type=authorization_code"
                + "&client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URI
                + "&code=" + authCode;

        // ✅ 디버깅용 로그 출력
        System.out.println("Access Token 요청 - URL: " + tokenUrl);
        System.out.println("Access Token 요청 - redirect_uri: " + KAKAO_REDIRECT_URI);
        System.out.println("Access Token 요청 - POST 데이터: " + postParams);

        return sendPostRequest(tokenUrl, postParams);
    }


    public static JsonNode getKakaoUserInfo(String accessToken) throws IOException {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
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
        int responseCode = con.getResponseCode(); // ✅ 응답 코드 체크

        System.out.println("HTTP 응답 코드: " + responseCode); // ✅ 응답 코드 출력

        InputStream stream;
        if (responseCode >= 200 && responseCode < 300) {
            stream = con.getInputStream();
        } else {
            stream = con.getErrorStream(); // ✅ 오류 발생 시 에러 스트림 읽기
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String response = in.readLine();
            System.out.println("HTTP 응답 데이터: " + response); // ✅ 응답 데이터 로그 출력
            return mapper.readTree(response);
        }
    }

}
