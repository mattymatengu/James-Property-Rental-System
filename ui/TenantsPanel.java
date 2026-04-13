package ui;

import dao.TenantDAO;
import model.Tenant;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TenantsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private TenantDAO tenantDAO = new TenantDAO();

    public TenantsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buildUI();
        loadTenants();
    }

    private void buildUI() {
        String[] columns = {"ID", "First Name", "Last Name", "ID Number", "Phone", "Email", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);

        JButton addBtn    = new JButton("Add Tenant");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.addActionListener(e -> showAddDialog());
        deleteBtn.addActionListener(e -> deleteSelected());
        refreshBtn.addActionListener(e -> loadTenants());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);

        add(btnPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTenants() {
        tableModel.setRowCount(0);
        ArrayList<Tenant> tenants = tenantDAO.getAllTenants();

        for (Tenant t : tenants) {
            tableModel.addRow(new Object[]{
                t.getTenantId(), t.getFirstName(), t.getLastName(),
                t.getIdNumber(), t.getPhone(), t.getEmail(), t.getStatus()
            });
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a tenant to delete.");
            return;
        }

        String tenantId = (String) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete tenant " + tenantId + "?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            tenantDAO.deleteTenant(tenantId);
            loadTenants();
        }
    }

    private void showAddDialog() {
        JTextField idField    = new JTextField(10);
        JTextField fnField    = new JTextField(15);
        JTextField lnField    = new JTextField(15);
        JTextField idNumField = new JTextField(15);
        JTextField phoneField = new JTextField(12);
        JTextField emailField = new JTextField(20);

        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Tenant ID:"));    form.add(idField);
        form.add(new JLabel("First Name:"));   form.add(fnField);
        form.add(new JLabel("Last Name:"));    form.add(lnField);
        form.add(new JLabel("ID Number:"));    form.add(idNumField);
        form.add(new JLabel("Phone:"));        form.add(phoneField);
        form.add(new JLabel("Email:"));        form.add(emailField);

        int result = JOptionPane.showConfirmDialog(this, form,
            "Add New Tenant", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            // Validation
            if (idField.getText().trim().isEmpty() || fnField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID and First Name cannot be empty.");
                return;
            }

            Tenant t = new Tenant(
                idField.getText().trim(),
                fnField.getText().trim(),
                lnField.getText().trim(),
                idNumField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                "Active"
            );

            boolean success = tenantDAO.addTenant(t);
            if (success) {
                JOptionPane.showMessageDialog(this, "Tenant added successfully!");
                loadTenants();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add tenant.");
            }
        }
    }
}
