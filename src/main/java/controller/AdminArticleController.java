package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import model.bean.Article;
import model.bo.ArticleBO;

@WebServlet("/admin-articles")
public class AdminArticleController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ArticleBO articleBO;

    @Override
    public void init() {
        articleBO = new ArticleBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        if (!"admin".equalsIgnoreCase(role)) {
            response.sendRedirect("welcome.jsp?error=forbidden");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "approveArticle":
                approveArticle(request, response);
                break;

            case "rejectArticle":
                rejectArticle(request, response);
                break;

            case "deleteArticle":
                deleteArticle(request, response);
                break;

            case "editArticle":
                showEditForm(request, response);
                break;

            default:
                listArticles(request, response);
        }
    }

    private void listArticles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Article> list = articleBO.getAllArticlesAdmin();
        request.setAttribute("adminArticles", list);
        request.getRequestDispatcher("admin-article.jsp").forward(request, response);
    }

    private void approveArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        articleBO.updateStatus(id, "published");
        response.sendRedirect("admin-articles?msg=approved");
    }

    private void rejectArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        articleBO.updateStatus(id, "rejected");
        response.sendRedirect("admin-articles?msg=rejected");
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        articleBO.deleteArticle(id);
        response.sendRedirect("admin-articles?msg=deleted");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Article article = articleBO.getArticleById(id);

        if (article == null) {
            response.sendRedirect("admin-articles?msg=notfound");
            return;
        }

        request.setAttribute("article", article);
        request.getRequestDispatcher("admin-article-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("updateArticle".equals(action)) {
            updateArticle(request, response);
        }
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String summary = request.getParameter("summary");
        String content = request.getParameter("content");

        articleBO.updateArticle(id, title, summary, content);

        response.sendRedirect("admin-articles?msg=updated");
    }
}
