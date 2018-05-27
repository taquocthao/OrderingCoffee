package com.tathao.orderingcoffee.model.SignUpModel;

import com.tathao.orderingcoffee.model.entity.City;
import com.tathao.orderingcoffee.model.entity.Country;
import com.tathao.orderingcoffee.model.entity.District;
import com.tathao.orderingcoffee.model.entity.Ward;

import java.util.List;

public interface OnRegisterLisener {
    void onLoadCountrySuccess(List<Country> countryList);
    void onLoadCitySuccess(List<City> cityList);
    void onLoadDistricSuccess(List<District> districtList);
    void onLoadWardSuccess(List<Ward> wardList);
    void onRegisterSuccess(String email);
    void onRegisterFailure();
}
