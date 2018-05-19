package com.tathao.orderingcoffee.NetworkAPI;

public class Config {
    private static String http = "http://192.168.0.11:81";
    public static final String urlLogin = http +"/cafe/api/login";
    public static final String urlUserInfor = http + "/cafe/api/user";
    public static final String urlUserUpdate = http + "/cafe/api/account/update";
    public static final String urlRegister = http +"/cafe/api/account/add";
    public static final String urlCountry = http +"/cafe/api/address/nation/get";
    public static final String urlCity = http +"/cafe/api/address/city/get?nation_id=";
    public static final String urlDistrict = http +"/cafe/api/address/district/get?city_id=";
    public static final String urlWard = http +"/cafe/api/address/ward/get?district_id=";
    public static final String urlCategoryFoodes = http+ "/cafe/api/product/category/get?shop_id=";
    public static final String urlFoodes = http + "/cafe/api/product/get?category_id=";
}
