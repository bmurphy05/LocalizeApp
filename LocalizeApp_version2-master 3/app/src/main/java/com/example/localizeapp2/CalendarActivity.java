package com.example.localizeapp2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;


public class CalendarActivity extends AppCompatActivity {

    EditText et_1;
    EditText et_2;
    EditText et_3;
    EditText et_4;
    Button event_btn;
    ListView events_list;
    ArrayList<ArrayList> mainArrayList;
    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;

    private TextView mTextMessage;

    private DatabaseReference events;
    FirebaseDatabase database_events;
    FirebaseAuth firebaseAuth1;

    private EditText mTitle, mDate_Time, mLocation, mTag;
    private Button mCreateEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Firebase
        database_events = FirebaseDatabase.getInstance();
        events = database_events.getReference("events");
        firebaseAuth1 = FirebaseAuth.getInstance();

        mTitle = findViewById(R.id.et1);
        mDate_Time = findViewById(R.id.et2);
        mLocation = findViewById(R.id.et3);
        mTag = findViewById(R.id.et4);

        mCreateEventButton = findViewById(R.id.eventbtn);


        Event event = new Event();

        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent(mTitle.getText().toString(), mDate_Time.getText().toString(), mLocation.getText().toString(), mTag.getText().toString());
            }
        });

        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getApplicationContext(), EventListActivity.class);
                startActivity(s);
            }
        });

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_explore:
                        Intent a = new Intent(CalendarActivity.this, MapsActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_events:
                        break;
                    case R.id.navigation_search:
                        Intent d = new Intent(CalendarActivity.this, SearchActivity.class);
                        startActivity(d);
                        break;
                    case R.id.navigation_profile:
                        Intent e = new Intent(CalendarActivity.this, ProfileActivity.class);
                        startActivity(e);
                        break;
                }
                return true;
            }
        });
    }

    private void createEvent(final String title, final String Date_time,
                             final String location, final String tag) {
        events.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(title).exists()) {
                    if (!title.isEmpty()) {
                        Event add = dataSnapshot.child(title).getValue(Event.class);
                        if (add.getDate_time().equals(Date_time)) {
                            Toast.makeText(CalendarActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(), MapsActivity.class);
                            startActivity(s);
                        } else {
                            Toast.makeText(CalendarActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CalendarActivity.this, "Username is not registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        et_1 = (EditText) findViewById(R.id.et1);
        et_2 = (EditText) findViewById(R.id.et2);
        et_3 = (EditText) findViewById(R.id.et3);
        et_4 = (EditText) findViewById(R.id.et4);

        event_btn = (Button) findViewById(R.id.eventbtn);
        events_list = (ListView) findViewById(R.id.eventslist);


        itemList = new ArrayList<String>();
        mainArrayList = new ArrayList<ArrayList>();
        adapter = new ArrayAdapter<String>(CalendarActivity.this, android.R.layout.simple_list_item_1,
                itemList);

        events_list.setAdapter(adapter);

        onBtnClick();


    }

    private void onBtnClick() {
        event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res1 = et_1.getText().toString();
                String res2 = et_2.getText().toString();
                String res3 = et_3.getText().toString();
                String res4 = et_4.getText().toString();
                itemList.add(res1);
                itemList.add(res2);
                itemList.add(res3);
                itemList.add(res4);

                mainArrayList.add(itemList);

                itemList.clear();

                adapter.notifyDataSetChanged();
            }
        });
    }

}
