package com.tathao.orderingcoffee.FragmentApp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tathao.orderingcoffee.DTO.Food;
import com.tathao.orderingcoffee.R;

import java.util.List;

/**
 * Created by USER on 3/30/2018.
 */

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.ViewHodler> {


    private List<Food> listFood;
    private Context context;

    public ListFoodAdapter(List<Food> listFood, Context context) {
        this.listFood = listFood;
        this.context = context;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_food, parent, false);
        return new ViewHodler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {

        holder.name.setText(listFood.get(position).getName());
        holder.category.setText("" + listFood.get(position).getCategoryID());
        holder.price.setText(" " + listFood.get(position).getPrice());
        // holder.add.setOnClickListener();

        // sửa sau
        holder.image.setImageResource(listFood.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView category;
        private TextView price;
        //  private Button add;
        private ImageView image;

        public ViewHodler(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgFood);
            name = itemView.findViewById(R.id.tvTitleFood);
            category = itemView.findViewById(R.id.tvCategoryFood);
            price = itemView.findViewById(R.id.tvPriceFood);
            //     add = (Button) itemView.findViewById(R.id.btnAddFoodToList);
        }
    }
}
