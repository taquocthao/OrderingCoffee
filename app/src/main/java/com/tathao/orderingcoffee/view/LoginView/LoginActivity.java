package com.tathao.orderingcoffee.view.LoginView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import com.tathao.orderingcoffee.NetworkAPI.UserDataStore;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.presenter.LoginPresenter.LoginPresenter;
import com.tathao.orderingcoffee.presenter.Adapter.LoginStore;
import com.tathao.orderingcoffee.view.SignUpView.SignUpActivity;
import com.tathao.orderingcoffee.view.Activity.MainActivity;

import java.util.Arrays;

public class LoginActivity extends Activity implements View.OnClickListener, OnLoginViewLisener {

    private TextView tvSignUp, tvRequire;
    private EditText edUsername, edPassword;
    private CheckBox ckRememberPassword;
    private Button btnLogin;
    //    private ProgressDialog progressDialog;
    private LoginButton btnLoginFacebook;
    private CallbackManager callbackManager;
    private SignInButton btnLoginGoogle;
    private static int RC_SIGN_IN = 101;
    /*
         @typeLogin - kiểu đăng nhập
         @type = 1 - đăng nhập bằng tài khoản đăng ký
         @type = 2 - đăng nhập bằng facebook
         @type = 3 - đăng nhập bằng google+
     */
    public int typeLogin = 0;
    private UserDataStore storeData;
    private LoginStore loginStore;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        storeData = new UserDataStore(getBaseContext());
        hasLogin(); // hàm kiểm tra người dùng đã đăng nhập chưa
        init(); // nếu chưa đăng nhập, bắt đầu khởi tạo các fields
        // người dùng vừa đăng kí, thông tin sẽ gửi lại activity login
        // lấy thông tin vừa nhận được
        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("email");
            edUsername.setText(email);
        }
    }

    private void hasLogin() {
        //Kiểm tra có đăng nhập bằng tài khoản mặc định hay không
        if (storeData.getToken() != null) {
            EnterMainActivity(1);
        }
        loginStore = new LoginStore();
        callbackManager = CallbackManager.Factory.create(); //facebook api
        //Kiểm tra có đăng nhập bằng facebook chưa
        AccessToken accessToken = loginStore.getAccessTokenUserFacebook();
        if (accessToken != null) {
            EnterMainActivity(2);
        }
        //kiểm tra có đăng nhập bằng google chưa
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            EnterMainActivity(3);
        }
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


        loginPresenter = new LoginPresenter(getBaseContext(), this);

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
            // close keyboard when click button login
            View view1 = this.getCurrentFocus();
            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
            final String email = edUsername.getText().toString();
            final String password = edPassword.getText().toString();
            //kiểm tra các input có hợp lệ
            if (isValidInput(email, password)){
                //LoginWithOrderApp(email, password);
                loginPresenter.receiveLoginInfor(email, password, getBaseContext());
            }
        } else if (id == R.id.button_login_facebook) {
            LoginWithFacebook();
        } else if (id == R.id.button_login_google) {
            LoginWithGoogle();
        }
    }

    // kiểm tra giá trị nhập vào
    private boolean isValidInput(String email, String password) {
        if (email.isEmpty()) {
            edUsername.setError("vui lòng nhập tên đăng nhập");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edUsername.setError("Vui lòng nhập đúng định dạng email");
            return false;
        }else if (password.isEmpty()) {
            edPassword.setError("vui lòng nhập mật khẩu");
            return false;
        } else if(password.length() < 5 || password.length() > 30){
            edPassword.setError("mật khẩu phải >= 5 và <= 30");
            return false;
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
        GoogleSignInClient mGoogleSignInClient = loginStore.getmGoogleSignInClient(getApplicationContext());
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


    // lấy dữ liệu từ Login presenter trả về
    @Override
    public void onLoginSucess() {
        EnterMainActivity(1);
        finish();
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
    }


}
