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
import com.smhrd.util.NaverLoginUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebServlet("/api/naver/callback")
public class NaverLoginController extends HttpServlet {

    private final MemberDAO memberDAO = new MemberDAO();
    private static final String SECRET_KEY = "mySecretKey";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔵 [NaverLoginController] 네이버 로그인 요청 시작!");

        String authCode = request.getParameter("code");
        String state = request.getParameter("state");

        if (authCode == null || state == null) {
            System.out.println("❌ [NaverLoginController] 인증 코드 또는 state 값 없음!");
            response.sendRedirect("/TJTSMDS/login.html?error=auth_code_missing");
            return;
        }

        JsonNode tokenResponse = NaverLoginUtil.getNaverAccessToken(authCode, state);
        if (tokenResponse == null || !tokenResponse.has("access_token")) {
            System.out.println("❌ [NaverLoginController] Access Token 요청 실패!");
            response.sendRedirect("/TJTSMDS/login.html?error=token_failed");
            return;
        }

        String accessToken = tokenResponse.get("access_token").asText();
        JsonNode userInfo = NaverLoginUtil.getNaverUserInfo(accessToken);
        if (userInfo == null || !userInfo.has("response")) {
            System.out.println("❌ [NaverLoginController] 사용자 정보 요청 실패!");
            response.sendRedirect("/TJTSMDS/login.html?error=user_info_failed");
            return;
        }

        JsonNode responseNode = userInfo.get("response");
        String naverEmail = responseNode.get("email").asText();
        String name = responseNode.get("name").asText();

        System.out.println("✅ [NaverLoginController] 사용자 이메일: " + naverEmail);

        MemberDTO member = memberDAO.findByEmail(naverEmail);
        if (member == null) {
            member = new MemberDTO();
            member.setEmail(naverEmail);
            member.setName(name);
            member.setNick(name);
            member.setSocialProvider("naver");
            member.setPw("SOCIAL_" + System.currentTimeMillis());
            memberDAO.insertSocialMember(member);
        } else {
            member.setSocialLinkedEmail(naverEmail);
            member.setSocialProvider("naver");
            memberDAO.updateSocialLink(member);
        }

        String jwtToken = Jwts.builder()
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        System.out.println("✅ [NaverLoginController] JWT 생성 완료!");

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(
                "<html><head>" +
                "<meta http-equiv='refresh' content='0;url=/TJTSMDS/Main.html'>" +
                "<script>" +
                "localStorage.setItem('authToken', '" + jwtToken + "');" +
                "</script></head><body></body></html>"
        );
    }
}
