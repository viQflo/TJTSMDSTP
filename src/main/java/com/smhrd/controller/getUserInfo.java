package com.smhrd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.smhrd.model.MemberDAO;
import com.smhrd.model.MemberDTO;

@WebServlet("/api/getUserInfo")
public class getUserInfo extends HttpServlet {
    // ✅ 기존 SECRET_KEY 유지 (로그인과 동일한 방식)
    private static final String SECRET_KEY = "mySecretKey";  

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ✅ Authorization 헤더에서 토큰 가져오기
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization 검증: " + authHeader);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("✅ 토큰 값: " + token);

        try {
            // ✅ 기존 방식과 동일한 방식으로 검증
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(SECRET_KEY) // ✅ 기존 방식 유지
                .parseClaimsJws(token);

            String userEmail = claimsJws.getBody().getSubject();
            System.out.println("✅ JWT에서 추출한 이메일: " + userEmail);

            // ✅ DB에서 사용자 정보 가져오기
            MemberDAO memberDAO = new MemberDAO();
            MemberDTO member = memberDAO.getMemberByEmail(userEmail);

            if (member != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(String.format(
                    "{\"email\": \"%s\", \"name\": \"%s\", \"phone\": \"%s\", \"job\": \"%s\"}",
                    member.getEmail(), member.getName(), member.getPhone(), member.getJob()
                ));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println("❌ JWT 검증 실패: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
