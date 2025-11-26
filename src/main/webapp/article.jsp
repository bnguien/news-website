<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Comment" %>
<%@ page import="model.bean.Article" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài viết</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        .error-msg { color: red; font-weight: bold; }
        .success-msg { color: green; font-weight: bold; }
        .comment-item { margin-bottom: 15px; padding: 10px; border-bottom: 1px solid #ddd; }
        .comment-meta { font-size: 14px; color: #555; }
        .comment-text { margin-top: 5px; }
        .delete-btn { color: red; text-decoration: none; margin-left: 10px; }
    </style>
</head>

<body>

    <!-- ===================== HEADER ===================== -->
    <jsp:include page="header.jsp" />

    <div class="container">

        <%-- Lấy article ID từ request hoặc param --%>
        <%
            String articleIdStr = request.getParameter("id");
            if (articleIdStr == null) {
                Object idObj = request.getAttribute("article_id");
                if (idObj != null) articleIdStr = idObj.toString();
            }
            if (articleIdStr == null) articleIdStr = "0";
        %>

        <!-- ===================== NỘI DUNG BÀI VIẾT ===================== -->
        <div class="article-content">
            <h2><%= ((Article) request.getAttribute("article")).getTitle() %></h2>

            <p style="color: #777;">
                Chuyên mục:
                <%= ((Article) request.getAttribute("article")).getCategoryId() %>
            </p>

            <img src="<%= ((Article) request.getAttribute("article")).getImage() %>"
                 alt="Thumbnail"
                 style="width: 100%; max-height: 420px; object-fit: cover; margin-bottom: 20px;">

            <p><%= ((Article) request.getAttribute("article")).getContent() %></p>
        </div>

        <hr>

        <!-- ===================== THÔNG BÁO ===================== -->
        <%
            String errorCode = (String) request.getAttribute("errorCode");
            String msg = request.getParameter("msg");

            if ("NOT_LOGGED_IN".equals(errorCode)) {
        %>
            <p class="error-msg">Bạn cần đăng nhập để bình luận.</p>

        <% } else if ("EMPTY_CONTENT".equals(errorCode)) { %>
            <p class="error-msg">Nội dung bình luận không được trống.</p>

        <% } else if ("SERVER_ERROR".equals(errorCode)) { %>
            <p class="error-msg">Lỗi hệ thống, vui lòng thử lại.</p>

        <% } else if ("comment_added".equals(msg)) { %>
            <p class="success-msg">Đã thêm bình luận.</p>

        <% } else if ("comment_deleted".equals(msg)) { %>
            <p class="success-msg">Đã xóa bình luận.</p>
        <% } %>

        <!-- ===================== FORM BÌNH LUẬN ===================== -->
        <div class="comment-section">
            <h3>Bình luận</h3>

            <form action="CommentController" method="post" class="comment-form">
                <input type="hidden" name="action" value="addComment">
                <input type="hidden" name="article_id" value="<%= articleIdStr %>">

                <textarea name="content" rows="4" placeholder="Nhập bình luận..."></textarea><br>
                <button type="submit">Gửi bình luận</button>
            </form>

            <!-- ===================== LIST COMMENT ===================== -->
            <div class="comment-list">
                <%
                    List<Comment> listComments = (List<Comment>) request.getAttribute("comments");
                    Integer currentUserId = (Integer) session.getAttribute("user_id");

                    if (listComments != null && !listComments.isEmpty()) {
                        for (Comment c : listComments) {
                %>

                    <div class="comment-item">
                        <div class="comment-meta">
                            <strong>User #<%= c.getUserId() %></strong>
                            <span>- <%= c.getCreatedAt() %></span>

                            <% if (currentUserId != null && currentUserId == c.getUserId()) { %>
                                <a class="delete-btn"
                                   href="CommentController?action=deleteComment&comment_id=<%= c.getCommentId() %>&article_id=<%= c.getArticleId() %>"
                                   onclick="return confirm('Bạn có chắc muốn xóa không?')">
                                   Xóa
                                </a>
                            <% } %>
                        </div>

                        <div class="comment-text">
                            <%= c.getContent() %>
                        </div>
                    </div>

                <%
                        }
                    } else {
                %>
                    <p>Chưa có bình luận nào.</p>
                <% } %>
            </div>

        </div>

    </div>

    <!-- ===================== FOOTER ===================== -->
    <jsp:include page="footer.jsp" />

</body>
</html>
