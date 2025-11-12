package model.bo;

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
}
