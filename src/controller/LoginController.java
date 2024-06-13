package controller;

import view.Login;

public class LoginController {
    private static final String ADMIN = "admin";
    private static final String PASSWORD = "admin123";
    private int attempts;
    private Login loginView;

    public LoginController(Login loginView) {
        this.loginView = loginView;
    }

    public void handleLogin(String enteredAdmin, String enteredPassword) {
        if (enteredAdmin.equals(ADMIN) && enteredPassword.equals(PASSWORD)) {
            loginView.showMessage("Login Successful!");
            loginView.navigateToHome();
        } else {
            attempts++;
            if (attempts >= 3) {
                loginView.showMessage("Too many failed attempts. Exiting...");
                System.exit(0);
            } else {
                loginView.showMessage("Incorrect admin or password. Please try again.");
            }
        }
    }
}
