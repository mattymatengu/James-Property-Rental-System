package ui;

import dao.TenantDAO;
import model.Tenant;
import util.ReportExporter;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReportsPanel extends JPanel {

    private JTextArea reportArea;
    private TenantDAO tenantDAO = new TenantDAO();

    public ReportsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buildUI();
    }

    private void buildUI() {
        reportArea = new JTextArea();
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 13));
        reportArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(reportArea);

        JButton tenantReportBtn = new JButton("Generate Tenant Report");
        JButton exportBtn       = new JButton("Export to CSV");

        tenantReportBtn.addActionListener(e -> generateTenantReport());
        exportBtn.addActionListener(e -> exportReport());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(tenantReportBtn);
        btnPanel.add(exportBtn);

        add(btnPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void generateTenantReport() {
        ArrayList<Tenant> tenants = tenantDAO.getAllTenants();

        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("       TENANT REPORT\n");
        sb.append("==============================\n\n");
        sb.append(String.format("%-10s %-20s %-15s %-10s%n",
            "ID", "Name", "Phone", "Status"));
        sb.append("----------------------------------------------\n");

        for (Tenant t : tenants) {
            sb.append(String.format("%-10s %-20s %-15s %-10s%n",
                t.getTenantId(),
                t.getFullName(),
                t.getPhone(),
                t.getStatus()));
        }

        sb.append("\nTotal tenants: ").append(tenants.size());
        reportArea.setText(sb.toString());
    }

    private void exportReport() {
        ArrayList<Tenant> tenants = tenantDAO.getAllTenants();

        if (tenants.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to export. Generate a report first.");
            return;
        }

        // File chooser so user picks where to save
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("Rental_Status_Report.csv"));
        int option = chooser.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            ReportExporter.exportTenantsToCSV(tenants, path);
            JOptionPane.showMessageDialog(this, "Report exported to:\n" + path);
        }
    }
}
