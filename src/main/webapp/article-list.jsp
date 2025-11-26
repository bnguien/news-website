<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Article" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách bài viết</title>
    <style>
        .article-item { border-bottom: 1px solid #ddd; padding: 15px 0; display: flex; gap: 20px; }
        .article-img { width: 150px; height: 100px; object-fit: cover; background: #eee; }
        .pagination { margin-top: 20px; }
        .pagination a { padding: 8px 12px; border: 1px solid #ccc; text-decoration: none; margin-right: 5px; }
        .pagination .active { background-color: #007bff; color: white; border-color: #007bff; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Danh sách bài viết</h1>

        <div class="list-container">
            <%
                List<Article> articles = (List<Article>) request.getAttribute("articles");
                if (articles != null && !articles.isEmpty()) {
                    for (Article a : articles) {
            %>
                <div class="article-item">
                    <img src="<%= (a.getImage() != null && !a.getImage().isEmpty()) ? a.getImage() : "images/no-image.png" %>" class="article-img">
                    
                    <div class="article-info">
                        <h3>
                            <a href="articles?action=viewDetail&id=<%= a.getArticleId() %>">
                                <%= a.getTitle() %>
                            </a>
                        </h3>
                        <p class="summary"><%= a.getSummary() %></p>
                        <small>Ngày đăng: <%= a.getPublishedAt() %> | Views: <%= a.getViewCount() %></small>
                    </div>
                </div>
            <%
                    }
                } else {
            %>
                <p>Không có bài viết nào.</p>
            <% } %>
        </div>

        <div class="pagination">
            <%
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                String currentAction = request.getParameter("action");
                if (currentAction == null) currentAction = "listPublic";
                
                if (totalPages != null && totalPages > 1) {
                    for (int i = 1; i <= totalPages; i++) {
                        String activeClass = (currentPage != null && currentPage == i) ? "active" : "";
            %>
                <a href="articles?action=<%= currentAction %>&page=<%= i %>" class="<%= activeClass %>"><%= i %></a>
            <%
                    }
                }
            %>
        </div>
    </div>
</body>
</html>