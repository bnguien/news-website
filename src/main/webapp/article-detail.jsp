<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.Article" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài viết</title>

    <style>
        body { background: #f8f9fa; }

        .detail-container {
            max-width: 900px;
            margin: 30px auto;
            background: #fff;
            padding: 25px;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .article-title { 
            font-size: 2em; 
            margin-bottom: 10px; 
        }

        .article-meta { 
            color: #666; 
            font-style: italic; 
            margin-bottom: 20px; 
        }

        .main-image { 
            width: 100%; 
            max-height: 400px; 
            object-fit: cover; 
            margin-bottom: 20px; 
            border-radius: 6px;
        }

        .article-summary { 
            font-weight: bold; 
            margin-bottom: 20px; 
            font-size: 1.1em;
        }

        .article-content { 
            line-height: 1.7; 
            font-size: 1.15em; 
            white-space: pre-line; 
        }

        .back-link {
            display: inline-block;
            margin-top: 25px;
            padding: 8px 14px;
            border-radius: 4px;
            background: #007bff;
            color: white !important;
            text-decoration: none;
        }
        .back-link:hover { background: #0056b3; }
    </style>
</head>

<body>

    <!-- ====================== HEADER ====================== -->
    <jsp:include page="header.jsp"/>

    <div class="detail-container">
        <%
            Article article = (Article) request.getAttribute("article");
            if (article != null) {
        %>
            <h1 class="article-title"><%= article.getTitle() %></h1>

            <div class="article-meta">
                Đăng bởi User #<%= article.getAuthorId() %> |
                Ngày: <%= article.getPublishedAt() %> |
                Lượt xem: <%= article.getViewCount() %>
            </div>

            <% if (article.getImage() != null && !article.getImage().trim().isEmpty()) { %>
                <img src="<%= article.getImage() %>" class="main-image" alt="Ảnh bài viết">
            <% } %>

            <% if (article.getSummary() != null && !article.getSummary().trim().isEmpty()) { %>
                <div class="article-summary">
                    <%= article.getSummary() %>
                </div>
            <% } %>

            <div class="article-content">
                <%= article.getContent() %>
            </div>

            <hr>

            <h3>Bình luận</h3>
            <p><i>(Hiển thị module bình luận tại đây…)</i></p>

        <% } else { %>
            <p style="color:red; font-size:18px;">Bài viết không tồn tại hoặc đã bị xóa.</p>
        <% } %>

        <a href="articles?action=listPublic" class="back-link">← Quay lại danh sách</a>
    </div>

    <!-- ====================== FOOTER ====================== -->
    <jsp:include page="footer.jsp"/>

</body>
</html>
