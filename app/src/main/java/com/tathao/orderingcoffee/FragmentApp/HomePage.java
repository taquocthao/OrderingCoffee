package com.tathao.orderingcoffee.FragmentApp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.tathao.orderingcoffee.R;

/**
 * Created by USER on 3/23/2018.
 */

public class HomePage extends Fragment implements View.OnClickListener, AddFragment {

    private Button btnScanBarCode;
    private FragmentTransaction fragmentListFood;
    private FragmentManager fragmentManager;

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
                Toast.makeText(getActivity(), "Không tìm thấy mã", Toast.LENGTH_LONG).show();
            } else {
                ListFoodPage listFoodPage = new ListFoodPage();
                addFragment(listFoodPage, "Thực đơn");
                //Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        fragmentManager = getActivity().getFragmentManager();
        fragmentListFood = getActivity().getFragmentManager().beginTransaction();
        fragmentListFood.replace(R.id.content, fragment);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        fragmentListFood.commit();
    }
}
