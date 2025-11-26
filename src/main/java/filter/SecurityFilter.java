package filter;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/articles")
public class SecurityFilter implements Filter{
	private final List<String> reporterActions = List.of(
            "myArticles",
            "createForm", "create",
            "editForm", "update",
            "delete",
            "submitForReview"
    );
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String action = req.getParameter("action");
		
        if (reporterActions.contains(action)) {
        	HttpSession session = req.getSession(false);
        	if (session == null || session.getAttribute("userId") == null) {
                System.out.println("[SECURITY_FILTER] Blocked: User not authenticated.");
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            String role = (String) session.getAttribute("role");
            if (!"reporter".equals(role) && !"admin".equals(role)) {
                System.out.println("[SECURITY_FILTER] Blocked: Role '" + role + "' is not authorized.");
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this resource.");
                return;
            }
        }
        System.out.println("[SECURITY_FILTER] Allowed: " + action);
        chain.doFilter(request, response);
	}

}
