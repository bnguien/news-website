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
<jsp:include page="header.jsp" />

<nav class="admin-navbar">
    <ul>
        
        <li>
            <a class="${param.activePage == 'dashboard' ? 'active' : ''}" 
               href="admin-dashboard">Dashboard</a> 
        </li>
        <li>
           
            <a class="${param.activePage == 'users' ? 'active' : ''}" 
               href="user">Quản Lý Người Dùng</a>
        </li>
        <li>
            
            <a class="${param.activePage == 'categories' ? 'active' : ''}" 
               href="category">Quản Lý Danh Mục</a>
        </li>
        <li>
            
            <a class="${param.activePage == 'ả' ? 'active' : ''}" 
               href="category">Quản Lý Bài viết</a>
        </li>
        
       
        <!-- 
        <li class="nav-right">
            <a href="logout">Đăng Xuất</a>
        </li> 
        -->
    </ul>
</nav>