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
	
	public boolean isUsernameExist(String username) {
		Optional<User> userOpt = userDAO.findByUsername(username);
		return userOpt.isPresent();
	}
	
	public String register(User user) {
		String role = user.getRole();
		if (!(role.equalsIgnoreCase("reader") || role.equalsIgnoreCase("reporter"))) {
			return "INVALID_ROLE";
		}
		
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		if (!Validators.isValidUsername(username)){
			return "INVALID_USERNAME";
		}
			
		if (!Validators.isValidEmail(email)) {
			return "INVALID_EMAIL";
		}
		
		if (!Validators.isValidPassword(password)) {
			return "INVALID_PASSWORD";
		}
		
		if (isEmailExist(email) || isUsernameExist(username)) {
			return "ACCOUNT_EXIST";
		}
		
		String hashedPassword = SecurityUtils.hashPassword(user.getPassword());
		user.setPassword(hashedPassword);
		if(userDAO.registerUser(user)) {
			return null;
		}
		return "NOT_FOUND_ERROR";
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
		} else if (Validators.isValidUsername(loginIdentifier)) {
			return loginByUsername(loginIdentifier, password);
		}
		return Optional.empty();
	}
}
