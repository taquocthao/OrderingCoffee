package com.tathao.orderingcoffee.view.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tathao.orderingcoffee.Interface.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.Food;
import com.tathao.orderingcoffee.model.InvoiceDetails;
import com.tathao.orderingcoffee.presenter.ListFoodAdapter;
import com.tathao.orderingcoffee.view.Dialog.ItemFoodDialog;
import com.tathao.orderingcoffee.view.Dialog.ShoppingCartDialog;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by USER on 3/31/2018.
 */

public class ListFoodPage extends Fragment implements OnItemRecyclerviewLisener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;
    private DBManager db;
    private List<Food> foodList;
    private List<InvoiceDetails> detailsList;
    private String shopID = null;
    private String tableID = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list_food, container, false);
        // hiển thị button back arrow trên toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        // tạo menu item trên toolbar
        setHasOptionsMenu(true);
        // khởi tạo các fields
        init(view);
        // kiểm tra thông tin nhận được từ trang category food
        if (getArguments() != null) { // nếu thông tin nhận được không rỗng
            String idCategory = getArguments().getString("category"); // id nhận dược từ category food
            shopID = getArguments().getString("shop_id");
            tableID = getArguments().getString("table_id");
            // hiển thị danh sách món ăn trong csdl
            boolean isSuccess = LoadFood(idCategory);
            if (isSuccess) {
                ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
                recyclerView.setAdapter(adapter);
            } else {
                foodList.add(new Food(null, "N/A", null, null, null, null, null, null, null));
                ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
                recyclerView.setAdapter(adapter);
            }
        }
        return view;
    }

    // hàm khởi tạo, các đối tượng
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

    // hiển thị danh sách món ăn trong csdl
    private boolean LoadFood(String idCategory) {
        // lấy danh sách món ăn theo loại
        // lấy được -> true
        // không lấy được -> false
        foodList = db.getListFood(idCategory);
        return foodList.size() > 0 ? true : false;
    }

    // khởi tạo menu trên toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_action_searh);
        searchView = (SearchView) menuItem.getActionView();
        // sự kiện tìm kiếm món ăn trên toolbar
        searchView.setOnQueryTextListener(this);

    }

    // sự kiện chọn vào item shopping cart trên toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // lấy id từ menu item
        if (id == R.id.menu_shopping_cart) {
            // hiển thị shopping carts trên dialog
            ShoppingCartDialog dialog = new ShoppingCartDialog();
            Bundle bundle = new Bundle();
            bundle.putString("shop_id", shopID);
            bundle.putString("table_id", tableID);
            dialog.setArguments(bundle);
            dialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog shopping cart");
        }
        return super.onOptionsItemSelected(item);
    }

    // sự kiện click trên recycler view
    @Override
    public void onItemClick(View view, int position) {
        int id = view.getId();
        if (id == R.id.layout_item_food) {
            try {
                // hiển thị chi tiết món ăn với mô tả đầy đủ trên dialog
                ShowDialogDescriptionFood(position);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.btnAddFoodToList) { // sự kiện click vào nút add trên mỗi recyclerView
            String idFood = foodList.get(position).ID;
            // kiểm tra món ăn đã được gọi trước đó hay chưa
            boolean isExists = checkFoodExistsInInvociceDetail(idFood);
            if (isExists) { // nếu đã dược gọi -> update table cộng thêm số lượng vào database
                // try vấn đến control numberpicker trên recyclerView đang được thao tác
                NumberPicker numberPicker = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.number_picker_food_page);
                // lấy số lượng món ăn sẽ được thêm vào danh sách của control numberpicker
                int newQuanlity = numberPicker.getValue();
                // lấy số lượng món ăn hiện tại mà danh sách lưu trữ
                int oldQuanlity = Integer.parseInt(detailsList.get(position).Quanlity);
                String quanlity = String.valueOf(oldQuanlity + newQuanlity);
//                Log.d("quall", quanlity);
                // kiểm tra cập nhật số lượng món ăn
                boolean updateResult = db.updateQuanlityFoodInInvoice(idFood, quanlity);
                if (updateResult) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else { // nếu món ăn này chưa được gọi trước đó
                // kiểm tra việc order một món ăn vào danh sách
                boolean isSuccess = order(position);
                if (isSuccess) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // kiểm tra món ăn đã được gọi hay chưa
    // true -> nếu đã được gọi trước đó
    // false -> chưa được gọi
    private boolean checkFoodExistsInInvociceDetail(String idFood) {
        detailsList = db.getAllListInvoiceDetails();
        for (InvoiceDetails x : detailsList) {
            if (x.ID.equals(idFood)) {
                // nếu món ăn đã được gọi và gọi lại 1 lần nữa!
                return true;
            }
        }
        return false;
    }

    // hàm thêm một món ăn vào chi tiết hóa đơn
    private boolean order(int position) {
        String id = foodList.get(position).ID;
        String name = foodList.get(position).Name;
        String category = foodList.get(position).ProductCategoryID;
        String image = foodList.get(position).Image;
        String price = foodList.get(position).SalePrice;
        // truy vấn đến control numberpicker trên recyclerView
        // lấy số lượng trên control numberpicker
        NumberPicker numberPicker = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.number_picker_food_page);
        int quanlity = numberPicker.getValue();
        long totalPrice = Long.parseLong(foodList.get(position).SalePrice) * quanlity;
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

    // sự kiện tìm kiếm trên searchview
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.isEmpty()) {
            List<Food> listFound = new ArrayList<>(); // khởi tạo một danh sách thức ăn rỗng,
            for (Food x : foodList) {
                if (x.Name.contains(newText)) {  // nếu trong danh sách thức ăn chính có một món ăn tên giống với newText
                    // thêm món ăn đó vào danh sách thức ăn rỗng
                    listFound.add(x);
                }
            }
            // hiển thị danh sách thức ăn vừa khởi tạo lên recyclerView
            ListFoodAdapter adapter = new ListFoodAdapter(listFound, getActivity().getBaseContext(), this);
            // recylerView set adapter
            recyclerView.setAdapter(adapter);
        } else {  // nếu ô tìm kiếm rỗng, không chưa kí tự
            ListFoodAdapter adapter = new ListFoodAdapter(foodList, getActivity().getBaseContext(), this);
            // recylerView set adapter
            recyclerView.setAdapter(adapter);
        }
        return true;
    }
}
