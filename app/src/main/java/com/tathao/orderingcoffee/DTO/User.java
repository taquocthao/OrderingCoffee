package com.tathao.orderingcoffee.DTO;

public class User {
    private String id;
    private String name;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;

    public User() {
        this.id = null;
        this.name = null;
        this.birthday = null;
        this.gender = null;
        this.phoneNumber = null;
        this.email = null;
        this.address = null;
    }

    public User(String id, String name, String gender, String birthday, String phoneNumber, String email, String address) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
