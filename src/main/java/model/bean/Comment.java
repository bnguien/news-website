package model.bean;

import java.util.Date;

public class Comment {
    private int commentId;
    private int articleId;
    private int userId;
    private String content;
    private Date createdAt;

    // Constructors
    public Comment() {}

    public Comment(int commentId, int articleId, int userId, String content, Date createdAt) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
