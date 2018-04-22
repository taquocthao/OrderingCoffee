package com.tathao.orderingcoffee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity implements View.OnClickListener {

    private Button btnCreateAccount;
    private EditText edFullname, edUsername, edPassword, edReTypePassword, edPhonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
    }

    private void init() {
        edFullname = (EditText)findViewById(R.id.sign_up_fullname);
        edUsername = (EditText)findViewById(R.id.sign_up_username);
        edPassword = (EditText)findViewById(R.id.sign_up_password);
        edReTypePassword = (EditText)findViewById(R.id.sign_up__retype_password);
        edPhonenumber = (EditText)findViewById(R.id.sign_up_number_phone);
        btnCreateAccount = findViewById(R.id.sign_up_button_create_account);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.sign_up_button_create_account){

        }
    }

    private void register(String name, String username, String password, String numberphone){

    }
}
