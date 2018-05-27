package com.tathao.orderingcoffee.database;

public class ConfigProductOrder {
    // Tạo bản product
    public static final String TABLE_PRODUCT_ORDER_HISTORY = "ProductOrder";
    public static final String TABLE_PRODUCT_ORDER_HISTORY_ID = "id";
    public static final String TABLE_PRODUCT_ORDER_HISTORY_NAME = "name";
    public static final String TABLE_PRODUCT_ORDER_HISTORY_CATEGORY = "category";
    public static final String TABLE_PRODUCT_ORDER_HISTORY_IMAGE = "image";
    public static final String TABLE_PRODUCT_ORDER_HISTORY_PRICE = "price";
    public static final String TABLE_PRODUCT_ORDER_HISTORY_QUANTITY = "quantity";
    public static final String TABLE_PRODUCT_ORDER_HISTORY_TOTAL_PRICE = "total_price";


    // Create Table Product
    public static final String SQL_CREATE_TABLE_PRODUCT_ORDER= "CREATE TABLE " + TABLE_PRODUCT_ORDER_HISTORY + " (" +
            TABLE_PRODUCT_ORDER_HISTORY_ID + " TEXT PRIMARY KEY, " +
            TABLE_PRODUCT_ORDER_HISTORY_NAME + " TEXT, " +
            TABLE_PRODUCT_ORDER_HISTORY_CATEGORY + " TEXT, " +
            TABLE_PRODUCT_ORDER_HISTORY_IMAGE + " TEXT, " +
            TABLE_PRODUCT_ORDER_HISTORY_PRICE + " TEXT, " +
            TABLE_PRODUCT_ORDER_HISTORY_QUANTITY + " TEXT, " +
            TABLE_PRODUCT_ORDER_HISTORY_TOTAL_PRICE + " TEXT)";
    // Drop Table Product Order
    public static final String DROP_TABLE_PRODUCT_ORDER_HISTORY = "DROP TABLE IF EXISTS " + TABLE_PRODUCT_ORDER_HISTORY;
    // truy vấn bản product order
    public static final String SQL_QUERY_TABLE_PRODUCT_ORDER = "SELECT * "+
            " FROM " + TABLE_PRODUCT_ORDER_HISTORY;
}
