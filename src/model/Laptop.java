/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author AN
 */
public class Laptop implements Serializable{
    private int id;
    private String name;
    private String price;
    private int quantity;
    private String image;
    private String description;
   
    public Laptop(){
        this.id = 0;
        this.name="";
        this.image="";
        this.quantity=0;
        this.price="";
        this.description="";
             
                
    }
    public Laptop(int id,String name, String price,int quantity,String image, String description) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.quantity=quantity;
        this.image = image;
        this.description = description;
    }
    
    public Laptop(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
