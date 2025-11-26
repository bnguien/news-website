<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Article" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý bài viết</title>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.min.css" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet"/>

    <style>
        .container-box {
            max-width: 1100px;
            margin: 30px auto;
            background: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .status {
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 13px;
            font-weight: bold;
            text-transform: uppercase;
        }
        .draft { background: #e0e0e0; }
        .pending { background: #ffe082; }
        .published { background: #81c784; color: #fff; }
        .rejected { background: #ef9a9a; color: #fff; }
    </style>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container-box">

    <h3 class="mb-4"><i class="fas fa-newspaper me-2"></i>Quản lý bài viết</h3>

    <%
        List<Article> list = (List<Article>) request.getAttribute("adminArticles");
    %>

    <table class="table table-hover align-middle">
        <thead class="table-primary">
        <tr>
            <th>ID</th>
            <th>Tiêu đề</th>
            <th>Tác giả</th>
            <th>Ngày tạo</th>
            <th>Trạng thái</th>
            <th class="text-center">Hành động</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (list != null && !list.isEmpty()) {
                for (Article a : list) {
        %>
        <tr>
            <td><%= a.getArticleId() %></td>

            <td style="max-width: 300px;">
                <strong><%= a.getTitle() %></strong><br>
                <span style="color:#666; font-size:13px;">
                    <%= a.getSummary() %>
                </span>
            </td>

            <td>User #<%= a.getAuthorId() %></td>

            <td>
                <span class="status 
                    <%= a.getStatus().equals("draft") ? "draft" :
                        a.getStatus().equals("pending") ? "pending" :
                        a.getStatus().equals("published") ? "published" : "rejected" %>">
                    <%= a.getStatus() %>
                </span>
            </td>

            <td class="text-center">

                <a href="articles?action=viewDetail&id=<%= a.getArticleId() %>"
                   class="btn btn-sm btn-info text-white mb-1">
                    <i class="fas fa-eye"></i>
                </a>

                <a href="admin?action=editArticle&id=<%= a.getArticleId() %>"
                   class="btn btn-sm btn-warning mb-1">
                    <i class="fas fa-edit"></i>
                </a>

                <%
                    if ("pending".equals(a.getStatus())) {
                %>
                <a href="admin?action=approveArticle&id=<%= a.getArticleId() %>"
                   class="btn btn-sm btn-success mb-1">
                    <i class="fas fa-check"></i>
                </a>

                <a href="admin?action=rejectArticle&id=<%= a.getArticleId() %>"
                   class="btn btn-sm btn-danger mb-1">
                    <i class="fas fa-times"></i>
                </a>
                <% } %>

                <a href="admin?action=deleteArticle&id=<%= a.getArticleId() %>"
                   class="btn btn-sm btn-outline-danger mb-1"
                   onclick="return confirm('Xóa bài viết này?');">
                    <i class="fas fa-trash"></i>
                </a>

            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="6" class="text-center py-4">Không có bài viết nào.</td>
        </tr>
        <% } %>

        </tbody>
    </table>

</div>

<jsp:include page="footer.jsp"/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.umd.min.js"></script>

</body>
</html>
