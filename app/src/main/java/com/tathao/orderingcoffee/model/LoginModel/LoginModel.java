package com.tathao.orderingcoffee.model.LoginModel;

import android.content.Context;

import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.UserDataStore;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LoginModel {

    private UserDataStore storeData;
    private Context context;
    private OnLoginLisener onLoginLisener;

    public LoginModel(Context context, OnLoginLisener onLoginLisener) {
        this.context = context;
        storeData = new UserDataStore(context);
        this.onLoginLisener = onLoginLisener;
    }

    public void processLoginInfor(String email, String password, Context context) {
        final String url = Config.urlLogin;
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        try {
            String result = new Client(url, Client.POST, params, context)
                    .execute().get().toString();

//                Log.d("login_result", result);
            storeData.setToken(result);
            storeData.setUserData(result);

            if (storeData.getToken() != null) {
                onLoginLisener.onLoginSuccess();
            } else {
                onLoginLisener.onLoginFailure();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
