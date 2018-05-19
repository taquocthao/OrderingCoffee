package com.tathao.orderingcoffee.view.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tathao.orderingcoffee.Interface.OnNumberPickerValueChangeLisener;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.InvoiceDetails;
import com.tathao.orderingcoffee.presenter.OrderAdapter;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDialog extends AppCompatDialogFragment implements View.OnClickListener, OnNumberPickerValueChangeLisener {


    private Button btnOrder, btnCancel;
    private TextView tvTotalPrice;
    private NumberPicker numberPicker;
    private List<InvoiceDetails> invoiceDetails;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    DBManager db;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialog.setTitle("Shopping carts");

        View view = inflater.inflate(R.layout.content_dialog_shopping_cart, null);
        init(view);
        LoadListFood();
//        tvTotalPrice.setText(totalPrice(foods) + "");
        dialog.setView(view);
        return dialog.create();
    }

    private void init(View view) {
        btnOrder = (Button) view.findViewById(R.id.btnOrder_shopping);
        btnCancel = (Button) view.findViewById(R.id.btnCancel_shopping);
        tvTotalPrice = (TextView) view.findViewById(R.id.tvTotalPrice_shopping);
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerViewListFood_shopping);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        invoiceDetails = new ArrayList<>();
        db = new DBManager(getContext());

        btnCancel.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
    }

    private void LoadListFood() {
//        foods.add(new Food("1", "aaaa", "1", "null", "20000", "taaaaaaa", "0", null, null));
//        foods.add(new Food("1", "bbbb", "1", "null", "20000", "taaaaaaa", "0", null, null));
//        foods.add(new Food("1", "cccc", "1", "null", "20000", "taaaaaaa", "0", null, null));
        invoiceDetails  = db.getAllListInvoiceDetails();
        OrderAdapter adapter = new OrderAdapter(invoiceDetails, getActivity().getBaseContext(), this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnCancel_shopping) {
            dismiss();
        } else if (id == R.id.btnOrder_shopping) {
            // do something
            //Xóa csdl
            db.deleteInvoiceDetails();
            dismiss();
        }
    }



    @Override
    public void onValueChange(int value, int position) {
//        double total = 0;
//        double totalEachFood = Double.parseDouble(foods.get(position).SalePrice ) * value;
        Log.d("valuechange", value+"");
    }

    private double total(){
//        double total = 0;
//        for(int i = 0; i < foods.size(); i++){
//        }
        return 0;
    }
}
