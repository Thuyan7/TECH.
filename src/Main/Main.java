package Main;

import view.Login;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        try {
            
            FlatMacLightLaf.setup();
        } catch (Exception e) {
          
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Failed to initialize LaF", e);
            JOptionPane.showMessageDialog(null, "Failed to initialize LaF", "Error", JOptionPane.ERROR_MESSAGE);
        }

        
        java.awt.EventQueue.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
        
        startServerThreads();
    }

    private static void startServerThreads() {
        Thread databaseServerThread = new Thread(() -> {
            try {
                DatabaseServer databaseServer = new DatabaseServer(1234);
                databaseServer.start();
            } catch (Exception e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error starting DatabaseServer", e);
            }
        });
        databaseServerThread.start();
        Thread serverThread = new Thread(() -> {
            try {
                Server server = new Server(1235);
                server.start();
            } catch (Exception e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error starting Server", e);
            }
        });
        serverThread.start();
    }
}
