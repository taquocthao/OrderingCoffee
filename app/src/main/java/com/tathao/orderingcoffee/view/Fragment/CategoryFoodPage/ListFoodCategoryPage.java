package com.tathao.orderingcoffee.view.Fragment.CategoryFoodPage;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.tathao.orderingcoffee.Interface.AddFragment;
import com.tathao.orderingcoffee.Interface.OnItemRecyclerViewLisener;
import com.tathao.orderingcoffee.Interface.OnUpdateFromDialog;
import com.tathao.orderingcoffee.NetworkAPI.Client;
import com.tathao.orderingcoffee.NetworkAPI.Config;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.entity.ConvertItemShoppingCart;
import com.tathao.orderingcoffee.model.entity.Food;
import com.tathao.orderingcoffee.model.entity.FoodCategory;
import com.tathao.orderingcoffee.model.entity.InvoiceDetails;
import com.tathao.orderingcoffee.presenter.Adapter.ListFoodCategoryAdapter;
import com.tathao.orderingcoffee.presenter.CategoryFoodPresenter.CategoryFoodPresenter;
import com.tathao.orderingcoffee.view.Activity.MainActivity;
import com.tathao.orderingcoffee.view.Dialog.ShoppingCartDialog;
import com.tathao.orderingcoffee.view.Fragment.ListFoodPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListFoodCategoryPage extends Fragment implements AddFragment, OnItemRecyclerViewLisener
        , SearchView.OnQueryTextListener, OnUpdateFromDialog, OnCategoryFoodViewLisener {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<FoodCategory> foodCategories;
    private List<Food> foodList;
    SearchView searchView;
    private String shopID = null;
    private String tableID = null;
    private static int count = 0;
    private DBManager db;
    private List<InvoiceDetails> invoiceDetails;


    // mô hình mvp
    CategoryFoodPresenter categoryFoodPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_food_category, container, false);
        // hiển thị button back arrow trên toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // cho hiển thị item trên toolbar
        setHasOptionsMenu(true);
        // khởi tạo các fields
        init(view);
        // kiểm tra thông tin nhận được từ trang home page
        if (getArguments() != null) { // nếu thông tin không rỗng
            shopID = getArguments().getString("shop_id");
            tableID = getArguments().getString("table_id");
            /* hiển thị danh sách category
               không load được danh sách -> false
               ngược lại -> true
             */
//            boolean isSuccess = LoadItemCategoryFood();
//            if (isSuccess) { // nếu trả về true
//                ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
//                recyclerView.setAdapter(foodCategoryAdapter);
//
//            } else { // nếu không load được -> tạo một item ảo, thông báo lỗi
//                foodCategories.add(new FoodCategory(null, "N/A", null, null, null, null, null));
//                ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
//                recyclerView.setAdapter(foodCategoryAdapter);
//            }
            invoiceDetails = db.getAllListInvoiceDetails();
            count = invoiceDetails.size();
            // reset toolbar
            ((AppCompatActivity) getActivity()).supportInvalidateOptionsMenu();
            String json = getArguments().getString("json");
            categoryFoodPresenter.getListCategoryFood(json);
        }
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recylerViewListFoodCategory);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        db = new DBManager(getActivity().getBaseContext());
        invoiceDetails = new ArrayList<>();
        foodCategories = new ArrayList<>();
        foodList = new ArrayList<>();

        categoryFoodPresenter = new CategoryFoodPresenter(this);
    }

    // hiển thị menu item trên toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItemCart = menu.findItem(R.id.menu_shopping_cart);
        menuItemCart.setIcon(ConvertItemShoppingCart.convertLayoutToImage(getActivity().getBaseContext(), count, R.drawable.ic_shopping_cart));


        MenuItem menuItem = menu.findItem(R.id.menu_action_searh);
        searchView = (SearchView) menuItem.getActionView();
        // bắt sự kiện tim kiếm trên search view
        searchView.setOnQueryTextListener(this);
    }

    // sự kiện click item trên menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_shopping_cart) {
            // hiển thị shopping carts trên dialog
            ShoppingCartDialog dialog = new ShoppingCartDialog();
            Bundle bundle = new Bundle();
            bundle.putString("shop_id", shopID);
            bundle.putString("table_id", tableID);
            dialog.setArguments(bundle);
//            dialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog shopping cart");
            dialog.setTargetFragment(this, 0);
            dialog.show(getFragmentManager(), "dialog shopping cart");
        }
        return super.onOptionsItemSelected(item);
    }

    // sự kiện click item trên recycler view
    @Override
    public void onItemClick(View view, int position) {
        // lấy id của một category từ vị trí trên recyclerView
        final String idCategory = foodCategories.get(position).ID;
        // khởi tạo một lớp thao tác database
        DBManager db = new DBManager(getActivity().getBaseContext());
        // kiểm tra xem danh sách thực phẩm của category đã được thêm vào csdl chưa
        // nếu đã tồn tại -> true
        // ngược lại -> false
        boolean existFoods = db.getFoodCount(idCategory) > 0 ? true : false;
        if (existFoods) { // nếu đã tồn tại trong csdl
            // đi tới trang hiển thị danh sách thực phẩm
            GotoListFood(idCategory);
        } else { // nếu chưa tồn tại danh sách thực phẩm
            // tải danh sách thực phẩm từ json
            boolean isSuccess = LoadItemFoodFromServer(idCategory);
            if (isSuccess) { // nếu tải thành công
                // lưu ds thực phẩm vừa tải vào csdl
                db.addFoodFromJSON(foodList);
                // đi đến trang hiển thị danh sách thực phẩm
                GotoListFood(idCategory);
            } else { // nếu không tải được danh sách thực phẩm từ server
                Toast.makeText(getActivity().getBaseContext(), "Connect fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNumberPickerValueChange(int value, int position) {

    }

    @Override
    public void onImageButtonDelete(View view, int positon) {

    }

    // đi đến trang hiển thị danh sách thực phẩm
    public void GotoListFood(String idCategory) {
        ListFoodPage foodPage = new ListFoodPage();
        Bundle bundle = new Bundle();
        bundle.putString("category", idCategory);
        bundle.putString("shop_id", shopID);
        bundle.putString("table_id", tableID);
        foodPage.setArguments(bundle);
        addFragment(foodPage, getString(R.string.menu));
    }

    // Kiểm tra việc tải ds thực phẩm từ server
    private boolean LoadItemFoodFromServer(String idCategory) {
        String url = Config.urlFoodes + idCategory;
        try {
            String json = new Client(url, Client.GET, null, (MainActivity) getActivity())
                    .execute().get().toString();
            Log.d("json_category_food", json);
            // ép kiểu kết quả trả về từ server thành object json
            // kiểm tra danh sách trong object
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("product");
            if (jsonArray.length() > 0) { // tồn tại danh sách các đối tượng
                // parse from json to object java
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

    // thêm một fragment
    @Override
    public void addFragment(android.app.Fragment fragment, String title) {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(getString(R.string.food_page))
                .commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    // Tìm kiếm danh sách category food
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


    // cập nhập lại số lượng trên item giỏ hàng
    @Override
    public void onDeleteFromDialog(boolean update) {
        invoiceDetails = db.getAllListInvoiceDetails();
        count = invoiceDetails.size();
        ((AppCompatActivity) getActivity()).supportInvalidateOptionsMenu();
    }

    @Override
    public void onAddFoodFormDialog(boolean isAdd) {

    }

    // lấy dữ liệu food category trả về từ presenter đổ vào recyclerview
    @Override
    public void onGetListCategorySuccess(List<FoodCategory> categoryList) {
        foodCategories = categoryList;
        ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(categoryList, getActivity().getBaseContext(), this);
        recyclerView.setAdapter(foodCategoryAdapter);
    }
    // lấy dữ liệu food category trả về từ presenter đổ vào recyclerview -> nếu thất bại
    @Override
    public void onGetListCategoryFailure() {
        // nếu load thất bại->tạo một item ảo, thông báo lỗi
        foodCategories.add(new FoodCategory(null, "N/A", null, null, null, null, null));
        ListFoodCategoryAdapter foodCategoryAdapter = new ListFoodCategoryAdapter(foodCategories, getActivity().getBaseContext(), this);
        recyclerView.setAdapter(foodCategoryAdapter);
    }
}




