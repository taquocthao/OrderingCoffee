package com.tathao.orderingcoffee.model;

public class FoodCategory {

    public String ID;
    public String Name;
    public String ShopID;
    public String Description;
    public String IsDelete;
    public String created_at;
    public String update_at;

    public FoodCategory(String ID, String name, String shopID, String description, String isDelete, String created_at, String update_at) {
        this.ID = ID;
        Name = name;
        ShopID = shopID;
        Description = description;
        IsDelete = isDelete;
        this.created_at = created_at;
        this.update_at = update_at;
    }
}
