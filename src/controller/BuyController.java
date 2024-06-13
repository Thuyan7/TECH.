package controller;


import java.sql.*;
import java.util.Date;

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
        String insertUserSql = "INSERT INTO `customer` (name, id, phone) VALUES (?, ?, ?)";
        PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSql);
        insertUserStmt.setString(1, name);
        insertUserStmt.setString(2, cccd);
        insertUserStmt.setString(3, phone);
        insertUserStmt.executeUpdate();
        insertUserStmt.close();
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
}
