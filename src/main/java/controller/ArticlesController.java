package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import model.bean.Article;
import model.bean.Category;
import model.bean.Comment;
import model.bo.ArticleBO;
import model.bo.CommentBO;

@WebServlet("/articles")
public class ArticlesController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ArticleBO articleBO;
    private CommentBO commentBO;
    private static final int PAGE_SIZE = 6;

    @Override
    public void init() {
        articleBO = new ArticleBO();
        commentBO = new CommentBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) action = "listPublic";

        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;

            case "viewDetail":
                viewDetail(request, response);
                break;

            case "searchPublic":
                searchPublic(request, response);
                break;

            default:
                listPublic(request, response);
                break;
        }
    }

    // =============================
    // CREATE PAGE
    // =============================
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object uid = request.getSession().getAttribute("user_id");
        String role = (String) request.getSession().getAttribute("role");

        if (uid == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (!"reporter".equalsIgnoreCase(role)) {
            response.sendRedirect("articles?action=listPublic");
            return;
        }

        List<Category> categories = articleBO.getAllCategories();
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("article-form.jsp").forward(request, response);
    }

    // =============================
    // POST HANDLER
    // =============================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("submitForReview".equals(action)) {
            submitForReview(request, response);
        }
    }

    private void submitForReview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object uidObj = request.getSession().getAttribute("user_id");
        String role = (String) request.getSession().getAttribute("role");

        if (uidObj == null || !"reporter".equalsIgnoreCase(role)) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) uidObj;

        String title = request.getParameter("title");
        String summary = request.getParameter("summary");
        String content = request.getParameter("content");
        String image = request.getParameter("image");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Article article = new Article();
        article.setTitle(title);
        article.setSummary(summary);
        article.setContent(content);
        article.setImage(image);
        article.setAuthorId(userId);
        article.setCategoryId(categoryId);
        article.setStatus("draft");

        articleBO.insertArticle(article);

        response.sendRedirect("articles?action=listPublic&msg=draft_created");
    }

    // =============================
    // LIST PUBLIC
    // =============================
    private void listPublic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int currentPage = parsePage(request);
        List<Article> articles = articleBO.getPublishedArticlesPaged(currentPage, PAGE_SIZE);
        int totalPages = articleBO.getTotalPagesForPublished(PAGE_SIZE);

        request.setAttribute("articles", articles);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("article-list.jsp").forward(request, response);
    }

    // =============================
    // DETAIL
    // =============================
    private void viewDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        int articleId;
        try {
            articleId = Integer.parseInt(idParam);
        } catch (Exception e) {
            response.sendRedirect("articles?action=listPublic");
            return;
        }

        articleBO.increaseViewCount(articleId);
        Article article = articleBO.getPublishedArticleById(articleId);

        if (article == null) {
            response.sendRedirect("articles?action=listPublic");
            return;
        }

        List<Comment> comments = commentBO.fetchCommentsByArticleId(articleId);

        request.setAttribute("article", article);
        request.setAttribute("comments", comments);

        request.getRequestDispatcher("article-detail.jsp").forward(request, response);
    }

    // =============================
    // SEARCH
    // =============================
    private void searchPublic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String criteria = request.getParameter("criteria");
        if (criteria == null) criteria = "";

        int currentPage = parsePage(request);

        List<Article> articles = articleBO.searchPublished(criteria, currentPage, PAGE_SIZE);
        int totalPages = articleBO.getTotalPagesForSearch(criteria, PAGE_SIZE);

        request.setAttribute("criteria", criteria);
        request.setAttribute("articles", articles);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("search-results.jsp").forward(request, response);
    }

    private int parsePage(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            return 1;
        }
    }
}
