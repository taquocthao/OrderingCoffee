package com.tathao.orderingcoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView tvSignUp;
    private EditText edUsername, edPassword;
    private CheckBox ckRememberPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();

    }

    //hàm khởi tạo
    private void init() {
        tvSignUp = findViewById(R.id.tvSignUp);
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        ckRememberPassword = findViewById(R.id.ckRememberPassword);
        btnLogin = findViewById(R.id.btnLogin);


        tvSignUp.setClickable(true);
        tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());

        tvSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvSignUp:
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                break;
            case R.id.btnLogin:
                Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentLogin);
                break;

        }
    }


    private boolean Login(String user, String password) {
        return false;
    }
}
