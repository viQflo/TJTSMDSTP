package com.smhrd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smhrd.model.BoardDAO;


@WebServlet("/BoardDelete")
public class BoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩
		request.setCharacterEncoding("UTF-8");
		// 2. request객체에서 데이터 가져오기
		int idx = Integer.parseInt(request.getParameter("idx"));
		// 3. BoardDAO의 deleteBoard메서드 호출		
		//	  -> deleteBoard메서드는 idx번호를 기준으로 해서 해당 게시글 삭제
		// 	  -> 해당 메서드는 DB에 접근해서 게시글 삭제
		BoardDAO dao = new BoardDAO();
		// delete sql문을 실행했을때, 영향 받는 행의 개수!
		int row = dao.deleteBoard(idx);
		// 4. 결과값 처리
		if(row>0) {
			System.out.println("삭제 완료");
			response.sendRedirect("boardlist.jsp");
		}else {
			System.out.println("삭제 실패");
			response.sendRedirect("boardlist.jsp");
		}
		
		
	}

}
