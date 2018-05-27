package com.tathao.orderingcoffee.presenter.OrderHistory;


import android.content.Context;

import com.tathao.orderingcoffee.model.entity.InvoiceDetails;
import com.tathao.orderingcoffee.model.OrderHistory.OnOrderHistoryModelLisener;
import com.tathao.orderingcoffee.model.OrderHistory.OrderHistoryModel;

import java.util.List;

public class OrderHistoryPresenter implements OnOrderHistoryModelLisener {
    private OrderHistoryModel orderHistoryModel;
    private OnOrderHistoryViewLisener onOrderHistoryViewLisener;
    public OrderHistoryPresenter(Context context, OnOrderHistoryViewLisener onOrderHistoryViewLisener){
        orderHistoryModel = new OrderHistoryModel(context, this);
        this.onOrderHistoryViewLisener = onOrderHistoryViewLisener;
    }

    public void getListOrder(){
        orderHistoryModel.getListOrder();
    }

    public void pay(){
        orderHistoryModel.pay();
    }

    @Override
    public void onGetListOrderSuccess(List<InvoiceDetails> invoiceDetails, long totalPrice) {
        onOrderHistoryViewLisener.onGetListOrderSuccess(invoiceDetails, totalPrice);
    }

    @Override
    public void onGetListOrderFailure() {
        onOrderHistoryViewLisener.onGetListOrderFailure();
    }

    @Override
    public void onPaySuccess() {
        onOrderHistoryViewLisener.onPaySuccess();
    }
}
