package com.tathao.orderingcoffee.view.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.tathao.orderingcoffee.R;

public class CheckInternetActivity extends Activity {

    private Button btnTryConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check_internet);

        if(isConnectInternet()){
            Intent intent = new Intent(CheckInternetActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        btnTryConnect = (Button)findViewById(R.id.btnTryConnectInternet);
        btnTryConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnectInternet()){
                    Intent intent = new Intent(CheckInternetActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), R.string.connect_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private boolean isConnectInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return  (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
