package controller;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.bean.User;
import model.bo.UserBO;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserBO userBO = new UserBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String loginIdentifier = request.getParameter("loginIdentifier");
        String password = request.getParameter("password");

        String errorCode = null;

        if (loginIdentifier == null || loginIdentifier.isEmpty()
                || password == null || password.isEmpty()) {
            errorCode = "MISSING_FIELDS";
            request.setAttribute("errorCode", errorCode);
            request.setAttribute("loginIdentifier", loginIdentifier);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            Optional<User> loginOpt = userBO.loginByAnyIdentifier(loginIdentifier, password);

            if (loginOpt.isPresent()) {
                User u = loginOpt.get();
                HttpSession session = request.getSession();
                session.setAttribute("user_id", u.getUserId());
                session.setAttribute("username", u.getUsername());
                session.setAttribute("email", u.getEmail());
                session.setAttribute("role", u.getRole());

                if ("admin".equalsIgnoreCase(u.getRole())) {
                    response.sendRedirect("admin-dashboard");
                } else {
                    response.sendRedirect("welcome.jsp?msg=login_success");
                }
                return;
            }

            errorCode = "ACCOUNT_NOT_EXISTS";
            request.setAttribute("errorCode", errorCode);
            request.setAttribute("loginIdentifier", loginIdentifier);
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception ex) {
            errorCode = "SERVER_ERROR";
            request.setAttribute("errorCode", errorCode);
            ex.printStackTrace();
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
