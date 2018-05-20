package com.tathao.orderingcoffee.view.Activity;

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

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.UserDataStore;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.City;
import com.tathao.orderingcoffee.model.Country;
import com.tathao.orderingcoffee.model.District;
import com.tathao.orderingcoffee.model.Ward;
import com.tathao.orderingcoffee.presenter.ListCityAdapter;
import com.tathao.orderingcoffee.presenter.ListCountryApdater;
import com.tathao.orderingcoffee.presenter.ListDistrictAdapter;
import com.tathao.orderingcoffee.presenter.ListWardApdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SignUpLayout2 extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button btnCreateAccount;
    private Spinner spinnerCountry, spinnerCity, spinnerDistrict, spinnerWard;
    private TextView tvError;

    private List<Country> listCountry;
    private List<City> listCity;
    private List<District> listDistrict;
    private List<Ward> listWard;
    private UserDataStore store;
    private String urlRegister = null;
    private String id_country = null;
    private String id_city = null;
    private String id_district = null;
    private String id_ward = null;
    private String country = null;
    private String city = null;
    private String district = null;
    private String ward = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up_layout2);
        init();
        LoadItemOnSpinnerCountry();
    }

    private void init() {
        urlRegister = Config.urlRegister;
        tvError = (TextView)findViewById(R.id.sign_up_error_textView);
        btnCreateAccount = (Button) findViewById(R.id.sign_up_button_create_account);
        spinnerCountry = (Spinner) findViewById(R.id.spinner_country_register);
        spinnerCity = (Spinner) findViewById(R.id.spinner_city_register);
        spinnerDistrict = (Spinner) findViewById(R.id.spinner_district_register);
        spinnerWard = (Spinner) findViewById(R.id.spinner_ward_register);

        store = new UserDataStore(getBaseContext());

        btnCreateAccount.setOnClickListener(this);
        spinnerCountry.setOnItemSelectedListener(this);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerDistrict.setOnItemSelectedListener(this);
        spinnerWard.setOnItemSelectedListener(this);

    }

    private boolean register(HashMap<String, String> params) {
        try {

            String jsonRegister = new Client(urlRegister, Client.POST, params, getBaseContext())
                    .execute().get().toString();
            JSONObject object = new JSONObject(jsonRegister);
            if(object.has("email")){
//                store.setUserData(jsonRegister);
                return  true;
            }
//            Log.d("jsonRegister", jsonRegister);
//            store.setToken(jsonRegister);
//
//            if (store.getToken() != null) {
//                return true;
//            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    // trờ lại form đăng nhập
    private void GotoLoginActivity(String email) {
        Intent i = new Intent(SignUpLayout2.this, LoginActivity.class);
        i.putExtra("email", email);

        startActivity(i);
        finish();
    }

    private void LoadItemOnSpinnerCountry() {
        listCountry = new ArrayList<>();
        String url = Config.urlCountry;
        try {
            String jsonCountry = new Client(url, Client.GET, null, getBaseContext())
                    .execute().get().toString();
//            Log.d("jsonCountry", jsonCountry);
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, Country.class);
            JsonAdapter<List<Country>> jsonAdapter = moshi.adapter(type);
            listCountry = jsonAdapter.fromJson(jsonCountry);
            listCountry.add(new Country("-1", "Country", "null", "null", "null", "null"));

            ListCountryApdater adapter = new ListCountryApdater(listCountry, getBaseContext());
            spinnerCountry.setAdapter(adapter);
            spinnerCountry.setSelection(adapter.getCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

//                Log.d("gennder", gender);
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("re_password", re_password);
                params.put("gender", gender);
                params.put("phonenumber", phone);
                params.put("ward_id", id_ward);
            }

            boolean isSuccess = register(params);
            if (isSuccess) {
                GotoLoginActivity(email);
            } else {
                Toast.makeText(getBaseContext(), R.string.sign_up_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

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

    private void LoadItemOnSpinnerCity(String idCountry) {
        listCity = new ArrayList<>();
        if (idCountry.equals("-1")) {
            listCity.add(new City("-1", "City", "-1", "null", "null", "null", "null"));
            ListCityAdapter adapter = new ListCityAdapter(listCity, getBaseContext());
            spinnerCity.setAdapter(adapter);
            // spinnerCity.setSelection(adapter.getCount());
        } else {
            String url = Config.urlCity + idCountry;
            try {
                String jsonCity = new Client(url, Client.GET, null, getBaseContext())
                        .execute().get().toString();
//                Log.d("jsonCity", jsonCity);
                JSONObject jsonObject = new JSONObject(jsonCity);
                if (jsonObject.has("city")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("city");
                    if (jsonArray.length() > 0) {
                        Moshi moshi = new Moshi.Builder().build();
                        Type type = Types.newParameterizedType(List.class, City.class);
                        JsonAdapter<List<City>> jsonAdapter = moshi.adapter(type);
                        listCity = jsonAdapter.fromJson(jsonArray.toString());
                        ListCityAdapter adapter = new ListCityAdapter(listCity, getBaseContext());
                        spinnerCity.setAdapter(adapter);
                    } else {
                        listCity.add(new City("-1", "City", "-1", "null", "null", "null", "null"));
                        ListCityAdapter adapter = new ListCityAdapter(listCity, getBaseContext());
                        spinnerCity.setAdapter(adapter);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void LoadItemOnSpinnerDistrict(String idCity) {
        listDistrict = new ArrayList<>();
        if (idCity.equals("-1")) {
            listDistrict.add(new District("-1", "Distric", "-1", "null", "null", "null", "null"));
            ListDistrictAdapter adapter = new ListDistrictAdapter(listDistrict, getBaseContext());
            spinnerDistrict.setAdapter(adapter);
        } else {
            String url = Config.urlDistrict + idCity;
            try {

                String jsonDistrict = new Client(url, Client.GET, null, getBaseContext())
                        .execute().get().toString();
//                Log.d("jsondistrict", jsonDistrict);
                JSONObject jsonObject = new JSONObject(jsonDistrict);
                if (jsonObject.has("district")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("district");
                    if (jsonArray.length() > 0) {
                        Moshi moshi = new Moshi.Builder().build();
                        Type type = Types.newParameterizedType(List.class, District.class);
                        JsonAdapter<List<District>> jsonAdapter = moshi.adapter(type);
                        listDistrict = jsonAdapter.fromJson(jsonArray.toString());
                        ListDistrictAdapter adapter = new ListDistrictAdapter(listDistrict, getBaseContext());
                        spinnerDistrict.setAdapter(adapter);
                    } else {
                        listDistrict.add(new District("-1", "Distric", "-1", "null", "null", "null", "null"));
                        ListDistrictAdapter adapter = new ListDistrictAdapter(listDistrict, getBaseContext());
                        spinnerDistrict.setAdapter(adapter);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void LoadItemOnSpinnerWard(String idDistric) {
        listWard = new ArrayList<>();
        if (idDistric.equals("-1")) {
            listWard.add(new Ward("-1", "Ward", "-1", "null", "null", "null", "null"));
            ListWardApdapter adapter = new ListWardApdapter(listWard, getBaseContext());
            spinnerWard.setAdapter(adapter);
        } else {
            String url = Config.urlWard + idDistric;
            try {
                String jsonWard = new Client(url, Client.GET, null, getBaseContext())
                        .execute().get().toString();
//                Log.d("jsonWard", jsonWard);
                JSONObject jsonObject = new JSONObject(jsonWard);
                if (jsonObject.has("ward")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("ward");
                    if (jsonArray.length() > 0) {
                        Moshi moshi = new Moshi.Builder().build();
                        Type type = Types.newParameterizedType(List.class, Ward.class);
                        JsonAdapter<List<Ward>> jsonAdapter = moshi.adapter(type);
                        listWard = jsonAdapter.fromJson(jsonArray.toString());
                        ListWardApdapter adapter = new ListWardApdapter(listWard, getBaseContext());
                        spinnerWard.setAdapter(adapter);
                    } else {
                        listWard.add(new Ward("-1", "Ward", "-1", "null", "null", "null", "null"));
                        ListWardApdapter adapter = new ListWardApdapter(listWard, getBaseContext());
                        spinnerWard.setAdapter(adapter);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idSpinner = parent.getId();
        if (idSpinner == R.id.spinner_country_register) {
            id_country = listCountry.get(position).ID;
            country = listCountry.get(position).Name;
            LoadItemOnSpinnerCity(id_country);

        } else if (idSpinner == R.id.spinner_city_register) {
            id_city = listCity.get(position).ID;
            city = listCity.get(position).Name;
            LoadItemOnSpinnerDistrict(id_city);
        } else if (idSpinner == R.id.spinner_district_register) {
            id_district = listDistrict.get(position).ID;
            district = listDistrict.get(position).Name;
            LoadItemOnSpinnerWard(id_district);
        } else if (idSpinner == R.id.spinner_ward_register) {
            id_ward = listWard.get(position).ID;
            ward = listWard.get(position).Name;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
