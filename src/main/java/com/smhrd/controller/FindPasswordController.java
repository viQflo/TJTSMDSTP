package com.smhrd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.smhrd.model.MemberDAO;

@WebServlet("/FindPasswordController")
public class FindPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String email = request.getParameter("email");
        
        MemberDAO dao = new MemberDAO();
        String password = dao.findPasswordByEmail(email);
        
        request.setAttribute("foundPassword", password);
        request.getRequestDispatcher("find_password.jsp").forward(request, response);
    }
}
