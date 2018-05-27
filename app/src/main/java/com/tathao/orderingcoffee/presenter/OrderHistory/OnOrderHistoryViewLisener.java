package com.tathao.orderingcoffee.presenter.OrderHistory;

import com.tathao.orderingcoffee.model.entity.InvoiceDetails;

import java.util.List;

public interface OnOrderHistoryViewLisener {
    void onGetListOrderSuccess(List<InvoiceDetails> invoiceDetails, long totalPrice);
    void onGetListOrderFailure();
    void onPaySuccess();
}
