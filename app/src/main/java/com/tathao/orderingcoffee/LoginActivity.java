package com.tathao.orderingcoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView txtSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
    }

    //hàm khởi tạo
    private void init() {
        txtSignUp = findViewById(R.id.txtSignUp);
        txtSignUp.setClickable(true);
        txtSignUp.setMovementMethod(LinkMovementMethod.getInstance());
        txtSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txtSignUp:
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
        }
    }
}
