<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Quản Lý Danh Mục</title>
    <style>
        /* CSS này được sao chép từ admin-dashboard để đồng bộ giao diện */
        body { 
            font-family: Arial, sans-serif; 
            background-color: #f4f4f4; 
            /* margin: 0; đã có trong admin-nav.jsp */
        }
        
        .admin-container {
            padding: 30px;
        }
        
        .admin-container h2, 
        .admin-container h3 { 
            color: #333; 
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }
        
        /* CSS cho Bảng */
        table { 
            width: 100%; 
            border-collapse: collapse; 
            margin-top: 20px; 
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        th, td { 
            border: 1px solid #ddd; 
            padding: 12px; 
            text-align: left; 
        }
        th { 
            background-color: #007bff; 
            color: white; 
        }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #f1f1f1; }
        
        /* CSS cho Link hành động */
        a { color: #007bff; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .action-link-delete { color: #dc3545; }
        .action-link-delete:hover { color: #c82333; }
        
        /* CSS cho Form thêm mới */
        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        .form-container div { margin-bottom: 10px; }
        .form-container label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-container input[type="text"],
        .form-container textarea {
            width: calc(100% - 24px); /* Tính cả padding */
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-container button {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-container button:hover { background-color: #218838; }

        /* CSS cho Thông báo */
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-weight: bold;
        }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>

    <jsp:include page="admin-nav.jsp">
        <jsp:param name="activePage" value="categories" />
    </jsp:include>

    <div class="admin-container">
        <h2>Quản Lý Danh Mục</h2>

        <%-- 
          PHẦN 3: HIỂN THỊ THÔNG BÁO TỪ CONTROLLER
          Kiểm tra các tham số 'msg' và 'error' trên URL.
        --%>
        <c:if test="${not empty param.msg}">
            <div class="alert alert-success">
                <c:choose>
                    <c:when test="${param.msg == 'added'}">Thêm danh mục mới thành công!</c:when>
                    <c:when test="${param.msg == 'updated'}">Cập nhật danh mục thành công!</c:when>
                    <c:when test="${param.msg == 'deleted'}">Xóa danh mục thành công!</c:when>
                </c:choose>
            </div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">
                <c:choose>
                    <c:when test="${param.error == 'invalid_name'}">Tên danh mục không được để trống!</c:when>
                    <c:when test="${param.error == 'in_use'}">
                        Không thể xóa: Danh mục này đang được sử dụng bởi các bài viết!
                    </c:when>
                    <c:otherwise>Đã xảy ra lỗi không xác định.</c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <div class="form-container">
            <h3>Thêm Danh Mục Mới</h3>
            <form action="category" method="POST">
                <input type="hidden" name="action" value="add">
                <div>
                    <label for="name">Tên Danh Mục:</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div>
                    <label for="description">Mô Tả:</label>
                    <textarea id="description" name="description" rows="3"></textarea>
                </div>
                <div>
                    <button type="submit">Thêm Mới</button>
                </div>
            </form>
        </div>

        <h3>Danh Sách Danh Mục Hiện Có</h3>
        <table>
            <thead>
                <tr>
                    <th style="width: 10%;">ID</th>
                    <th style="width: 30%;">Tên Danh Mục</th>
                    <th style="width: 40%;">Mô Tả</th>
                    <th style="width: 20%;">Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="category" items="${categoryList}">
                    <tr>
                        <td>${category.categoryId}</td>
                        <td><c:out value="${category.name}" /></td>
                        <td><c:out value="${category.description}" /></td>
                        <td>
                            
                            <a href="category?action=edit&id=${category.categoryId}">Sửa</a>
                            |
                            <a href="category?action=delete&id=${category.categoryId}"
                               class="action-link-delete"
                               onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục \'${category.name}\'?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
              
                <c:if test="${empty categoryList}">
                    <tr>
                        <td colspan="4" style="text-align: center;">Chưa có danh mục nào.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

    </div> 

</body>
</html>