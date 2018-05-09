package com.tathao.orderingcoffee.FragmentApp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;
import com.tathao.orderingcoffee.DAO.LoginHandler;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.OkHttpHandler;
import com.tathao.orderingcoffee.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by USER on 3/23/2018.
 */

public class ProfileUser extends Fragment implements View.OnClickListener {

    private EditText edFullname, edEmail, edPhoneNumber, edAddress, edBirthday;
    private RadioGroup rbGender;
    private Button btnSave, btnUpdate;
    private Calendar calendar;
    private ImageView imgProfile;
    private LinearLayout layoutOfPhoneNumber;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private LoginHandler loginHandler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_profile_user, container, false);
        //ánh xạ các control
        loginHandler = new LoginHandler();
        init(view);
        int typeLogin = getActivity().getIntent().getIntExtra("typeLogin", 0);
        if(typeLogin == 1){ // đăng nhập bằng tài khoản mặc định
            try {

                setProfileFromOrderApp();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(typeLogin == 2){ // đăng nhập bằng facebook
            AccessToken accessToken = loginHandler.getAccessTokenUserFacebook();
            setProfileFormFacebook(accessToken);
        }
        else if(typeLogin == 3){ // đăng nhập bằng google
            setProfileFromGoogle();
        }

        return view;
    }

    private void init(View view) {
        imgProfile = (ImageView)view.findViewById(R.id.imageProfile_fragment);
        edFullname = view.findViewById(R.id.edFullName);
        edEmail = view.findViewById(R.id.edEmail);
        edPhoneNumber = view.findViewById(R.id.edPhoneNumber);
        edAddress = view.findViewById(R.id.edAddress);
        edBirthday = view.findViewById(R.id.edBirthDay);
        rbGender = view.findViewById(R.id.rdGender);
        btnSave = view.findViewById(R.id.btnSaveProfile);
        btnUpdate = view.findViewById(R.id.btnUpdateProfile);
        layoutOfPhoneNumber = view.findViewById(R.id.background_of_item_phone_number);

        // các fields Edittext sẽ dưới dạng chỉ đọc
          defaultControl(true);

        //Sử dụng datetimepicker cho edittext ngày sinh
        calendar = Calendar.getInstance();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateLable();
            }
        };


        btnSave.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        edBirthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.btnUpdateProfile) {
            defaultControl(false);
        } else if (id == R.id.btnSaveProfile) {
            defaultControl(true);
            UpdateProfile();

        } else if (id == R.id.edBirthDay) {
            new DatePickerDialog(view.getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private void UpdateProfile(){
        String url = Config.urlUserUpdate;
        String name = edFullname.getText().toString();
        String phone = edPhoneNumber.getText().toString();
        String email = edEmail.getText().toString();
        String birthday = edBirthday.getText().toString();
        String gender = "";
        if(rbGender.getCheckedRadioButtonId() == R.id.rbMale){
            gender = "1";
        }
        else {
            gender = "0";
        }
        String address = edAddress.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("dbo", birthday);
        params.put("gender", gender);
        params.put("phonenumber", phone);
        params.put("email", email);

        Object object = new OkHttpHandler(url, OkHttpHandler.PUT,  params, getActivity().getBaseContext()).execute();
    }

    @SuppressLint("ResourceAsColor")
    private void defaultControl(boolean isDefault) {
        if (isDefault) {
            edFullname.setEnabled(false);
            edEmail.setEnabled(false);
            edPhoneNumber.setEnabled(false);
            edBirthday.setEnabled(false);
            edAddress.setEnabled(false);
            rbGender.setEnabled(false);
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
        } else {
            edFullname.setEnabled(true);
            edEmail.setEnabled(true);
            edPhoneNumber.setEnabled(true);
            edBirthday.setEnabled(true);
            edAddress.setEnabled(true);
            rbGender.setEnabled(true);
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        }
    }

    private void updateLable() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edBirthday.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void setProfileFromOrderApp() throws ExecutionException, InterruptedException, IOException, JSONException {

        String url = Config.urlUserInfor;
        String json = new OkHttpHandler(url, OkHttpHandler.GET, null, getActivity().getBaseContext())
                .execute()
                .get().toString();
        Log.d("jsonUser", json);

        // ánh xạ chuỗi json về đối tượng user bằng thư viện moshi
//        Moshi moshi = new Moshi.Builder().build();
//        JsonAdapter<User> userJsonAdapter = moshi.adapter(User.class);
//        User user = userJsonAdapter.fromJson(jsonUser);
        JSONObject object = new JSONObject(json);

        String name = object.getString("name");
        String email = object.getString("email");
        String phone = object.getString("phonenumber");
        String birthday = object.getString("dob");
        if(birthday.equals("null")){
            birthday = null;
        }
        String gender = object.getString("gender");
        String address = object.getString("address");
        if(address.equals("null")){
            address = null;
        }
        //Log.d("aaaaa", phone);

        imgProfile.setImageResource(R.drawable.profile_default);
        edFullname.setText(name);
        edPhoneNumber.setText(phone);
        edEmail.setText(email);
        edBirthday.setText(birthday);
        if(gender == "1"){
            rbGender.check(R.id.rbMale);
        }
        else
            rbGender.check(R.id.rbFemale);
        edAddress.setText(address);

    }

    private void setProfileFormFacebook(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    // ẩn sdt người dùng
                    edPhoneNumber.setVisibility(View.GONE);
                    layoutOfPhoneNumber.setVisibility(View.GONE);
                    //không thể sửa đổi được thông tin nên ẩn button update
                    btnUpdate.setVisibility(View.GONE);
                    String id = object.getString("id");
                    Picasso.get()
                            .load("https://graph.facebook.com/" + id + "/picture?type=large")
                            .into(imgProfile);

                    String name = object.getString("name");
                    edFullname.setText(name);

                    if(object.has("email")){
                        String email = object.getString("email");
                        edEmail.setText(email);
                    }

                    String birthday = object.getString("birthday");
                    edBirthday.setText(birthday);

                    String gender = object.getString("gender");
                    if(gender.equals("male")){
                        rbGender.check(R.id.rbMale);
                    }
                    else {
                        rbGender.check(R.id.rbFemale);
                    }

                    JSONObject objectLocal = object.getJSONObject("location");
                    String locationCity  = objectLocal.getString("name");
                    edAddress.setText(locationCity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,birthday,gender,location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setProfileFromGoogle(){
        //
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        if(account != null){


            Uri uri = account.getPhotoUrl();
            Picasso.get().load(uri).into(imgProfile);

            String name = account.getDisplayName();
            edFullname.setText(name);

            String email = account.getEmail();
            edEmail.setText(email);

            btnUpdate.setVisibility(View.GONE);


        }






    }

}
