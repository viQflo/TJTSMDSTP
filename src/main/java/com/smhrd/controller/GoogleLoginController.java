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
import com.smhrd.util.GoogleLoginUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebServlet("/api/google/callback")
public class GoogleLoginController extends HttpServlet {

    private final MemberDAO memberDAO = new MemberDAO();
    private static final String SECRET_KEY = "mySecretKey";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔵 [GoogleLoginController] Google 로그인 요청 시작!");

        String authCode = request.getParameter("code");
        if (authCode == null) {
            System.out.println("❌ [GoogleLoginController] authCode가 없음!");
            response.sendRedirect("/TJTSMDS/login.html?error=auth_code_missing");
            return;
        }

        System.out.println("✅ [GoogleLoginController] 받은 authCode: " + authCode);

        // ✅ Google Access Token 요청
        JsonNode tokenResponse = GoogleLoginUtil.getGoogleAccessToken(authCode);

        if (tokenResponse == null) {
            System.out.println("❌ [GoogleLoginController] Access Token 요청 실패! 응답이 null");
            response.sendRedirect("/TJTSMDS/login.html?error=token_failed");
            return;
        }

        System.out.println("✅ [GoogleLoginController] Access Token 응답: " + tokenResponse.toString());

        if (!tokenResponse.has("access_token")) {
            System.out.println("❌ [GoogleLoginController] Access Token이 응답에 없음!");
            response.sendRedirect("/TJTSMDS/login.html?error=invalid_token_response");
            return;
        }

        String accessToken = tokenResponse.get("access_token").asText();
        System.out.println("✅ [GoogleLoginController] 받은 Access Token: " + accessToken);

        // ✅ Google 사용자 정보 요청
        JsonNode userInfo = GoogleLoginUtil.getGoogleUserInfo(accessToken);

        if (userInfo == null) {
            System.out.println("❌ [GoogleLoginController] 사용자 정보 요청 실패! 응답이 null");
            response.sendRedirect("/TJTSMDS/login.html?error=user_info_failed");
            return;
        }

        System.out.println("✅ [GoogleLoginController] 사용자 정보 응답: " + userInfo.toString());

        if (!userInfo.has("email")) {
            System.out.println("❌ [GoogleLoginController] 응답에 email이 없음!");
            response.sendRedirect("/TJTSMDS/login.html?error=user_info_invalid");
            return;
        }

        String googleEmail = userInfo.get("email").asText();
        String name = userInfo.has("name") ? userInfo.get("name").asText() : "GoogleUser";
        System.out.println("✅ [GoogleLoginController] 사용자 이메일: " + googleEmail);
        System.out.println("✅ [GoogleLoginController] 사용자 이름: " + name);

        // ✅ DB에서 이메일로 회원 조회
        MemberDTO member = memberDAO.findByEmail(googleEmail);

        if (member == null) {
            System.out.println("🟡 [GoogleLoginController] 기존 회원 없음 → 신규 가입 진행");

            member = new MemberDTO();
            member.setEmail(googleEmail);
            member.setName(name);
            member.setNick(name);
            member.setSocialProvider("google");
            member.setPw("SOCIAL_" + System.currentTimeMillis());

            // 신규 회원 추가
            int result = memberDAO.insertSocialMember(member);
            if (result > 0) {
                System.out.println("✅ [GoogleLoginController] 신규 회원 가입 성공!");
            } else {
                System.out.println("❌ [GoogleLoginController] 신규 회원 가입 실패!");
                response.sendRedirect("/TJTSMDS/login.html?error=db_insert_failed");
                return;
            }
        } else {
            System.out.println("✅ [GoogleLoginController] 기존 회원 존재! 이메일: " + member.getEmail());

            // ✅ 기존 계정과 Google 계정 연동
            member.setSocialLinkedEmail(googleEmail);
            member.setSocialProvider("google");
            int updateResult = memberDAO.updateSocialLink(member);

            if (updateResult > 0) {
                System.out.println("✅ [GoogleLoginController] 계정 연동 성공!");
            } else {
                System.out.println("❌ [GoogleLoginController] 계정 연동 실패!");
                response.sendRedirect("/TJTSMDS/login.html?error=db_update_failed");
                return;
            }
        }

        // ✅ JWT 생성 및 로그인 처리
        String jwtToken = Jwts.builder()
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        System.out.println("✅ [GoogleLoginController] JWT 생성 완료!");

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
