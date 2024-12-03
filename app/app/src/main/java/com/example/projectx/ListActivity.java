package com.example.projectx;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        List<Country> countries = CountryDataLoader.loadCountries(this);

        CountryAdapter adapter = new CountryAdapter(this, countries);
        ListView countriesList = findViewById(R.id.countriesList);
        countriesList.setAdapter(adapter);
    }
}
