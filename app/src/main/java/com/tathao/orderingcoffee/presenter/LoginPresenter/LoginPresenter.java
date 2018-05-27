package com.tathao.orderingcoffee.presenter.LoginPresenter;

import android.content.Context;

import com.tathao.orderingcoffee.model.LoginModel.LoginModel;
import com.tathao.orderingcoffee.model.LoginModel.OnLoginLisener;
import com.tathao.orderingcoffee.view.LoginView.OnLoginViewLisener;

public class LoginPresenter implements OnLoginLisener {
    private LoginModel loginModel;
    private OnLoginViewLisener onLoginViewLisener;

    public LoginPresenter(Context context, OnLoginViewLisener onLoginViewLisener){
        this.onLoginViewLisener = onLoginViewLisener;
        loginModel = new LoginModel(context, this);
    }

    public void receiveLoginInfor(String email, String password, Context context){
        loginModel.processLoginInfor(email, password, context);
    }


    // lấy từ model trả về
    @Override
    public void onLoginSuccess() {
        this.onLoginViewLisener.onLoginSucess();
    }

    @Override
    public void onLoginFailure() {
        this.onLoginViewLisener.onLoginFailure();
    }
}
