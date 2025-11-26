package controller;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.User;
import model.bo.UserBO;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final UserBO userBO = new UserBO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		final String DEBUG_PREFIX = "[LOGIN_DEBUG] ";
		request.setCharacterEncoding("UTF-8");
		
		String loginIdentifier = request.getParameter("loginIdentifier");
		String password = request.getParameter("password");
		
		String errorCode = null;
		if (loginIdentifier == null || loginIdentifier.isEmpty() 
				|| password == null || password.isEmpty()) {
			errorCode = "MISSING_FIELDS";
			System.out.println(DEBUG_PREFIX + errorCode + ": : Required fields are missing.");
		}
		
		if (errorCode != null) {
			request.setAttribute("errorCode", errorCode);
			request.setAttribute("loginIdentifier", loginIdentifier);
			
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		try {
			Optional<User> loggedUserOpt = userBO.loginByAnyIdentifier(loginIdentifier, password);
			if (loggedUserOpt.isPresent()) {
				System.out.println(DEBUG_PREFIX + "SUCCESS: User logged in: " + loginIdentifier);
				
				User loggedUser = loggedUserOpt.get();
	            HttpSession session = request.getSession();
	            // Dùng key 'user_id' thống nhất với các JSP / controller khác
	            session.setAttribute("user_id", loggedUser.getUserId());
	            session.setAttribute("username", loggedUser.getUsername());
	            session.setAttribute("email", loggedUser.getEmail());
	            session.setAttribute("role", loggedUser.getRole());
	            
				response.sendRedirect("welcome.jsp?msg=login_success");
			} else {
				errorCode = "ACCOUNT_NOT_EXISTS";
				System.out.println(DEBUG_PREFIX + errorCode + ": Username or email was not registered.");
				
				request.setAttribute("errorCode", errorCode);
				request.setAttribute("loginIdentifier", loginIdentifier);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch(Exception ex) {
			errorCode = "SERVER_ERROR";
			System.err.println(DEBUG_PREFIX + errorCode + ": Database/Server Error during registration: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
