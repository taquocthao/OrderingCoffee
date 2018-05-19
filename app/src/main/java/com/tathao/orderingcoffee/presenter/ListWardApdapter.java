package com.tathao.orderingcoffee.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.Ward;

import java.util.List;

public class ListWardApdapter extends BaseAdapter {

    private List<Ward> wards;
    private Context context;

    public ListWardApdapter(List<Ward> wards, Context context){
        this.wards = wards;
        this.context = context;
    }


    @Override
    public int getCount() {
        return wards.size();
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
        view = inflater.inflate(R.layout.item_list_ward, null);
        TextView tvName = (TextView)view.findViewById(R.id.tvWardName_register);

        tvName.setText(wards.get(i).Name);
        return view;
    }
}
