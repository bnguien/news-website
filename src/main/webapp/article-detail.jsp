<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.Article" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài viết</title>
    <style>
        .detail-container { max-width: 800px; margin: 0 auto; }
        .article-title { font-size: 2em; margin-bottom: 10px; }
        .article-meta { color: #666; font-style: italic; margin-bottom: 20px; }
        .article-content { line-height: 1.6; font-size: 1.1em; }
        .main-image { width: 100%; max-height: 400px; object-fit: cover; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="detail-container">
        <%
            Article article = (Article) request.getAttribute("article");
            if (article != null) {
        %>
            <h1 class="article-title"><%= article.getTitle() %></h1>
            
            <div class="article-meta">
                Đăng bởi: User #<%= article.getAuthorId() %> | Ngày: <%= article.getPublishedAt() %> | Views: <%= article.getViewCount() %>
            </div>

            <% if (article.getImage() != null && !article.getImage().isEmpty()) { %>
                <img src="<%= article.getImage() %>" class="main-image" alt="Article Image">
            <% } %>

            <div class="article-summary" style="font-weight: bold; margin-bottom: 20px;">
                <%= article.getSummary() %>
            </div>

            <div class="article-content">
                <%= article.getContent() %> </div>

            <hr>
            <h3>Bình luận</h3>
            <p><i>(Include comment section here...)</i></p>

        <% } else { %>
            <p style="color:red">Bài viết không tồn tại hoặc đã bị xóa.</p>
        <% } %>
        
        <a href="articles?action=listPublic">Quay lại danh sách</a>
    </div>
</body>
</html>