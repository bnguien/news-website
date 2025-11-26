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
}
