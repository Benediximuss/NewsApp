package com.uguroztunc.cs310news;

import java.io.Serializable;

public class CommentItem implements Serializable {

    private int commentID;
    private int newsID;
    private String username;
    private String commentText;

    public CommentItem(int commentID, int newsID, String username, String commentText) {
        this.commentID = commentID;
        this.newsID = newsID;
        this.username = username;
        this.commentText = commentText;
    }

    public CommentItem(int newsID, String username, String commentText) {
        this.newsID = newsID;
        this.username = username;
        this.commentText = commentText;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
