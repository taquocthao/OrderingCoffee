package com.tathao.orderingcoffee;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tathao.orderingcoffee.DAO.LoginHandler;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.OkHttpHandler;
import com.tathao.orderingcoffee.NetworkAPI.UserDataStore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView tvSignUp, tvRequire;
    private EditText edUsername, edPassword;
    private CheckBox ckRememberPassword;
    private Button btnLogin;

    private ProgressDialog progressDialog;

    private LoginButton btnLoginFacebook;
    private CallbackManager callbackManager;

    private SignInButton btnLoginGoogle;
    //  private GoogleSignInClient mGoogleSignInClient;

    private static int RC_SIGN_IN = 101;
    /*
         @typeLogin - kiểu đăng nhập
         @type = 1 - đăng nhập bằng tài khoản đăng ký
         @type = 2 - đăng nhập bằng facebook
         @type = 3 - đăng nhập bằng google+
     */
    public int typeLogin = 0;

    private UserDataStore storeData;


    private LoginHandler loginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginHandler = new LoginHandler();
        callbackManager = CallbackManager.Factory.create(); //facebook api
        storeData = new UserDataStore(getBaseContext());

        //Kiểm tra có đăng nhập bằng tài khoản mặc định hay không
        if(storeData.getToken() != null){
            EnterMainActivity(1);
        }
        //Kiểm tra có đăng nhập bằng facebook chưa
        AccessToken accessToken = loginHandler.getAccessTokenUserFacebook();
        if (accessToken != null) {
            EnterMainActivity(2);
        }
        //kiểm tra có đăng nhập bằng google chưa
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            EnterMainActivity(3);
        }

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
        btnLoginGoogle = findViewById(R.id.button_login_google);

        tvSignUp.setClickable(true);
        tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());

        tvSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLoginFacebook.setOnClickListener(this);
        btnLoginGoogle.setOnClickListener(this);





    }

    private void EnterMainActivity(int type) {
        typeLogin = type;
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("typeLogin", typeLogin);
        startActivity(i);
        finish();
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
//            EnterMainActivity(1);
//            finish();
        } else if (id == R.id.button_login_facebook) {
            LoginWithFacebook();
        } else if (id == R.id.button_login_google) {
            LoginWithGoogle();
        }

    }


    //Đăng nhập bằng tài khoản mặc định
    private boolean LoginWithOrderApp(final String username, final String password) {

        if (username.isEmpty()) {
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


//
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String url = Config.urlLogin;
                        final HashMap<String, String> map = new HashMap<String, String>();
                        map.put("email", username);
                        map.put("password", password);

                        String result = new OkHttpHandler(url, OkHttpHandler.POST, map, getBaseContext()).execute().get().toString();

//                        jsonObject = new JSONObject(result);
//                        String accessToken = jsonObject.getString("access_token");

                    //    Log.d("token", result);
                        storeData.setToken(result);

                        if(storeData.getToken() != null){
                            EnterMainActivity(1);
                            finish();

                            progressDialog.dismiss();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
        return true;
    }

    //Đăng nhập bằng tài khoản facebook
    private void LoginWithFacebook() {

        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_location"));
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                EnterMainActivity(2);
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

    // Đăng nhập bằng tài khoản google

    private void LoginWithGoogle() {

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.PROFILE))
//                .build();
//        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInClient mGoogleSignInClient = loginHandler.getmGoogleSignInClient(getApplicationContext());
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void SignInGoogleResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                EnterMainActivity(3);
            }

        } catch (ApiException e) {
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> result = GoogleSignIn.getSignedInAccountFromIntent(data);
            SignInGoogleResult(result);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
