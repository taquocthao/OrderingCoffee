package com.tathao.orderingcoffee.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tathao.orderingcoffee.Interface.OnItemRecyclerviewLisener;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.FoodCategory;

import java.util.List;

public class ListFoodCategoryAdapter extends RecyclerView.Adapter<ListFoodCategoryAdapter.ViewHolder>{

    private List<FoodCategory> foodCategories;
    private Context context;
    private OnItemRecyclerviewLisener onItemRecyclerviewLisener;


    public ListFoodCategoryAdapter(List<FoodCategory> foodCategories, Context context, OnItemRecyclerviewLisener onItemRecyclerviewLisener){
        this.context = context;
        this.foodCategories = foodCategories;
        this.onItemRecyclerviewLisener = onItemRecyclerviewLisener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_food_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvNameCategory.setText(foodCategories.get(position).Name);
//        holder.tvDescription.setText(foodCategories.get(position).getDescription());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemRecyclerviewLisener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodCategories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameCategory;
//        private TextView tvDescription;
        private LinearLayout item;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvNameCategory = (TextView)itemView.findViewById(R.id.tvNameCategoryFood);
//            tvDescription = (TextView)itemView.findViewById(R.id.tvDescriptionCategoryFood);
            item  = (LinearLayout)itemView.findViewById(R.id.item_category_food);
        }
    }
}
