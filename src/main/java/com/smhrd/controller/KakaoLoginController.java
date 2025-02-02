package com.smhrd.controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.smhrd.model.MemberDAO;
import com.smhrd.model.MemberDTO;
import com.smhrd.util.HttpUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebServlet("/api/kakao/callback")
public class KakaoLoginController extends HttpServlet {

    private final MemberDAO memberDAO = new MemberDAO();
    private static final String SECRET_KEY = "mySecretKey";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String authCode = request.getParameter("code");
        if (authCode == null) {
            response.sendRedirect("/TJTSMDS/login.html?error=auth_code_missing");
            return;
        }

        JsonNode tokenResponse = HttpUtil.getKakaoAccessToken(authCode);
        if (tokenResponse == null || tokenResponse.get("access_token") == null) {
            response.sendRedirect("/TJTSMDS/login.html?error=token_failed");
            return;
        }

        String accessToken = tokenResponse.get("access_token").asText();
        JsonNode userInfo = HttpUtil.getKakaoUserInfo(accessToken);
        if (userInfo == null) {
            response.sendRedirect("/TJTSMDS/login.html?error=user_info_failed");
            return;
        }

        JsonNode kakaoAccount = userInfo.get("kakao_account");
        String socialEmail = (kakaoAccount != null && kakaoAccount.has("email")) ? kakaoAccount.get("email").asText() : null;
        String nickname = userInfo.has("properties") && userInfo.get("properties").has("nickname") ? userInfo.get("properties").get("nickname").asText() : null;

        long randomValue = System.currentTimeMillis();
        if (socialEmail == null) {
            socialEmail = "kakao_" + randomValue + "@kakao.com";
        }
        if (nickname == null) {
            nickname = "KAKAOUSER_" + randomValue;
        }

        String name = "KAKAOUSER";

        MemberDTO member = memberDAO.findBySocialEmail(socialEmail);

        if (member == null) {
            member = new MemberDTO();
            member.setEmail(socialEmail);
            member.setName(name);
            member.setNick(nickname);
            member.setSocialProvider("kakao");
            member.setPw("SOCIAL_" + randomValue);

            try {
                memberDAO.insertSocialMember(member);
            } catch (Exception e) {
                member = memberDAO.findBySocialEmail(socialEmail);
            }
        }

        String jwtToken = Jwts.builder()
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // ✅ `Main.html`의 절대 경로로 이동하도록 수정
        response.getWriter().write("<script>" +
                "localStorage.setItem('authToken', '" + jwtToken + "');" +
                "window.location.href = '/TJTSMDS/Main.html';" + 
                "</script>");
    }
}
