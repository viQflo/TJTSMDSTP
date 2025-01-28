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

@WebServlet("/TJTSMDS/api/users")
public class JoinController extends HttpServlet {

	private final MemberDAO memberDAO = new MemberDAO();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// JSON 데이터 파싱
		ObjectMapper mapper = new ObjectMapper();
		MemberDTO memberDTO = mapper.readValue(request.getReader(), MemberDTO.class);

		try {
			// 회원가입 로직 처리
			int result = memberDAO.insertMember(memberDTO);

			if (result > 0) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"message\":\"회원가입이 완료되었습니다!\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"message\":\"회원가입에 실패했습니다.\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\":\"서버 오류가 발생했습니다.\"}");
		}
	}
}
