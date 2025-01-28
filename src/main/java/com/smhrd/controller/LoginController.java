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

	// JWT 비밀 키
	private static final String SECRET_KEY = "mySecretKey";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ObjectMapper mapper = new ObjectMapper();
		MemberDTO memberDTO;

		try {
			// JSON 데이터 파싱
			memberDTO = mapper.readValue(request.getReader(), MemberDTO.class);
			System.out.println("로그인 요청 데이터: " + memberDTO.getEmail() + ", " + memberDTO.getPw());
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"message\":\"잘못된 JSON 데이터입니다.\"}");
			return;
		}

		try {
			// 사용자 인증 로직 처리
			MemberDTO result = memberDAO.login(memberDTO);
			if (result != null) {
				// 로그인 성공 시 JWT 생성
				String jwtToken = Jwts.builder().setSubject(result.getEmail()) // 사용자 이메일을 Subject로 설정
						.setIssuedAt(new Date()) // 발급 시간
						.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 만료 시간 (1시간)
						.signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 서명 (비밀 키 사용)
						.compact();

				// JWT 토큰을 클라이언트에 반환
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"message\":\"로그인 성공\", \"token\":\"" + jwtToken + "\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{\"message\":\"이메일 또는 비밀번호가 잘못되었습니다.\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\":\"서버 오류가 발생했습니다.\"}");
		}
	}
}
