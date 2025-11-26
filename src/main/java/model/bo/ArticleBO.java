package model.bo;

import java.util.List;

import model.bean.Article;
import model.dao.ArticleDAO;

public class ArticleBO {

    private static final int DEFAULT_PAGE_SIZE = 6;

    private ArticleDAO articleDAO = new ArticleDAO();

    public List<Article> getAllPublishedArticles() {
        return articleDAO.getAllPublishedArticles();
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
}


