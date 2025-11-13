package model.bo;

import java.util.List;

import model.bean.Comment;
import model.dao.CommentDAO;

public class CommentBO {
	private CommentDAO commentDAO = new CommentDAO();
	
	public int addComment(Comment comment) {
		return commentDAO.insertComment(comment.getArticleId(), comment.getUserId(), comment.getContent(), comment.getCreatedAt());
	}
	
	public boolean removeComment(int commentId) {
		return commentDAO.deleteComment(commentId);
	}
	
	public List<Comment> fetchCommentsByArticleId(int articleId) {
		return commentDAO.getCommentsByArticleId(articleId);
	}
}
