package dao;

import model.Tenant;
import java.sql.*;
import java.util.ArrayList;

// DAO = Data Access Object
// This is the ONLY class that talks to the database for Tenant data.
// The UI calls these methods — it never writes SQL itself.
public class TenantDAO {

    // ── ADD a new tenant ─────────────────────────────────────────
    public boolean addTenant(Tenant tenant) {
        String sql = "INSERT INTO tenants (tenant_id, first_name, last_name, " +
                     "id_number, phone, email, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenant.getTenantId());
            stmt.setString(2, tenant.getFirstName());
            stmt.setString(3, tenant.getLastName());
            stmt.setString(4, tenant.getIdNumber());
            stmt.setString(5, tenant.getPhone());
            stmt.setString(6, tenant.getEmail());
            stmt.setString(7, tenant.getStatus());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding tenant: " + e.getMessage());
            return false;
        }
    }

    // ── GET all tenants — returns an ArrayList ────────────────────
    public ArrayList<Tenant> getAllTenants() {
        ArrayList<Tenant> tenants = new ArrayList<>();
        String sql = "SELECT * FROM tenants";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tenant t = new Tenant(
                    rs.getString("tenant_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("id_number"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("status")
                );
                tenants.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Error loading tenants: " + e.getMessage());
        }

        return tenants;
    }

    // ── DELETE a tenant by ID ─────────────────────────────────────
    public boolean deleteTenant(String tenantId) {
        String sql = "DELETE FROM tenants WHERE tenant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenantId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error deleting tenant: " + e.getMessage());
            return false;
        }
    }
}
