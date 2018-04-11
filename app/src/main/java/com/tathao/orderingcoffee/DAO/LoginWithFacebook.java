package com.tathao.orderingcoffee.DAO;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.tathao.orderingcoffee.DTO.User;

public class LoginWithFacebook {

    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    User infor;

    public LoginWithFacebook() {
        infor = new User();
    }

    public AccessToken getAccessTokenUser() {

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken;
    }

    public void destroyAccessToken() {
        accessTokenTracker.stopTracking();
    }


}
