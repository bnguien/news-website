package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import config.DBConnection;
import model.bean.User;

public class UserDAO {
	Connection conn;
	public UserDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch(SQLException ex) {
			System.out.println("[DATABASE_DEBUG] Cannot connect to Database");
			ex.printStackTrace();
		}
	}

	public Optional<User> findByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				User user = new User(rs.getInt("user_id"), 
								rs.getString("username"), 
								rs.getString("email"), 
								rs.getString("password"), 
								rs.getString("role"));
				return Optional.of(user);
			} 
		} catch(SQLException ex) {
			System.out.println("[DEBUG] " + ex.getMessage());
			ex.printStackTrace();
		}
		return Optional.empty();
	}
	
	public Optional<User> findByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				User user = new User(rs.getInt("user_id"), 
								rs.getString("username"), 
								rs.getString("email"), 
								rs.getString("password"), 
								rs.getString("role"));
				return Optional.of(user);
			} 
		} catch(SQLException ex) {
			System.out.println("[DEBUG] " + ex.getMessage());
			ex.printStackTrace();
		}
		return Optional.empty();
	}
	
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users(username, email, password, role) VALUES(?, ?, ?, ?)";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());
			
			return ps.executeUpdate() > 0;
		} catch(SQLException ex) {
			System.out.println("[SQL_DEBUG] " + ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}
}
