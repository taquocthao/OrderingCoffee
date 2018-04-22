package com.tathao.orderingcoffee.FragmentApp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tathao.orderingcoffee.DTO.FoodCategory;
import com.tathao.orderingcoffee.DTO.ListFoodCategoryAdapter;
import com.tathao.orderingcoffee.InterfaceHandler.AddFragment;
import com.tathao.orderingcoffee.InterfaceHandler.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.R;

import java.util.ArrayList;
import java.util.List;

public class ListFoodCategoryPage extends Fragment implements  AddFragment, OnItemRecyclerviewLisener {

    private List<FoodCategory> foodCategories;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_list_food_category, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recylerViewListFoodCategory);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);


        foodCategories = new ArrayList<>();

        foodCategories.add(new FoodCategory(null, "Cafe capochino", "1", "thức uống"));
        foodCategories.add(new FoodCategory(null, "Cafe capochino", "1", "thức uống"));
        foodCategories.add(new FoodCategory(null, "Cafe capochino", "1", "thức uống"));
        foodCategories.add(new FoodCategory(null, "Cafe capochino", "1", "thức uống"));
        foodCategories.add(new FoodCategory(null, "Cafe capochino", "1", "thức uống"));
        foodCategories.add(new FoodCategory(null, "Cafe capochino", "1", "thức uống"));

        ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
        recyclerView.setAdapter(foodCategoryAdapter);


        //show back button
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }



    @Override
    public void addFragment(Fragment fragment, String title) {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content, fragment)
                .addToBackStack(getString(R.string.food_page))
                .commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);

    }
// sự kiện click item trên recycler view

    @Override
    public void onItemClick(View view, int position) {
        ListFoodPage foodPage = new ListFoodPage();
        String title = getString(R.string.menu);
        addFragment(foodPage, title);
    }
}
