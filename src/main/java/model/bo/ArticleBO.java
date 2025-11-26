package model.bo;

import java.sql.SQLException;
import java.util.List;

import model.bean.Article;
import model.bean.ArticleSearchCriteria;
import model.dao.ArticleDAO;

public class ArticleBO {
	public static final ArticleDAO articleDAO = new ArticleDAO();
	public ArticleBO() {}
	
	public List<Article> getPublicArticles(int page, int pageSize) throws SQLException {
		List<Article> articles = articleDAO.getPublicList(page, pageSize);
		return articles;
	}
	
	public int getPublicArticlesCount() throws SQLException {
	    return articleDAO.getPublicArticlesCount();
	}
	
	public Article getPublicArticleById(int articleID) throws SQLException{
		return articleDAO.getPublicDetail(articleID);
	}

	public void incrementViewCount(int articleID) throws SQLException {
	    articleDAO.increaseView(articleID);
	}

	public List<Article> getPublicArticlesByCategoryId(int categoryId, int page, int pageSize) throws SQLException {
		return articleDAO.getPublicByCategory(categoryId, page, pageSize);
	}

	public int getPublicArticlesCountByCategory(int categoryId) throws SQLException {
		return articleDAO.getPublicCountByCategory(categoryId);
	}

	public List<Article> searchPublicArticles(ArticleSearchCriteria criteria) throws SQLException  {
		int categoryId = 0;
		if (criteria.getCategoryIdStr() != null 
            && !criteria.getCategoryIdStr().isEmpty() 
            && !criteria.getCategoryIdStr().equals("all")) { 
            try {
                categoryId = Integer.parseInt(criteria.getCategoryIdStr());
            } catch (NumberFormatException e) { 
                categoryId = 0; 
            }
        }
		
		String timeSqlFilter = parseTimeFilter(criteria.getTimeFilter());
		
		return articleDAO.search(
	            criteria.getSearchValue(), 
	            categoryId, 
	            timeSqlFilter, 
	            criteria.getPage(), 
	            criteria.getPageSize()
	        );
	}

	private String parseTimeFilter(String timeFilter) {
		if (timeFilter == null || timeFilter.equals("all")) {
            return null;
        }
        
        switch (timeFilter) {
            case "1ngayqua":
                return "AND published_at >= NOW() - INTERVAL 1 DAY";
            case "1tuanqua":
                return "AND published_at >= NOW() - INTERVAL 1 WEEK";
            case "1thangqua":
                return "AND published_at >= NOW() - INTERVAL 1 MONTH";
            case "1namqua":
                return "AND published_at >= NOW() - INTERVAL 1 YEAR";
            default:
                return null;
        }
	}

	public int getPublicSearchCount(ArticleSearchCriteria criteria) throws SQLException {
		int categoryId = 0;
		if (criteria.getCategoryIdStr() != null 
            && !criteria.getCategoryIdStr().isEmpty() 
            && !criteria.getCategoryIdStr().equals("all")) { 
            try {
                categoryId = Integer.parseInt(criteria.getCategoryIdStr());
            } catch (NumberFormatException e) { 
                categoryId = 0; 
            }
        }
		
		String timeSqlFilter = parseTimeFilter(criteria.getTimeFilter());
		return articleDAO.searchCount(
	            criteria.getSearchValue(), 
	            categoryId, 
	            timeSqlFilter
	        );
	}

	public List<Article> getArticlesByAuthorId(int userId, int page, int pageSize) throws SQLException {
		List<Article> articles = articleDAO.getArticlesByAuthorId(userId, page, pageSize);
		return articles;
	}
	
	public int getArticlesCountByAuthorId(int userId) throws SQLException {
		return articleDAO.getArticlesCountByAuthorId(userId);
	}

	public void createArticle(Article article) {
		// TODO Auto-generated method stub
		
	}
}
