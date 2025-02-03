package com.smhrd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.smhrd.model.MemberDAO;
import com.smhrd.model.MemberDTO;

@WebServlet("/FindIdController")
public class FindIdController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        
        MemberDAO dao = new MemberDAO();
        String email = dao.findEmailByNameAndPhone(name, phone);
        
        request.setAttribute("foundEmail", email); // 찾은 이메일 전달
        request.getRequestDispatcher("find_id.jsp").forward(request, response); // 같은 JSP에서 모달로 처리
    }
}
