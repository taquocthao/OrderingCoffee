package com.tathao.orderingcoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.OkHttpHandler;
import com.tathao.orderingcoffee.NetworkAPI.UserDataStore;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class SignUpActivity extends Activity implements View.OnClickListener {

    private Button btnCreateAccount;
    private EditText edFullname, edEmail, edPassword, edReTypePassword, edPhonenumber;
    private RadioGroup rgGender;
    private UserDataStore store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        init();
    }

    private void init() {
        edFullname = (EditText)findViewById(R.id.sign_up_fullname);
        edEmail = (EditText)findViewById(R.id.sign_up_email);
        edPassword = (EditText)findViewById(R.id.sign_up_password);
        edReTypePassword = (EditText)findViewById(R.id.sign_up__retype_password);
        edPhonenumber = (EditText)findViewById(R.id.sign_up_number_phone);
        rgGender = (RadioGroup)findViewById(R.id.sign_up_rgGender);
        btnCreateAccount = findViewById(R.id.sign_up_button_create_account);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.sign_up_button_create_account){
            String url = Config.urlRegister;
            HashMap<String, String> params = new HashMap<>();
            params.put("email", edEmail.getText().toString());
            params.put("password",edPassword.getText().toString());
            params.put("re_password", edReTypePassword.getText().toString());
            params.put("name", edFullname.getText().toString());
            String gender = "";
            if(rgGender.getCheckedRadioButtonId() == R.id.sign_up_rbMale){
                gender = "1";
            }
            else
                gender = "0";
            params.put("gender", gender);
            params.put("phonenumber", edPhonenumber.getText().toString());
            try {
                register(params, url);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void register(HashMap<String, String> params, String url) throws ExecutionException, InterruptedException {

        String jsonResult = new OkHttpHandler(url, OkHttpHandler.POST, params, getBaseContext())
                .execute().get().toString();
      //  Log.d("jsonRegister", jsonResult);
        store.setToken(jsonResult);
        if(store.getToken() != null){
            EnterLoginActivity();
        }
        else {
            Toast.makeText(this.getBaseContext(), R.string.sign_up_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private void EnterLoginActivity(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
