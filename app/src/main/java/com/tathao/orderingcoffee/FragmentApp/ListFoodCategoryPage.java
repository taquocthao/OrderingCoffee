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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tathao.orderingcoffee.DTO.FoodCategory;
import com.tathao.orderingcoffee.DTO.ListFoodCategoryAdapter;
import com.tathao.orderingcoffee.InterfaceHandler.AddFragment;
import com.tathao.orderingcoffee.InterfaceHandler.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.NetworkAPI.OkHttpHandler;
import com.tathao.orderingcoffee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListFoodCategoryPage extends Fragment implements AddFragment, OnItemRecyclerviewLisener {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<FoodCategory> foodCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_list_food_category, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.recylerViewListFoodCategory);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        String url = Config.urlCategoryFoodes   ;
        foodCategories = new ArrayList<>();

        try {        //foodCategories.add(new FoodCategory(null, "Cafe capochino", "1", "thức uống", null, null, null));


            String jsonResult = new OkHttpHandler(url, OkHttpHandler.GET, null, getActivity().getBaseContext())
                    .execute().get().toString();

            JSONArray jsonArray = new JSONArray(jsonResult);
         //   Log.d("jonresult", jsonResult);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("ID");
                String name = jsonObject.getString("Name");
                String shop_id = jsonObject.getString("ShopID");
                String description = jsonObject.getString("Description");
//
                FoodCategory foodCategory = new FoodCategory(id, name, shop_id, description, null, null, null);
                foodCategories.add(foodCategory);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
        recyclerView.setAdapter(foodCategoryAdapter);

        //show back button
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            ShopingCart shopingCart = new ShopingCart();
            String title  = getString(R.string.recent_order);
            addFragment(shopingCart, title);
        }

        return super.onOptionsItemSelected(item);
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
//        int id = Integer.parseInt(foodCategories.get(position).getId());
//        Toast.makeText(getActivity().getBaseContext(), "this is "+id, Toast.LENGTH_SHORT).show();
        ShowFoodByCategoryID(1);

    }

    private void ShowFoodByCategoryID(int id) {
//        String url = Config.urlFoodes;
//        HashMap<String, String> map = new HashMap<>();
//        map.put("shop_id", "value");
//        map.put("category_id", ""+id);
//        try {
//
//            String jsonResult = new OkHttpHandler(url, OkHttpHandler.GET, map, getActivity().getBaseContext())
//                    .execute().get().toString();
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        ListFoodPage foodPage = new ListFoodPage();
        String title = getString(R.string.menu);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        foodPage.setArguments(bundle);
        addFragment(foodPage, title);
    }
}
