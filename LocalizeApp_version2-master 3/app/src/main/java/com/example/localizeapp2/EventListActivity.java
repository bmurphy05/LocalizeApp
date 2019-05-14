package com.example.localizeapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class EventListActivity extends AppCompatActivity {

    // Firebase
    private DatabaseReference events;
    FirebaseDatabase database_events;
    FirebaseAuth firebaseAuth1;

    private EditText mTitleField;
    private EditText mDate_TimeField;
    private EditText mLocationField;
    private EditText mTagField;
    private Button mCreateEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Firebase
        database_events = FirebaseDatabase.getInstance();
        events = database_events.getReference("events");
        firebaseAuth1 = FirebaseAuth.getInstance();

        // Views
        mTitleField = findViewById(R.id.et1);
        mDate_TimeField = findViewById(R.id.et2);
        mLocationField = findViewById(R.id.et3);
        mTagField = findViewById(R.id.et4);
        mCreateEventButton = findViewById(R.id.eventbtn);


        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Event event = new Event(mTitleField.getText().toString(),
                        mDate_TimeField.getText().toString(),
                        mLocationField.getText().toString(),
                        mTagField.getText().toString());

                if (TextUtils.isEmpty(mTitleField.getText().toString()) | TextUtils.isEmpty(mDate_TimeField.getText().toString()) | TextUtils.isEmpty(mLocationField.getText().toString()) | TextUtils.isEmpty(mTagField.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    events.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            if (dataSnapshot1.child(event.getTitle()).exists())
                                Toast.makeText(EventListActivity.this, "The event has already been added!", Toast.LENGTH_SHORT).show();
                            else {
                                events.child(event.getTitle()).setValue(event);
                                Toast.makeText(EventListActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                Intent s = new Intent(getApplicationContext(), CalendarActivity.class);
                                startActivity(s);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });

    }
}