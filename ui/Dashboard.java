package ui;

import model.User;
import javax.swing.*;
import java.awt.*;

// The main screen after login — has tabs for each section
public class Dashboard extends JFrame {

    private User currentUser;

    public Dashboard(User currentUser) {
        this.currentUser = currentUser;

        setTitle("James Property System — Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
    }

    private void buildUI() {
        // Top bar showing who is logged in
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(30, 78, 121));
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JLabel titleLabel = new JLabel("James Property Holdings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Logged in as: " + currentUser.getUsername()
                                    + " (" + currentUser.getRole() + ")");
        userLabel.setForeground(new Color(180, 210, 240));

        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(userLabel, BorderLayout.EAST);

        // Tabs — each tab is a different panel/screen
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Properties", new PropertiesPanel());
        tabs.addTab("Tenants",    new TenantsPanel());
        tabs.addTab("Reports",    new ReportsPanel());

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            this.dispose();
        });
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomBar.add(logoutBtn);

        // Put it all together
        add(topBar, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);
    }
}
