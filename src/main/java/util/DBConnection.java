package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3307/news_website_db?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {

            // Nạp class của driver MySQL
            // (Sử dụng 'cj' cho các phiên bản driver mới)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Lỗi này xảy ra nếu bạn CHƯA THÊM file .jar của MySQL Connector
            // vào thư mục WEB-INF/lib
            System.err.println("Không tìm thấy MySQL JDBC Driver!");

            e.printStackTrace();
        }
    }
    

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
