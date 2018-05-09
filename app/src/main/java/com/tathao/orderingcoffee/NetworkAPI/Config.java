package com.tathao.orderingcoffee.NetworkAPI;

public class Config {
    private static String http = "http://192.168.0.24:81";
    public static final String urlLogin = http +"/cafe/api/login";
    public static final String urlUserInfor = http + "/cafe/api/user";
    public static final String urlUserUpdate = http + "/cafe/api/account/update";
    public static final String urlRegister = http +"/cafe/api/account/add";

    public static final String urlCategoryFoodes = http+ "/cafe/api/product/category/get?shop_id=15";
    public static final String urlFoodes = http + "/cafe/api/product/get?category_id=";
}
