package com.tathao.orderingcoffee.model;

public class District {
    public String ID;
    public String Name;
    public String CityID;
    public String Description;
    public String IsDelete;
    public String created_at;
    public String updated_at;

    public District(String ID, String name, String cityID, String description, String isDelete, String created_at, String updated_at) {
        this.ID = ID;
        Name = name;
        CityID = cityID;
        Description = description;
        IsDelete = isDelete;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
