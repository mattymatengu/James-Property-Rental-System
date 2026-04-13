package ui;

import dao.PropertyDAO;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PropertiesPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private PropertyDAO propertyDAO = new PropertyDAO();

    public PropertiesPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buildUI();
        loadProperties(); // load from database when panel opens
    }

    private void buildUI() {
        // Table to display properties
        String[] columns = {"ID", "Type", "Address", "Location", "Rent (N$)", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons at the top
        JButton addBtn    = new JButton("Add Property");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.addActionListener(e -> showAddDialog());
        refreshBtn.addActionListener(e -> loadProperties());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(addBtn);
        btnPanel.add(refreshBtn);

        add(btnPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Loads all properties from the database and fills the table
    private void loadProperties() {
        tableModel.setRowCount(0); // clear existing rows
        ArrayList<Property> properties = propertyDAO.getAllProperties();

        for (Property p : properties) {
            tableModel.addRow(new Object[]{
                p.getPropertyId(),
                p.getType(),
                p.getAddress(),
                p.getLocation(),
                p.getMonthlyRent(),
                p.isAvailable() ? "Yes" : "No"
            });
        }
    }

    // Simple dialog to add a new property
    private void showAddDialog() {
        JTextField idField      = new JTextField(10);
        JTextField addressField = new JTextField(20);
        JTextField locationField = new JTextField(15);
        JTextField rentField    = new JTextField(10);
        JTextField mktField     = new JTextField(10);
        String[] types = {"House", "Townhouse", "Flat"};
        JComboBox<String> typeBox = new JComboBox<>(types);

        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Property ID:"));  form.add(idField);
        form.add(new JLabel("Type:"));         form.add(typeBox);
        form.add(new JLabel("Address:"));      form.add(addressField);
        form.add(new JLabel("Location:"));     form.add(locationField);
        form.add(new JLabel("Market Value:")); form.add(mktField);
        form.add(new JLabel("Monthly Rent:")); form.add(rentField);

        int result = JOptionPane.showConfirmDialog(this, form,
            "Add New Property", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            // Validation
            if (idField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID and Address cannot be empty.");
                return;
            }

            try {
                String id       = idField.getText().trim();
                String address  = addressField.getText().trim();
                String location = locationField.getText().trim();
                double mkt      = Double.parseDouble(mktField.getText().trim());
                double rent     = Double.parseDouble(rentField.getText().trim());
                String type     = (String) typeBox.getSelectedItem();

                // Create the right subclass based on type selected
                Property p;
                if (type.equals("House")) {
                    p = new House(id, address, location, mkt, rent, 0);
                } else if (type.equals("Townhouse")) {
                    p = new Townhouse(id, address, location, mkt, rent, "1", false);
                } else {
                    p = new Flat(id, address, location, mkt, rent, "1", 1, false, false);
                }

                boolean success = propertyDAO.addProperty(p);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Property added successfully!");
                    loadProperties(); // refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add property.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Market value and rent must be numbers.");
            }
        }
    }
}
