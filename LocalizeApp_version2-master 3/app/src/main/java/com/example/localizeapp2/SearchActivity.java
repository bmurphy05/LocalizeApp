package com.example.localizeapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private TextView mTextMessage;
    SearchView searchBar;
    ListView searchList;


    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.searchView);
        searchList = findViewById(R.id.searchList);

        list=new ArrayList<String>();

        list.add("Verified");
        list.add("Rock");
        list.add("Hip Hop");
        list.add("Concerts");
        list.add("Networking");
        list.add("18+");
        list.add("Art");
        list.add("Happy Hour");
        list.add("Sports");
        list.add("Food");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        searchList.setAdapter(adapter);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);

                return false;
            }

        });

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_explore:
                        Intent a = new Intent(SearchActivity.this, MapsActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_events:
                        Intent c = new Intent(SearchActivity.this, CalendarActivity.class);
                        startActivity(c);
                        break;
                    case R.id.navigation_search:
                        break;
                    case R.id.navigation_profile:
                        Intent d = new Intent(SearchActivity.this, ProfileActivity.class);
                        startActivity(d);
                        break;
                }
                return true;
            }
        });
    }

}