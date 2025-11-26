<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Danh Mục</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; margin: 40px; }
        .container {
            max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        h2 { color: #007bff; text-align: center; }
        label { display: block; margin: 15px 0 8px; font-weight: bold; color: #333; }
        input[type="text"], textarea {
            width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 6px; font-size: 16px;
        }
        textarea { resize: vertical; }
        .btn-group { margin-top: 25px; text-align: center; }
        button {
            background: #007bff; color: white; padding: 12px 30px; border: none; border-radius: 6px;
            font-size: 16px; cursor: pointer;
        }
        button:hover { background: #0056b3; }
        a.back {
            display: inline-block; margin-left: 15px; color: #6c757d; text-decoration: none;
            padding: 12px 25px; border: 1px solid #ddd; border-radius: 6px;
        }
        a.back:hover { background: #f8f9fa; }
    </style>
</head>
<body>

<%
    model.bean.Category category = (model.bean.Category) request.getAttribute("category");
%>

<div class="container">
    <h2>SỬA DANH MỤC</h2>

    <% if (category == null) { %>
        <p style="color:red; text-align:center;">Không tìm thấy danh mục!</p>
        <p style="text-align:center;"><a href="admin-category.jsp">← Quay lại</a></p>
    <% } else { %>

        <form action="category" method="POST">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= category.getCategoryId() %>">

            <div>
                <label>ID:</label>
                <input type="text" value="<%= category.getCategoryId() %>" disabled style="background:#f0f0f0;">
            </div>

            <div>
                <label>Tên Danh Mục *</label>
                <input type="text" name="name" value="<%= category.getName() %>" required maxlength="100">
            </div>

            <div>
                <label>Mô Tả</label>
                <textarea name="description" rows="4" placeholder="Mô tả ngắn về danh mục (không bắt buộc)"><%= category.getDescription() %></textarea>
            </div>

            <div class="btn-group">
                <button type="submit">Cập Nhật Danh Mục</button>
                <a href="admin-category.jsp" class="back">Hủy bỏ</a>
            </div>
        </form>

    <% } %>

</div>

</body>
</html>
