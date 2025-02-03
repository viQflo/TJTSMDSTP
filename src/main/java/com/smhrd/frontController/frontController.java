package com.smhrd.frontController;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class frontController extends HttpServlet {
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 무한 루프를 방지하기 위한 조건 추가
        if (request.getAttribute("processed") != null) {
            return;  // 이미 처리된 요청은 다시 처리하지 않도록 합니다.
        }

        try {
            // 요청 처리 로직
            String action = request.getParameter("action");

            // 예시: 특정 action 처리
            if ("someAction".equals(action)) {
                // 예시: 특정 처리
                request.setAttribute("processed", true);  // 처리된 요청에 플래그 추가
            }

            // 여기서 실제 로직 처리
            // 예: 요청에 따른 서블릿 처리

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        } finally {
            // 응답 후 리소스 정리 등
        }
    }
}
