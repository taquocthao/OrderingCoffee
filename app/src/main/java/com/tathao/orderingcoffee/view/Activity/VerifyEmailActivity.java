package com.tathao.orderingcoffee.view.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.tathao.orderingcoffee.R;

public class VerifyEmailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_verify_email);
    }
}
