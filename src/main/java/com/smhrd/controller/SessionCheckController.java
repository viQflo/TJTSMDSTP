package com.smhrd.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smhrd.model.MemberDTO;

import io.jsonwebtoken.Jwts;

@WebServlet("/api/session-check")
public class SessionCheckController extends HttpServlet {

	private static final String SECRET_KEY = "mySecretKey";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token); // JWT 유효성 검사

				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"isLoggedIn\": true}");
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{\"isLoggedIn\": false}");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"isLoggedIn\": false}");
		}
	}
}
