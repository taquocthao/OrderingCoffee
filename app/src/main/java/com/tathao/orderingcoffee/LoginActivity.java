package com.tathao.orderingcoffee;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView tvSignUp, tvRequire;
    private EditText edUsername, edPassword;
    private CheckBox ckRememberPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;

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
        tvRequire = findViewById(R.id.tvRequire);
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
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                LoginWithOrderApp(username, password);
                break;

        }
    }

    private boolean LoginWithOrderApp(final String user, final String password) {

        if (user.isEmpty()) {
            edUsername.setError("vui lòng nhập tên đăng nhập");
        } else if (password.isEmpty()) {
            edPassword.setError("vui lòng nhập mật khẩu");
        } else {

            progressDialog = new ProgressDialog(this) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                    progressDialog.dismiss();
                }
            };
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
            progressDialog.show();

            String url = "https://google.com.vn";
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("username", user);
//            try {
//                String okHttpHandler = new OkHttpHandler(url, OkHttpHandler.POST, map, getBaseContext()).execute().get().toString();
//                Log.d("keyresult", okHttpHandler);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    progressDialog.dismiss();
                    finish();
                }
            });

        }
        return true;
    }

}
