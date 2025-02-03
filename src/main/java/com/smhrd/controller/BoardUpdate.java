package com.smhrd.controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.smhrd.model.BoardDAO;
import com.smhrd.model.BoardDTO;

@WebServlet("/BoardUpdate.do")
public class BoardUpdate extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        // 2. 파일 업로드 설정
        ServletContext context = request.getServletContext();
        String uploadPath = context.getRealPath("upload"); // 업로드 경로
        int maxSize = 500 * 1024 * 124; // 최대 파일 크기 5MB

        // MultipartRequest 객체 생성
        MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());

        // 3. 폼 데이터 가져오기
        String postTitle = multi.getParameter("title");
        String postContent = multi.getParameter("content");
        int postIdx = Integer.parseInt(multi.getParameter("idx"));
        String postFile = multi.getFilesystemName("img"); // 파일 이름

        // 4. DAO 호출하여 데이터 처리
        BoardDAO dao = new BoardDAO();
        BoardDTO dto = new BoardDTO(postTitle, postContent, postIdx, postFile);

        // 게시글 수정
        int row = dao.updateBoard(dto);

        // 5. 결과 처리
        if (row > 0) {
            // 성공 시 게시글 리스트로 리다이렉트
            response.sendRedirect("boardlist.jsp");
        } else {
            // 실패 시 게시글 상세 페이지로 리다이렉트
            response.sendRedirect("boardcontent.jsp?idx=" + postIdx);
        }
    }
}
