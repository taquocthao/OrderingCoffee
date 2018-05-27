package com.tathao.orderingcoffee.model.OrderHistory;


import android.content.Context;

import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.entity.InvoiceDetails;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryModel {
    private DBManager dbManager;
    private OnOrderHistoryModelLisener onOrderHistoryModelLisener;


    public OrderHistoryModel(Context context, OnOrderHistoryModelLisener onOrderHistoryModelLisener) {
        dbManager = new DBManager(context);
        this.onOrderHistoryModelLisener = onOrderHistoryModelLisener;
    }

    public void getListOrder() {
        List<InvoiceDetails> productList = new ArrayList<>();
        productList = dbManager.getListProductOrderHistory();
        long totalPrice = dbManager.calculateTotalPrice(productList);
        if(productList.size() > 0){
            onOrderHistoryModelLisener.onGetListOrderSuccess(productList, totalPrice);
        }
        else
            onOrderHistoryModelLisener.onGetListOrderFailure();

    }

    public void pay(){
        // delete table order history
        if(dbManager.deleteProductOrderHistory() == true){
            onOrderHistoryModelLisener.onPaySuccess();
        }
    }
}
