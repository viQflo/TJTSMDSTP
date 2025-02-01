package com.smhrd.controller;

import com.smhrd.model.VoucherDAO;
import com.smhrd.model.VoucherDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@WebServlet("/Voucher")
public class VoucherController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("🚀 VoucherController 실행됨!");

        VoucherDAO dao = new VoucherDAO();
        List<VoucherDTO> vouchers = dao.getVouchers();

        System.out.println("📌 DAO에서 가져온 바우처 개수: " + vouchers.size()); // 데이터 개수 확인

        request.setAttribute("vouchers", vouchers);
        System.out.println("✅ JSP로 데이터 전달 완료!");

        request.getRequestDispatcher("voucher.jsp").forward(request, response);
    }
}

