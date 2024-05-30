package controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddProductController {
    private DefaultTableModel purchaseModel;

    public AddProductController() {
        List<Object[]> purchases = readPurchasesFromXML("C:\\HP\\purchase.xml");
        String[] columnNames = {"Name", "Price", "Quantity"};
        purchaseModel = new DefaultTableModel(columnNames, 0);
        for (Object[] purchase : purchases) {
            purchaseModel.addRow(purchase);
        }
    }

    public DefaultTableModel getPurchaseTableModel() {
        return purchaseModel;
    }

    private List<Object[]> readPurchasesFromXML(String filePath) {
        List<Object[]> purchases = new ArrayList<>();
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList productList = doc.getElementsByTagName("Product");
            for (int temp = 0; temp < productList.getLength(); temp++) {
                Element element = (Element) productList.item(temp);
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String price = element.getElementsByTagName("price").item(0).getTextContent();
                int quantity = Integer.parseInt(element.getElementsByTagName("quantity").item(0).getTextContent());
                purchases.add(new Object[]{name, price, quantity});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

    public double calculateTotalRevenue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}