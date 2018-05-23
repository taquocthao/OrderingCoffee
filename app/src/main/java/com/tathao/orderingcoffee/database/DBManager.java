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

    // thêm danh sách món ăn vào database
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

    // Lấy danh sách món ăn từ id của category food
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

    // Xóa danh sách món ăn
    public boolean deleteTableFood(){
        SQLiteDatabase db = getWritableDatabase();
        int affectRow = db.delete(DBConfig.TABLE_FOOD, null, null);
        db.close();
        return  affectRow > 0;
    }

    // lấy số lượng món ăn trong danh sách
    public int getFoodCount(String idCategory){
        String sql = DBConfig.SQL_QUERY_FOOD + idCategory;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    // thêm chi tiết hóa đơn
    public boolean addInvoiceDetails(InvoiceDetails details) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConfig.TABLE_INVOICES_DETAILS_ID, details.getID());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_NAME, details.getName());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_CATEGORY, details.getProductCategoryID());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_IMAGE, details.getImage());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_PRICE, details.getSalePrice());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_QUANTITY, details.getQuantity());
        values.put(DBConfig.TABLE_INVOICES_DETAILS_TOTAL_PRICE, details.getTotalPrice());
        long affectRow = db.insert(DBConfig.TABLE_INVOICES_DETAILS, null, values);
        db.close();
        return affectRow > 0;
    }

    // lấy tất cả danh sách chi tiết hóa đơn
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

    // xóa một chi tiết hóa đơn bằng id food
    public boolean deleteInvoiceDetailsById(String id) {
        db = this.getWritableDatabase();
        int affectRow = db.delete(DBConfig.TABLE_INVOICES_DETAILS, DBConfig.TABLE_INVOICES_DETAILS_ID+"=?", new String[]{id});
        return affectRow > 0;
    }

    // xóa tất cả chi tiết hóa đơn
    public boolean deleteInvoiceDetails() {
        db = this.getWritableDatabase();
        int affectRow = db.delete(DBConfig.TABLE_INVOICES_DETAILS, null, null);
        return affectRow > 0;
    }

    // cập nhật số lượng thức ăn trong danh sách chi tiết hóa đơn
    public boolean updateQuanlityFoodInInvoice(Food food, int quantity){
        db = this.getWritableDatabase();
        // lấy giá sản phẩm của đối tượng đó
        long price = Long.parseLong(food.getSalePrice());
        // tính tổng giá = giá tiền * số lượng
        long totalPrice = price * quantity;
//        Log.d("toa" , totalPrice +"");
        // truyền giá trị {số lượng, tổng tiền} để cập nhật lại thông tin chi tiết hóa đơn
        ContentValues values = new ContentValues();
        values.put(DBConfig.TABLE_INVOICES_DETAILS_QUANTITY, quantity);
        values.put(DBConfig.TABLE_INVOICES_DETAILS_TOTAL_PRICE, totalPrice);
        // tính lại tổng tiền của thực phẩm
        int affectRow = db.update(DBConfig.TABLE_INVOICES_DETAILS, values, DBConfig.TABLE_INVOICES_DETAILS_ID+"=?",new String[]{food.getID()});
        db.close();
        return affectRow >0;
    }

    // cập nhật số lượng thức ăn trong danh sách chi tiết hóa đơn
    public boolean updateQuanlityFoodInInvoice(InvoiceDetails details, int quantity){
        db = this.getWritableDatabase();
        // lấy giá sản phẩm của đối tượng đó
        long price = Long.parseLong(details.getSalePrice());
        // tính tổng giá = giá tiền * số lượng
        long totalPrice = price * quantity;
//        Log.d("toa" , totalPrice +"");
        // truyền giá trị {số lượng, tổng tiền} để cập nhật lại thông tin chi tiết hóa đơn
        ContentValues values = new ContentValues();
        values.put(DBConfig.TABLE_INVOICES_DETAILS_QUANTITY, quantity);
        values.put(DBConfig.TABLE_INVOICES_DETAILS_TOTAL_PRICE, totalPrice);
        // tính lại tổng tiền của thực phẩm
        int affectRow = db.update(DBConfig.TABLE_INVOICES_DETAILS, values, DBConfig.TABLE_INVOICES_DETAILS_ID+"=?",new String[]{details.getID()});
        db.close();
        return affectRow >0;
    }


    // cập nhật số lượng thức ăn trong danh sách chi tiết hóa đơn thông qua idFood
    public boolean updateQuanlityFoodInInvoice(String idFood, int quantity){
        db = this.getWritableDatabase();
        // lấy giá sản phẩm của đối tượng đó
        long price = getSalePriceOfFood(idFood);
        // tính tổng giá = giá tiền * số lượng
        long totalPrice = price * quantity;
//        Log.d("toa" , totalPrice +"");
        // truyền giá trị {số lượng, tổng tiền} để cập nhật lại thông tin chi tiết hóa đơn
        ContentValues values = new ContentValues();
        values.put(DBConfig.TABLE_INVOICES_DETAILS_QUANTITY, quantity);
        values.put(DBConfig.TABLE_INVOICES_DETAILS_TOTAL_PRICE, totalPrice);
        // tính lại tổng tiền của thực phẩm
        int affectRow = db.update(DBConfig.TABLE_INVOICES_DETAILS, values, DBConfig.TABLE_INVOICES_DETAILS_ID+"=?",new String[]{idFood});
        db.close();
        return affectRow >0;
    }

    // lấy số lượng của một món ăn được gọi
    public int getQuantityOfOneProductFromDetails(String idFood){
        db = getReadableDatabase();
        String sql = DBConfig.SQL_QUERY_INVOICES_DETAILS_QUANLITY + idFood;
        Cursor cursor = db.rawQuery(sql, null);
        String quanlity = "0";
        if(cursor.moveToFirst()){
           quanlity = cursor.getString(0);
        }
        db.close();
        cursor.close();
        return Integer.parseInt(quanlity);
    }


    // lấy số lượng của một món ăn được gọi
    public long getSalePriceOfFood(String idFood){
        db = getReadableDatabase();
        String sql = DBConfig.SQL_QUERY_PRICE_FOOD + idFood;
        Cursor cursor = db.rawQuery(sql, null);
        long salePrice = 0;
        if(cursor.moveToFirst()){
            salePrice = Long.parseLong(cursor.getString(0));
        }
        db.close();
        cursor.close();
        return salePrice;
    }

    // tính tổng giá tiền
    public long calculateTotalPrice(List<InvoiceDetails> details){
        db = this.getReadableDatabase();
        long total = 0;
        for (InvoiceDetails x : details){
            total += Long.parseLong(x.TotalPrice.trim());
        }
        return total;
    }


}
