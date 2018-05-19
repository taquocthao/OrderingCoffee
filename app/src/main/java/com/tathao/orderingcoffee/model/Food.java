package com.tathao.orderingcoffee.model;

/**
 * Created by USER on 3/30/2018.
 */

public class Food {

    public String ID;
    public String Name;
    public String ProductCategoryID;
    public String Image;
    public String SalePrice;
    public String Description;
    public String IsDelete;
    public String create_at;
    public String update_at;

    public Food(String ID, String name, String productCategoryID, String image, String salePrice, String description, String isDelete, String create_at, String update_at) {
        this.ID = ID;
        Name = name;
        ProductCategoryID = productCategoryID;
        Image = image;
        SalePrice = salePrice;
        Description = description;
        IsDelete = isDelete;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProductCategoryID() {
        return ProductCategoryID;
    }

    public void setProductCategoryID(String productCategoryID) {
        ProductCategoryID = productCategoryID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
