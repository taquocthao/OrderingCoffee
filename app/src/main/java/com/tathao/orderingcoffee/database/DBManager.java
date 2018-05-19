package com.tathao.orderingcoffee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tathao.orderingcoffee.model.Food;
import com.tathao.orderingcoffee.model.InvoiceDetails;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper{

    private Context context;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConfig.SQL_CREATE_TABLE_FOOD);
        db.execSQL(DBConfig.SQL_CREATE_TABLE_INVOICE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBConfig.DROP_TABLE_FOOD);
        db.execSQL(DBConfig.DROP_TABLE_INVOICE_DETAILS);
        onCreate(db);
    }

    public void addFoodFromJSON(List<Food> foods){
        db = this.getWritableDatabase();
        for(Food x : foods){
            ContentValues values = new ContentValues();
            values.put(DBConfig.TABLE_FOOD_ID, x.ID);
            values.put(DBConfig.TABLE_FOOD_NAME, x.Name);
            values.put(DBConfig.TABLE_FOOD_CATEGORY, x.ProductCategoryID);
            values.put(DBConfig.TABLE_FOOD_IMAGE, x.Image);
            values.put(DBConfig.TABLE_FOOD_PRICE, x.SalePrice);
            values.put(DBConfig.TABLE_FOOD_DESCRIPTION, x.Description);
//            values.put(DBConfig.TABLE_FOOD_IS_DELETE, x.IsDelete);
//            values.put(DBConfig.TABLE_FOOD_ID, x.ID);
            db.insert(DBConfig.TABLE_FOOD, null, values);
        }
        db.close();
    }

    public List<Food> getListFood(String idCategory){
        db = this.getWritableDatabase();
        List<Food> foods = new ArrayList<>();
        String sql = DBConfig.SQL_QUERY_FOOD + idCategory;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String productCategoryID = cursor.getString(2);
                String image = cursor.getString(3);
                String price = cursor.getString(4);
                String description = cursor.getString(5);

                Food food = new Food(id, name, productCategoryID, image, price, description, null, null, null);
                foods.add(food);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foods;
    }

    public void deleteTableFood(){
//        SQLiteDatabase db = getWritableDatabase();
//        db.delete(DBConfig.TABLE_FOOD);
//        db.close();
    }

    public int getFoodCount(String idCategory){
        String sql = DBConfig.SQL_QUERY_FOOD + idCategory;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }


    public boolean addInvoiceDetails(InvoiceDetails details) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConfig.TABLE_INVOICES_DETAILS_ID, details.getID());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_NAME, details.getName());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_CATEGORY, details.getProductCategoryID());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_IMAGE, details.getImage());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_PRICE, details.getSalePrice());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_QUANLITY, details.getQuanlity());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_TOTAL_PRICE, details.getTotalPrice());
        long affectRow = db.insert(DBConfig.TABLE_INVOICES_DETAILS, null, values);
        db.close();
        return affectRow > 0;
    }

    public List<InvoiceDetails> getAllListInvoiceDetails() {
        List<InvoiceDetails> list = new ArrayList<>();
        db = this.getReadableDatabase();
        String sql = DBConfig.SQL_QUERY_INVOICES_DETAILS;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String productCategoryID = cursor.getString(2);
                String image = cursor.getString(3);
                String price = cursor.getString(4);
                String quanlity = cursor.getString(5);
                String totalPrice = cursor.getString(6);
                InvoiceDetails details = new InvoiceDetails(id, name, productCategoryID, image,price, quanlity, totalPrice);
                list.add(details);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean deleteInvoiceDetailsById(String id) {
        db = this.getWritableDatabase();
        int affectRow = db.delete(DBConfig.TABLE_INVOICES_DETAILS, "ID=?", new String[]{id});
        return affectRow > 0;
    }

    public boolean deleteInvoiceDetails() {
        db = this.getWritableDatabase();
        int affectRow = db.delete(DBConfig.TABLE_INVOICES_DETAILS, null, null);
        return affectRow > 0;
    }

    public void updateQuanlityFoodInInvoice(String idFood){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
    }





}
