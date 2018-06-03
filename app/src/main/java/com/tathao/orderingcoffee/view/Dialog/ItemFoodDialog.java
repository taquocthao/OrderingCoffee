package com.tathao.orderingcoffee.view.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tathao.orderingcoffee.Interface.OnUpdateFromDialog;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.entity.Food;
import com.tathao.orderingcoffee.model.entity.InvoiceDetails;
import com.travijuu.numberpicker.library.NumberPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemFoodDialog extends DialogFragment {

    private ImageView imgFood;
    private TextView tvName, tvPrice, tvDescription;
    private NumberPicker numberPicker;
    private Button btnAdd;
    private String id = null;
    private String name = null;
    private String category = null;
    private String price = null;
    private String description = null;
    private String image = null;
    private String isDelete = null;
    private String create_at = null;
    private String update_at = null;
    private DBManager dbManager;
    private List<InvoiceDetails> detailsList;
    private OnUpdateFromDialog onUpdateFromDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        onUpdateFromDialog = (OnUpdateFromDialog) getTargetFragment();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_item_food, null);

        init(view);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            name = getArguments().getString("name_food");
            category = getArguments().getString("category");
            price = getArguments().getString("price");
            description = getArguments().getString("description");
            isDelete = getArguments().getString("isDelete");
            create_at = getArguments().getString("created_at");
            update_at = getArguments().getString("updated_at");
            tvName.setText(name);
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            long priceOfFood = Long.parseLong(price);
            tvPrice.setText(numberFormat.format(priceOfFood));
            tvDescription.setText(description);
            image = getArguments().getString("image");
//            if(image != null){
//                byte[] decodeString = image.getBytes();
//                Bitmap bitmapImage = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//                imgFood.setImageBitmap(bitmapImage);
//            }
        }
        dialog.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSuccess = AddFoodShopingCart();
                if(isSuccess){
                    Toast.makeText(getActivity().getBaseContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    onUpdateFromDialog.onAddFoodFormDialog(true);
                }
                else {
                    Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        return dialog.create();
    }

    private void init(View view) {
        tvName = (TextView) view.findViewById(R.id.tvNameFood_item_description);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription_item_description);
        tvPrice = (TextView) view.findViewById(R.id.tvPriceFood_item_description);
        imgFood = (ImageView) view.findViewById(R.id.imgFood_item_description);
        numberPicker = (NumberPicker) view.findViewById(R.id.number_picker_item_description);
        numberPicker.setMin(1);
        btnAdd = (Button) view.findViewById(R.id.btnAdd_item_description);
        dbManager = new DBManager(getActivity().getBaseContext());
        detailsList = new ArrayList<>();
    }

    private boolean AddFoodShopingCart() {

        //kiểm tra món ăn đã được gọi chưa
        boolean isExists = checkFoodExistsInInvociceDetail(id);
        if(isExists){ // nếu món ăn đã được gọi -> cập nhật lại số lượng sản phẩm trong database
            // lấy số lượng món ăn từ numberpicker
            int newQuantity = numberPicker.getValue();
            // lấy số lượng của một sản phẩm trong database
            int oldQuantity = dbManager.getQuantityOfOneProductFromDetails(id);
            int quantity = newQuantity + oldQuantity;
//            Log.d("quanlity", quantity+"");
            Food food = new Food(id, name, category, image, price, description, isDelete, create_at, update_at);
            boolean updateSuccess = dbManager.updateQuanlityFoodInInvoice(food, quantity);
            if(updateSuccess){

                return true;
            }
            else {

                return false;
            }

        }
        else { // nếu món ăn chưa được gọi -> gọi món vào invoice details
            InvoiceDetails invoiceDetails = new InvoiceDetails();
            invoiceDetails.setID(id);
            invoiceDetails.setName(name);
            invoiceDetails.setProductCategoryID(category);
            invoiceDetails.setImage(image);
            invoiceDetails.setSalePrice(price);
            int quantity = numberPicker.getValue();
            invoiceDetails.setQuantity(String.valueOf(quantity));
            long totalPrice = Long.parseLong(price)* quantity;
            invoiceDetails.setTotalPrice(String.valueOf(totalPrice));
            boolean addSuccess = dbManager.addInvoiceDetails(invoiceDetails);
            if(addSuccess){
                return true;
            }
            else {
                return false;
            }
        }
    }

    // kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
    private boolean checkFoodExistsInInvociceDetail(String idFood){
        detailsList = dbManager.getAllListInvoiceDetails();
        for(InvoiceDetails x : detailsList){
            if (x.getID().equals(idFood))
                return true;
        }
        return false;
    }
}
