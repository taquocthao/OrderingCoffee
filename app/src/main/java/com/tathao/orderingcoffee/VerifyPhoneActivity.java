package com.tathao.orderingcoffee;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class VerifyPhoneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_verify_phone);
    }
}
