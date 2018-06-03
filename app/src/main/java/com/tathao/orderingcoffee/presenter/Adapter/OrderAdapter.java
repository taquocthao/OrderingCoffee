package com.tathao.orderingcoffee.presenter.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tathao.orderingcoffee.Interface.OnItemRecyclerViewLisener;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.database.DBManager;
import com.tathao.orderingcoffee.model.entity.InvoiceDetails;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<InvoiceDetails> foods;
    private Context context;
    private OnItemRecyclerViewLisener onItemRecyclerViewLisener;
    private DBManager db;

    public OrderAdapter(List<InvoiceDetails> foods, Context context, OnItemRecyclerViewLisener onItemRecyclerViewLisener) {
        this.foods = foods;
        this.context = context;
        this.onItemRecyclerViewLisener = onItemRecyclerViewLisener;
        db = new DBManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_food_ordered, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.name.setText(foods.get(position).Name);
        long totalPrice = Long.parseLong(foods.get(position).getTotalPrice());
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        holder.totalPrice.setText(numberFormat.format(totalPrice));
        holder.quantity.setValue(Integer.parseInt(foods.get(position).getQuantity()));
        holder.quantity.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                onItemRecyclerViewLisener.onNumberPickerValueChange(value, position);
            }
        });

        holder.imgDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemRecyclerViewLisener.onImageButtonDelete(v, position);
            }
        });
        try {

            Bitmap bitmap = BitmapFactory.decodeByteArray(foods.get(position).getImage().getBytes("utf-8"), 0, foods.get(position).getImage().length());
            holder.image.setImageBitmap(bitmap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private NumberPicker quantity;
        private ImageView image, imgDeleteButton;
        private TextView totalPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imgFood_ordered);
            name = (TextView) itemView.findViewById(R.id.tvNameFood_ordered);
            totalPrice = (TextView)itemView.findViewById(R.id.tvTotaoPriceFood_ordered);
            quantity = (NumberPicker) itemView.findViewById(R.id.number_picker_shopping);
            quantity.setMin(1);
            imgDeleteButton = (ImageView)itemView.findViewById(R.id.imgDelete_ordered);
        }
    }
}
