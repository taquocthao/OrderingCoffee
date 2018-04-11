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
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView tvSignUp, tvRequire;
    private EditText edUsername, edPassword;
    private CheckBox ckRememberPassword;
    private Button btnLogin;

    private ProgressDialog progressDialog;

    private LoginButton btnLoginFacebook;
    private CallbackManager callbackManager;
    /*
         @typeLogin - kiểu đăng nhập
         @type = 1 - đăng nhập bằng tài khoản đăng ký
         @type = 2 - đăng nhập bằng facebook
         @type = 3 - đăng nhập bằng google+
     */
    private int typeLogin = 0;

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

        btnLogin = findViewById(R.id.button_Login_app);
        btnLoginFacebook = findViewById(R.id.button_login_facebook);

        tvSignUp.setClickable(true);
        tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());

        tvSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLoginFacebook.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.tvSignUp) {
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
        } else if (id == R.id.button_Login_app) {
            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();

            LoginWithOrderApp(username, password);
        } else if (id == R.id.button_login_facebook) {

            LoginWithFacebook();
        }

    }


    //Đăng nhập bằng tài khoản mặc định
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
                    typeLogin = 1;
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("typeLogin", typeLogin);
                    startActivity(i);
                    progressDialog.dismiss();
                    finish();
                }
            });

        }
        return true;
    }

    //Đăng nhập bằng tài khoản facebook
    private void LoginWithFacebook() {

        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                typeLogin = 2;
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("typeLogin", typeLogin);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
