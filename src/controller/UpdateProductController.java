package controller;

import view.UpdateProduct;
import DataBase.LaptopManager;
import DataBase.PhoneManager;
import model.Laptop;
import model.Phone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProductController {
    private UpdateProduct view;

    public UpdateProductController(UpdateProduct view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getKButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        view.getUpdatebt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        view.getImg().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });

        String type = view.getTypetxt().getText();
        if (type.equals("LAPTOP")) {
            getLaptop(Integer.parseInt(view.getIdtxt().getText()));
        } else if (type.equals("SMARTPHONE")) {
            getPhone(Integer.parseInt(view.getIdtxt().getText()));
        }
    }

    private void getLaptop(int id) {
        Laptop laptop = LaptopManager.getLaptopById(id);
        if (laptop != null) {
            view.getNametxt().setText(laptop.getName());
            view.getPricetxt().setText(laptop.getPrice());
            view.getQuantityTxt().setValue(laptop.getQuantity());
            view.getImgtxt().setText(laptop.getImage());
            view.getDestxt().setText(laptop.getDescription());
            view.getTypecb().setSelectedItem("Laptop");
        } else {
            JOptionPane.showMessageDialog(view, "No laptop found with the given ID.");
        }
    }

    private void getPhone(int id) {
        Phone phone = PhoneManager.getPhoneById(id);
        if (phone != null) {
            view.getNametxt().setText(phone.getName());
            view.getPricetxt().setText(phone.getPrice());
            view.getQuantityTxt().setValue(phone.getQuantity());
            view.getImgtxt().setText(phone.getImage());
            view.getDestxt().setText(phone.getDescription());
            view.getTypecb().setSelectedItem("Smartphone");
        } else {
            JOptionPane.showMessageDialog(view, "No phone found with the given ID.");
        }
    }

    private void updateProduct() {
        try {
            int idd = Integer.parseInt(view.getIdtxt().getText());
            String name = view.getNametxt().getText();
            String price = view.getPricetxt().getText();
            int quantity = (int) view.getQuantityTxt().getValue();
            String des = view.getDestxt().getText();
            String img = view.getImgtxt().getText();
            String type = view.getTypetxt().getText();

            if (type.equals("LAPTOP")) {
                Laptop laptop = new Laptop(idd, name, price, quantity, img, des);
                LaptopManager.updateLaptop(laptop);
            } else if (type.equals("SMARTPHONE")) {
                Phone phone = new Phone(idd, name, price, quantity, img, des);
                PhoneManager.updatePhone(phone);
            }
            JOptionPane.showMessageDialog(view, "Product updated successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid ID format.");
        }
    }

    private void clearForm() {
        view.getNametxt().setText("");
        view.getPricetxt().setText("");
        view.getDestxt().setText("");
        view.getImgtxt().setText("");
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        java.io.File file = chooser.getSelectedFile();
        String filename = file.getAbsolutePath();
        view.getImgtxt().setText(filename);
    }
}
