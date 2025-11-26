<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!-- CSS cho toàn bộ website -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
<link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.min.css" rel="stylesheet"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/">
            <strong>Báo điện tử NNN</strong>
        </a>

        <button class="navbar-toggler" type="button" data-mdb-toggle="collapse" data-mdb-target="#navbarContent">
            <i class="fas fa-bars"></i>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link active" href="<%= request.getContextPath() %>/">Trang chủ</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Công nghệ</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Thời sự</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Thể thao</a></li>
            </ul>

            <ul class="navbar-nav d-flex flex-row">
                <c:choose>

                    <c:when test="${not empty sessionScope.username}">

                        <c:if test="${sessionScope.role == 'reporter'}">
                            <li class="nav-item me-3 me-lg-0">
                                <a class="btn btn-warning btn-sm"
                                   href="<%= request.getContextPath() %>/articles?action=create"
                                   style="margin-right: 15px; color:black; font-weight:bold;">
                                   Đăng bài
                                </a>
                            </li>
                        </c:if>

                        <li class="nav-item me-3 me-lg-0">
                            <span class="nav-link">
                                Xin chào, ${sessionScope.role} ${sessionScope.username}!
                            </span>
                        </li>

                        <li class="nav-item me-3 me-lg-0">
                            <a class="nav-link" href="<%= request.getContextPath() %>/logout">Đăng xuất</a>
                        </li>

                    </c:when>

                    <c:otherwise>
                        <li class="nav-item me-3 me-lg-0">
                            <a class="nav-link" href="<%= request.getContextPath() %>/login">Đăng nhập</a>
                        </li>
                    </c:otherwise>

                </c:choose>
            </ul>
        </div>
    </div>
</nav>
