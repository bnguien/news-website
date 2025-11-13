package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import model.bean.Category;
import model.bo.CategoryBO;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CategoryBO categoryBO;

    @Override
    public void init() throws ServletException {
        categoryBO = new CategoryBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "edit":
                showEditForm(request, response);
                break;

            case "delete":
                deleteCategory(request, response);
                break;

            default:
                listCategory(request, response);;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addCategory(request, response);
                break;

            case "update":
                updateCategory(request, response);
                break;
        }
    }
    private void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	List<Category> categoryList = categoryBO.getAllCategories();
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("/admin-category.jsp").forward(request, response);
        
    }
    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Category category = new Category();
        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));

        int result = categoryBO.addCategory(category);

        if (result == -1) {
            response.sendRedirect("admin-category.jsp?error=invalid_name");
            return;
        }

        response.sendRedirect("admin-category.jsp?msg=added");
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Category category = new Category();
        category.setCategoryId(Integer.parseInt(request.getParameter("id")));
        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));

        boolean success = categoryBO.editCategory(category);

        if (!success) {
            response.sendRedirect("admin-category.jsp?error=invalid_name");
            return;
        }

        response.sendRedirect("admin-category.jsp?msg=updated");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        boolean success = categoryBO.removeCategory(id);

        if (!success) {
            response.sendRedirect("admin-category.jsp?error=in_use");
            return;
        }

        response.sendRedirect("admin-category.jsp?msg=deleted");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryBO.getCategoryById(id);

        request.setAttribute("category", category);
        request.getRequestDispatcher("admin-category-edit.jsp").forward(request, response);
    }
}