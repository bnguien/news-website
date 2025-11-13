package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.bean.*;
import model.bo.UserBO;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public final UserBO userBO = new UserBO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String DEBUG_PREFIX = "[REGISTER_DEBUG] ";
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String role = request.getParameter("role");
		
		String errorCode = null;
		if (confirmPassword == null || confirmPassword.equals(password)) {
			errorCode = "PASSWORD_MISMATCH";
			System.out.println(DEBUG_PREFIX + errorCode + ": Passwords do not match.");
	        return;
		} else if (username == null || username.trim().isEmpty()
			|| email == null || email.trim().isEmpty()
			|| password == null || password.trim().isEmpty()
			|| role == null || role.trim().isEmpty()) {
			errorCode = "MISSING_FIELDS";
			System.out.println(DEBUG_PREFIX + errorCode + ": Required fields are missing.");
		}
		
		if (errorCode != null) {
			request.setAttribute("errorCode", errorCode);
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		try {
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			user.setRole(role);
			
			boolean registerSuccess = userBO.register(user);
			if (registerSuccess) {
				System.out.println(DEBUG_PREFIX + "SUCCESS: User registered: " + username);
				response.sendRedirect("login.jsp?msg=register_success");
			} else {
				errorCode = "REGISTER_EXISTS";
				System.out.println(DEBUG_PREFIX + errorCode + ": Username or email already in use.");
				
				request.setAttribute("errorCode", errorCode);
				request.setAttribute("username", username);
				request.setAttribute("email", email);
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		} catch(Exception ex) {
			errorCode = "SERVER_ERROR";
			System.err.println(DEBUG_PREFIX + errorCode 
					+ ": Database/Server Error during registration: " + ex.getMessage());
			ex.printStackTrace(); 
			
			request.setAttribute("errorCode", errorCode);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
}