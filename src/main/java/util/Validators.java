package util;

import java.util.List;
import java.util.regex.Pattern;

import jakarta.servlet.http.Part;

public class Validators {
	// Quy tắc: 3-24 ký tự; chỉ chữ cái, số, _, -, . và ít nhất 1 chữ cái.
    private static final String USERNAME_PATTERN = 
            "^(?=.*[a-zA-Z])[a-zA-Z0-9_.-]{3,24}$";

    // Quy tắc: Định dạng cơ bản local@domain.tld
    private static final String EMAIL_PATTERN = 
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Quy tắc: 8-20 ký tự; bắt buộc 1 chữ hoa, 1 chữ thường, 1 số, 1 ký tự đặc biệt.
    private static final String PASSWORD_PATTERN = 
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
    
    /**
     * Kiểm tra tính hợp lệ của Username.
     * Quy tắc: 3-24 ký tự; chỉ chữ cái, số, _, -, . và ít nhất 1 chữ cái.
     */
	public static boolean isValidUsername(String username) {
        if (username == null) return false;
        return Pattern.compile(USERNAME_PATTERN).matcher(username.trim()).matches();
	}

    /**
     * Kiểm tra tính hợp lệ của Email.
     * Quy tắc: Định dạng local@domain.tld cơ bản.
     */
	public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return Pattern.compile(EMAIL_PATTERN).matcher(email.trim()).matches();
	}

    /**
     * Kiểm tra độ phức tạp của Password.
     * Quy tắc: 8-20 ký tự; bắt buộc 1 chữ hoa, 1 chữ thường, 1 số, 1 ký tự đặc biệt (@$!%*?&).
     */
	public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
	}
	
	/**
     * Kiểm tra một chuỗi có rỗng (null, "", " ") hay không.
     * @param field Chuỗi cần kiểm tra
     * @return true nếu rỗng, false nếu có nội dung
     */
    public static boolean isFieldEmpty(String field) {
        return field == null || field.trim().isEmpty();
    }
    
    /**
     * Kiểm tra độ dài chuỗi có vượt quá giới hạn không.
     * @param field Chuỗi cần kiểm tra
     * @param maxLength Độ dài tối đa cho phép
     * @return true nếu vượt quá, false nếu hợp lệ
     */
    public static boolean isLengthExceeded(String field, int maxLength) {
        // Chỉ check độ dài nếu nó không rỗng
        return field != null && field.length() > maxLength;
    }
    
    /**
     * Kiểm tra một chuỗi có phải là số nguyên dương hợp lệ không
     * (Dùng cho ID)
     * @param field Chuỗi cần kiểm tra (ví dụ: "1", "abc", "-5")
     * @return true nếu hợp lệ (là số > 0), false nếu không
     */
    public static boolean isValidPositiveInteger(String field) {
        if (isFieldEmpty(field)) {
            return false; // ID không được rỗng
        }
        try {
            int id = Integer.parseInt(field);
            return id > 0; // ID phải lớn hơn 0
        } catch (NumberFormatException e) {
            return false; // Không phải là số
        }
    }
    
    /**
     * Kiểm tra file upload có phải là ảnh hợp lệ không (check loại file).
     * @param filePart File nhận từ request.getPart()
     * @return true nếu là file ảnh, false nếu không
     */
    public static boolean isImageFile(Part filePart) {
        if (filePart == null || filePart.getSize() == 0) {
            return false; 
        }
        String contentType = filePart.getContentType();
        List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
        return contentType != null && allowedTypes.contains(contentType);
    }
    
    /**
     * Kiểm tra kích thước file có vượt quá giới hạn không.
     * @param filePart File nhận từ request.getPart()
     * @param maxSizeBytes Kích thước tối đa (bytes) (ví dụ: 10 * 1024 * 1024 cho 10MB)
     * @return true nếu file quá lớn, false nếu hợp lệ
     */
     public static boolean isFileSizeExceeded(Part filePart, long maxSizeBytes) {
         return filePart != null && filePart.getSize() > maxSizeBytes;
     }
}
