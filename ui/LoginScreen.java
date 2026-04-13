package ui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

// This is the first screen the user sees
public class LoginScreen extends JFrame {

    private JTextField     usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("James Property System — Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centre on screen
        setResizable(false);

        buildUI();
    }

    private void buildUI() {
        // Main panel with some padding
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // Title label
        JLabel title = new JLabel("James Property Holdings", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Username row
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password row
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(30, 100, 180));
        loginBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        // What happens when login is clicked
        loginBtn.addActionListener(e -> attemptLogin());

        // Also login when Enter is pressed in password field
        passwordField.addActionListener(e -> attemptLogin());

        add(panel);
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validation — check fields are not empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password.",
                "Missing Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Call the DAO to check credentials against the database
        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);

        if (user != null) {
            // Login success — open the dashboard
            JOptionPane.showMessageDialog(this, "Welcome, " + user.getUsername() + "!");
            new Dashboard(user).setVisible(true);
            this.dispose(); // close the login screen
        } else {
            // Login failed
            JOptionPane.showMessageDialog(this,
                "Incorrect username or password.",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}
