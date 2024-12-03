package com.example.projectx;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountryDataLoader {

    public static List<Country> loadCountries(Context context) {
        List<Country> countries = new ArrayList<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.countries);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String capital = jsonObject.getString("capital");
                String flag = jsonObject.getString("flag");

                countries.add(new Country(name, capital, flag));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }
}
