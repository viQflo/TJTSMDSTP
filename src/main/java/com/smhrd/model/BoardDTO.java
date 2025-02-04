package com.smhrd.model;

public class BoardDTO {
    private int postIdx;         // 글 식별자
    private String postTitle;    // 글 제목
    private String postContent;  // 글 내용
    private String postFile;     // 글 첨부파일
    private int postViews;       // 글 조회수
    private int postLikes;       // 글 좋아요수
    private String email;        // 글 작성자 (userEmail)

    // 기본 생성자
    public BoardDTO() {}

    // 모든 필드를 초기화하는 생성자
    public BoardDTO(int postIdx, String postTitle, String postContent, String postFile, int postViews, int postLikes, String email) {
        this.postIdx = postIdx;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postFile = postFile;
        this.postViews = postViews;
        this.postLikes = postLikes;
        this.email = email;
    }

    // Getter 및 Setter
    public int getPostIdx() {
        return postIdx;
    }

    public void setPostIdx(int postIdx) {
        this.postIdx = postIdx;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostFile() {
        return postFile;
    }

    public void setPostFile(String postFile) {
        this.postFile = postFile;
    }

    public int getPostViews() {
        return postViews;
    }

    public void setPostViews(int postViews) {
        this.postViews = postViews;
    }

    public int getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(int postLikes) {
        this.postLikes = postLikes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    @Override
    public String toString() {
        return "BoardDTO{" +
                "postIdx=" + postIdx +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postFile='" + postFile + '\'' +
                ", postViews=" + postViews +
                ", postLikes=" + postLikes +
                ", email='" + email + '\'' +
                '}';
    }
}
