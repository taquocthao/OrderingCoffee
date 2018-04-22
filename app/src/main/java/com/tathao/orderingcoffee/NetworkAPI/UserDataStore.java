package com.tathao.orderingcoffee.NetworkAPI;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 3/28/2018.
 */

public class UserDataStore {
    private SharedPreferences preferences;
    private boolean isAuthenticaed;

    public UserDataStore(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isAuthenticaed() {
        this.isAuthenticaed = this.getToken() != null;
        return this.isAuthenticaed;
    }


    public boolean setToken(String result) {

        SharedPreferences.Editor editor = preferences.edit();
        try {
            JSONObject object = new JSONObject(result);
            if (object.getString("access_token") != null) {
                editor.putString("token", object.getString("access_token"));
                editor.commit();
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getToken() {
        return preferences.getString("token", null);
    }

    public JSONObject getUserData() {
        JSONObject user = null;

        try {
            user = new JSONObject(preferences.getString("userdata", null));
            if (user == null || !this.isAuthenticaed())
                return null;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void setUserData(String JSonString) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userdata", JSonString);
        editor.commit();
    }

    public void destroyToken(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
