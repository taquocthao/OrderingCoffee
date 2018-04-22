package com.tathao.orderingcoffee.DTO;

/**
 * Created by USER on 3/30/2018.
 */

public class Food {

    private int id;
    private String name;
    private int categoryID;
    private byte image;
    private double price;
    private String description;


    public Food(int id, String name, int categoryID, byte image, double price, String description) {
        this.id = id;
        this.name = name;
        this.categoryID = categoryID;
        this.image = image;
        this.price = price;
        this.description = description;

    }

    public Food() {
        this.id = 0;
        this.name = null;
        this.categoryID = 0;
        this.image = 0;
        this.price = 0;
        this.description = null;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public byte getImage() {
        return image;
    }

    public void setImage(byte image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
