package com.tathao.orderingcoffee.view.Fragment;

import android.app.Fragment;;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tathao.orderingcoffee.Interface.AddFragment;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * Created by USER on 3/23/2018.
 */

public class HomePage extends Fragment implements View.OnClickListener, AddFragment {

    private Button btnScanBarCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_home_page, container, false);
        init(view);

        return view;
    }


    @Override
    public void onClick(View view) {
        // event click scan barcode button
        if (view.getId() == R.id.btnScanBarcode) {
            IntentIntegrator intentIntegrator = IntentIntegrator.forFragment(HomePage.this)
                    .setCameraId(0)
                    .setBeepEnabled(false)
                    .setTimeout(25000) // đơn vị milisecond
                    .setPrompt("Đặt barcode vào khung hình đảm bảo barcode hiển thị rõ ràng");
            intentIntegrator.initiateScan();
//            ListFoodCategoryPage foodCategoryPage = new ListFoodCategoryPage();
//            addFragment(foodCategoryPage, getString(R.string.category));
        }
    }

    private void init(View view) {
        btnScanBarCode = view.findViewById(R.id.btnScanBarcode);
        btnScanBarCode.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), getString(R.string.cannot_find_code), Toast.LENGTH_LONG).show();
            } else { //quét được một mã nào đó
                try {
                    // ép kiểu kết quả trả về từ barcode thành object
                    // kiểm tra đối tượng object đó có chưa key shop_id hay không
                    // false -> toast barcode không hợp lệ
                    // true -> gửi giá trị vừa quét được lên server
                    String requestJson = result.getContents();
                    JSONObject jsonObject = new JSONObject(requestJson);
                    if(jsonObject.has("shop_id")){//xem xét mã barcode có hợp lệ
                        String shopID = jsonObject.getString("shop_id");
                        String tableID = jsonObject.getString("table_id");
                        String url = Config.urlCategoryFoodes + shopID;
                        String jsonResult = new Client(url, Client.GET, null, getActivity().getBaseContext())
                                            .execute().get().toString();
                        // ép kiểu giá trị vừa nhận về từ server thành json array
                        JSONArray jsonArray = new JSONArray(jsonResult);
                        // kiểm tra độ dài của array có hợp lệ hay không
                        if(jsonArray.length() <= 0){
                            Toast.makeText(getActivity(), R.string.cannot_find_category_food, Toast.LENGTH_SHORT).show();
                        }else{ // nếu độ dài array hợp lệ -> có danh sách category food
                            // truyền danh sách đó qua trang danh sách thức ăn
                            Bundle bundle = new Bundle();
                            bundle.putString("json", jsonResult);
                            bundle.putString("shop_id", shopID);
                            bundle.putString("table_id", tableID);
                            ListFoodCategoryPage foodCategoryPage = new ListFoodCategoryPage();
                            foodCategoryPage.setArguments(bundle);
                            addFragment(foodCategoryPage, getString(R.string.category));
                        }
                    }else { //nếu mã barcode không hợp lệ
                        Toast.makeText(getActivity(), getString(R.string.invalid_code), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void addFragment(android.app.Fragment fragment, String title) {
        (getActivity()).getFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(getString(R.string.food_category_page))
                .commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }
}
