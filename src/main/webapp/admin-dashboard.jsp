<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
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
        body {
            background-color: #f5f6fa;
            font-family: "Roboto", Arial, sans-serif;
        }
        .dashboard-wrapper {
            max-width: 1200px;
            margin: 30px auto 40px;
            padding: 0 16px;
        }
        .card-stat {
            border-radius: 16px;
            box-shadow: 0 10px 24px rgba(15, 23, 42, 0.08);
            border: none;
        }
        .card-stat .card-body {
            padding: 20px 22px;
        }
        .card-stat-icon {
            width: 48px;
            height: 48px;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #fff;
            margin-bottom: 10px;
        }
        .bg-users { background: linear-gradient(135deg, #6366f1, #4f46e5); }
        .bg-articles { background: linear-gradient(135deg, #14b8a6, #0f766e); }
        .bg-categories { background: linear-gradient(135deg, #f97316, #ea580c); }
        .bg-comments { background: linear-gradient(135deg, #ec4899, #db2777); }

        .stat-label {
            font-size: 0.9rem;
            color: #6b7280;
        }
        .stat-value {
            font-size: 1.8rem;
            font-weight: 700;
            color: #111827;
        }
        .section-title {
            font-weight: 600;
            margin: 28px 0 14px;
            color: #111827;
        }
        .recent-placeholder {
            padding: 18px 20px;
            border-radius: 12px;
            background: #ffffff;
            box-shadow: 0 4px 12px rgba(15, 23, 42, 0.04);
            color: #6b7280;
            font-size: 0.95rem;
        }
    </style>
</head>
<body>

<jsp:include page="admin-nav.jsp">
    <jsp:param name="activePage" value="dashboard" />
</jsp:include>

<div class="dashboard-wrapper">
    <h2 class="mb-3">Tổng quan hệ thống</h2>
    <p class="text-muted mb-4">
        Xin chào admin, đây là số liệu nhanh về website báo điện tử của bạn.
    </p>

    <div class="row g-4">
        <div class="col-md-3">
            <div class="card card-stat">
                <div class="card-body">
                    <div class="card-stat-icon bg-users">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="stat-label">Người dùng</div>
                    <div class="stat-value">
                        <c:out value="${totalUsers}" default="0"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card card-stat">
                <div class="card-body">
                    <div class="card-stat-icon bg-articles">
                        <i class="far fa-newspaper"></i>
                    </div>
                    <div class="stat-label">Bài viết đã xuất bản</div>
                    <div class="stat-value">
                        <c:out value="${totalArticles}" default="0"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card card-stat">
                <div class="card-body">
                    <div class="card-stat-icon bg-categories">
                        <i class="fas fa-layer-group"></i>
                    </div>
                    <div class="stat-label">Danh mục</div>
                    <div class="stat-value">
                        <c:out value="${totalCategories}" default="0"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card card-stat">
                <div class="card-body">
                    <div class="card-stat-icon bg-comments">
                        <i class="fas fa-comments"></i>
                    </div>
                    <div class="stat-label">Bình luận</div>
                    <div class="stat-value">
                        <c:out value="${totalComments}" default="0"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <h5 class="section-title">Hoạt động gần đây</h5>
    <div class="recent-placeholder">
        Khu vực này bạn có thể mở rộng trong tương lai để hiển thị danh sách bài viết mới tạo,
        người dùng mới đăng ký, hoặc bình luận mới nhất.
    </div>
</div>

<script
  type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.1/mdb.umd.min.js"
></script>
</body>
</html>


