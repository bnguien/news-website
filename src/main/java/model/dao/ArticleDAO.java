package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import model.bean.Article;

public class ArticleDAO {
	Connection conn;
	public ArticleDAO() {}
	
	public List<Article> getPublicList(int page, int pageSize) throws SQLException {
		String sql = "SELECT * FROM articles " +
                "WHERE status = 'published' " +  
                "ORDER BY published_at DESC " + 
                "LIMIT ? OFFSET ?";
		
		int offset = (page - 1) * pageSize;
		
		List<Article> articles = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pageSize);
			ps.setInt(2, offset);
			
			try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Article article = new Article();
	                
	                article.setArticleId(rs.getInt("article_id"));
	                article.setTitle(rs.getString("title"));
	                article.setSummary(rs.getString("summary"));
	                article.setContent(rs.getString("content"));
	                article.setImage(rs.getString("image"));
	                article.setAuthorId(rs.getInt("author_id"));
	                article.setCategoryId(rs.getInt("category_id"));
	                article.setViewCount(rs.getInt("view_count"));
	                article.setPublishedAt(rs.getTimestamp("published_at"));
	                article.setStatus(rs.getString("status"));
	                
	                articles.add(article);
	            }
	        }
		} catch (SQLException ex) {
			System.err.println("[ARTICLES_DATABASE_DEBUG] " + ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
		return articles;
	}
	
	public int getPublicArticlesCount() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM articles WHERE status = 'published'";
	    int count = 0;
	    
	    try (Connection conn = DBConnection.getConnection();
	    	 PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (SQLException ex) {
	        System.err.println("[ARTICLES_COUNT_DEBUG] " + ex.getMessage());
	        throw ex;
	    }
	    return count;
	}
	
	public Article getPublicDetail(int articleID) throws SQLException {
		String sql = "SELECT * FROM articles WHERE status = 'published' AND article_id = ?";
		
		Article article = null;
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)
				) {
			ps.setInt(1, articleID);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					article = new Article();
					
					article.setArticleId(rs.getInt("article_id"));
					article.setTitle(rs.getString("title"));
					article.setSummary(rs.getString("summary"));
					article.setContent(rs.getString("content"));
					article.setImage(rs.getString("image"));
					article.setAuthorId(rs.getInt("author_id"));
					article.setCategoryId(rs.getInt("category_id"));
					article.setViewCount(rs.getInt("view_count"));
					article.setPublishedAt(rs.getTimestamp("published_at"));
					article.setStatus(rs.getString("status"));
				}	
			}
		} catch (SQLException ex) {
			System.err.println("[ARTICLES_DATABASE_DEBUG] " + ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
		return article;
	}

	public void increaseView(int articleID) throws SQLException {
	    String sql = "UPDATE articles SET view_count = view_count + 1 WHERE article_id = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setInt(1, articleID);
	        ps.executeUpdate(); 
	        
	    } catch (SQLException ex) {
	        System.err.println("[UPDATE_VIEW_COUNT_DEBUG] " + ex.getMessage());
	        ex.printStackTrace();
	        throw ex;
	    }
	}

	public List<Article> getPublicByCategory(int categoryId, int page, int pageSize) throws SQLException {
		String sql = "SELECT * FROM articles " +
                "WHERE status = 'published' " + 
				"AND category_id = ?" + 
                "ORDER BY published_at DESC " + 
                "LIMIT ? OFFSET ?";
		
		int offset = (page - 1) * pageSize;
		
		List<Article> articles = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, categoryId);
			ps.setInt(2, pageSize);
			ps.setInt(3, offset);
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Article article = new Article(); 
					
					article.setArticleId(rs.getInt("article_id"));
					article.setTitle(rs.getString("title"));
					article.setSummary(rs.getString("summary"));
					article.setContent(rs.getString("content"));
					article.setImage(rs.getString("image"));
					article.setAuthorId(rs.getInt("author_id"));
					article.setCategoryId(rs.getInt("category_id"));
					article.setViewCount(rs.getInt("view_count"));
					article.setPublishedAt(rs.getTimestamp("published_at"));
					article.setStatus(rs.getString("status"));
					
					articles.add(article);
				}
			}
		} catch (SQLException ex) {
			System.err.println("[LIST_BY_CATEGORY_DEBUG] " + ex.getMessage());
			ex.printStackTrace();
	        throw ex;
		}
		return articles;
	}

	public int getPublicCountByCategory(int categoryId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM articles WHERE status = 'published' AND category_id = ?";
	    int count = 0;
	    
	    try (Connection conn = DBConnection.getConnection();
	    		PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, categoryId);
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		count = rs.getInt(1);
	        	}
	        }
	    } catch (SQLException ex) {
	        System.err.println("[ARTICLES_COUNT_BY_CATEGORY_DEBUG] " + ex.getMessage());
	        ex.printStackTrace();
	        throw ex;
	    }
	    return count;
	}

	public List<Article> search(String searchValue, int categoryId, String timeSqlFilter, int page, int pageSize) throws SQLException {
		List<Article> articles = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder(
				"SELECT * FROM articles WHERE status = 'published' ");
		List<Object> params = new ArrayList<>();
		
		if (searchValue != null && !searchValue.trim().isEmpty()) {
            sql.append("AND (title LIKE ? OR summary LIKE ?) ");
            params.add("%" + searchValue + "%");
            params.add("%" + searchValue + "%");
        }
		
		if (categoryId > 0) { 
            sql.append("AND category_id = ? ");
            params.add(categoryId);
        }
		
		if (timeSqlFilter != null) {
            sql.append(timeSqlFilter);
        }
		
		sql.append("ORDER BY published_at DESC LIMIT ? OFFSET ?");
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
           int i = 1;
           for (Object param : params) {
               ps.setObject(i++, param);
           }
           
           try (ResultSet rs = ps.executeQuery()) {
        	   while (rs.next()) {
   					Article article = new Article(); 
   					
   					article.setArticleId(rs.getInt("article_id"));
   					article.setTitle(rs.getString("title"));
   					article.setSummary(rs.getString("summary"));
   					article.setContent(rs.getString("content"));
   					article.setImage(rs.getString("image"));
   					article.setAuthorId(rs.getInt("author_id"));
   					article.setCategoryId(rs.getInt("category_id"));
   					article.setViewCount(rs.getInt("view_count"));
   					article.setPublishedAt(rs.getTimestamp("published_at"));
   					article.setStatus(rs.getString("status"));
   					
   					articles.add(article);
				}
           }	
        } catch (SQLException ex) {
        	System.err.println("[SEARCH_ARTICLES_DEBUG] " + ex.getMessage());
	        ex.printStackTrace();
	        throw ex;
        }
		
		return articles;
	}

	public int searchCount(String searchValue, int categoryId, String timeSqlFilter) throws SQLException {
		int count = 0;
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM articles WHERE status = 'published' ");
		List<Object> params = new ArrayList<>();
		
		if (searchValue != null && !searchValue.trim().isEmpty()) {
            sql.append("AND (title LIKE ? OR summary LIKE ?) ");
            params.add("%" + searchValue + "%");
            params.add("%" + searchValue + "%");
        }
		
		if (categoryId > 0) { 
            sql.append("AND category_id = ? ");
            params.add(categoryId);
        }
		
		if (timeSqlFilter != null) {
            sql.append(timeSqlFilter);
        }
		
		try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
           int i = 1;
           for (Object param : params) {
               ps.setObject(i++, param);
           }
           
           try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		count = rs.getInt(1);
	        	}
	        }
	    } catch (SQLException ex) {
	        System.err.println("[ARTICLES_SEARCH_COUNT_DEBUG] " + ex.getMessage());
	        ex.printStackTrace();
	        throw ex;
	    }
        
		return count;
	}

	public List<Article> getArticlesByAuthorId(int userId, int page, int pageSize) throws SQLException {
		String sql = "SELECT * FROM articles WHERE author_id = ? ORDER BY published_at DESC LIMIT ? OFFSET ?";
		int offset = (page - 1) * pageSize;
		
		List<Article> articles = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pageSize);
			ps.setInt(2, offset);
			
			try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Article article = new Article();
	                
	                article.setArticleId(rs.getInt("article_id"));
	                article.setTitle(rs.getString("title"));
	                article.setSummary(rs.getString("summary"));
	                article.setContent(rs.getString("content"));
	                article.setImage(rs.getString("image"));
	                article.setAuthorId(rs.getInt("author_id"));
	                article.setCategoryId(rs.getInt("category_id"));
	                article.setViewCount(rs.getInt("view_count"));
	                article.setPublishedAt(rs.getTimestamp("published_at"));
	                article.setStatus(rs.getString("status"));
	                
	                articles.add(article);
	            }
	        }
		} catch (SQLException ex) {
			System.err.println("[ARTICLES_DATABASE_DEBUG] " + ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
		return articles;
	}

	public int getArticlesCountByAuthorId(int userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM articles WHERE status = 'published' AND author_id = ?";
	    int count = 0;
	    
	    try (Connection conn = DBConnection.getConnection();
	    		PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		count = rs.getInt(1);
	        	}
	        }
	    } catch (SQLException ex) {
	        System.err.println("[ARTICLES_COUNT_BY_CATEGORY_DEBUG] " + ex.getMessage());
	        ex.printStackTrace();
	        throw ex;
	    }
	    return count;
	}
}
