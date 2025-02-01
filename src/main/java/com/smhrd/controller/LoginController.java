package com.smhrd.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.model.MemberDAO;
import com.smhrd.model.MemberDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {

    private final MemberDAO memberDAO = new MemberDAO();
    private static final String SECRET_KEY = "mySecretKey";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        MemberDTO memberDTO;

        try {
            memberDTO = mapper.readValue(request.getReader(), MemberDTO.class);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"잘못된 JSON 데이터입니다.\"}");
            return;
        }

        try {
            MemberDTO result = memberDAO.login(memberDTO);

            if (result == null) {
                // ⚠️ 기존 로그인 정보가 없으면, 소셜 로그인 계정이 연동되었는지 확인
                result = memberDAO.findBySocialEmail(memberDTO.getEmail());

                if (result == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"message\":\"이메일 또는 비밀번호가 잘못되었습니다.\"}");
                    return;
                }
            }

            // ✅ JWT 생성
            String jwtToken = Jwts.builder()
                    .setSubject(result.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"로그인 성공\", \"token\":\"" + jwtToken + "\"}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"서버 오류가 발생했습니다.\"}");
        }
    }
}

