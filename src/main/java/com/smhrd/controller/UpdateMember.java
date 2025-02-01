package com.smhrd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smhrd.model.MemberDAO;

@WebServlet("/UpdateMember")
public class UpdateMember extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        MemberDAO dao = new MemberDAO();
        int result = dao.updatePhone(email, phone);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (result > 0) {
            response.getWriter().write("{\"status\": \"success\"}");
        } else {
            response.getWriter().write("{\"status\": \"error\"}");
        }
    }
}
