package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;

public class PropertyDAO {

    // ── ADD a property ────────────────────────────────────────────
    public boolean addProperty(Property property) {
        String sql = "INSERT INTO properties (property_id, type, address, location, " +
                     "market_value, monthly_rent, is_available) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, property.getPropertyId());
            stmt.setString(2, property.getType());
            stmt.setString(3, property.getAddress());
            stmt.setString(4, property.getLocation());
            stmt.setDouble(5, property.getMarketValue());
            stmt.setDouble(6, property.getMonthlyRent());
            stmt.setBoolean(7, property.isAvailable());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding property: " + e.getMessage());
            return false;
        }
    }

    // ── GET all properties ────────────────────────────────────────
    // Notice we use ArrayList<Property> — polymorphism means we can
    // store House, Townhouse, and Flat all in one list
    public ArrayList<Property> getAllProperties() {
        ArrayList<Property> properties = new ArrayList<>();
        String sql = "SELECT * FROM properties";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("type");
                Property p;

                // We create the right subclass based on the type column
                if (type.equals("House")) {
                    p = new House(
                        rs.getString("property_id"),
                        rs.getString("address"),
                        rs.getString("location"),
                        rs.getDouble("market_value"),
                        rs.getDouble("monthly_rent"),
                        0 // plot size — simplified for demo
                    );
                } else if (type.equals("Townhouse")) {
                    p = new Townhouse(
                        rs.getString("property_id"),
                        rs.getString("address"),
                        rs.getString("location"),
                        rs.getDouble("market_value"),
                        rs.getDouble("monthly_rent"),
                        "N/A", false
                    );
                } else {
                    p = new Flat(
                        rs.getString("property_id"),
                        rs.getString("address"),
                        rs.getString("location"),
                        rs.getDouble("market_value"),
                        rs.getDouble("monthly_rent"),
                        "N/A", 1, false, false
                    );
                }
                properties.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error loading properties: " + e.getMessage());
        }

        return properties;
    }
}
