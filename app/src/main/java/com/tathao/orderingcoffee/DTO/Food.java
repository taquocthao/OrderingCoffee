package com.tathao.orderingcoffee.DTO;

/**
 * Created by USER on 3/30/2018.
 */

public class Food {

    private String id;
    private String name;
    private String categoryID;
    private String image;
    private String price;
    private String description;


    public Food(String id, String name, String categoryID, String image, String price, String description) {
        this.id = id;
        this.name = name;
        this.categoryID = categoryID;
        this.image = image;
        this.price = price;
        this.description = description;

    }

    public Food() {
        this(null, null, null, null, null, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
