package com.smhrd.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.model.CounselorDAO;
import com.smhrd.model.CounselorDTO;
import com.smhrd.model.MemberDAO;
import com.smhrd.model.MemberDTO;

@WebServlet("/api/users")
public class JoinController extends HttpServlet {

	private final MemberDAO memberDAO = new MemberDAO();
	private final CounselorDAO counselorDAO = new CounselorDAO();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		MemberDTO memberDTO;
		CounselorDTO counselorDTO = null;

		try {
			BufferedReader reader = request.getReader();
			StringBuilder jsonBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonBuilder.append(line);
			}

			System.out.println("수신된 JSON 데이터: " + jsonBuilder.toString());

			memberDTO = mapper.readValue(jsonBuilder.toString(), MemberDTO.class);

			// 만약 user_type이 "상담"이면 추가 정보도 받아서 DTO로 변환
			if ("상담".equals(memberDTO.getUserType())) {
				counselorDTO = mapper.readValue(jsonBuilder.toString(), CounselorDTO.class);
				counselorDTO.setCsId(memberDTO.getEmail()); // CS_ID는 EMAIL 참조
				counselorDTO.setCsApproved("N"); // 승인 상태 기본값
			}

		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"message\":\"잘못된 JSON 데이터입니다.\"}");
			return;
		}

		try {
			int result = memberDAO.insertMember(memberDTO);

			if (result > 0) {
				// 상담사일 경우 추가 정보도 저장
				if ("상담".equals(memberDTO.getUserType()) && counselorDTO != null) {
					int counselorResult = counselorDAO.insertCounselorInfo(counselorDTO);
					if (counselorResult <= 0) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().write("{\"message\":\"상담사 추가 정보 등록 실패\"}");
						return;
					}
				}

				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"message\":\"회원가입이 완료되었습니다!\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"message\":\"회원가입에 실패했습니다.\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\":\"서버 내부 오류가 발생했습니다.\"}");
		}
	}
}
