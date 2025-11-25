<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Comment" %>
<%@ page import="model.bean.Article" %> <!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài viết</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .error-msg { color: red; font-weight: bold; }
        .success-msg { color: green; font-weight: bold; }
    </style>
</head>
<body>

    <div class="header">
        <h1>Báo Điện Tử</h1>
        <% 
            // Kiểm tra session user_id để hiển thị nút Đăng nhập/Đăng xuất
            Integer currentUserId = (Integer) session.getAttribute("user_id");
            if(currentUserId != null) {
        %>
            <span>Xin chào, User ID: <%= currentUserId %></span> | <a href="LogoutController">Đăng xuất</a>
        <% } else { %>
            <a href="login.jsp">Đăng nhập</a>
        <% } %>
    </div>

    <div class="container">
        <%
            // Lấy article_id từ tham số URL hoặc attribute để dùng cho form
            String articleIdStr = request.getParameter("id");
            if (articleIdStr == null) articleIdStr = (String) request.getAttribute("article_id");
            // Code hiển thị bài viết ở đây...
        %>
        
        <div class="article-content">
            <h2>Tiêu đề bài viết (Demo)</h2>
            <p>Nội dung bài viết mẫu... ID bài viết: <%= articleIdStr %></p>
        </div>

        <hr>

        <%
            String errorCode = (String) request.getAttribute("errorCode");
            String msg = request.getParameter("msg"); // Lấy từ URL khi redirect

            if ("NOT_LOGGED_IN".equals(errorCode)) {
        %>
            <p class="error-msg">Bạn vui lòng đăng nhập để thực hiện chức năng này.</p>
        <% } else if ("EMPTY_CONTENT".equals(errorCode)) { %>
            <p class="error-msg">Nội dung bình luận không được để trống!</p>
        <% } else if ("SERVER_ERROR".equals(errorCode)) { %>
            <p class="error-msg">Lỗi hệ thống, vui lòng thử lại sau.</p>
        <% }
        
            if ("comment_added".equals(msg)) {
        %>
            <p class="success-msg">Thêm bình luận thành công!</p>
        <% } else if ("comment_deleted".equals(msg)) { %>
            <p class="success-msg">Đã xóa bình luận.</p>
        <% } %>


        <div class="comment-section">
            <h3>Bình luận</h3>
            
            <form action="CommentController" method="post" class="comment-form">
                <input type="hidden" name="action" value="addComment">
                <input type="hidden" name="article_id" value="<%= articleIdStr %>">
                
                <textarea name="content" rows="4" placeholder="Nhập bình luận của bạn..."></textarea>
                <br>
                <button type="submit">Gửi bình luận</button>
            </form>

            <div class="comment-list">
                <%
                    List<Comment> listComments = (List<Comment>) request.getAttribute("comments");
                    if (listComments != null && !listComments.isEmpty()) {
                        for (Comment c : listComments) {
                %>
                    <div class="comment-item">
                        <div class="comment-meta">
                            <strong>User #<%= c.getUserId() %></strong> 
                            <span>- <%= c.getCreatedAt() %></span>
                            
                            <% if (currentUserId != null && currentUserId == c.getUserId()) { %>
                                <a href="CommentController?action=deleteComment&comment_id=<%= c.getCommentId() %>&article_id=<%= c.getArticleId() %>" 
                                   onclick="return confirm('Bạn có chắc muốn xóa?')" 
                                   class="delete-btn">Xóa</a>
                            <% } %>
                        </div>
                        <div class="comment-text">
                            <%= c.getContent() %>
                        </div>
                    </div>
                <%      }
                    } else {
                %>
                    <p>Chưa có bình luận nào.</p>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>