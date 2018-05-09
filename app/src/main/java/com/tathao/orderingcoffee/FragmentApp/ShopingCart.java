package com.tathao.orderingcoffee.FragmentApp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tathao.orderingcoffee.DTO.Food;
import com.tathao.orderingcoffee.DTO.ListFoodOrderedAdapter;
import com.tathao.orderingcoffee.InterfaceHandler.AddFragment;
import com.tathao.orderingcoffee.R;

import java.util.ArrayList;
import java.util.List;

public class ShopingCart extends Fragment implements View.OnClickListener, AddFragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Food> foods;
    private Button btnBackToHome;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_shopping_cart, container, false);

        init(view);
        //


        ListFoodOrderedAdapter adapter = new ListFoodOrderedAdapter(foods, getActivity().getBaseContext());

        recyclerView.setAdapter(adapter);


        return view;
    }

    public void init(View view){
        recyclerView = view.findViewById(R.id.recylerViewListFood_ordered);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        foods = new ArrayList<>();

        btnBackToHome = (Button)view.findViewById(R.id.btnBackToHome_ordered);
        btnBackToHome.setOnClickListener(this);
    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnBackToHome_ordered){
            HomePage homePage = new HomePage();
            String title = getString(R.string.home);
            addFragment(homePage, title);
        }
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }
}
