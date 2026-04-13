import ui.LoginScreen;
import javax.swing.SwingUtilities;

// This is the entry point — the first thing that runs when you start the app
public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater makes sure the UI is built on the right thread
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
