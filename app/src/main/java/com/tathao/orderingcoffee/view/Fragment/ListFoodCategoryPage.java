package com.tathao.orderingcoffee.view.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.tathao.orderingcoffee.Interface.AddFragment;
import com.tathao.orderingcoffee.Interface.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.Food;
import com.tathao.orderingcoffee.model.FoodCategory;
import com.tathao.orderingcoffee.presenter.ListFoodCategoryAdapter;
import com.tathao.orderingcoffee.view.Dialog.ShoppingCartDialog;
import com.tathao.orderingcoffee.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListFoodCategoryPage extends Fragment implements AddFragment, OnItemRecyclerviewLisener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<FoodCategory> foodCategories;
    //    ProgressDialog progressDialog;
    private List<Food> foodList;

    SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_food_category, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        init(view);
        if (getArguments() != null) {
            boolean isSuccess = LoadItemCategoryFood();
            if (isSuccess) {
                ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
                recyclerView.setAdapter(foodCategoryAdapter);
            } else {
                foodCategories.add(new FoodCategory(null, "N/A", null, null, null, null, null));
                ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
                recyclerView.setAdapter(foodCategoryAdapter);
            }
        }
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recylerViewListFoodCategory);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        foodCategories = new ArrayList<>();
//        progressDialog = new ProgressDialog(getActivity()){
//            @Override
//            public void onBackPressed() {
//                super.onBackPressed();
//                progressDialog.dismiss();
//            }
//        };
        foodList = new ArrayList<>();
    }

    private boolean LoadItemCategoryFood() {
        try {
            String json = getArguments().getString("json");
            if (!json.isEmpty()) {
                Moshi moshi = new Moshi.Builder().build();
                Type type = Types.newParameterizedType(List.class, FoodCategory.class);
                JsonAdapter<List<FoodCategory>> jsonAdapter = moshi.adapter(type);
                foodCategories = jsonAdapter.fromJson(json);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_action_searh);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_shopping_cart) {
            ShoppingCartDialog cartDialog = new ShoppingCartDialog();
            cartDialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog shopping cart");
        }
        return super.onOptionsItemSelected(item);
    }

    // sự kiện click item trên recycler view
    @Override
    public void onItemClick(View view, int position) {
        final String idCategory = foodCategories.get(position).ID;
        DBManager db = new DBManager(getActivity().getBaseContext());
        boolean existFoods = db.getFoodCount(idCategory) > 0 ? true : false;
        if(existFoods){ // nếu đã tồn tại csdl
            GotoListFood(idCategory);
        }
        else {
            boolean isSuccess = LoadItemFoodFromJSON(idCategory);
            if (isSuccess) {
                db.addFoodFromJSON(foodList);
                GotoListFood(idCategory);
            } else {
                Toast.makeText(getActivity().getBaseContext(), "Connect fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void GotoListFood(String idCategory) {
        ListFoodPage foodPage = new ListFoodPage();
        Bundle bundle = new Bundle();
        bundle.putString("category", idCategory);
        foodPage.setArguments(bundle);
        addFragment(foodPage, "Thực dơn");
    }

    private boolean LoadItemFoodFromJSON(String idCategory) {
        String url = Config.urlFoodes + idCategory;
        try {
            String json = new Client(url, Client.GET, null, (MainActivity) getActivity())
                    .execute().get().toString();
            //       Log.d("jsonresult", json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("product");
            if (jsonArray.length() > 0) {
                Moshi moshi = new Moshi.Builder().build();
                Type type = Types.newParameterizedType(List.class, Food.class);
                JsonAdapter<List<Food>> adapter = moshi.adapter(type);
                foodList = adapter.fromJson(jsonArray.toString());
//                Log.d("fiaaaa", foodList.size() + "");
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void addFragment(Fragment fragment, String title) {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(getString(R.string.food_page))
                .commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.isEmpty()) {
            List<FoodCategory> listFound = new ArrayList<>();
            for (FoodCategory x : foodCategories) {
                if (x.Name.contains(newText)) {
                    listFound.add(x);
                }
            }
            ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(listFound, getActivity().getBaseContext(), this);
            recyclerView.setAdapter(foodCategoryAdapter);
        } else {
            ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
            recyclerView.setAdapter(foodCategoryAdapter);
        }
        return true;
    }
}




