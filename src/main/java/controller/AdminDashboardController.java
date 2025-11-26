package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import model.bo.ArticleBO;
import model.bo.CategoryBO;
import model.bo.CommentBO;
import model.bo.UserBO;
import model.dao.CommentDAO;
import model.dao.UserDAO;
import model.dao.CategoryDAO;
import model.dao.ArticleDAO;

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

        int totalArticles = articleDAO.countPublishedArticles();
        int totalCategories = categoryDAO.countAllCategories();
        int totalComments = commentDAO.countAllComments();
        int totalUsers = userDAO.countAllUsers();

        request.setAttribute("totalArticles", totalArticles);
        request.setAttribute("totalCategories", totalCategories);
        request.setAttribute("totalComments", totalComments);
        request.setAttribute("totalUsers", totalUsers);

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
}


