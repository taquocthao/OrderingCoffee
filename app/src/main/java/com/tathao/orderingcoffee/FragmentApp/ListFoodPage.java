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

import com.tathao.orderingcoffee.DTO.Food;
import com.tathao.orderingcoffee.DTO.ListFoodAdapter;
import com.tathao.orderingcoffee.DialogHandler.ItemFoodDialog;
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

/**
 * Created by USER on 3/31/2018.
 */

public class ListFoodPage extends Fragment implements OnItemRecyclerviewLisener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Food> foodList;
    private int categoryID = 0;
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

        foodList = new ArrayList<>();

        if(getArguments() != null){
            categoryID = getArguments().getInt("id");
        }


        String url = Config.urlFoodes + categoryID;
        // lấy json từ web service
        try {
            String json = new OkHttpHandler(url, OkHttpHandler.GET, null, getActivity().getBaseContext())
                    .execute().get().toString();

            JSONObject jsonObject = new JSONObject(json);
//            String id = jsonObject.getString("ID");
//            String name = jsonObject.getString("Name");
//            String shop_id = jsonObject.getString("ShopID");
//            String description = jsonObject.getString("Description");
//            String isDelete = jsonObject.getString("IsDelete");
//            String created_at = jsonObject.getString("created_at");
//            String updated_at = jsonObject.getString("updated_at");
//            Log.d("aaaa", json);
            JSONArray jsonArray = jsonObject.getJSONArray("product");
            for(int i = 0; i <jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id_food = object.getString("ID");
                String name_food = object.getString("Name");
                String productCategoryID = object.getString("ProductCategoryID");
                String img = object.getString("Image");
                String saleprice = object.getString("SalePrice");
                String description_food = object.getString("Description");

                Food food = new Food(id_food, name_food, productCategoryID, img, saleprice, description_food);
                foodList.add(food);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
//            Toast.makeText(getActivity(), "layout press in " + foodID + "", Toast.LENGTH_SHORT).show();
            try {

                ShowDialogDescriptionFood(position);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else if(id == R.id.btnAddFoodToList){
            //Toast.makeText(getActivity(), "button press in " + position + "", Toast.LENGTH_SHORT).show();
            String urlAddFood = "";
        }
    }

    private void ShowDialogDescriptionFood(int position) throws ExecutionException, InterruptedException {

        String id = foodList.get(position).getId();
        String name = foodList.get(position).getName();
        String price = foodList.get(position).getPrice();
        String description = foodList.get(position).getDescription();
        String image = foodList.get(position).getImage();


        ItemFoodDialog itemFoodDialog = new ItemFoodDialog();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name_food", name);
        bundle.putString("price", price);
        bundle.putString("description", description);
        bundle.putString("image", image);

        itemFoodDialog.setArguments(bundle);
        itemFoodDialog.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(), "dialog description food");
    }
}
