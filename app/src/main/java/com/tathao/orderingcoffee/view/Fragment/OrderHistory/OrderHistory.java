package com.tathao.orderingcoffee.view.Fragment.OrderHistory;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tathao.orderingcoffee.Interface.AddFragment;
import com.tathao.orderingcoffee.Interface.OnItemRecyclerViewLisener;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.entity.Food;
import com.tathao.orderingcoffee.model.entity.InvoiceDetails;
import com.tathao.orderingcoffee.presenter.Adapter.OrderAdapter;
import com.tathao.orderingcoffee.presenter.OrderHistory.OnOrderHistoryViewLisener;
import com.tathao.orderingcoffee.presenter.OrderHistory.OrderHistoryPresenter;
import com.tathao.orderingcoffee.view.Fragment.HomePage;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends Fragment implements View.OnClickListener, AddFragment, OnOrderHistoryViewLisener, OnItemRecyclerViewLisener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Food> foods;
    private Button btnBackToHome, btnPay;
    private TextView tvTotalPrice;
    OrderHistoryPresenter order;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_shopping_cart, container, false);

        init(view);

        // gọi presenter, yêu cầu lấy danh sách các món đã order từ csdl
        order.getListOrder();


        return view;
    }

    public void init(View view){
        recyclerView = view.findViewById(R.id.recylerViewListOrder_OrderHistory);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        tvTotalPrice = (TextView)view.findViewById(R.id.tvTotalPrice_OrderHistory);
        btnBackToHome = (Button)view.findViewById(R.id.btnBackToHome_orderHistory);
        btnPay = (Button)view.findViewById(R.id.btnPay_OrderHistory);

        btnPay.setOnClickListener(this);
        btnBackToHome.setOnClickListener(this);

        foods = new ArrayList<>();
        order = new OrderHistoryPresenter(getActivity().getBaseContext(), this);
    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnBackToHome_orderHistory){
            HomePage homePage = new HomePage();
            String title = getString(R.string.home);
            addFragment(homePage, title);
        }
        else if(view.getId() == R.id.btnPay_OrderHistory){
            order.pay();
        }
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }
    // interface trả về từ presenter -> lấy danh sách các món ăn đã order ->thành công
    @Override
    public void onGetListOrderSuccess(List<InvoiceDetails> invoiceDetails, long totalPrice) {
        OrderAdapter adapter = new OrderAdapter(invoiceDetails, getActivity().getBaseContext(), this);
        recyclerView.setAdapter(adapter);
        tvTotalPrice.setText(totalPrice +"");
    }
    // interface trả về từ presenter -> lấy danh sách các món ăn đã order ->thất bại
    @Override
    public void onGetListOrderFailure() {

    }
    // thanh toán thành công
    @Override
    public void onPaySuccess() {
        List<InvoiceDetails> invoiceDetails = new ArrayList<>();
        OrderAdapter adapter = new OrderAdapter(invoiceDetails, getActivity().getBaseContext(), this);
        recyclerView.setAdapter(adapter);
        tvTotalPrice.setText("price");
    }

    // interface onclick trên recyler -> bỏ qua
    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onNumberPickerValueChange(int value, int position) {

    }

    @Override
    public void onImageButtonDelete(View view, int positon) {

    }
}
