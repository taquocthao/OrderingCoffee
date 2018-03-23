package com.tathao.orderingcoffee.FragmentApp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tathao.orderingcoffee.DTO.Food;
import com.tathao.orderingcoffee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 3/31/2018.
 */

public class ListFoodPage extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Food> foodList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_food, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recylerViewListFood);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // tạo list food

        foodList = new ArrayList<>();
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));

        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null, true, null, null));

        // add list food vào adapter

        ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity());


        // recylerView set adapter


        recyclerView.setAdapter(adapter);
        return view;

    }

    private void init() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
}
