package com.tathao.orderingcoffee.view.SignUpView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.entity.City;
import com.tathao.orderingcoffee.model.entity.Country;
import com.tathao.orderingcoffee.model.entity.District;
import com.tathao.orderingcoffee.model.entity.Ward;
import com.tathao.orderingcoffee.presenter.Adapter.ListCityAdapter;
import com.tathao.orderingcoffee.presenter.Adapter.ListCountryApdater;
import com.tathao.orderingcoffee.presenter.Adapter.ListDistrictAdapter;
import com.tathao.orderingcoffee.presenter.Adapter.ListWardApdapter;
import com.tathao.orderingcoffee.presenter.SignUpPresenter.SignUpPresenter;
import com.tathao.orderingcoffee.view.LoginView.LoginActivity;

import java.util.HashMap;
import java.util.List;

public class SignUpLayout2 extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnSignUpViewLisener {



    private Button btnCreateAccount;
    private Spinner spinnerCountry, spinnerCity, spinnerDistrict, spinnerWard;
    private TextView tvError;

    private List<Country> listCountry;
    private List<City> listCity;
    private List<District> listDistrict;
    private List<Ward> listWard;
    private String id_country = null;
    private String id_city = null;
    private String id_district = null;
    private String id_ward = null;
//    private String country = null;
//    private String city = null;
//    private String district = null;
//    private String ward = null;


    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up_layout2);
        init();

        // hiển thị dữ liệu lên spinner country khi fragment vừa được tạo
        signUpPresenter.LoadCountryAddress(getBaseContext());

        spinnerCountry.setOnItemSelectedListener(this);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerDistrict.setOnItemSelectedListener(this);
        spinnerWard.setOnItemSelectedListener(this);
    }

    private void init() {
        tvError = (TextView)findViewById(R.id.sign_up_error_textView);
        btnCreateAccount = (Button) findViewById(R.id.sign_up_button_create_account);
        spinnerCountry = (Spinner) findViewById(R.id.spinner_country_register);
        spinnerCity = (Spinner) findViewById(R.id.spinner_city_register);
        spinnerDistrict = (Spinner) findViewById(R.id.spinner_district_register);
        spinnerWard = (Spinner) findViewById(R.id.spinner_ward_register);
        btnCreateAccount.setOnClickListener(this);

        signUpPresenter = new SignUpPresenter(this);

    }

    // trờ lại form đăng nhập
    private void GotoLoginActivity(String email) {
        Intent i = new Intent(SignUpLayout2.this, LoginActivity.class);
        i.putExtra("email", email);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_up_button_create_account) {
            String email = null;
            HashMap<String, String> params = new HashMap<>();
            // lấy thông tin từ activity trước
            Intent intent = getIntent();
            if (intent != null && isValidAddress()) {
                Bundle bundle = intent.getBundleExtra("params");
                String name = bundle.getString("name");
                email = bundle.getString("email");
                String password = bundle.getString("password");
                String re_password = bundle.getString("re_password");
                String gender = bundle.getString("gender");
                String phone = bundle.getString("phone_number");

                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("re_password", re_password);
                params.put("gender", gender);
                params.put("phonenumber", phone);
                params.put("ward_id", id_ward);
            }

            signUpPresenter.register(params, getBaseContext());


        }
    }

    // kiểm tra dữ liệu có đúng trước khi đăng kí tài khoản
    private boolean isValidAddress(){
        if(id_country.equals("-1")){
            tvError.setText("Please choose your country");
            return false;
        }
        else if(id_city.equals("-1")){
            tvError.setText("Please choose your city");
            return false;
        }
        else if(id_district.equals("-1")){
            tvError.setText("Please choose your district");
            return false;
        }
        else if(id_ward.equals("-1")){
            tvError.setText("Please choose your ward");
            return false;
        }
        return true;
    }

    // implement interface select trên spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idSpinner = parent.getId();
        if (idSpinner == R.id.spinner_country_register) {
            id_country = listCountry.get(position).ID;
//            country = listCountry.get(position).Name;
            signUpPresenter.LoadCity(getBaseContext(), id_country);
        } else if (idSpinner == R.id.spinner_city_register) {
            id_city = listCity.get(position).ID;
//            city = listCity.get(position).Name;
            signUpPresenter.LoadDistrict(getBaseContext(), id_city);
        } else if (idSpinner == R.id.spinner_district_register) {
            id_district = listDistrict.get(position).ID;
//            district = listDistrict.get(position).Name;
            signUpPresenter.LoadWard(getBaseContext(),id_district);
        } else if (idSpinner == R.id.spinner_ward_register) {
            id_ward = listWard.get(position).ID;
//            ward = listWard.get(position).Name;
        }
    }
    // implement interface select trên spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // lấy dữ liệu trả về từ presenter đổ vào spinner country
    @Override
    public void onDisplayCountry(List<Country> countryList) {
        listCountry = countryList;
        ListCountryApdater adapter = new ListCountryApdater(countryList, getBaseContext());
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setSelection(adapter.getCount());
    }
    // lấy dữ liệu trả về từ presenter đổ vào spinner city
    @Override
    public void onDisplayCity(List<City> cityList) {
        listCity = cityList;
        ListCityAdapter adapter = new ListCityAdapter(cityList, getBaseContext());
        spinnerCity.setAdapter(adapter);
    }
    // lấy dữ liệu trả về từ presenter đổ vào spinner district
    @Override
    public void onDisplayDistrict(List<District> districtList) {
        listDistrict = districtList;
        ListDistrictAdapter adapter = new ListDistrictAdapter(districtList, getBaseContext());
        spinnerDistrict.setAdapter(adapter);
    }
    // lấy dữ liệu trả về từ presenter đổ vào spinner ward
    @Override
    public void onDisplayWard(List<Ward> wardList) {
        listWard = wardList;
        ListWardApdapter adapter = new ListWardApdapter(wardList, getBaseContext());
        spinnerWard.setAdapter(adapter);
    }
    // lấy dữ liệu từ presenter ->đăng kí thành công
    @Override
    public void onRegisterSuccess(String email) {
        GotoLoginActivity(email);
    }
    // lấy dữ liệu từ presenter -> đăng kí thất bại
    @Override
    public void onRegisterFailure() {
        Toast.makeText(getBaseContext(), R.string.sign_up_fail, Toast.LENGTH_SHORT).show();
    }
}
