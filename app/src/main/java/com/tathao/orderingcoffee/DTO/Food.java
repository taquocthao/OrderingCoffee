package com.tathao.orderingcoffee.DTO;

import java.sql.Date;

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
    private boolean isDelete;
    private Date create_at;
    private Date update_at;

    public Food(int id, String name, int categoryID, byte image, double price, String description, boolean isDelete, Date create_at, Date update_at) {
        this.id = id;
        this.name = name;
        this.categoryID = categoryID;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isDelete = isDelete;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public Food() {
        this.id = 0;
        this.name = null;
        this.categoryID = 0;
        this.image = 0;
        this.price = 0;
        this.description = null;
        this.isDelete = true;
        this.create_at = null;
        this.update_at = null;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
}
