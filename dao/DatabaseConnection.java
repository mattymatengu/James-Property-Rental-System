package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class manages the connection to your MySQL database.
// Every DAO uses this — you write it once and share it everywhere.
public class DatabaseConnection {

    // Change these three values to match your own MySQL setup
    private static final String URL      = "jdbc:mysql://localhost:3306/james_property";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "*Lapulga2007";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
