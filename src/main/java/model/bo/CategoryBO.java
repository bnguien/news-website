package model.bo;

import java.util.List;

import model.bean.Category;
import model.dao.CategoryDAO;

public class CategoryBO {

    private CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }

    public int addCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return -1;
        }
        category.setName(category.getName().trim());

        return categoryDAO.insertCategory(category.getName(), category.getDescription());
    }

    public boolean editCategory(Category category) {

        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return false;
        }

        category.setName(category.getName().trim());

        return categoryDAO.updateCategory(
                category.getCategoryId(),
                category.getName(),
                category.getDescription());
    }

    public boolean removeCategory(int categoryId) {
        int count = categoryDAO.countArticlesByCategory(categoryId);
        if (count > 0) {
            return false;
        }

        return categoryDAO.deleteCategory(categoryId);
    }
}
