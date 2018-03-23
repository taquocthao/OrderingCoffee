package com.tathao.orderingcoffee.FragmentApp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tathao.orderingcoffee.R;

/**
 * Created by USER on 3/23/2018.
 */

public class ProfileUser extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_profile_user, container, false);
        return view;
    }
}
