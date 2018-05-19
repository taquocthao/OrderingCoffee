package com.tathao.orderingcoffee.model;

public class Ward {
    public String ID;
    public String Name;
    public String DistrictID;
    public String Description;
    public String IsDelete;
    public String created_at;
    public String updated_at;

    public Ward(String ID, String name, String districtID, String description, String isDelete, String created_at, String updated_at) {
        this.ID = ID;
        Name = name;
        DistrictID = districtID;
        Description = description;
        IsDelete = isDelete;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
