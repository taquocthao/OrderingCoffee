package com.tathao.orderingcoffee.DTO;

public class FoodCategory {

    private String id;
    private String name;
    private String shopID;
    private String description;


    public FoodCategory(){
        this.id = null;
        this.name = null;
        this.shopID = null;
        this.description = null;
    }

    public FoodCategory(String id, String name,String shopID, String description) {
        this.id = id;
        this.name = name;
        this.shopID = shopID;
        this.description = description;
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

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
