package Main;

import database.DatabaseConnection;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Server extends Thread {
    private int port;
    
    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            
            while (true) {
                Socket socket = serverSocket.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream input = socket.getInputStream();
             ObjectInputStream objectInput = new ObjectInputStream(input);
             OutputStream output = socket.getOutputStream();
             ObjectOutputStream objectOutput = new ObjectOutputStream(output)) {
            
            String[] userInfo = (String[]) objectInput.readObject();
            String requestType = userInfo[0];
            System.out.println(requestType);
            if ("register".equals(requestType)) {
                String username = userInfo[1];
                String password = userInfo[2];
                String fullname = userInfo[3];
                String citizenid = userInfo[4];
                String date = userInfo[5];
                
                if (!isUsernameExists(username)) {
                    boolean isRegistered = registerUser(username, password, fullname, citizenid, date);
                    objectOutput.writeBoolean(isRegistered);
                    objectOutput.flush();
                } else {
                    objectOutput.writeBoolean(false);
                    objectOutput.flush();
                }
            } else if ("login".equals(requestType)) {
                String username = userInfo[1];
                String password = userInfo[2];
                
                boolean isLogin = loginUser(username, password);
                objectOutput.writeBoolean(isLogin);
                objectOutput.flush();
            } else if ("buy".equals(requestType)) {
                String customerName = userInfo[1];
                String citizenId = userInfo[2];
                String phone = userInfo[3];
                String productName = userInfo[4];
                String price = userInfo[5];
                String quantity = userInfo[6];
                int quantityInt = Integer.parseInt(quantity);
                
                if (checkProductStock(productName, quantityInt)) {
                    boolean isBuyProduct = buyProduct(productName, price, quantityInt);
                    objectOutput.writeBoolean(isBuyProduct);
                    objectOutput.flush();

                    if (!checkCitizenIdExists(citizenId)) {
                        boolean isSaveCustomer = saveCustomer(customerName, citizenId, phone);
                        objectOutput.writeBoolean(isSaveCustomer);
                        objectOutput.flush();
                    } else {
                        objectOutput.writeBoolean(true);
                        objectOutput.flush();
                    }
                } else {
                    objectOutput.writeBoolean(false);
                    objectOutput.flush();
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private boolean registerUser(String username, String password, String fullname, String citizenid, String date) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String hashedPassword = hashPassword(password);

            String query = "INSERT INTO accountuser (username, password, fullname, citizenid, date, is_approved) VALUES (?, ?, ?, ?, ?, 0)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.setString(3, fullname);
                statement.setString(4, citizenid);
                statement.setString(5, date);
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isUsernameExists(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM accountuser WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean loginUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String hashedPassword = hashPassword(password);

            String query = "SELECT is_approved FROM accountuser WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean("is_approved");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean saveCustomer(String name, String id, String phone) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO customer (name, id, phone) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setString(2, id);
                statement.setString(3, phone);
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean buyProduct(String productName, String price, int quantity) {
        String insertOrderSql = "INSERT INTO `order` (nameproduct, price, quantity, date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement insertOrderStmt = connection.prepareStatement(insertOrderSql)) {

            insertOrderStmt.setString(1, productName);
            insertOrderStmt.setString(2, price);
            insertOrderStmt.setInt(3, quantity);
            Timestamp currentTimestamp = new Timestamp(new java.util.Date().getTime());
            insertOrderStmt.setTimestamp(4, currentTimestamp);

            insertOrderStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkCitizenIdExists(String cccd) {
        String query = "SELECT COUNT(*) FROM `customer` WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cccd);

            try (ResultSet idResult = statement.executeQuery()) {
                if (idResult.next()) {
                    int idCount = idResult.getInt(1);
                    return idCount > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkProductStock(String productName, int quantity) {
        boolean isProductFound = false;
        String[] tables = {"phone", "laptop"};

        try (Connection connection = DatabaseConnection.getConnection()) {
            for (String table : tables) {
                String checkSql = "SELECT quantity FROM " + table + " WHERE name = ?";
                try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                    checkStmt.setString(1, productName);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next()) {
                            isProductFound = true;
                            int currentQuantity = rs.getInt("quantity");
                            if (currentQuantity >= quantity) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isProductFound;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] byteData = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : byteData) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
