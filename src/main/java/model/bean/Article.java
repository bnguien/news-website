package model.bean;

import java.util.Date;

public class Article {
    private int articleId;
    private String title;
    private String summary;
    private String content;
    private String image;
    private int authorId;
    private int categoryId;
    private int viewCount;
    private Date publishedAt;
    private String status;

    // Constructors
    public Article() {}

    public Article(int articleId, String title, String summary, String content, String image,
                   int authorId, int categoryId, int viewCount, Date publishedAt, String status) {
        this.articleId = articleId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.image = image;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.viewCount = viewCount;
        this.publishedAt = publishedAt;
        this.status = status;
    }

    // Getters and Setters
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
