package com.example.localizeapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_explore:
                        Intent a = new Intent(BottomNavigationActivity.this, MapsActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_events:
                        Intent c = new Intent(BottomNavigationActivity.this, CalendarActivity.class);
                        startActivity(c);
                        break;
                    case R.id.navigation_search:
                        Intent d = new Intent(BottomNavigationActivity.this, SearchActivity.class);
                        startActivity(d);
                        break;
                    case R.id.navigation_profile:
                        Intent e = new Intent(BottomNavigationActivity.this, ProfileActivity.class);
                        startActivity(e);
                        break;
                }
                return true;
            }
        });
    }
}