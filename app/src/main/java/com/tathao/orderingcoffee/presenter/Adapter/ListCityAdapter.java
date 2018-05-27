package com.tathao.orderingcoffee.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.entity.City;

import java.util.List;

public class ListCityAdapter extends BaseAdapter {

    private List<City> cities;
    private Context context;

    public ListCityAdapter(List<City> cities, Context context){
        this.cities = cities;
        this.context = context;
    }


    @Override
    public int getCount() {
//        int count = cities.size();
//        return count > 0 ? count - 1: count;
        return cities.size();
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
        view = inflater.inflate(R.layout.item_list__city, null);
        TextView tvName = (TextView)view.findViewById(R.id.tvCityName_register);
        tvName.setText(cities.get(i).Name);
        return view;
    }
}
