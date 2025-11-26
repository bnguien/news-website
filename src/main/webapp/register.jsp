<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>

    <style>
        body { background: #f8f9fa; font-family: Arial; }
        .container { max-width: 480px; margin: 40px auto; background: #fff; padding: 30px;
                     border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
        label { font-weight: 600; margin-bottom: 5px; display: block; }
        input, select { width: 100%; padding: 10px; margin-bottom: 15px;
                        border-radius: 5px; border: 1px solid #ddd; }
        button { width: 100%; background: #1A73E8; color: #fff; padding: 12px;
                 border: none; border-radius: 5px; font-size: 16px; cursor: pointer; }
        button:hover { background: #0c47b7; }
        .error-box { background: #f8d7da; color: #842029; padding: 12px; border-radius: 4px;
                     margin-bottom: 20px; }
        .text-center { text-align: center; }
        a { text-decoration: none; }
    </style>
</head>

<body>

<div class="container">
    <h2 class="text-center">Đăng ký tài khoản</h2>

    <% 
        String errorCode = (String) request.getAttribute("errorCode");
        if (errorCode != null) {
    %>
        <div class="error-box">
            <% if ("PASSWORD_MISMATCH".equals(errorCode)) { %>
                Mật khẩu nhập lại không khớp!
            <% } else if ("MISSING_FIELDS".equals(errorCode)) { %>
                Vui lòng điền đầy đủ thông tin!
            <% } else { %>
                Lỗi không xác định. Vui lòng thử lại.
            <% } %>
        </div>
    <% } %>

    <form action="register" method="POST">

        <label>Tên đăng nhập (username)</label>
        <input type="text" name="username" required 
               value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>">

        <label>Email</label>
        <input type="email" name="email" required 
               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>">

        <label>Mật khẩu</label>
        <input type="password" name="password" required>

        <label>Nhập lại mật khẩu</label>
        <!-- SỬA CHUẨN THEO CONTROLLER -->
        <input type="password" name="confirmPassword" required>

        <label>Vai trò</label>
        <select name="role">
            <option value="Reader">Reader</option>
            <option value="Reporter">Reporter</option>
        </select>

        <button type="submit">Đăng ký</button>
    </form>

    <p class="text-center">
        Already have an account? <a href="login.jsp"><strong>LOGIN</strong></a>
    </p>
</div>

</body>
</html>
