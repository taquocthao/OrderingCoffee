package com.tathao.orderingcoffee.DTO;

public class FoodCategory {

    private String id;
    private String name;
    private String shopID;
    private String description;
    private String isDelete;
    private String created_at;
    private String update_at;

    public FoodCategory(){
        this.id = null;
        this.name = null;
        this.shopID = null;
        this.description = null;
        this.isDelete = null;
        this.created_at = null;
        this.update_at = null;
    }

    public FoodCategory(String id, String name,String shopID, String description, String isDelete, String created_at, String update_at) {
        this.id = id;
        this.name = name;
        this.shopID = shopID;
        this.description = description;
        this.isDelete = isDelete;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
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
