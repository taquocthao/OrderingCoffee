package com.tathao.orderingcoffee.model.entity;

public class Country {
    public String ID;
    public String Name;
    public String Description;
    public String IsDelete;
    public String created_at;
    public String updated_at;

    public Country(String ID, String name, String description, String isDelete, String created_at, String updated_at) {
        this.ID = ID;
        Name = name;
        Description = description;
        IsDelete = isDelete;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
