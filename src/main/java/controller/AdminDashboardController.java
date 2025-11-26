package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import model.dao.ArticleDAO;
import model.dao.CategoryDAO;
import model.dao.CommentDAO;
import model.dao.UserDAO;

@WebServlet("/admin-dashboard")
public class AdminDashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ArticleDAO articleDAO;
    private CategoryDAO categoryDAO;
    private CommentDAO commentDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        articleDAO = new ArticleDAO();
        categoryDAO = new CategoryDAO();
        commentDAO = new CommentDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Chưa đăng nhập → bắt đăng nhập
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Không phải admin → chặn truy cập
        String role = (String) session.getAttribute("role");
        if (!"admin".equalsIgnoreCase(role)) {
            response.sendRedirect("welcome.jsp?error=forbidden");
            return;
        }

        // Lấy số liệu thống kê
        int totalArticles = articleDAO.countPublishedArticles();
        int totalCategories = categoryDAO.countAllCategories();
        int totalComments = commentDAO.countAllComments();
        int totalUsers = userDAO.countAllUsers();

        // Đổ dữ liệu sang JSP
        request.setAttribute("totalArticles", totalArticles);
        request.setAttribute("totalCategories", totalCategories);
        request.setAttribute("totalComments", totalComments);
        request.setAttribute("totalUsers", totalUsers);

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
}
