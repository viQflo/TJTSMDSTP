<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="com.smhrd.model.BoardDTO"%>
<%@ page import="com.smhrd.model.BoardDAO"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<%
    // 게시글 번호를 URL 파라미터에서 가져오기
    int idx = Integer.parseInt(request.getParameter("idx"));
    // idx 값을 이용하여 해당 idx의 게시글 전체 정보를 가져오는 메서드 getBoard 사용
    BoardDAO dao = new BoardDAO();
    // idx 값에 해당하는 한개의 게시글에 대한 전체 정보는 board에 담김
    BoardDTO board = dao.getBoardContent(idx);
    
    // 세션에서 이메일을 가져오기
    HttpSession Session = request.getSession();
    String email = (String) Session.getAttribute("userEmail"); // 로그인된 이메일 가져오기
%>

<div class="container">
  <h2>게시글 수정</h2>
  <div class="panel panel-default">
    <div class="panel-heading">게시글 수정</div>
    <div class="panel-body">
        <form class="form-horizontal" action="BoardUpdate.do" method="post" enctype="multipart/form-data">
        
            <!-- 게시글 번호 (읽기 전용) -->
            <div class="form-group">
              <label class="control-label col-sm-2" for="idx">게시글 번호:</label>
              <div class="col-sm-10">
                <input type="text" readonly class="form-control" id="idx" name="idx" value="<%= board.getPostIdx() %>">
              </div>
            </div>

            <!-- 제목 입력 -->
            <div class="form-group">
              <label class="control-label col-sm-2" for="title">제목:</label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" value="<%= board.getPostTitle() %>">
              </div>
            </div>

            <!-- 내용 입력 -->
            <div class="form-group">
              <label class="control-label col-sm-2" for="content">내용:</label>
              <div class="col-sm-10">          
                <textarea rows="10" name="content" class="form-control"><%= board.getPostContent() %></textarea>
              </div>
            </div>

            <!-- 작성자 (세션에서 가져온 이메일로 자동 채워짐) -->
            <div class="form-group">
              <label class="control-label col-sm-2" for="writer">작성자:</label>
              <div class="col-sm-10">          
                <input type="text" readonly name="writer" class="form-control" value="<%= email %>">
              </div>
            </div>

            <!-- 이미지 업로드 -->
            <div class="form-group">
              <label class="control-label col-sm-2" for="img">이미지:</label>
              <div class="col-sm-10">          
                <input type="file" name="img" class="form-control">
              </div>
            </div>

            <!-- 버튼들 -->
            <div class="form-group">        
              <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success btn-default">수정</button>
                <button type="reset" class="btn btn-warning btn-default">리셋</button>
              </div>
            </div>
        </form>
    </div>
    <div class="panel-footer">빅데이터 분석서비스 개발자과정 (담임 : 강인훈)</div>
  </div>
</div>

</body>
</html>
