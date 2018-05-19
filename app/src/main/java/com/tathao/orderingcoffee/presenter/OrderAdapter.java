package com.tathao.orderingcoffee.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tathao.orderingcoffee.Interface.OnNumberPickerValueChangeLisener;
import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.InvoiceDetails;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<InvoiceDetails> foods;
    private Context context;
    private OnNumberPickerValueChangeLisener valueChangedListener;

    public OrderAdapter(List<InvoiceDetails> foods, Context context, OnNumberPickerValueChangeLisener valueChangedListener) {
        this.foods = foods;
        this.context = context;
        this.valueChangedListener = valueChangedListener;
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
        holder.price.setText(foods.get(position).SalePrice);
        holder.quanlity.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                double price = Double.parseDouble(foods.get(position).SalePrice) * value;
                holder.price.setText(price + "");
                valueChangedListener.onValueChange(value, position);
            }
        });
//        double price  = Double.parseDouble(foods.get(position).SalePrice) * holder.quanlity.getValue();
//        holder.price.setText(price + "");

        holder.image.setImageBitmap(null);
    }


    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private NumberPicker quanlity;
        private ImageView image;
//        private TextView totalPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvNameFood_ordered);
            price = (TextView) itemView.findViewById(R.id.tvPriceFood_ordered);
            quanlity = (NumberPicker) itemView.findViewById(R.id.number_picker_shopping);
            quanlity.setMin(1);
            image = (ImageView) itemView.findViewById(R.id.imgFood_ordered);
//            totalPrice = (TextView)itemView.findViewById(R.id.tvTotalPrice_shopping);
        }
    }
}
