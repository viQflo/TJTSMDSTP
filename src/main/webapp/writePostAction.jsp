<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="javax.servlet.annotation.MultipartConfig"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="io.jsonwebtoken.Jwts, io.jsonwebtoken.Claims"%>
<%@ page import="com.smhrd.model.BoardDTO, com.smhrd.model.BoardDAO"%>
<%@ page
	import="javax.servlet.http.Part, java.io.*, java.nio.file.Paths, java.util.Collection"%>


<%
// 1️⃣ JWT 토큰 검증
String token = request.getHeader("Authorization");
System.out.print(token);

if (token == null || !token.startsWith("Bearer ")) {
	response.setContentType("application/json");
	response.getWriter().write("{\"success\": false, \"message\": \"인증되지 않은 요청입니다.\"}");
	return;
}

// 'Bearer ' 부분 제거
token = token.substring(7);
String secretKey = "mySecretKey";
String userEmail = null;

try {
	Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	userEmail = claims.getSubject();
} catch (Exception e) {
	response.setContentType("application/json");
	response.getWriter().write("{\"success\": false, \"message\": \"토큰 검증 실패\"}");
	return;
}

// 2️⃣ `request.getParts()`를 사용하여 데이터 받기
String postTitle = null;
String postContent = null;
String fileName = "";

Collection<Part> parts = request.getParts();
for (Part part : parts) {
	String fieldName = part.getName();

	if ("postTitle".equals(fieldName)) {
		postTitle = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8")).readLine();
	} else if ("postContent".equals(fieldName)) {
		postContent = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8")).readLine();
	} else if ("postFile".equals(fieldName) && part.getSize() > 0) {
		fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		String uploadPath = application.getRealPath("/") + "uploads";
		part.write(uploadPath + File.separator + fileName);
	}
}

// ✅ 값 디버깅
System.out.println("제목: " + postTitle);
System.out.println("내용: " + postContent);
System.out.println("파일: " + fileName);

// 3️⃣ NULL 방지
if (postTitle == null || postTitle.trim().isEmpty())
	postTitle = "제목 없음";
if (postContent == null || postContent.trim().isEmpty())
	postContent = "내용 없음";

// 3️⃣ DTO 생성 후 DAO를 통해 DB에 삽입
BoardDTO board = new BoardDTO();
board.setPostTitle(postTitle);
board.setPostContent(postContent);
board.setPostFile(fileName);
board.setPostViews(0);
board.setPostLikes(0);
board.setEmail(userEmail);

BoardDAO boardDAO = new BoardDAO();
int result = boardDAO.insertBoard(board);

// 4️⃣ 결과 응답
response.setContentType("application/json");
if (result > 0) {
	response.getWriter().write("{\"success\": true, \"message\": \"게시글 작성 완료\"}");
} else {
	response.getWriter().write("{\"success\": false, \"message\": \"게시글 저장 실패\"}");
}
%>
