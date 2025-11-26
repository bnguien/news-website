<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Article" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm</title>
    </head>
<body>
    <div class="container">
        <h1>Kết quả tìm kiếm</h1>
        
        <p>Từ khóa: <strong>"<%= request.getAttribute("criteria") %>"</strong></p>

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
                    <p><%= a.getSummary() %></p>
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
                String criteria = (String) request.getAttribute("criteria");
                if (totalPages != null && totalPages > 1) {
                    for (int i = 1; i <= totalPages; i++) {
            %>
                <a href="articles?action=searchPublic&criteria=<%= criteria %>&page=<%= i %>"><%= i %></a>
            <%
                    }
                }
            %>
        </div>
    </div>
</body>
</html>