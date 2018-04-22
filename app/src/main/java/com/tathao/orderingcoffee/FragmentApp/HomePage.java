package com.tathao.orderingcoffee.FragmentApp;

import android.app.Fragment;
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
import com.tathao.orderingcoffee.InterfaceHandler.AddFragment;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.OkHttpHandler;
import com.tathao.orderingcoffee.R;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by USER on 3/23/2018.
 */

public class HomePage extends Fragment implements View.OnClickListener, AddFragment {

    private Button btnScanBarCode;

    public String resultRequest = null;
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
//            IntentIntegrator intentIntegrator = IntentIntegrator.forFragment(HomePage.this)
//                    .setCameraId(0)
//                    .setBeepEnabled(false)
//                    .setTimeout(25000) // đơn vị milisecond
//                    .setPrompt("Đặt barcode vào khung hình đảm bảo barcode hiển thị rõ ràng");
//            intentIntegrator.initiateScan();
            ListFoodCategoryPage foodCategoryPage = new ListFoodCategoryPage();
            addFragment(foodCategoryPage, getString(R.string.category));
        }
    }

    private void init(View view) {
        btnScanBarCode = view.findViewById(R.id.btnScanBarcode);
        btnScanBarCode.setOnClickListener(this);

        resultRequest = new String();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(getActivity(), getString(R.string.cannot_find_code), Toast.LENGTH_LONG).show();
            } else {
                String requestJson = result.getContents();
                String url = Config.urlLogin;
                HashMap<String, String> map = new HashMap<>();
                map.put("request_list_food", requestJson);
                try {
                    resultRequest = new  OkHttpHandler(url, OkHttpHandler.POST, map, getActivity().getBaseContext())
                            .execute()
                            .get().toString();
                    if(resultRequest != null){
                       // ListFoodPage listFoodPage = new ListFoodPage();
                        ListFoodCategoryPage foodCategoryPage = new ListFoodCategoryPage();
                        addFragment(foodCategoryPage, getString(R.string.category));
                    }
                    else {
                        Toast.makeText(getActivity(), getString(R.string.invalid_code), Toast.LENGTH_SHORT).show();
                    }
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
    public void addFragment(Fragment fragment, String title) {
        (getActivity()).getFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(getString(R.string.food_category_page))
                .commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }
}
