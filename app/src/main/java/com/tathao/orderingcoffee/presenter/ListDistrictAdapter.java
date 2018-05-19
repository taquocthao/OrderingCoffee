package com.tathao.orderingcoffee.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.District;

import java.util.List;

public class ListDistrictAdapter extends BaseAdapter {
    private List<District> districts;
    private Context context;

    public ListDistrictAdapter(List<District> districts, Context context) {
        this.districts = districts;
        this.context = context;
    }


    @Override
    public int getCount() {
        return districts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_list_district, null);
        TextView tvName = (TextView) view.findViewById(R.id.tvDistrictName_register);

        tvName.setText(districts.get(i).Name);
        return view;
    }
}
