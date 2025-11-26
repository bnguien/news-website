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
            // Trả về qua query param để JSP đọc bằng param.error
            String errorParam = "unknown";
            if ("INVALID_NAME".equals(result)) {
                errorParam = "invalid_name";
            }
            response.sendRedirect("admin-category.jsp?error=" + errorParam);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Category category = new Category();
        // JSP dùng tham số 'id' cho form sửa
        category.setCategoryId(Integer.parseInt(request.getParameter("id")));
        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));

        String result = categoryBO.editCategory(category);

        if ("updated".equals(result)) {
            response.sendRedirect("admin-category.jsp?msg=updated");
        } else {
            String errorParam = "unknown";
            if ("INVALID_NAME".equals(result)) {
                errorParam = "invalid_name";
            }
            response.sendRedirect("admin-category.jsp?error=" + errorParam);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JSP gọi href=\"category?action=delete&id=...\"
        int id = Integer.parseInt(request.getParameter("id"));
        String result = categoryBO.removeCategory(id);

        if ("deleted".equals(result)) {
            response.sendRedirect("admin-category.jsp?msg=deleted");
        } else {
            String errorParam = "unknown";
            if ("IN_USE".equals(result)) {
                errorParam = "in_use";
            }
            response.sendRedirect("admin-category.jsp?error=" + errorParam);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JSP gọi href=\"category?action=edit&id=...\"
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryBO.getCategoryById(id);

        request.setAttribute("category", category);
        request.getRequestDispatcher("admin-category-edit.jsp").forward(request, response);
    }
}
