package com.smhrd.controller;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.smhrd.model.BoardDAO;
import com.smhrd.model.BoardDTO;

@WebServlet("/write.do")
public class BoardWrite extends HttpServlet {
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
        String postTitle = multi.getParameter("post_title");
        String postContent = multi.getParameter("content");
        String postFile = multi.getFilesystemName("img"); // 파일 이름
        String token = multi.getParameter("token"); // JWT 토큰

        String email = null;

        // 이메일 추출: 토큰이 있을 경우 디코딩해서 이메일을 가져옴
        if (token != null) {
            email = decodeToken(token); // JWT 토큰을 디코딩하여 이메일을 가져옴
        } else {
            email = multi.getParameter("email"); // 만약 토큰이 없으면 email을 hidden 필드에서 가져옴
        }

        if (email == null) {
            response.sendRedirect("/login.html"); // 로그인 페이지로 리다이렉트
            return;
        }

        // 4. DAO 호출하여 데이터 처리
        BoardDAO dao = new BoardDAO();
        BoardDTO dto = new BoardDTO(postTitle, postContent, email, postFile); // 이메일 전달

        // 게시글 작성
        int row = dao.write(dto);

        // 5. 결과 처리
        if (row > 0) {
            // 성공 시 게시글 리스트로 리다이렉트
            response.sendRedirect("/TJTSMDS/boardlist.jsp");
        } else {
            // 실패 시 게시글 작성 페이지로 리다이렉트
            response.sendRedirect("/TJTSMDS/boardform.jsp");
        }
    }

    // JWT 토큰 디코딩 메서드
    private String decodeToken(String token) {
        String email = null;
        String[] parts = token.split("\\.");
        if (parts.length == 3) {
            String payload = parts[1];
            String decoded = new String(Base64.getDecoder().decode(payload)); // base64 디코딩
            try {
                JSONObject json = new JSONObject(decoded); // 디코딩된 JSON 데이터를 파싱
                email = json.getString("sub"); // 이메일 필드를 가져옵니다
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return email;
    }
}
