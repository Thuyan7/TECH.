package controller;

import DataBase.StaffManager;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import database.DatabaseConnection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeController {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public DefaultTableModel getDailyTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Date");
        model.addColumn("Total");

        String query = "SELECT nameproduct, quantity, price, date FROM `order`";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String nameproduct = resultSet.getString("nameproduct");
                int quantity = resultSet.getInt("quantity");
                int price = Integer.parseInt(resultSet.getString("price").replaceAll("[^\\d]", ""));
                Date date = resultSet.getDate("date");
                String formattedDate = DATE_FORMAT.format(date);
                int total = quantity * price;
                model.addRow(new Object[]{nameproduct, quantity, price + "đ", formattedDate, total + "đ"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getUserTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Customer Name");
        model.addColumn("ID");
        model.addColumn("Phone Number");

        String query = "SELECT name, id, phone FROM `customer`";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String namecustomer = resultSet.getString("name");
                String id = resultSet.getString("id");
                String phone = resultSet.getString("phone");
                model.addRow(new Object[]{namecustomer, id, phone});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }
    
    public DefaultTableModel getStaffTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Staff Name");
        model.addColumn("Citizen ID");
        model.addColumn("Birth Date");
        model.addColumn("Account Status");
        
        String query = "SELECT id, fullname, citizenid, date, is_approved FROM `accountuser`";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullname = resultSet.getString("fullname");
                String citizenid = resultSet.getString("citizenid");
                String date = resultSet.getString("date");
                boolean isApproved = resultSet.getBoolean("is_approved");
                String status = isApproved ? "approved" : "reject";
                model.addRow(new Object[]{id, fullname, citizenid, date, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public void filterTable(JTable table, DefaultTableModel model, String searchText, String columnName) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        int columnIndex = model.findColumn(columnName);
        if (!searchText.isEmpty()) {
RowFilter<DefaultTableModel, Integer> rowFilter = RowFilter.regexFilter(searchText, columnIndex);
            sorter.setRowFilter(rowFilter);
        } else {
            sorter.setRowFilter(null);
        }
    }

    public int calculateTotalRevenue(JTable table) {
        int totalRevenue = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            int quantity = (int) table.getValueAt(i, 1);
            String value = table.getValueAt(i, 2).toString();
            value = value.replaceAll("\\D", "");
            int price = Integer.parseInt(value);
            totalRevenue += quantity * price;
        }
        return totalRevenue;
    }
    
    public void addTableMouseListener(JTable table, DefaultTableModel model) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int accountId = (int) model.getValueAt(selectedRow, 0);
                        String accountName = (String) model.getValueAt(selectedRow, 1);
                        int response = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to approve the account for " + accountName + "?",
                            "Approve Account",
                            JOptionPane.YES_NO_OPTION
                        );
                        boolean isApproved = (response == JOptionPane.YES_OPTION);
                        StaffManager staffManager = new StaffManager();
                        if (staffManager.updateApprovalStatus(accountId, isApproved)) {
                            model.setValueAt(isApproved ? "approved" : "rejected", selectedRow, 4);
                            JOptionPane.showMessageDialog(null, "Account status updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update account status.");
                        }
                    }
                }
            }
        });
    }
}

