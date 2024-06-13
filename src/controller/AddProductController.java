package controller;

import DataBase.LaptopManager;
import DataBase.PhoneManager;

public class AddProductController {

    public static void addProduct(String name, String price, int quantity, String description, String image, String type) {
        if (type.equals("Laptop")) {
            LaptopManager.addLaptop(name, price, quantity, description, image);
        } else if (type.equals("Smartphone")) {
            PhoneManager.addPhone(name, price, quantity, description, image);
        }
    }
}
