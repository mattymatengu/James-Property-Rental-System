package util;

import model.Tenant;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReportExporter {

    // Exports a list of tenants to a CSV file at the given path
    public static void exportTenantsToCSV(ArrayList<Tenant> tenants, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            // Header row
            writer.write("ID,First Name,Last Name,ID Number,Phone,Email,Status\n");

            // One row per tenant
            for (Tenant t : tenants) {
                writer.write(
                    t.getTenantId()  + "," +
                    t.getFirstName() + "," +
                    t.getLastName()  + "," +
                    t.getIdNumber()  + "," +
                    t.getPhone()     + "," +
                    t.getEmail()     + "," +
                    t.getStatus()    + "\n"
                );
            }

            System.out.println("Report saved: " + filePath);

        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }
}
