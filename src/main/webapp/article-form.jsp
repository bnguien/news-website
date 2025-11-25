<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Category" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm bài viết mới</title>
    <script src="https://cdn.ckeditor.com/4.22.1/standard/ckeditor.js"></script>
    
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f5f5f5; padding: 20px; }
        .container { background: #fff; padding: 30px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); max-width: 960px; margin: 0 auto; }
        
        h2 { border-bottom: 2px solid #007bff; padding-bottom: 10px; margin-top: 0; color: #333; }

        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; color: #555; }
        
        
        input[name="title"] {
            width: 100%; padding: 10px; font-size: 16px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
        }

        
        .meta-info {
            background-color: #f9f9f9;
            padding: 15px;
            border: 1px solid #eee;
            border-radius: 4px;
            margin-bottom: 20px;
            display: grid;
            grid-template-columns: 1fr 1fr; /* Chia 2 cột */
            gap: 20px;
        }

        input[type="file"], select, textarea[name="summary"] {
            width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
        }

        /* Nút bấm */
        .btn-submit {
            background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; margin-top: 10px;
        }
        .btn-submit:hover { background-color: #0056b3; }
        .btn-cancel {
            background-color: #6c757d; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; margin-left: 10px; text-decoration: none; display: inline-block;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Thêm bài viết mới</h2>

    <form action="articles" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="submitForReview">
        <input type="hidden" name="csrfToken" value="<%= request.getAttribute("csrfToken") %>">

        <div class="form-group">
            <label>Tiêu đề bài viết:</label>
            <input type="text" name="title" required placeholder="Nhập tiêu đề tại đây...">
        </div>

        <div class="meta-info">
            <div class="form-group">
                <label>Chuyên mục:</label>
                <select name="categoryId">
                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("categories");
                        if (categories != null) {
                            for (Category cat : categories) {
                    %>
                        <option value="<%= cat.getCategoryId() %>"><%= cat.getName() %></option>
                    <%
                            }
                        } else {
                    %>
                        <option value="1">Mặc định</option>
                    <% } %>
                </select>
            </div>

            <div class="form-group">
                <label>Ảnh đại diện (Thumbnail):</label>
                <input type="file" name="image" accept="image/*" required>
            </div>

            <div class="form-group" style="grid-column: span 2;">
                <label>Tóm tắt nội dung:</label>
                <textarea name="summary" rows="3" placeholder="Nhập mô tả ngắn gọn về bài viết..." required></textarea>
            </div>
        </div>

        <div class="form-group">
            <label>Nội dung chi tiết:</label>
            <textarea name="content" id="content-editor" rows="10" required></textarea>
        </div>

        <div style="text-align: right;">
            <button type="submit" class="btn-submit">Đăng bài</button>
            <a href="articles?action=listPublic" class="btn-cancel">Hủy</a>
        </div>
    </form>
</div>

<script>
    CKEDITOR.replace('content-editor');
</script>

</body>
</html>