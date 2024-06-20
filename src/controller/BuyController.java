package controller;


import java.sql.*;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class BuyController {
    public boolean checkCitizenIdExists(String cccd, Connection conn) throws SQLException {
        String checkIdSql = "SELECT COUNT(*) FROM `customer` WHERE id = ?";
        PreparedStatement checkIdStmt = conn.prepareStatement(checkIdSql);
        checkIdStmt.setString(1, cccd);
        ResultSet idResult = checkIdStmt.executeQuery();
        idResult.next();
        int idCount = idResult.getInt(1);
        checkIdStmt.close();
        return idCount > 0;
    }

    public boolean checkProductStock(String productName, int quantity, Connection conn) throws SQLException {
        boolean isProductFound = false;
        String[] tables = {"phone", "laptop"};
        for (String table : tables) {
            String checkSql = "SELECT quantity FROM " + table + " WHERE name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, productName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                isProductFound = true;
                int currentQuantity = rs.getInt("quantity");
                if (currentQuantity < quantity) {
                    checkStmt.close();
                    return false;
                }
                String updateSql = "UPDATE " + table + " SET quantity = quantity - ? WHERE name = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, quantity);
                updateStmt.setString(2, productName);
                updateStmt.executeUpdate();
                updateStmt.close();
                break;
            }
            checkStmt.close();
        }
        return isProductFound;
    }

    public void saveCustomer(String name, String cccd, String phone, Connection conn) throws SQLException {
        if (!checkCitizenIdExists(cccd, conn)) {
            String insertUserSql = "INSERT INTO `customer` (name, id, phone) VALUES (?, ?, ?)";
            PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSql);
            insertUserStmt.setString(1, name);
            insertUserStmt.setString(2, cccd);
            insertUserStmt.setString(3, phone);
            insertUserStmt.executeUpdate();
            insertUserStmt.close();
    }  
    }

    public void saveOrder(String productName, String price, int quantity,Connection conn) throws SQLException {
        String insertOrderSql = "INSERT INTO `order` (nameproduct, price, quantity, date) VALUES (?, ?, ?, ?)";
        PreparedStatement insertOrderStmt = conn.prepareStatement(insertOrderSql);
        insertOrderStmt.setString(1, productName);
        insertOrderStmt.setString(2, price);
        insertOrderStmt.setInt(3, quantity);
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        insertOrderStmt.setTimestamp(4, currentTimestamp);  
        insertOrderStmt.executeUpdate();
        insertOrderStmt.close();
    }
    
    public boolean validateInput(String customerName, String citizenId, String phone, int quantity) {
        if (citizenId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Citizen ID should not be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!Pattern.matches("(0)+([0-9]{11})\\b", citizenId)) {
            JOptionPane.showMessageDialog(null, "Citizen ID is not correct", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone should not be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!Pattern.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b", phone)) {
            JOptionPane.showMessageDialog(null, "Phone number is not in the correct format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if (quantity == 0) {
            JOptionPane.showMessageDialog(null, "Quantity must be greater than 0 ", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name should not be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
