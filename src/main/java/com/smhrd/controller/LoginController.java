package com.smhrd.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.model.MemberDAO;
import com.smhrd.model.MemberDTO;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {

	private final MemberDAO memberDAO = new MemberDAO();

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
				response.setStatus(HttpServletResponse.SC_OK);
				String token = generateToken(result); // JWT 토큰 생성
				response.getWriter().write("{\"token\":\"" + token + "\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{\"message\":\"이메일 또는 비밀번호가 잘못되었습니다.\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\":\"서버 내부 오류가 발생했습니다.\"}");
		}
	}

	// JWT 토큰 생성 메서드 (간단한 예시)
	private String generateToken(MemberDTO member) {
		return member.getEmail() + "_token"; // 실제로는 JWT 라이브러리를 사용해 토큰 생성
	}
}
