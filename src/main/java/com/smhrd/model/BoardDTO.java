package com.smhrd.model;

import java.sql.Timestamp;

public class BoardDTO {
    private int postIdx;
    private String postTitle;
    private String postContent;
    private String postFile;
    private Timestamp createDt;
    private int postViews;
    private int postLikes;
    private String email;

    public BoardDTO() {}

    public BoardDTO(int postIdx, String postTitle, String postContent, String postFile, Timestamp createDt, int postViews, int postLikes, String email) {
        this.postIdx = postIdx;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postFile = postFile;
        this.createDt = createDt;
        this.postViews = postViews;
        this.postLikes = postLikes;
        this.email = email;
    }

    public int getPostIdx() { return postIdx; }
    public void setPostIdx(int postIdx) { this.postIdx = postIdx; }

    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }

    public String getPostContent() { return postContent; }
    public void setPostContent(String postContent) { this.postContent = postContent; }

    public String getPostFile() { return postFile; }
    public void setPostFile(String postFile) { this.postFile = postFile; }

    public Timestamp getCreateDt() { return createDt; }
    public void setCreateDt(Timestamp createDt) { this.createDt = createDt; }

    public int getPostViews() { return postViews; }
    public void setPostViews(int postViews) { this.postViews = postViews; }

    public int getPostLikes() { return postLikes; }
    public void setPostLikes(int postLikes) { this.postLikes = postLikes; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
