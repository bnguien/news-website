package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.bean.Comment;
import util.DBConnection;

public class CommentDAO {

    // Giữ method gốc để tương thích nếu nơi khác có dùng
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

    // API thân thiện hơn cho tầng BO
    public int insertComment(Comment comment) {
        if (comment == null) {
            return -1;
        }
        return insertComment(
                comment.getArticleId(),
                comment.getUserId(),
                comment.getContent(),
                new java.util.Date()
        );
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

	public int countAllComments() {
		String sql = "SELECT COUNT(*) FROM comments";
		try (Connection conn = DBConnection.getConnection();
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
