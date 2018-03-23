package com.tathao.orderingcoffee.APIHandler;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by USER on 3/28/2018.
 */

public class OkHttpHandler extends AsyncTask {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    OkHttpClient client;
    HashMap<String, String> params;
    Context context;
    UserData userDataStore;
    private String url, method, result = "";


    public OkHttpHandler(String url, String method, HashMap<String, String> params, Context context) {
        this.url = url;
        this.method = method;
        this.params = params;
        this.context = context;
        this.userDataStore = new UserData(context);
        client = new OkHttpClient();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (this.method == "GET") {
            try {
                this.performGet(new APICallBack() {
                    @Override
                    public void onSuccess(String result) {
                        OkHttpHandler.this.result = result;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.method == "POST") {

            try {
                performPost(new APICallBack() {
                    @Override
                    public void onSuccess(String result) {
                        OkHttpHandler.this.result = result;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return OkHttpHandler.this.result;
    }

    public void performGet(APICallBack callBack) throws IOException {

        Request request;
        if (userDataStore.isAuthenticaed()) {
            request = new Request.Builder()
                    .url(this.url)
                    .header("Authorization", "" + new UserData(this.context).getToken())
                    .build();
        } else {
            request = new Request.Builder()
                    .url(this.url)
                    .build();
        }
        Response response = this.client.newCall(request).execute();
        callBack.onSuccess(response.body().string());
    }

    public void performPost(APICallBack callBack) throws IOException {

        FormBody.Builder formBuilder = new FormBody.Builder();
        if (this.params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = formBuilder.build();
        Request request;
        if (userDataStore.isAuthenticaed()) {
            request = new Request.Builder()
                    .url(this.url)
                    .header("Authorization", "" + new UserData(this.context).getToken())
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(this.url)
                    .post(requestBody)
                    .build();
        }

        Response response = this.client.newCall(request).execute();
        callBack.onSuccess(response.body().string());
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
