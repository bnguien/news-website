package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import model.bean.Comment;

public class CommentDAO {
	public int insertComment(int articleId, int userId, String content, java.util.Date createdAt) {
		String sql = "INSERT INTO comments (article_id, user_id, content) VALUES (?, ?, ?)";
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, articleId);
			ps.setInt(2, userId);
			ps.setString(3, content);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean deleteComment(int commentId) {
		String sql = "DELETE FROM comments WHERE comment_id = ?";
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, commentId);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Comment> getCommentsByArticleId(int articleId) {
		List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE article_id = ? ORDER BY created_at DESC";
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, articleId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setArticleId(rs.getInt("article_id"));
				comment.setUserId(rs.getInt("user_id"));
				comment.setContent(rs.getString("content"));
				comment.setCreatedAt(rs.getTimestamp("created_at"));
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
}
