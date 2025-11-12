<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<%-- 
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
  <div class="container">
    <a class="navbar-brand" href="home"> <strong>Lotus News</strong>
    </a>
    <button
      class="navbar-toggler"
      type="button"
      data-mdb-toggle="collapse"
      data-mdb-target="#navbarContent"
    >
      <i class="fas fa-bars"></i>
    </button>
    <div class="collapse navbar-collapse" id="navbarContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" href="home">Trang chủ</a> </li>
        
        <c:forEach var="category" items="${categories}">
            <li class="nav-item">
              <a class="nav-link" href="category?id=${category.categoryId}">
                  ${category.name}
              </a>
            </li>
        </c:forEach>
        
      </ul>
      <ul class="navbar-nav d-flex flex-row">
        <li class="nav-item me-3 me-lg-0">
            <a class="nav-link" href="login">Đăng nhập</a> </li>
      </ul>
    </div>
  </div>
</nav>--%>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
      <div class="container">
        <a class="navbar-brand" href="#">
            <strong>Báo điện tử NNN</strong>
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-mdb-toggle="collapse"
          data-mdb-target="#navbarContent"
        >
          <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link active" href="#">Trang chủ</a>
            </li>
            
            <li class="nav-item">
              <a class="nav-link" href="#">Công nghệ</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Thời sự</a>
            </li>
             <li class="nav-item">
              <a class="nav-link" href="#">Thể thao</a>
            </li>
            </ul>
          <ul class="navbar-nav d-flex flex-row">
            <li class="nav-item me-3 me-lg-0">
                <a class="nav-link" href="login.jsp">Đăng nhập</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
