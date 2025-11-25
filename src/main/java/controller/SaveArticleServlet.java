package controller;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Đăng ký Servlet này với đường dẫn "/saveArticle"
// Đường dẫn này phải khớp với 'action' trong <form>
@WebServlet("/saveArticle")
public class SaveArticleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Thiết lập encoding UTF-8 để nhận Tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 2. Lấy dữ liệu từ form
        String title = request.getParameter("articleTitle");
        
        // Đây là mấu chốt: Lấy chuỗi HTML từ CKEditor
        // "articleContent" là thuộc tính 'name' của thẻ <textarea>
        String htmlContent = request.getParameter("articleContent");

        // 3. (Giai đoạn này, bạn sẽ gọi BO/DAO để lưu 'htmlContent' vào DB)
        // Ở đây chúng ta sẽ in ra để kiểm tra
        System.out.println("--- TIÊU ĐỀ NHẬN ĐƯỢC ---");
        System.out.println(title);
        
        System.out.println("\n--- CHUỖI HTML SẠCH NHẬN ĐƯỢC TỪ EDITOR ---");
        System.out.println(htmlContent);

        // 4. Phản hồi về trình duyệt (chỉ để demo)
        PrintWriter out = response.getWriter();
        out.println("<html><head><meta charset='UTF-8'><title>Đã nhận</title></head><body>");
        out.println("<h2>Đã nhận bài viết thành công!</h2>");
        out.println("<h3>Tiêu đề:</h3>");
        out.println("<p>" + title + "</p>");
        
        out.println("<h3>Nội dung HTML nhận được (chưa render):</h3>");
        // Hiển thị mã HTML dưới dạng text thuần túy
        out.println("<pre>" + escapeHtml(htmlContent) + "</pre>"); 

        out.println("<h3>Nội dung được render (giống khi đọc báo):</h3>");
        // In thẳng chuỗi HTML ra để trình duyệt render
        out.println("<div>" + htmlContent + "</div>"); 
        
        out.println("</body></html>");
    }
    
    // Hàm phụ trợ để hiển thị code HTML (tránh bị render)
    private String escapeHtml(String html) {
        if (html == null) return "";
        return html.replace("<", "&lt;").replace(">", "&gt;");
    }
}