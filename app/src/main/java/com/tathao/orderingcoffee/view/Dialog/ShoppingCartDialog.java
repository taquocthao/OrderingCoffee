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
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.tathao.orderingcoffee.Interface.OnNumberPickerValueChangeLisener;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.UserDataStore;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.Invoice;
import com.tathao.orderingcoffee.model.InvoiceDetails;
import com.tathao.orderingcoffee.model.Product;
import com.tathao.orderingcoffee.presenter.OrderAdapter;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShoppingCartDialog extends AppCompatDialogFragment implements View.OnClickListener, OnNumberPickerValueChangeLisener {


    private Button btnOrder, btnCancel;
    private TextView tvTotalPrice;
    private NumberPicker numberPicker;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<InvoiceDetails> invoiceDetails;
    private List<Product> productList;
    private UserDataStore store;
    private DBManager db;
    private String shopID = null;
    private String tableID = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialog.setTitle("Shopping Carts");
        View view = inflater.inflate(R.layout.content_dialog_shopping_cart, null);
        // khởi tạo
        init(view);
        // kiểm tra thông tin gửi lên từ foodpage
        if(getArguments() != null){ //nếu khác rỗng
            shopID = getArguments().getString("shop_id");
            tableID = getArguments().getString("table_id");
            // tải danh sách món ăn sẽ được order
            LoadListFood();
        }
        //
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

        productList = new ArrayList<>();

        store = new UserDataStore(getContext());

        btnCancel.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
    }

    // tải danh sách món ăn sẽ được order
    private void LoadListFood() {
        invoiceDetails = db.getAllListInvoiceDetails(); // lấy danh sách món ăn đã được gọi từ csdl
        // tạo một adapter
        OrderAdapter adapter = new OrderAdapter(invoiceDetails, getActivity().getBaseContext(), this);
        // set adapter đó cho recyclerview
        recyclerView.setAdapter(adapter);
        // hiển thị tổng tiền
        long total = db.calculateTotalPrice(invoiceDetails);
        tvTotalPrice.setText(total +"");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnCancel_shopping) { // click button cancel để tắt giỏ hàng
            dismiss();
        } else if (id == R.id.btnOrder_shopping) { // click button order để gọi món lên hệ thống
            // bước 1: kiểm tra danh sách trong shopping cart có món ăn nào không
            if (invoiceDetails.size() <= 0) { // nếu danh sách shopping cart rỗng -> Toast
                Toast.makeText(getContext(), "Bạn chưa gọi món", Toast.LENGTH_SHORT).show();
            } else { // nếu danh sách shopping cart có món ăn -> bắt đầu gọi món
                // lấy thông tin id, quantity
                // gán vào danh sách product. danh sách này sẽ được gửi lên hệ thống
                for (InvoiceDetails x : invoiceDetails) {
                    String product_id = x.ID;
                    String quantity = x.Quanlity;
                    Product product = new Product(product_id, quantity);
                    productList.add(product);
                }
                // lấy thông tin user -> cụ thể là user id;
                JSONObject object = store.getUserData();
                try {
                    String user_id = object.getString("id");
                    // khởi tạo một lớp chứa các thông tin cần thiết để gửi lên server
                    Invoice invoice = new Invoice(shopID, tableID, user_id, productList);
                    // chuyển object java về kiêu String dạng json
                    String response = parseToJson(invoice);
                    Log.d("response", response);
                    // tạo một params truyền giá trị lên server
                    HashMap<String, String> params = new HashMap<>();
                    params.put("invocice_details", response);
                    // tạo một url
                    String url = Config.urlAddSaleBill;
                    // gửi giá trị (danh sách order) lên server
                    String jsonReponse = new Client(url, Client.POST, params, getActivity().getBaseContext()).execute().get().toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            //Xóa csdl
            db.deleteInvoiceDetails();
            dismiss();
        }

    }

    // chuyển object java về kiểu string dạng json, sử dụng thư viện moshi
    private String parseToJson(Invoice invoice) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Invoice> jsonAdapter = moshi.adapter(Invoice.class);
        String response = jsonAdapter.toJson(invoice);
        return response;
    }


    // sự kiện thay đổi số lượng món ăn trên numberpicker
    @Override
    public void onValueChange(int value, int position) {
//        double total = 0;
//        double totalEachFood = Double.parseDouble(foods.get(position).SalePrice ) * value;
        Log.d("valuechange", value + "");
    }

}