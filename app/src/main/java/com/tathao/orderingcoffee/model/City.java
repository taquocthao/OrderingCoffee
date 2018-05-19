package com.tathao.orderingcoffee.model;

public class City {
    public String ID;
    public String Name;
    public String NationID;
    public String Description;
    public String isDelete;
    public String created_at;
    public String updated_at;

    public City(String ID, String name, String nationID, String description, String isDelete, String created_at, String updated_at) {
        this.ID = ID;
        Name = name;
        NationID = nationID;
        Description = description;
        this.isDelete = isDelete;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
