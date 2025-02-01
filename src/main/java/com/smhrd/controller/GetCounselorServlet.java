package com.smhrd.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.smhrd.model.CounselorDAO;
import com.smhrd.model.CounselorDTO;

@WebServlet("/GetCounselorServlet")
public class GetCounselorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        String location = request.getParameter("location");
        String csCharge = request.getParameter("cs_charge");

        System.out.println("servlet");
        System.out.println("DEBUG: 서블릿에서 받은 location = " + location);
        System.out.println("DEBUG: 서블릿에서 받은 csCharge = " + csCharge);

        CounselorDAO dao = new CounselorDAO();
        List<CounselorDTO> counselors = dao.getFilteredCounselors(location, csCharge);

        if (counselors == null || counselors.isEmpty()) {
            System.out.println("DEBUG: 서버에서 반환할 상담사 데이터 없음.");
            response.getWriter().write("[a]"); // 빈 배열 반환
            return;
        }

        System.out.println("DEBUG: 서버에서 보낼 데이터 개수 = " + counselors.size());
        String json = new Gson().toJson(counselors);
        response.getWriter().write(json);
    }
}
