package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Laptop;
import database.DatabaseConnection;
import model.Phone;

public class DatabaseServer extends Thread {
    private int port;

    public DatabaseServer(int port) {
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

    class ServerThread extends Thread {
        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream()); 
                 ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                String request = (String) input.readObject();

                switch (request) {
                    case "GET_LAPTOPS":
                        List<Laptop> laptopList = getLaptopList();
                        output.writeObject(laptopList);
                        output.flush();
                        break;
                    case "GET_PHONES":
                        List<Phone> phoneList = getPhoneList();
                        output.writeObject(phoneList);
                        output.flush();
                        break;    
                    default:
                        break;
                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        private List<Laptop> getLaptopList() {
            List<Laptop> laptopList = new ArrayList<>();
            String sql = "SELECT * FROM laptop";
            try {
                Connection con = DatabaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String price = rs.getString("price");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");

                    Laptop laptop = new Laptop(id, name, price,quantity, image, description);
                    laptopList.add(laptop);
                }

                DatabaseConnection.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return laptopList;
        }

        

        private List<Phone> getPhoneList() {
            List<Phone> phoneList = new ArrayList<>();
            String sql = "SELECT * FROM phone";
            try {
                Connection con = DatabaseConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String price = rs.getString("price");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");

                    Phone phone = new Phone(id, name, price,quantity, image, description);
                    phoneList.add(phone);
                }

                DatabaseConnection.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
                }
            return phoneList;
        }
    }
}
