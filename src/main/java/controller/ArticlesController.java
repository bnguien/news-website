package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import model.bean.Article;
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
        if (action == null || action.isEmpty()) {
            action = "listPublic";
        }

        switch (action) {
            case "viewDetail":
                viewDetail(request, response);
                break;
            case "searchPublic":
                searchPublic(request, response);
                break;
            case "listPublic":
            default:
                listPublic(request, response);
        }
    }

    private int parsePage(HttpServletRequest request) {
        String pageStr = request.getParameter("page");
        int page = 1;
        try {
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }
        } catch (NumberFormatException ignored) {
        }
        return Math.max(page, 1);
    }

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

    private void viewDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        int articleId;
        try {
            articleId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("articles?action=listPublic");
            return;
        }

        // Tăng view trước rồi đọc lại bài viết
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

    private void searchPublic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String criteria = request.getParameter("criteria");
        if (criteria == null) {
            criteria = "";
        }

        int currentPage = parsePage(request);

        List<Article> articles = articleBO.searchPublished(criteria, currentPage, PAGE_SIZE);
        int totalPages = articleBO.getTotalPagesForSearch(criteria, PAGE_SIZE);

        request.setAttribute("criteria", criteria);
        request.setAttribute("articles", articles);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);

        request.getRequestDispatcher("search-results.jsp").forward(request, response);
    }
}


