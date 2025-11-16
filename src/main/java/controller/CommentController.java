package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import model.bean.Comment;
import model.bo.CommentBO;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommentBO commentBO;

    @Override
    public void init() {
        commentBO = new CommentBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        switch (action) {
            case "viewComment":
                viewComments(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        switch (action) {
            case "addComment":
                addComment(request, response);
                break;

            case "deleteComment":
                deleteComment(request, response);
                break;

            default:
                response.sendRedirect("index.jsp");
        }
    }

    private void addComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Integer userId = (Integer) request.getSession().getAttribute("user_id");
        int articleId = Integer.parseInt(request.getParameter("article_id"));
        String content = request.getParameter("content");

        if (userId == null) {
            request.setAttribute("errorCode", "NOT_LOGGED_IN");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (content == null || content.trim().isEmpty()) {
            request.setAttribute("errorCode", "EMPTY_CONTENT");
            request.setAttribute("article_id", articleId);
            request.getRequestDispatcher("article.jsp").forward(request, response);
            return;
        }

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUserId(userId);
        comment.setContent(content);

        String result = commentBO.addComment(comment);

        if ("added".equals(result)) {
            response.sendRedirect("article.jsp?id=" + articleId + "&msg=comment_added");
        } else {
            request.setAttribute("errorCode", "SERVER_ERROR");
            request.getRequestDispatcher("article.jsp").forward(request, response);
        }
    }

    private void deleteComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int commentId = Integer.parseInt(request.getParameter("comment_id"));
        int articleId = Integer.parseInt(request.getParameter("article_id"));
        String result = commentBO.deleteComment(commentId);

        if ("deleted".equals(result)) {
            response.sendRedirect("article.jsp?id=" + articleId + "&msg=comment_deleted");
        } else {
            request.setAttribute("errorCode", "SERVER_ERROR");
            request.getRequestDispatcher("article.jsp").forward(request, response);
        }
    }

    private void viewComments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int articleId = Integer.parseInt(request.getParameter("article_id"));
        List<Comment> comments = commentBO.fetchCommentsByArticleId(articleId);

        request.setAttribute("comments", comments);
        request.getRequestDispatcher("article.jsp").forward(request, response);
    }
}
