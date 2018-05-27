package com.tathao.orderingcoffee.presenter.SignUpPresenter;

import android.content.Context;

import com.tathao.orderingcoffee.model.SignUpModel.OnRegisterLisener;
import com.tathao.orderingcoffee.model.SignUpModel.SignUpModel;
import com.tathao.orderingcoffee.model.entity.City;
import com.tathao.orderingcoffee.model.entity.Country;
import com.tathao.orderingcoffee.model.entity.District;
import com.tathao.orderingcoffee.model.entity.Ward;
import com.tathao.orderingcoffee.view.SignUpView.OnSignUpViewLisener;

import java.util.HashMap;
import java.util.List;

public class SignUpPresenter implements OnRegisterLisener {
    private SignUpModel signUpModel;
    private OnSignUpViewLisener onSignUpViewLisener;
    public SignUpPresenter(OnSignUpViewLisener onSignUpViewLisener){
        signUpModel = new SignUpModel(this);
        this.onSignUpViewLisener = onSignUpViewLisener;
    }

    public void LoadCountryAddress(Context context){
        signUpModel.getListCountry(context);
    }

    public void LoadCity(Context context, String idCountry){
        signUpModel.getListCity(context, idCountry);
    }

    public void LoadDistrict(Context context, String idCity){
        signUpModel.getListDistric(context, idCity);
    }

    public void LoadWard(Context  context, String idDistrict){
        signUpModel.getListWard(context, idDistrict);
    }

    public void register(HashMap<String, String> params, Context context){
        signUpModel.register(params, context);
    }

//    public void LoadWard(Co)

    @Override
    public void onLoadCountrySuccess(List<Country> countryList) {
        onSignUpViewLisener.onDisplayCountry(countryList);
    }

    @Override
    public void onLoadCitySuccess(List<City> cityList) {
        onSignUpViewLisener.onDisplayCity(cityList);
    }

    @Override
    public void onLoadDistricSuccess(List<District> districtList) {
        onSignUpViewLisener.onDisplayDistrict(districtList);
    }

    @Override
    public void onLoadWardSuccess(List<Ward> wardList) {
        onSignUpViewLisener.onDisplayWard(wardList);
    }

    @Override
    public void onRegisterSuccess(String email) {
        onSignUpViewLisener.onRegisterSuccess(email);
    }


    @Override
    public void onRegisterFailure() {
        onSignUpViewLisener.onRegisterFailure();
    }
}
