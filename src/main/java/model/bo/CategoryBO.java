package model.bo;

import java.util.List;

import model.bean.Category;
import model.dao.CategoryDAO;

public class CategoryBO {
	private CategoryDAO categoryDAO = new CategoryDAO();
	
	public int addCategory(Category category) {
		return categoryDAO.insertCategory(category.getName(), category.getDescription());
	}
	
	public boolean editCategory(Category category) {
		return categoryDAO.updateCategory(category.getCategoryId(), category.getName(), category.getDescription());
	}
	
	public boolean removeCategory(int categoryId) {
		return categoryDAO.deleteCategory(categoryId);
	}

	public Category getCategoryById(int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

    private CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }

    public String addCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return "INVALID_NAME";
        }

        category.setName(category.getName().trim());

        int result = categoryDAO.insertCategory(category.getName(), category.getDescription());
        return result > 0 ? "added" : "SERVER_ERROR";
    }

    public String editCategory(Category category) {
        Category existing = categoryDAO.getCategoryById(category.getCategoryId());
        if (existing == null) {
            return "NOT_FOUND";
        }

        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return "INVALID_NAME";
        }

        category.setName(category.getName().trim());

        boolean success = categoryDAO.updateCategory(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );

        return success ? "updated" : "SERVER_ERROR";
    }

    public String removeCategory(int categoryId) {
        Category existing = categoryDAO.getCategoryById(categoryId);
        if (existing == null) {
            return "NOT_FOUND";
        }

        int count = categoryDAO.countArticlesByCategory(categoryId);
        if (count > 0) {
            return "IN_USE";
        }

        boolean success = categoryDAO.deleteCategory(categoryId);
        return success ? "deleted" : "SERVER_ERROR";
    }
}
