package com.tathao.orderingcoffee.model.entity;

public class Product {
    public String product_id;
    public String quantity;

    public Product(String productID, String quantity){
        this.product_id = productID;
        this.quantity = quantity;
    }
}
