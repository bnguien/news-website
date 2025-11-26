package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import config.FileStorageConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.bean.Article;
import model.bean.ArticleSearchCriteria;
import model.bean.Category;
import model.bean.User;
import model.bo.ArticleBO;
import model.bo.CategoryBO;
import util.Validators;

@WebServlet("/articles")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB (file tạm)
	    maxFileSize = 1024 * 1024 * 10, // 10MB (max 1 ảnh)
	    maxRequestSize = 1024 * 1024 * 50 // 50MB (max cả request)
)
public class ArticleController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public final ArticleBO articleBO = new ArticleBO();
	public final CategoryBO categoryBO = new CategoryBO();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		final String DEBUG_PREFIX = "[ARTICLE_DEBUG]";
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if (action == null) {
			doGet(request, response);
		}
		try {
			switch(action) {
				case "create":
					handleCreate(request, response);
					break;
				case "edit":
				case "delete":
				default:
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if (action == null) {
			action = "listPublic";
		} 
		try {
			switch(action) {
				/*Chức năng cho tài khoản reporter*/
				case "myArticles":
					handleMyArticles(request, response);
					break;
				case "createForm":
					handleShowCreateForm(request, response);
					break;
				case "editForm":
					//handleShowEditForm(request, response);
					break;
				case "submitForReview":
					//handleSubmitForReview(request, response);
					break;
					
				/*Chức năng cho tài khoản reader/non-logged in user*/
				case "searchPublic":
					handleSearchPublic(request, response);
					break;
				case "listByCategory":
					handleListByCategory(request, response);
					break;
				case "viewDetail":
					handleViewDetail(request, response);
					break;
	            case "listPublic":
	            default:
	                handleListPublic(request, response);
	                break;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*Handle GET function*/
	private void handleShowCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		List<Category> categories = categoryBO.getAllCategories();
		
		//Tạo CSRF Token
		String csrfToken = UUID.randomUUID().toString();
        request.getSession().setAttribute("csrfToken", csrfToken);
        
        request.setAttribute("categories", categories);
        request.setAttribute("csrfToken", csrfToken); 

        request.getRequestDispatcher("/article-form.jsp").forward(request, response);
	}

	private void handleMyArticles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String pageStr = request.getParameter("page");
		int page = 1;
		int pageSize = 10;
		
		if (pageStr != null && !pageStr.isEmpty()) {
		    try {
		        page = Integer.parseInt(pageStr);
		    } catch (NumberFormatException e) {
		        page = 1;
		    }
		}
		List<Article> articles = articleBO.getArticlesByAuthorId(user.getUserId(), page, pageSize);
		int totalArticles = articleBO.getArticlesCountByAuthorId(user.getUserId());
		int totalPages = (int) Math.ceil((double) totalArticles / pageSize);
		
		request.setAttribute("articles", articles);
	    request.setAttribute("totalPages", totalPages);
	    request.setAttribute("currentPage", page);
	    
        request.getRequestDispatcher("/article-list.jsp").forward(request, response);
	}

	private void handleSearchPublic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String searchValue = request.getParameter("search_value");
		String categoryIdStr = request.getParameter("cateogory");
		String timeFilter = request.getParameter("updatedTime");
		String pageStr = request.getParameter("page");
		
		int page = 1;
	    int pageSize = 10; 
	    if (pageStr != null && !pageStr.isEmpty()) {
	        try {
	            page = Integer.parseInt(pageStr);
	        } catch (NumberFormatException e) {
	            page = 1; 
	        }
	    }
	    
	    ArticleSearchCriteria criteria = new ArticleSearchCriteria();
	    criteria.setSearchValue(searchValue);
	    criteria.setCategoryIdStr(categoryIdStr);
	    criteria.setTimeFilter(timeFilter);
	    criteria.setPage(page);
	    criteria.setPageSize(pageSize);
		
	    List<Article> articles = articleBO.searchPublicArticles(criteria);
	    int totalArticles = articleBO.getPublicSearchCount(criteria); 
	    int totalPages = (int) Math.ceil((double) totalArticles / pageSize);
	    
	    request.setAttribute("articles", articles);
	    request.setAttribute("totalPages", totalPages);
	    request.setAttribute("currentPage", page);
	    request.setAttribute("criteria", criteria);
	    
	    request.getRequestDispatcher("/search-results.jsp").forward(request, response);
	}

	private void handleListByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String categoryIdStr = request.getParameter("categoryId");	
		String pageStr = request.getParameter("page");
		int page = 1;
		int pageSize = 10;
		int categoryId = 0;
		
		if (categoryIdStr == null || categoryIdStr.isEmpty()) {
			System.out.println("[LIST_CATEGORY_DEBUG] Missing CategoryId");
			handleListPublic(request, response); 
	        return;
		}
		
		try {
	        categoryId = Integer.parseInt(categoryIdStr);
	    } catch (NumberFormatException e) {
	        System.out.println("[LIST_CATEGORY_DEBUG] Invalid CategoryId");
	        handleListPublic(request, response);
	        return; 
	    }
		
		Category category = categoryBO.getCategoryById(categoryId);
		if (category == null) {
		    handleListPublic(request, response);
		    return;
		}
		
		if (pageStr != null && !pageStr.isEmpty()) {
	        try {
	            page = Integer.parseInt(pageStr);
	        } catch (NumberFormatException e) {
	            page = 1; 
	        }
	    }
		
		List<Article> articles = articleBO.getPublicArticlesByCategoryId(categoryId, page, pageSize);
		int totalArticles = articleBO.getPublicArticlesCountByCategory(categoryId);
	    int totalPages = (int) Math.ceil((double) totalArticles / pageSize);
	    
	    request.setAttribute("articles", articles);
	    request.setAttribute("totalPages", totalPages);
	    request.setAttribute("currentPage", page);
	    
	    request.getRequestDispatcher("/article-list.jsp").forward(request, response);
	}

	private void handleListPublic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//String DEBUG_PREFIX = "[ARTICLE_DEBUG] - [PUBLIC_LIST] ";
		String pageStr = request.getParameter("page");
		int page = 1;
		int pageSize = 10;

		if (pageStr != null && !pageStr.isEmpty()) {
		    try {
		        page = Integer.parseInt(pageStr);
		    } catch (NumberFormatException e) {
		        page = 1;
		    }
		}
		List<Article> articles = articleBO.getPublicArticles(page, pageSize);
		int totalArticles = articleBO.getPublicArticlesCount();
		int totalPages = (int) Math.ceil((double) totalArticles / pageSize);
		
		request.setAttribute("articles", articles);
	    request.setAttribute("totalPages", totalPages);
	    request.setAttribute("currentPage", page);
	    
        request.getRequestDispatcher("/article-list.jsp").forward(request, response);
	}

	private void handleViewDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//String DEBUG_PREFIX = "[ARTICLE_DEBUG] - [VIEW_DETAIL]";
		String idStr = request.getParameter("id");
		
		int articleID = 0;
		if (idStr == null || idStr.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing article ID");
	        return;
	    }
		
		try {
	        articleID = Integer.parseInt(idStr);
	    } catch (NumberFormatException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid article ID");
	        return;
	    }
		
		articleBO.incrementViewCount(articleID);
		
		Article article = articleBO.getPublicArticleById(articleID);
		
		if (article == null) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy bài viết.");
	        return;
	    }
		request.setAttribute("article", article);
		
		request.getRequestDispatcher("/article-detail.jsp").forward(request, response);
	}
	
	
	/*Handle POST function*/
	private void handleCreate(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException, SQLException {
	    
	    HttpSession session = request.getSession();

	    String sessionToken = (String) session.getAttribute("csrfToken");
	    String requestToken = request.getParameter("csrfToken");
	    
	    if (sessionToken == null || !sessionToken.equals(requestToken)) {
	        session.removeAttribute("csrfToken"); 
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CSRF token.");
	        return;
	    }
	    session.removeAttribute("csrfToken");

	    String title = request.getParameter("title");
	    String summary = request.getParameter("summary");
	    String content = request.getParameter("content");
	    String categoryIdStr = request.getParameter("categoryId");
	    String submitType = request.getParameter("submitType"); 
	    Part filePart = request.getPart("image");
	    
	    List<String> errors = new ArrayList<>();
	    if (Validators.isFieldEmpty(title)) {
	        errors.add("TITLE_REQUIRED");
	    } else if (Validators.isLengthExceeded(title, 255)) {
	        errors.add("TITLE_MAX_LENGTH_255");
	    }
	    if (Validators.isLengthExceeded(summary, 300)) {
	        errors.add("SUMMARY_MAX_LENGTH_300");
	    }
	    if (Validators.isFieldEmpty(content)) {
	        errors.add("CONTENT_REQUIRED");
	    }
	    if (!Validators.isValidPositiveInteger(categoryIdStr)) {
	        errors.add("CATEGORY_INVALID");
	    }
	    
	    // Validate Image
	    long maxFileSize = 10 * 1024 * 1024; // 10MB
	    if (filePart == null || filePart.getSize() == 0) {
	        errors.add("IMAGE_REQUIRED");
	    } else if (!Validators.isImageFile(filePart)) {
	        errors.add("IMAGE_INVALID_TYPE");
	    } else if (Validators.isFileSizeExceeded(filePart, maxFileSize)) {
	        errors.add("IMAGE_MAX_SIZE_10MB");
	    }
	    
	    if (!errors.isEmpty()) {
	        System.out.println("[CREATE_VALIDATE_DEBUG] Validation failed: " + errors);
	        
	        request.setAttribute("errors", errors);
	        
	        request.setAttribute("oldTitle", title);
	        request.setAttribute("oldSummary", summary);
	        request.setAttribute("oldContent", content);
	        request.setAttribute("oldCategoryId", categoryIdStr); 
	        
	        handleShowCreateForm(request, response);
	        return; 
	    }
	    
	    try {
	        User user = (User) session.getAttribute("user"); 
	        
	        String originalFileName = filePart.getSubmittedFileName();
	        String newFileName = generateUniqueFileName(originalFileName); 
	        String saveDir = FileStorageConfig.getFullAbsoluteDir();
	        String absoluteFilePath = saveDir + File.separator + newFileName;

	        filePart.write(absoluteFilePath); // Lưu file vào C:\Users\...\my-news-uploads\article_images
	        String dbPath = FileStorageConfig.getFullWebPath(newFileName); // Lấy path /uploads/article_images/...

	        Article article = new Article();
	        article.setTitle(title);
	        article.setSummary(summary);
	        article.setContent(content);
	        article.setImage(dbPath);
	        article.setAuthorId(user.getUserId()); 
	        article.setCategoryId(Integer.parseInt(categoryIdStr)); // Giờ mới parse
	        article.setViewCount(0);
	        article.setPublishedAt(null);
	        article.setStatus("pending".equals(submitType) ? "pending" : "draft");

	        // --- 5c. Gọi BO để lưu ---
	        articleBO.createArticle(article);
	        
	        // --- 5d. Điều hướng khi thành công ---
	        response.sendRedirect("articles?action=myArticles&create=success");
	    } catch (Exception e) {
	    	System.err.println("[CREATE_DEBUG]" + "Server error during article creation.");
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error during article creation.");
	    }
	}
}

private String generateUniqueFileName(String originalFileName) {
    String extension = "";
    int i = originalFileName.lastIndexOf('.');
    if (i > 0) {
        extension = originalFileName.substring(i); 
    return System.currentTimeMillis() + extension; 
}
