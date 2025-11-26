package model.bo;

import java.util.List;

import model.bean.Article;
import model.bean.Category;
import model.dao.ArticleDAO;

public class ArticleBO {

    private static final int DEFAULT_PAGE_SIZE = 6;

    private ArticleDAO articleDAO = new ArticleDAO();

    // =============================
    // PUBLIC (NGƯỜI DÙNG)
    // =============================
    public List<Article> getAllPublishedArticles() {
        return articleDAO.getAllPublishedArticles();
    }

    public List<Category> getAllCategories() {
        return articleDAO.getAllCategories();
    }

    public void insertArticle(Article article) {
        articleDAO.insertArticle(article);
    }

    public List<Article> getPublishedArticlesPaged(int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = DEFAULT_PAGE_SIZE;

        int offset = (page - 1) * pageSize;
        return articleDAO.getPublishedArticlesPaged(offset, pageSize);
    }

    public int getTotalPagesForPublished(int pageSize) {
        if (pageSize <= 0) pageSize = DEFAULT_PAGE_SIZE;
        int total = articleDAO.countPublishedArticles();
        return (int) Math.ceil((double) total / pageSize);
    }

    public Article getPublishedArticleById(int id) {
        return articleDAO.getPublishedArticleById(id);
    }

    public void increaseViewCount(int id) {
        articleDAO.increaseViewCount(id);
    }

    public List<Article> searchPublished(String keyword, int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = DEFAULT_PAGE_SIZE;

        int offset = (page - 1) * pageSize;
        return articleDAO.searchPublished(keyword, offset, pageSize);
    }

    public int getTotalPagesForSearch(String keyword, int pageSize) {
        if (pageSize <= 0) pageSize = DEFAULT_PAGE_SIZE;
        int total = articleDAO.countSearchPublished(keyword);
        return (int) Math.ceil((double) total / pageSize);
    }


    // =============================
    // ADMIN
    // =============================
    public List<Article> getAllArticlesAdmin() {
        return articleDAO.getAllArticlesAdmin();
    }

    public void updateStatus(int articleId, String status) {
        articleDAO.updateStatus(articleId, status);
    }

    public void deleteArticle(int articleId) {
        articleDAO.deleteArticle(articleId);
    }

    public Article getArticleById(int id) {
        return articleDAO.getArticleById(id);
    }

    public void updateArticle(int id, String title, String summary, String content) {
        articleDAO.updateArticle(id, title, summary, content);
    }
}
