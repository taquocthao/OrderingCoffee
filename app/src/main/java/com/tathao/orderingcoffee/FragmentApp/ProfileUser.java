package com.tathao.orderingcoffee.FragmentApp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.tathao.orderingcoffee.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by USER on 3/23/2018.
 */

public class ProfileUser extends Fragment implements View.OnClickListener {

    private EditText edFullname, edEmail, edPhoneNumber, edAddress, edBirthday;
    private RadioGroup rbGender;
    private Button btnSave, btnUpdate;
    private Calendar calendar;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_profile_user, container, false);
        //ánh xạ các control
        init(view);

        return view;
    }

    private void init(View view) {
        edFullname = view.findViewById(R.id.edFullName);
        edEmail = view.findViewById(R.id.edEmail);
        edPhoneNumber = view.findViewById(R.id.edPhoneNumber);
        edAddress = view.findViewById(R.id.edAddress);
        edBirthday = view.findViewById(R.id.edBirthDay);
        rbGender = view.findViewById(R.id.rdGender);
        btnSave = view.findViewById(R.id.btnSaveProfile);
        btnUpdate = view.findViewById(R.id.btnUpdateProfile);

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
        } else if (id == R.id.edBirthDay) {
            // Toast.makeText(view.getContext(), "Tâsd", Toast.LENGTH_LONG).show();
            new DatePickerDialog(view.getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
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
}
