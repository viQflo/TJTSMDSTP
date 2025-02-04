<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smhrd.model.BoardDAO"%>

<%
    // 요청에서 게시글 ID 가져오기
    String postIdxParam = request.getParameter("postIdx");
    if (postIdxParam != null && !postIdxParam.isEmpty()) {
        int postIdx = Integer.parseInt(postIdxParam);

        // DAO에서 좋아요 증가 실행
        BoardDAO boardDAO = new BoardDAO();
        boardDAO.incrementLikeCount(postIdx);

        // 증가된 좋아요 개수 가져오기
        int newLikes = boardDAO.getPostById(postIdx).getPostLikes();

        // 숫자만 출력 (HTML 없음)
        out.print(newLikes);
    }
%>
