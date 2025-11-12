package util;
import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtils {
	public static String hashPassword(String plaintextPassword) {
		return BCrypt.hashpw(plaintextPassword, BCrypt.gensalt());
	}
	
	public static boolean verifyPassword(String plaintextPassword, String hashedPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }
}
