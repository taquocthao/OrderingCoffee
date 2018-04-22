package com.tathao.orderingcoffee.FragmentApp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tathao.orderingcoffee.DTO.Food;
import com.tathao.orderingcoffee.DTO.ListFoodAdapter;
import com.tathao.orderingcoffee.InterfaceHandler.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 3/31/2018.
 */

public class ListFoodPage extends Fragment implements OnItemRecyclerviewLisener {

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

//        NumberPicker numberPicker = (NumberPicker)view.findViewById(R.id.number_picker);
//        numberPicker.setUnit(2);
//        numberPicker.setValue(10);

        //Moshi - chuyển json thành các đối tượng trong food
//        Moshi moshi = new Moshi.Builder().build();
//        Type types = Types.newParameterizedType(List.class, Food.class);
//        JsonAdapter<List<Food>> jsonAdapter = moshi.adapter(types);
//
//        String json = "..";
//        // tạo list food
//
//        try {
//            foodList = jsonAdapter.fromJson(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        foodList = new ArrayList<>();
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));
        foodList.add(new Food(1, "Pizza", 1, (byte) 0, 50, null));

        // add list food vào adapter
        ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(),this);

        // recylerView set adapter
        recyclerView.setAdapter(adapter);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.menu_shopping_cart){
            Snackbar.make(getView(), "shoping cart pressed", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

        return super.onOptionsItemSelected(item);
    }

// sự kiện click trên recycler view
    @Override
    public void onItemClick(View view, int position) {
        int id = view.getId();
        if(id == R.id.layout_item_food){
            Toast.makeText(getActivity(), "layout press in " + position + "", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.btnAddFoodToList){
            Toast.makeText(getActivity(), "button press in " + position + "", Toast.LENGTH_SHORT).show();
        }
    }
}
