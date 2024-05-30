/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import view.Login;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.JOptionPane;
/**
 *
 * @author AN
 */
public class Main {
    public static void main(String args[]) {
      FlatMacLightLaf.setup();
      Login login = new Login();
      login.setVisible(true);
    }
}
