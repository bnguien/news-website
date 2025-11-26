package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Article;
import util.DBConnection;

public class ArticleDAO {

    private Article mapRow(ResultSet rs) throws SQLException {
        Article a = new Article();
        a.setArticleId(rs.getInt("article_id"));
        a.setTitle(rs.getString("title"));
        a.setSummary(rs.getString("summary"));
        a.setContent(rs.getString("content"));
        a.setImage(rs.getString("image"));
        a.setAuthorId(rs.getInt("author_id"));
        a.setCategoryId(rs.getInt("category_id"));
        a.setViewCount(rs.getInt("view_count"));
        a.setPublishedAt(rs.getTimestamp("published_at"));
        a.setStatus(rs.getString("status"));
        return a;
    }

    public List<Article> getAllPublishedArticles() {
        List<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE status = 'published' ORDER BY published_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Article> getPublishedArticlesPaged(int offset, int limit) {
        List<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE status = 'published' " +
                     "ORDER BY published_at DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countPublishedArticles() {
        String sql = "SELECT COUNT(*) FROM articles WHERE status = 'published'";
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

    public Article getPublishedArticleById(int id) {
        String sql = "SELECT * FROM articles WHERE article_id = ? AND status = 'published'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void increaseViewCount(int id) {
        String sql = "UPDATE articles SET view_count = view_count + 1 WHERE article_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Article> searchPublished(String keyword, int offset, int limit) {
        List<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles " +
                     "WHERE status = 'published' AND (title LIKE ? OR summary LIKE ?) " +
                     "ORDER BY published_at DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setInt(3, limit);
            ps.setInt(4, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSearchPublished(String keyword) {
        String sql = "SELECT COUNT(*) FROM articles " +
                     "WHERE status = 'published' AND (title LIKE ? OR summary LIKE ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}


