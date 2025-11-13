<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
  File này chỉ chứa thanh điều hướng (navbar).
  Nó sẽ được nhúng vào các trang khác.
  Chúng ta sẽ dùng 'param.activePage' để xác định link nào sẽ được tô sáng.
--%>

<style>
    /* Reset CSS cơ bản */
    body {
        margin: 0;
        font-family: Arial, sans-serif;
    }
    
    /* Style cho thanh điều hướng */
    .admin-navbar {
        background-color: #333;
        overflow: hidden; /* Clear float */
        width: 100%;
    }

    .admin-navbar ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        display: flex; /* Sử dụng flexbox để căn chỉnh */
    }

    .admin-navbar li {
        /* không cần float khi dùng flex */
    }

    .admin-navbar li a {
        display: block;
        color: white;
        text-align: center;
        padding: 16px 20px;
        text-decoration: none;
        font-size: 16px;
        transition: background-color 0.3s;
    }

    /* Hiệu ứng khi rê chuột */
    .admin-navbar li a:hover {
        background-color: #555;
    }

    /* Tô sáng link của trang hiện tại */
    .admin-navbar li a.active {
        background-color: #007bff; /* Màu xanh nổi bật */
        font-weight: bold;
    }
    
    /* Đẩy link "Đăng xuất" (nếu có) sang bên phải */
    .admin-navbar li.nav-right {
        margin-left: auto; /* Đây là trick của Flexbox */
    }
    
    .admin-navbar li.nav-right a {
        background-color: #dc3545; /* Màu đỏ cho hành động nguy hiểm/quan trọng */
    }
    .admin-navbar li.nav-right a:hover {
        background-color: #c82333;
    }
    
</style>

<nav class="admin-navbar">
    <ul>
        <%--
          Sử dụng JSTL <c:if> hoặc biểu thức EL để kiểm tra 'param.activePage'.
          Ví dụ: ${param.activePage == 'dashboard' ? 'active' : ''}
          Điều này sẽ thêm class "active" nếu điều kiện đúng.
        --%>
        <li>
            <%-- SỬA LỖI: Trỏ đến servlet, ví dụ "dashboard" thay vì "admin-dashboard.jsp" --%>
            <a class="${param.activePage == 'dashboard' ? 'active' : ''}" 
               href="admin-dashboard.jsp">Dashboard</a> <%-- Tạm thời giữ lại .jsp cho dashboard vì nó chưa có controller --%>
        </li>
        <li>
            <%-- SỬA LỖI: Trỏ đến "user" (UserController) thay vì "admin-user.jsp" --%>
            <a class="${param.activePage == 'users' ? 'active' : ''}" 
               href="user">Quản Lý Người Dùng</a>
        </li>
        <li>
            <%-- SỬA LỖI: Trỏ đến "category" (CategoryController) thay vì "admin-category.jsp" --%>
            <a class="${param.activePage == 'categories' ? 'active' : ''}" 
               href="category">Quản Lý Danh Mục</a>
        </li>
        
        <%-- Ví dụ về link Đăng xuất ở bên phải --%>
        <!-- 
        <li class="nav-right">
            <a href="logout">Đăng Xuất</a>
        </li> 
        -->
    </ul>
</nav>