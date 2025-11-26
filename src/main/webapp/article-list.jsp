<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Article" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách bài viết</title>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.min.css" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>

    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; padding: 20px; }
        .article-container { max-width: 900px; margin: 0 auto; background: #fff; padding: 20px;
                             border-radius: 6px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        h1 { margin-top: 0; padding-bottom: 10px; border-bottom: 2px solid #007bff; }

        .article-item { border-bottom: 1px solid #ddd; padding: 15px 0; display: flex; gap: 20px; }
        .article-item:last-child { border-bottom: none; }

        .article-img { width: 150px; height: 100px; object-fit: cover; border-radius: 4px; }
        .article-info { flex: 1; }
        .article-info h3 { margin: 0; }
        .article-info a { text-decoration: none; color: #007bff; }
        .article-info a:hover { text-decoration: underline; }
        .summary { margin: 8px 0; color: #444; }

        .pagination { margin-top: 20px; text-align: center; }
        .pagination a { padding: 8px 12px; border: 1px solid #ccc; text-decoration: none;
                        margin-right: 5px; border-radius: 4px; color: #333; }
        .pagination a:hover { background: #e9ecef; }
        .pagination .active { background-color: #007bff; color: white; border-color: #007bff; }
    </style>
</head>

<body>

    <jsp:include page="header.jsp"/>

    <div class="article-container">

        <h1>Danh sách bài viết</h1>

        <div class="list-container">
            <%
                List<Article> articles = (List<Article>) request.getAttribute("articles");

                if (articles != null && !articles.isEmpty()) {
                    for (Article a : articles) {
                        String img = (a.getImage() != null && !a.getImage().trim().isEmpty())
                                     ? a.getImage()
                                     : "images/no-image.png";
            %>

                <div class="article-item">
                    <img src="<%= img %>" class="article-img" alt="thumbnail">

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

            <%
                }
            %>
        </div>

        <div class="pagination">
            <%
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                Integer currentPage = (Integer) request.getAttribute("currentPage");

                if (totalPages != null && totalPages > 1) {
                    for (int i = 1; i <= totalPages; i++) {
                        String active = (currentPage != null && currentPage == i) ? "active" : "";
            %>

                <a href="articles?action=listPublic&page=<%= i %>" class="<%= active %>"><%= i %></a>

            <%
                    }
                }
            %>
        </div>

    </div>

    <jsp:include page="footer.jsp"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.umd.min.js"></script>
</body>
</html>
