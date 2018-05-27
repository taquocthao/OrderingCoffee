package com.tathao.orderingcoffee.database;

public class DBConfig {
    public static final String DATABASE_NAME = "cf_ordering";
    //   table Food
    public static final String TABLE_FOOD = "Food";
    public static final String TABLE_FOOD_ID = "ID";
    public static final String TABLE_FOOD_NAME = "Name";
    public static final String TABLE_FOOD_CATEGORY = "ProductCategoryID";
    public static final String TABLE_FOOD_IMAGE = "Image";
    public static final String TABLE_FOOD_PRICE = "SalePrice";
    public static final String TABLE_FOOD_DESCRIPTION = "Description";
    // Create Table
    public static final String SQL_CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + " (" +
            TABLE_FOOD_ID + " TEXT PRIMARY KEY, " +
            TABLE_FOOD_NAME + " TEXT, " +
            TABLE_FOOD_CATEGORY + " TEXT, " +
            TABLE_FOOD_IMAGE + " TEXT, " +
            TABLE_FOOD_PRICE + " TEXT, " +
            TABLE_FOOD_DESCRIPTION + " TEXT)";
    // Drop Table
    public static final String DROP_TABLE_FOOD = "DROP TABLE IF EXISTS " + TABLE_FOOD;
    // Query table food
    public static final String SQL_QUERY_FOOD = "SELECT * FROM " + TABLE_FOOD + " WHERE " + TABLE_FOOD_CATEGORY + " = ";
    // lấy ra món ăn bằng id món ăn
    public static final String SQL_QUERY_FOOD_BY_ID = "SELECT * FROM " + TABLE_FOOD + " WHERE " + TABLE_FOOD_ID+ " = ";

    //    Table invoice details
    public static final String TABLE_INVOICES_DETAILS = "InvoiceDetails";
    public static final String TABLE_INVOICES_DETAILS_ID = "id_details";
    public static final String TABLE_INVOICES_DETAILS_NAME = "name_details";
    public static final String TABLE_INVOICES_DETAILS_CATEGORY = "category";
    public static final String TABLE_INVOICES_DETAILS_IMAGE = "image_details";
    public static final String TABLE_INVOICES_DETAILS_PRICE = "price_details";
    public static final String TABLE_INVOICES_DETAILS_QUANTITY = "quantity";
    public static final String TABLE_INVOICES_DETAILS_TOTAL_PRICE = "total_price";
    // Create Table InvoiceDetails
    public static final String SQL_CREATE_TABLE_INVOICE_DETAILS = "CREATE TABLE " + TABLE_INVOICES_DETAILS + " (" +
            TABLE_INVOICES_DETAILS_ID + " TEXT PRIMARY KEY, " +
            TABLE_INVOICES_DETAILS_NAME + " TEXT, " +
            TABLE_INVOICES_DETAILS_CATEGORY + " TEXT, " +
            TABLE_INVOICES_DETAILS_IMAGE + " TEXT, " +
            TABLE_INVOICES_DETAILS_PRICE + " TEXT, " +
            TABLE_INVOICES_DETAILS_QUANTITY + " TEXT, " +
            TABLE_INVOICES_DETAILS_TOTAL_PRICE + " TEXT)";
    // Drop Table
    public static final String DROP_TABLE_INVOICE_DETAILS = "DROP TABLE IF EXISTS " + TABLE_INVOICES_DETAILS;
    // Query table invoice details
    public static final String SQL_QUERY_INVOICES_DETAILS = "SELECT * FROM " + TABLE_INVOICES_DETAILS;

    // try vấn giá sản phẩm thông qua id
    public static final String SQL_QUERY_PRICE_FOOD = "SELECT "+TABLE_FOOD_PRICE+
            " FROM " + TABLE_FOOD +
            " WHERE " +TABLE_FOOD_ID + " = ";
    // get quantity food
    public static final String SQL_QUERY_INVOICES_DETAILS_QUANLITY = "SELECT "+TABLE_INVOICES_DETAILS_QUANTITY+
                                                                     " FROM " + TABLE_INVOICES_DETAILS +
                                                                     " WHERE " +TABLE_INVOICES_DETAILS_ID + " = ";



}
