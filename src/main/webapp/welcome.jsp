<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang Báo Điện Tử </title>
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
  rel="stylesheet"
/>
<link
  href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
  rel="stylesheet"
/>
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.min.css"
  rel="stylesheet"
/>
<style>
    /* Style này vẫn giữ nguyên */
    .card-img-top {
        width: 100%;
        height: 200px;
        object-fit: cover;
    }
    .featured-article img {
        width: 100%;
        height: 400px;
        object-fit: cover;
    }
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
<%--    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
      <div class="container">
        <a class="navbar-brand" href="#">
            <strong>Lotus News</strong>
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
 --%>  
    <div class="container mt-5">

        <section class="mb-5 featured-article">
            <div class="card">
                <div class="row g-0">
                    <div class="col-lg-7">
                        <img 
                            src="https://mdbcdn.b-cdn.net/img/Photos/Slides/1.webp" 
                            class="img-fluid rounded-start" 
                            alt="Tiêu đề bài báo"
                        />
                    </div>
                    <div class="col-lg-5 d-flex flex-column">
                        <div class="card-body">
                            <h2 class="card-title h2">Tiêu đề bài báo nổi bật </h2>
                            <p class="card-text">
                                Đây là tóm tắt (summary) của bài báo nổi bật nhất. Nội dung này được viết cứng vào file JSP để test giao diện.
                            </p>
                            <a href="#" class="btn btn-primary btn-lg">
                                Đọc chi tiết
                            </a>
                        </div>
                        <div class="card-footer text-muted">
                            <i class="fas fa-eye"></i> 999 Lượt xem
                            &nbsp;&nbsp;
                            <i class="fas fa-clock"></i> 10/11/2025
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <hr/>

        <h3 class="mb-4">Tin tức khác</h3>
        <section>
            <div class="row">
            
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100">
                        <img 
                            src="https://mdbcdn.b-cdn.net/img/Photos/Slides/2.webp" 
                            class="card-img-top" 
                            alt="Tiêu đề bài 1"
                        />
                        <div class="card-body">
                            <h5 class="card-title">
                                <a href="#">Tiêu đề bài báo số 1 (Dữ liệu thô)</a>
                            </h5>
                            <p class="card-text">
                                Tóm tắt của bài báo số 1. Viết cứng vào đây.
                            </p>
                        </div>
                        <div class="card-footer text-muted">
                            <i class="fas fa-eye"></i> 150
                            <span class="float-end">
                               <i class="fas fa-clock"></i> 09/11/2025
                            </span>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100">
                        <img 
                            src="https://mdbcdn.b-cdn.net/img/Photos/Slides/3.webp" 
                            class="card-img-top" 
                            alt="Tiêu đề bài 2"
                        />
                        <div class="card-body">
                            <h5 class="card-title">
                                <a href="#">Tiêu đề bài báo số 2 (Dữ liệu thô)</a>
                            </h5>
                            <p class="card-text">
                                Tóm tắt của bài báo số 2. Copy-paste và sửa lại.
                            </p>
                        </div>
                        <div class="card-footer text-muted">
                            <i class="fas fa-eye"></i> 450
                            <span class="float-end">
                               <i class="fas fa-clock"></i> 08/11/2025
                            </span>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100">
                        <img 
                            src="https://mdbcdn.b-cdn.net/img/Photos/Slides/4.webp" 
                            class="card-img-top" 
                            alt="Tiêu đề bài 3"
                        />
                        <div class="card-body">
                            <h5 class="card-title">
                                <a href="#">Tiêu đề bài báo số 3 </a>
                            </h5>
                            <p class="card-text">
                                Tóm tắt của bài báo số 3. Copy-paste và sửa lại.
                            </p>
                        </div>
                        <div class="card-footer text-muted">
                            <i class="fas fa-eye"></i> 320
                            <span class="float-end">
                               <i class="fas fa-clock"></i> 07/11/2025
                            </span>
                        </div>
                    </div>
                </div>
                
                </div>
        </section>
        

    </div> 
    <jsp:include page="footer.jsp" />

<script
  type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.umd.min.js"
></script>
</body>
</html>