package com.tathao.orderingcoffee.view.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
import com.tathao.orderingcoffee.Interface.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.Food;
import com.tathao.orderingcoffee.model.InvoiceDetails;
import com.tathao.orderingcoffee.presenter.ListFoodAdapter;
import com.tathao.orderingcoffee.view.Dialog.ItemFoodDialog;
import com.tathao.orderingcoffee.view.Dialog.ShoppingCartDialog;
import com.tathao.orderingcoffee.view.MainActivity;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by USER on 3/31/2018.
 */

public class ListFoodPage extends Fragment implements OnItemRecyclerviewLisener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Food> foodList;
    private SearchView searchView;
    //    private InvoiceDetailsDBManager invoiceDetailsDBManager;
    private DBManager db;
    private List<InvoiceDetails> detailsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_food, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        setHasOptionsMenu(true);
        init(view);


        if (getArguments() != null) {
            String idCategory = getArguments().getString("category");
            boolean isSuccess = LoadFood(idCategory);
            if (isSuccess) {
                ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
                recyclerView.setAdapter(adapter);
            } else {
                foodList.add(new Food(null, "N/A", null, null, null, null, null, null, null));
                ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
                recyclerView.setAdapter(adapter);
            }
//            boolean isSuccess = LoadItemFood(idCategory);
//            if (isSuccess){
//                ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
//                recyclerView.setAdapter(adapter);
//            }
//            else {
//                foodList.add(new Food(null, "N/A", null, null, null, null, null, null, null));
//                ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
//                recyclerView.setAdapter(adapter);
//            }
        }
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recylerViewListFood);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        foodList = new ArrayList<>();
        detailsList = new ArrayList<>();
        db = new DBManager(getActivity().getBaseContext());
    }

    private boolean LoadItemFood(String idCategory) {
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

    private boolean LoadFood(String idCategory) {
        foodList = db.getListFood(idCategory);
        return foodList.size() > 0 ? true : false;
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
            ShoppingCartDialog dialog = new ShoppingCartDialog();
            dialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog shopping cart");
        }

        return super.onOptionsItemSelected(item);
    }

    // sự kiện click trên recycler view
    @Override
    public void onItemClick(View view, int position) {
        int id = view.getId();
        if (id == R.id.layout_item_food) {
            //     Toast.makeText(getActivity(), "layout press in " + foodID + "", Toast.LENGTH_SHORT).show();
            try {
                ShowDialogDescriptionFood(position);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.btnAddFoodToList) {
            String idFood = foodList.get(position).ID;
            for(InvoiceDetails x : detailsList){
                if(x.ID.equals(idFood)){
                    // nếu món ăn đã được gọi và gọi lại 1 lần nữa!
                    db.updateQuanlityFoodInInvoice();
                    return;
                }
            }
            boolean isSuccess = order(position);
            if (isSuccess) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean order(int position) {
        String id = foodList.get(position).ID;
        String name = foodList.get(position).Name;
        String category = foodList.get(position).ProductCategoryID;
        String image = foodList.get(position).Image;
        String price = foodList.get(position).SalePrice;

        NumberPicker numberPicker = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.number_picker_food_page);
        int quanlity = numberPicker.getValue();
        double totalPrice = Double.parseDouble(foodList.get(position).SalePrice) * quanlity;
        String Quanlity = quanlity + "";
        String total = totalPrice + "";
        InvoiceDetails invoiceDetails = new InvoiceDetails(id, name, category, image, price, Quanlity, total);
        detailsList.add(invoiceDetails);
        return db.addInvoiceDetails(invoiceDetails);
    }

    private void ShowDialogDescriptionFood(int position) throws ExecutionException, InterruptedException {

        String id = foodList.get(position).ID;
        String name = foodList.get(position).Name;
        String price = foodList.get(position).SalePrice;
        String description = foodList.get(position).Description;
        String image = foodList.get(position).Image;

        ItemFoodDialog itemFoodDialog = new ItemFoodDialog();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name_food", name);
        bundle.putString("price", price);
        bundle.putString("description", description);
        bundle.putString("image", image);

        itemFoodDialog.setArguments(bundle);
        itemFoodDialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog description food");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.isEmpty()) {
            List<Food> listFound = new ArrayList<>();
            for (Food x : foodList) {
                if (x.Name.contains(newText)) {
                    listFound.add(x);
                    Log.d("aaaaa", newText);
                }
            }
            ListFoodAdapter adapter = new ListFoodAdapter(listFound, getActivity().getBaseContext(), this);
            // recylerView set adapter
            recyclerView.setAdapter(adapter);
        } else {
            ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
            // recylerView set adapter
            recyclerView.setAdapter(adapter);
        }
        return true;
    }
}
