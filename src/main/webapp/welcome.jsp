<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang Báo Điện Tử</title>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
<link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.min.css" rel="stylesheet"/>
<style>
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
    /* Thêm style để cắt ngắn text nếu quá dài */
    .card-text {
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container mt-5">

        <c:if test="${empty articles}">
            <div class="alert alert-warning text-center">
                Hiện chưa có bài viết nào được xuất bản.
            </div>
        </c:if>

        <c:if test="${not empty articles}">
        
            <c:set var="featured" value="${articles[0]}" />
            
            <section class="mb-5 featured-article">
                <div class="card">
                    <div class="row g-0">
                        <div class="col-lg-7">
                            <img 
                                src="${not empty featured.image ? featured.image : 'https://mdbcdn.b-cdn.net/img/Photos/Slides/1.webp'}" 
                                class="img-fluid rounded-start" 
                                alt="${featured.title}"
                                onerror="this.src='https://mdbcdn.b-cdn.net/img/Photos/Slides/1.webp'"
                            />
                        </div>
                        <div class="col-lg-5 d-flex flex-column">
                            <div class="card-body">
                                <h2 class="card-title h2">${featured.title}</h2>
                                <p class="card-text">
                                    ${featured.summary}
                                </p>
                                <a href="articles?action=viewDetail&id=${featured.articleId}" class="btn btn-primary btn-lg">
                                    Đọc chi tiết
                                </a>
                            </div>
                            <div class="card-footer text-muted">
                                <i class="fas fa-eye"></i> ${featured.viewCount} Lượt xem
                                &nbsp;&nbsp;
                                <i class="fas fa-clock"></i> 
                                <fmt:formatDate value="${featured.publishedAt}" pattern="dd/MM/yyyy HH:mm"/>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <hr/>

            <h3 class="mb-4">Tin tức khác</h3>
            <section>
                <div class="row">
                    <c:forEach items="${articles}" var="a" begin="1">
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="card h-100">
                                <img 
                                    src="${not empty a.image ? a.image : 'https://mdbcdn.b-cdn.net/img/Photos/Slides/2.webp'}" 
                                    class="card-img-top" 
                                    alt="${a.title}"
                                    onerror="this.src='https://mdbcdn.b-cdn.net/img/Photos/Slides/2.webp'"
                                />
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a href="articles?action=viewDetail&id=${a.articleId}" style="text-decoration: none; color: inherit;">
                                            ${a.title}
                                        </a>
                                    </h5>
                                    <p class="card-text">
                                        ${a.summary}
                                    </p>
                                </div>
                                <div class="card-footer text-muted">
                                    <i class="fas fa-eye"></i> ${a.viewCount}
                                    <span class="float-end">
                                       <i class="fas fa-clock"></i> 
                                       <fmt:formatDate value="${a.publishedAt}" pattern="dd/MM/yyyy"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <c:if test="${articles.size() == 1}">
                        <div class="col-12 text-center text-muted">
                            <p>Không có tin tức khác.</p>
                        </div>
                    </c:if>
                </div>
            </section>
        
        </c:if>

    </div> 
    <jsp:include page="footer.jsp" />

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.umd.min.js"></script>
</body>
</html>




<%--
Trong đoạn code controller lấy list arcitle để hiện về welcome phải có !!!!!!!!!!!!!
// Trong phương thức doGet
ArticleBO articleBO = new ArticleBO();
List<Article> list = articleBO.getAllArticles(); // Hoặc getTopArticles(10)

request.setAttribute("articles", list);

request.getRequestDispatcher("welcome.jsp").forward(request, response);
--%>