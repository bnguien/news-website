package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.bean.Article;
import model.bean.Category;
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

    // ==============================
    // INSERT ARTICLE
    // ==============================
    public void insertArticle(Article article) {
        String sql = "INSERT INTO articles (title, summary, content, image, author_id, category_id, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, article.getTitle());
            ps.setString(2, article.getSummary());
            ps.setString(3, article.getContent());
            ps.setString(4, article.getImage());
            ps.setInt(5, article.getAuthorId());
            ps.setInt(6, article.getCategoryId());
            ps.setString(7, article.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // CATEGORY LIST
    // ==============================
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==============================
    // PUBLIC ARTICLES
    // ==============================
    public List<Article> getAllPublishedArticles() {
        List<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE status='published' ORDER BY published_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Article> getPublishedArticlesPaged(int offset, int limit) {
        List<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE status='published' "
                   + "ORDER BY published_at DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countPublishedArticles() {
        String sql = "SELECT COUNT(*) FROM articles WHERE status='published'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Article getPublishedArticleById(int id) {
        String sql = "SELECT * FROM articles WHERE article_id=? AND status='published'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void increaseViewCount(int id) {
        String sql = "UPDATE articles SET view_count = view_count + 1 WHERE article_id=?";

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
        String sql = "SELECT * FROM articles WHERE status='published' "
                   + "AND (title LIKE ? OR summary LIKE ?) "
                   + "ORDER BY published_at DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setInt(3, limit);
            ps.setInt(4, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countSearchPublished(String keyword) {
        String sql = "SELECT COUNT(*) FROM articles WHERE status='published' "
                   + "AND (title LIKE ? OR summary LIKE ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ==============================
    // ADMIN FUNCTIONS
    // ==============================
    public List<Article> getAllArticlesAdmin() {
        List<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Article getArticleById(int id) {
        String sql = "SELECT * FROM articles WHERE article_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateStatus(int id, String status) {
        String sql = "UPDATE articles SET status = ? WHERE article_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateArticle(int id, String title, String summary, String content) {
        String sql = "UPDATE articles SET title=?, summary=?, content=? WHERE article_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, summary);
            ps.setString(3, content);
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteArticle(int id) {
        String sql = "DELETE FROM articles WHERE article_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
