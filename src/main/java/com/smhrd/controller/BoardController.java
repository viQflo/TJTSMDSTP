package com.smhrd.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smhrd.model.BoardDAO;
import com.smhrd.model.BoardDTO;

@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BoardDAO boardDAO = new BoardDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                List<BoardDTO> posts = boardDAO.getAllPosts();
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("Board.jsp").forward(request, response);
                break;
            case "view":
                int postIdx = Integer.parseInt(request.getParameter("postIdx"));
                BoardDTO post = boardDAO.getPostById(postIdx);
                request.setAttribute("post", post);
                request.getRequestDispatcher("ViewPost.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "write":
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String file = request.getParameter("file");
                String email = request.getParameter("email");
                
                BoardDTO newPost = new BoardDTO(0, title, content, file, null, 0, 0, email);
                boardDAO.insertPost(newPost);
                response.sendRedirect("BoardController?action=list");
                break;
            case "update":
                int postIdx = Integer.parseInt(request.getParameter("postIdx"));
                String updatedTitle = request.getParameter("title");
                String updatedContent = request.getParameter("content");
                String updatedFile = request.getParameter("file");
                
                BoardDTO updatedPost = new BoardDTO(postIdx, updatedTitle, updatedContent, updatedFile, null, 0, 0, null);
                boardDAO.updatePost(updatedPost);
                response.sendRedirect("BoardController?action=view&postIdx=" + postIdx);
                break;
            case "delete":
                int deletePostIdx = Integer.parseInt(request.getParameter("postIdx"));
                boardDAO.deletePost(deletePostIdx);
                response.sendRedirect("BoardController?action=list");
                break;
        }
    }
}
