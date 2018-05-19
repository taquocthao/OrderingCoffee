package com.tathao.orderingcoffee.presenter;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;

public class LoginStore {

    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;

    private GoogleSignInClient mGoogleSignInClient;


    public AccessToken getAccessTokenUserFacebook() {

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

    public void destroyAccessTokenFacebook() {
        accessTokenTracker.stopTracking();
    }


    public GoogleSignInClient getmGoogleSignInClient(Context context){
        //google api
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestScopes(new Scope( "https://www.googleapis.com/auth/plus.login"))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        return mGoogleSignInClient;
    }



}
