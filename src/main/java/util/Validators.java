package util;

import java.util.regex.Pattern;

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
}
