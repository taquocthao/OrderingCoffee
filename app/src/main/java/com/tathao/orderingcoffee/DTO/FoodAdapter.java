package com.tathao.orderingcoffee.DTO;

import java.util.List;

public class FoodAdapter {
    private String id;
    private String name;
    private String shopID;
    private String description;
    private String isDelete;
    private String created_at;
    private String update_at;
    private List<Food> foods;

    public FoodAdapter(String id, String name, String shopID, String description, String isDelete, String created_at, String update_at, List<Food> foods) {
        this.id = id;
        this.name = name;
        this.shopID = shopID;
        this.description = description;
        this.isDelete = isDelete;
        this.created_at = created_at;
        this.update_at = update_at;
        this.foods = foods;
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

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
