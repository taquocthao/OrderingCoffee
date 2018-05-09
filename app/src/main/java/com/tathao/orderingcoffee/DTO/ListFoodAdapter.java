package com.tathao.orderingcoffee.DTO;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tathao.orderingcoffee.InterfaceHandler.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.R;

import java.util.List;

/**
 * Created by USER on 3/30/2018.
 */

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.ViewHodler> {


    private List<Food> listFood;
    private Context context;
    private OnItemRecyclerviewLisener onItemRecyclerviewLisener;

    public ListFoodAdapter(List<Food> listFood, Context context, OnItemRecyclerviewLisener onItemRecyclerviewLisener) {
        this.listFood = listFood;
        this.context = context;
        this.onItemRecyclerviewLisener = onItemRecyclerviewLisener;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_food, parent, false);
        return new ViewHodler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {

        holder.name.setText(listFood.get(position).getName());
        holder.category.setText(listFood.get(position).getCategoryID());
        holder.price.setText(listFood.get(position).getPrice());

//        if(listFood.get(position).getImage() != null){
//            byte[] decodeString = Base64.decode(listFood.get(position).getImage(), Base64.DEFAULT);
//            Bitmap bitmapImage = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//            holder.image.setImageBitmap(bitmapImage);
//        }


       // holder.image.setImageResource(listFood.get(position).getImage());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemRecyclerviewLisener.onItemClick(view, position);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemRecyclerviewLisener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }


    public class ViewHodler extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView category;
        private TextView price;
        private Button btnAdd;
        private ImageView image;
        private LinearLayout layout;

        public ViewHodler(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgFood);
            name = itemView.findViewById(R.id.tvTitleFood);
            category = itemView.findViewById(R.id.tvCategoryFood);
            price = itemView.findViewById(R.id.tvPriceFood);
            btnAdd = (Button) itemView.findViewById(R.id.btnAddFoodToList);
            layout = (LinearLayout)itemView.findViewById(R.id.layout_item_food);
        }
    }

}
