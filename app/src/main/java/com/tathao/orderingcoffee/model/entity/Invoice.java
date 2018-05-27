package com.tathao.orderingcoffee.model.entity;

import java.util.List;

public class Invoice {
    public String shop_id;
    public String table_id;
    public String user_id;
    public List<Product> products;

    public Invoice(String shop_id, String table_id, String user_id,List<Product> products) {
        this.shop_id = shop_id;
        this.table_id = table_id;
        this.user_id = user_id;
        this.products = products;
    }
}
