package com.tathao.orderingcoffee.model.OrderHistory;

import com.tathao.orderingcoffee.model.entity.InvoiceDetails;

import java.util.List;

public interface OnOrderHistoryModelLisener {
    void onGetListOrderSuccess(List<InvoiceDetails> invoiceDetails, long totalPrice);
    void onGetListOrderFailure();
    void onPaySuccess();
}
