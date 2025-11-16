package model.bo;

import java.util.List;

import model.bean.Comment;
import model.dao.CommentDAO;

public class CommentBO {

    private CommentDAO commentDAO = new CommentDAO();

    public String addComment(Comment c) {
        int result = commentDAO.insertComment(c);
        return (result > 0) ? "added" : "SERVER_ERROR";
    }

    public String deleteComment(int id) {
        boolean ok = commentDAO.deleteComment(id);
        return ok ? "deleted" : "SERVER_ERROR";
    }

    public List<Comment> fetchCommentsByArticleId(int articleId) {
        return commentDAO.getCommentsByArticleId(articleId);
    }
}
