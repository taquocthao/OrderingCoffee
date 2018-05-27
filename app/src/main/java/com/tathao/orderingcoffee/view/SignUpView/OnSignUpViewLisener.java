package com.tathao.orderingcoffee.view.SignUpView;

import com.tathao.orderingcoffee.model.entity.City;
import com.tathao.orderingcoffee.model.entity.Country;
import com.tathao.orderingcoffee.model.entity.District;
import com.tathao.orderingcoffee.model.entity.Ward;

import java.util.List;

public interface OnSignUpViewLisener {
    void onDisplayCountry(List<Country> countryList);
    void onDisplayCity(List<City> cityList);
    void onDisplayDistrict(List<District> districtList);
    void onDisplayWard(List<Ward> wardList);
    void onRegisterSuccess(String email);
    void onRegisterFailure();
}
