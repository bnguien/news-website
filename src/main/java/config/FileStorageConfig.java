package config;
import java.io.File;

/**
 * Lớp này chứa các hằng số cấu hình cho việc lưu trữ file upload.
 * Mục tiêu là lưu file ra một thư mục CỐ ĐỊNH BÊN NGOÀI project
 * để tránh bị mất file khi build lại server (Tomcat).
 */
public class FileStorageConfig {
	public static final String ABSOLUTE_PATH_ROOT = System.getProperty("user.home") 
            + File.separator + "my-news-uploads";
	public static final String ARTICLE_IMAGE_DIR = "article_images";
	public static final String WEB_URL_PREFIX = "/uploads";
	
	/**
     * Lấy đường dẫn TUYỆT ĐỐI đầy đủ đến thư mục LƯU ẢNH.
     * (Hàm này dùng trong `handleCreate` để LƯU FILE VẬT LÝ)
     * * Ví dụ: Trả về "C:\Users\Username\my-news-uploads\article_images"
     * * @return Đường dẫn tuyệt đối đến thư mục, tự động tạo nếu chưa có.
     */
	public static String getFullAbsoluteDir() {
        String fullPath = ABSOLUTE_PATH_ROOT + File.separator + ARTICLE_IMAGE_DIR;
        
        File dir = new File(fullPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("[FileStorageConfig] Created directory: " + fullPath);
            } else {
                System.err.println("[FileStorageConfig] FAILED to create directory: " + fullPath);
            }
        }
        return fullPath;
    }
	
	/**
     * Lấy đường dẫn WEB đầy đủ của file (để LƯU VÀO DATABASE).
     * (Hàm này dùng trong `handleCreate` để set vào object Article)
     * * Ví dụ: "/uploads/article_images/ten-file-moi.jpg"
     * * @param fileName Tên file (ví dụ: "ten-file-moi.jpg")
     * @return Đường dẫn web đầy đủ
     */
	public static String getFullWebPath(String fileName) {
        return WEB_URL_PREFIX + "/" + ARTICLE_IMAGE_DIR + "/" + fileName;
    }
}
