package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogueListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++){
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the city at clicked position
                City cityToEdit = dataList.get(position);
                // Show edit dialog
                AddCityFragment editFragment = AddCityFragment.newInstance(cityToEdit, position);
                editFragment.show(getSupportFragmentManager(), "Edit City");
            }
        });

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //smth to do w this later
                new AddCityFragment().show(getSupportFragmentManager(), "Add City");
            }
        });



    }

    @Override
    public void addCity(City city) {
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City updatedCity, int position) {
        // Make sure position is valid
        if (position >= 0 && position < dataList.size()) {
            // Get the existing city from the list
            City existingCity = dataList.get(position);

            // Update its properties with the new values
            existingCity.setName(updatedCity.getName());
            existingCity.setProvince(updatedCity.getProvince());

            // Tell the adapter to refresh the display
            cityAdapter.notifyDataSetChanged();
        }
    }

}