package com.tathao.orderingcoffee.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tathao.orderingcoffee.R;
import com.tathao.orderingcoffee.model.entity.Country;

import java.util.List;

public class ListCountryApdater extends BaseAdapter{

    private List<Country> countries;
    private Context context;

    public ListCountryApdater(List<Country> countries, Context context){
        this.countries = countries;
        this.context = context;
    }


    @Override
    public int getCount() {
        int count = countries.size();
        return count > 0 ? count - 1:count;
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
        view = inflater.inflate(R.layout.item_list_country, null);
        TextView tvName = (TextView)view.findViewById(R.id.tvCountryName_register);

        tvName.setText(countries.get(i).Name);
        return view;
    }
}
