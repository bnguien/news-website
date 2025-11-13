package model.bo;

import java.util.Optional;

import model.bean.User;
import model.dao.UserDAO;
import util.SecurityUtils;
import util.Validators;

public class UserBO {
	public static final UserDAO userDAO = new UserDAO();
	public UserBO() {}
	
	public boolean isEmailExist(String email) {
		Optional<User> userOpt = userDAO.findByEmail(email);
		return userOpt.isPresent();
	}
	
	public boolean register(User user) {
		String role = user.getRole();
		if (!role.equalsIgnoreCase("reader") || !role.equalsIgnoreCase("reporter")) {
			return false;
		}
		
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		if (!Validators.isValidUsername(username) || 
				!Validators.isValidEmail(email) ||
				!Validators.isValidPassword(password)) {
			return false;
		}
		
		if (isEmailExist(email)) {
			return false;
		}
		
		String hashedPassword = SecurityUtils.hashPassword(user.getPassword());
		user.setPassword(hashedPassword);
		return userDAO.registerUser(user);
	}
	
	public Optional<User> loginByUsername(String username, String password) {
		if (!Validators.isValidUsername(username) || !Validators.isValidPassword(password)) {
			return Optional.empty();
		}
		Optional<User> userOpt = userDAO.findByUsername(username);
		
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			String storedHash = user.getPassword();
			if (SecurityUtils.verifyPassword(password, storedHash)) {
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}
	
	public Optional<User> loginByEmail(String email, String password) {
		if (!Validators.isValidEmail(email) || !Validators.isValidPassword(password)) {
			return Optional.empty();
		}
		Optional<User> userOpt = userDAO.findByEmail(email);
		
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			String storedHash = user.getPassword();
			if (SecurityUtils.verifyPassword(password, storedHash)) {
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}
	
	public Optional<User> loginByAnyIdentifier(String loginIdentifier, String password) {
		if (Validators.isValidEmail(loginIdentifier)) {
			return loginByEmail(loginIdentifier, password);
		} else if (Validators.isValidUsername(password)) {
			return loginByUsername(loginIdentifier, password);
		}
		return Optional.empty();
	}
}