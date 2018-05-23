package com.tathao.orderingcoffee.model;

public class InvoiceDetails {
    public String ID;
    public String Name;
    public String ProductCategoryID;
    public String Image;
    public String SalePrice;
    public String Quantity;
    public String TotalPrice;


    public InvoiceDetails(){
        new InvoiceDetails(null,null,null,null,null,null,null);
    }

    public InvoiceDetails(String ID, String name, String productCategoryID, String image, String salePrice, String Quantity, String totalPrice) {
        this.ID = ID;
        Name = name;
        ProductCategoryID = productCategoryID;
        Image = image;
        SalePrice = salePrice;
        this.Quantity = Quantity;
        TotalPrice = totalPrice;
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
//
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
