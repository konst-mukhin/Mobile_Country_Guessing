package com.example.projectx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CountryAdapter extends BaseAdapter {

    private Context context;
    private List<Country> countries;

    public CountryAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        }

        Country country = countries.get(position);

        TextView flagView = convertView.findViewById(R.id.flag);
        TextView countryName = convertView.findViewById(R.id.countryName);
        TextView capitalName = convertView.findViewById(R.id.capitalName);

        flagView.setText(country.getFlag());
        countryName.setText(country.getName());
        capitalName.setText(country.getCapital());

        return convertView;
    }

}
