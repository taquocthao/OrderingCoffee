package com.tathao.orderingcoffee.DialogHandler;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tathao.orderingcoffee.R;
import com.travijuu.numberpicker.library.NumberPicker;

public class ItemFoodDialog extends AppCompatDialogFragment {

    private ImageView imgFood;
    private TextView tvName, tvPrice, tvDescription;
    private NumberPicker numberPicker;
    private Button btnAdd;
    private String id = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_item_food, null);

        init(view);
        if(getArguments() != null){
            id = getArguments().getString("id");
            String name = getArguments().getString("name_food");
            tvName.setText(name);
            String price = getArguments().getString("price");
            tvPrice.setText(price);
            String description = getArguments().getString("description");
            tvDescription.setText(description);

            String image = getArguments().getString("image");
//            if(image != null){
//                byte[] decodeString = Base64.decode(image, Base64.DEFAULT);
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
                AddFoodShopingCart(view);
                dismiss();
            }
        });


        return dialog.create();
    }

    private void init(View view){
        tvName = (TextView)view.findViewById(R.id.tvNameFood_item_description);
        tvDescription = (TextView)view.findViewById(R.id.tvDescription_item_description);
        tvPrice = (TextView)view.findViewById(R.id.tvPriceFood_item_description);
        imgFood = (ImageView)view.findViewById(R.id.imgFood_item_description);
        numberPicker = (NumberPicker)view.findViewById(R.id.number_picker);
        btnAdd = (Button)view.findViewById(R.id.btnAdd_item_description);
    }

    private void AddFoodShopingCart(View view){
        Toast.makeText(getActivity().getBaseContext(), "bạn đã thêm món ăn "+ id +" vào thực đơn", Toast.LENGTH_SHORT).show();
    }
}
