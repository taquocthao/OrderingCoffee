package com.tathao.orderingcoffee.DTO;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tathao.orderingcoffee.R;

import java.util.List;

public class ListFoodOrderedAdapter extends RecyclerView.Adapter<ListFoodOrderedAdapter.ViewHolder> {

    private List<Food> foods;
    private Context context;

    public ListFoodOrderedAdapter(List<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list_food_ordered, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(foods.get(position).getName());
        holder.price.setText(foods.get(position).getPrice());
        holder.noOfFoodOrdered.setText("");

        holder.image.setImageBitmap(null);

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private TextView noOfFoodOrdered;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.tvNameFood_ordered);
            price = (TextView)itemView.findViewById(R.id.tvPriceFood_ordered);
            noOfFoodOrdered = (TextView)itemView.findViewById(R.id.tvPriceFood_ordered);
            image = (ImageView)itemView.findViewById(R.id.imgFood_ordered);

        }
    }
}
