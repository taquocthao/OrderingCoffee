package com.tathao.orderingcoffee.model.SignUpModel;

import android.content.Context;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.model.entity.City;
import com.tathao.orderingcoffee.model.entity.Country;
import com.tathao.orderingcoffee.model.entity.District;
import com.tathao.orderingcoffee.model.entity.Ward;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SignUpModel {
    private OnRegisterLisener onRegisterLisener;
    public SignUpModel(OnRegisterLisener onRegisterLisener){
        this.onRegisterLisener = onRegisterLisener;
    }

    public void getListCountry(Context context){
        List<Country> listCountry = new ArrayList<>();
        String url = Config.urlCountry;
        try {
            String jsonCountry = new Client(url, Client.GET, null, context)
                    .execute().get().toString();
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, Country.class);
            JsonAdapter<List<Country>> jsonAdapter = moshi.adapter(type);
            listCountry = jsonAdapter.fromJson(jsonCountry);
            listCountry.add(new Country("-1", "Country", "null", "null", "null", "null"));
            if(listCountry.size() >= 0){
                onRegisterLisener.onLoadCountrySuccess(listCountry);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getListCity(Context context, String idCountry){
        List<City> listCity = new ArrayList<>();
        if (idCountry.equals("-1")) {
            listCity.add(new City("-1", "City", "-1", "null", "null", "null", "null"));
//            onRegisterLisener.onLoadCitySuccess(listCity);
        } else {
            String url = Config.urlCity + idCountry;
            try {
                String jsonCity = new Client(url, Client.GET, null, context)
                        .execute().get().toString();
                JSONObject jsonObject = new JSONObject(jsonCity);
                if (jsonObject.has("city")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("city");
                    if (jsonArray.length() > 0) {
                        Moshi moshi = new Moshi.Builder().build();
                        Type type = Types.newParameterizedType(List.class, City.class);
                        JsonAdapter<List<City>> jsonAdapter = moshi.adapter(type);
                        listCity = jsonAdapter.fromJson(jsonArray.toString());
//                        onRegisterLisener.onLoadCitySuccess(listCity);
                    } else {
                        listCity.add(new City("-1", "City", "-1", "null", "null", "null", "null"));
//                        onRegisterLisener.onLoadCitySuccess(listCity);
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
        onRegisterLisener.onLoadCitySuccess(listCity);
    }

    public void getListDistric(Context context, String idCity){
        List<District> listDistrict = new ArrayList<>();
        if (idCity.equals("-1")) {
            listDistrict.add(new District("-1", "Distric", "-1", "null", "null", "null", "null"));
        } else {
            String url = Config.urlDistrict + idCity;
            try {

                String jsonDistrict = new Client(url, Client.GET, null, context)
                        .execute().get().toString();
                JSONObject jsonObject = new JSONObject(jsonDistrict);
                if (jsonObject.has("district")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("district");
                    if (jsonArray.length() > 0) {
                        Moshi moshi = new Moshi.Builder().build();
                        Type type = Types.newParameterizedType(List.class, District.class);
                        JsonAdapter<List<District>> jsonAdapter = moshi.adapter(type);
                        listDistrict = jsonAdapter.fromJson(jsonArray.toString());
                    } else {
                        listDistrict.add(new District("-1", "Distric", "-1", "null", "null", "null", "null"));
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
        onRegisterLisener.onLoadDistricSuccess(listDistrict);
    }

    public void getListWard(Context context, String idDistrict){
        List<Ward> listWard = new ArrayList<>();
        if (idDistrict.equals("-1")) {
            listWard.add(new Ward("-1", "Ward", "-1", "null", "null", "null", "null"));
        } else {
            String url = Config.urlWard + idDistrict;
            try {
                String jsonWard = new Client(url, Client.GET, null, context)
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
                    } else {
                        listWard.add(new Ward("-1", "Ward", "-1", "null", "null", "null", "null"));
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
        onRegisterLisener.onLoadWardSuccess(listWard);
    }

    public void register(HashMap<String, String> params, Context context){
        String urlRegister = Config.urlRegister;
        String jsonRegister = null;
        try {
            jsonRegister = new Client(urlRegister, Client.POST, params, context)
                    .execute().get().toString();
            JSONObject object = new JSONObject(jsonRegister);
            if(object.has("email")){
                onRegisterLisener.onRegisterSuccess(object.getString("email"));
            }
            else {
                onRegisterLisener.onRegisterFailure();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
