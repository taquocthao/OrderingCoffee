package com.tathao.orderingcoffee.presenter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tathao.orderingcoffee.Interface.OnItemRecyclerViewLisener;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.Food;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

/**
 * Created by USER on 3/30/2018.
 */

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.ViewHodler> {


    private List<Food> listFood;
    private Context context;
    private OnItemRecyclerViewLisener onItemRecyclerViewLisener;

    public ListFoodAdapter(List<Food> listFood, Context context, OnItemRecyclerViewLisener onItemRecyclerViewLisener) {
        this.listFood = listFood;
        this.context = context;
        this.onItemRecyclerViewLisener = onItemRecyclerViewLisener;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_food, parent, false);
        return new ViewHodler(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {

        holder.tvName.setText(listFood.get(position).Name);
        holder.tvPrice.setText(listFood.get(position).SalePrice);

//        byte[] decodeString = listFood.get(position).Image.getBytes();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//        holder.image.setImageBitmap(bitmap);

        holder.image.setImageResource(R.drawable.image_food);

        holder.numberPicker.getValue();

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemRecyclerViewLisener.onItemClick(view, position);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemRecyclerViewLisener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }


    public class ViewHodler extends RecyclerView.ViewHolder {

        private TextView tvName;
        //  private TextView category;
        private TextView tvPrice;
        private Button btnAdd;
        private ImageView image;
        private LinearLayout layout;
        private NumberPicker numberPicker;

        public ViewHodler(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgFood_FoodPage);
            tvName = itemView.findViewById(R.id.tvTitleFood);
            //   category = itemView.findViewById(R.id.tvCategoryFood);
            tvPrice = itemView.findViewById(R.id.tvPriceFood);
            numberPicker = itemView.findViewById(R.id.number_picker_food_page);
            btnAdd = (Button) itemView.findViewById(R.id.btnAddFoodToList);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_item_food);
        }
    }

}
