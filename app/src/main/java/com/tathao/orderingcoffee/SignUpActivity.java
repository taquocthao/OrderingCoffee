package com.tathao.orderingcoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SignUpActivity extends Activity implements View.OnClickListener {

    private Button btnCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
    }

    private void init() {
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnCreateAccount:
                Intent i = new Intent(SignUpActivity.this, VerifyPhoneActivity.class);
                startActivity(i);
                break;
        }
    }
}
