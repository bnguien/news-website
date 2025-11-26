package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnection;

public class CategoryDAO {
	public int insertCategory(String name, String description) {
		String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, name);
			ps.setString(2, description);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean updateCategory(int id, String name, String description) {
			String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
			try(Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, name);
				ps.setString(2, description);
				ps.setInt(3, id);
				int rowsAffected = ps.executeUpdate();
				return rowsAffected > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
	}
	
	public boolean deleteCategory(int id) {
			String sql = "DELETE FROM categories WHERE id = ?";
			try(Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);
				int rowsAffected = ps.executeUpdate();
				return rowsAffected > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
}
