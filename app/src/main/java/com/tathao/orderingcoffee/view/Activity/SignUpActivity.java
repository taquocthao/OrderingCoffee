package com.tathao.orderingcoffee.view.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.tathao.orderingcoffee.R;

import java.util.HashMap;

public class SignUpActivity extends Activity implements View.OnClickListener {

    private Button btnContinue;
    private EditText edFullname, edEmail, edPassword, edReTypePassword, edPhonenumber;
    private RadioGroup rgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up_layout1);
        init();
    }

    private void init() {
        edFullname = (EditText)findViewById(R.id.sign_up_fullname);
        edEmail = (EditText)findViewById(R.id.sign_up_email);
        edPassword = (EditText)findViewById(R.id.sign_up_password);
        edReTypePassword = (EditText)findViewById(R.id.sign_up__retype_password);
        edPhonenumber = (EditText)findViewById(R.id.sign_up_number_phone);
        rgGender = (RadioGroup)findViewById(R.id.sign_up_rgGender);
        btnContinue = (Button)findViewById(R.id.sign_up_button_continue);

        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.sign_up_button_continue){
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
            params.put("phone_number", edPhonenumber.getText().toString());
//
            if(isValidInput(params)){
                ContinueSignUp(params);
            }
           // ContinueSignUp(params);
        }
    }

    private boolean isValidInput(HashMap<String, String> params){
        String name = params.get("name");
        String email = params.get("email");
        String password = params.get("password");
        String re_password = params.get("re_password");
        String gender = params.get("gender");
        String phone = params.get("phone_number");

        if(name.isEmpty()){
            edFullname.setError("username can not empty");
            return false;
        }
        else if(email.isEmpty()){
            edEmail.setError("email can not empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("enter a valid email address");
            return false;
        }
        else if(password.isEmpty()){
            edPassword.setError("password can not empty");
            return false;
        }
        else if(password.length() < 5){
            edPassword.setError("password invalid! must to >=5 character");
            return false;
        }
        else if(re_password.isEmpty()){
            edReTypePassword.setError("password can not empty");
            return false;
        }
        else if(!re_password.equals(password)){
            edReTypePassword.setError("password do not match");
            return false;
        }
        else if(gender == null || params.get("gender").isEmpty()){
//            Toast.makeText(SignUpActivity.this, "gender could not choosen", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(phone.isEmpty()){
            edPhonenumber.setError("number phone can not empty");
            return false;
        }
        else if(phone.length() <10 || phone.length() >11){
            edPhonenumber.setError("invalid phone number");
            return false;
        }

        return true;
    }

    private void ContinueSignUp(HashMap<String, String> params){
        Bundle bundle = new Bundle();
        bundle.putString("name", params.get("name"));
        bundle.putString("email", params.get("email"));
        bundle.putString("password", params.get("password"));
        bundle.putString("re_password", params.get("re_password"));
        bundle.putString("gender", params.get("gender"));
        bundle.putString("phone_number", params.get("phone_number"));
        Intent intent = new Intent(SignUpActivity.this, SignUpLayout2.class);
        intent.putExtra("params", bundle);
        startActivity(intent);
        this.finish();
    }


}
