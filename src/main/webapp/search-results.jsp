<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Article" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm</title>

    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: #fff; padding: 20px;
                     border-radius: 6px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }

        h1 { margin-top: 0; border-bottom: 2px solid #007bff; padding-bottom: 10px; }

        ul { padding: 0; list-style: none; }
        li { padding: 15px 0; border-bottom: 1px solid #ddd; }
        li:last-child { border-bottom: none; }

        a { color: #007bff; text-decoration: none; }
        a:hover { text-decoration: underline; }

        .summary { margin: 8px 0; color: #444; }

        .pagination { margin-top: 20px; text-align: center; }
        .pagination a {
            padding: 8px 12px; border: 1px solid #ccc; border-radius: 4px;
            text-decoration: none; margin-right: 5px; color: #333;
        }
        .pagination a:hover { background: #e9ecef; }
        .pagination .active { background: #007bff; color: #fff; border-color: #007bff; }
    </style>

</head>
<body>
    <div class="container">
        <h1>Kết quả tìm kiếm</h1>

        <%
            String keyword = (String) request.getAttribute("criteria");
        %>

        <p>Từ khóa: <strong>"<%= keyword %>"</strong></p>

        <ul>
            <%
                List<Article> articles = (List<Article>) request.getAttribute("articles");

                if (articles != null && !articles.isEmpty()) {
                    for (Article a : articles) {
            %>
                <li>
                    <a href="articles?action=viewDetail&id=<%= a.getArticleId() %>">
                        <%= a.getTitle() %>
                    </a>
                    <p class="summary"><%= a.getSummary() %></p>
                </li>
            <%
                    }
                } else {
            %>
                <p>Không tìm thấy bài viết nào phù hợp.</p>
            <% } %>
        </ul>

        <div class="pagination">
            <%
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                Integer currentPage = (Integer) request.getAttribute("currentPage");

                if (totalPages != null && totalPages > 1) {
                    for (int i = 1; i <= totalPages; i++) {
                        String active = (currentPage != null && currentPage == i) ? "active" : "";
            %>
                <a href="articles?action=searchPublic&criteria=<%= keyword %>&page=<%= i %>"
                   class="<%= active %>"><%= i %></a>
            <%
                    }
                }
            %>
        </div>
    </div>
</body>
</html>
