package dao;

import model.User;
import java.sql.*;

public class UserDAO {

    // Returns a User object if login succeeds, or null if it fails
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Login successful — return the user
                return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null; // login failed
    }
}
