package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.bean.Category;
import model.bo.CategoryBO;

@WebServlet("/category")
public class CategoryController extends HttpServlet {

    private CategoryBO categoryBO;

    @Override
    public void init() {
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
                response.sendRedirect("admin-category.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":
                addCategory(request, response);
                break;

            case "update":
                updateCategory(request, response);
                break;

            default:
                response.sendRedirect("admin-category.jsp");
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Category category = new Category();
        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));

        String result = categoryBO.addCategory(category);

        if ("added".equals(result)) {
            response.sendRedirect("admin-category.jsp?msg=added");
        } else {
            request.setAttribute("errorCode", result);
            request.getRequestDispatcher("admin-category.jsp").forward(request, response);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Category category = new Category();
        category.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));

        String result = categoryBO.editCategory(category);

        if ("updated".equals(result)) {
            response.sendRedirect("admin-category.jsp?msg=updated");
        } else {
            request.setAttribute("errorCode", result);
            request.getRequestDispatcher("admin-category-edit.jsp").forward(request, response);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("category_id"));
        String result = categoryBO.removeCategory(id);

        if ("deleted".equals(result)) {
            response.sendRedirect("admin-category.jsp?msg=deleted");
        } else {
            request.setAttribute("errorCode", result);
            request.getRequestDispatcher("admin-category.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("category_id"));
        Category category = categoryBO.getCategoryById(id);

        request.setAttribute("category", category);
        request.getRequestDispatcher("admin-category-edit.jsp").forward(request, response);
    }
}
