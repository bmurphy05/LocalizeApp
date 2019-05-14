package com.example.localizeapp2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    // Firebase
    FirebaseUser user;
    private DatabaseReference profileUserReference;
    FirebaseAuth firebaseAuth;

    private String currentUserId;

    private ImageView mProfilePicture;
    private EditText mUsernameField;
    private EditText mBioField;
    private EditText mNameField;
    private EditText mEmailField;

    private Button mSaveButton;
    private Button mChangePassword;
    private Button mChangeProfilePicture;
    private Button mSignOutButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        profileUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

        // Views
        mProfilePicture = findViewById(R.id.profilePicture);
        mUsernameField = findViewById(R.id.fieldUsername);
        mBioField = findViewById(R.id.fieldBio);
        mNameField = findViewById(R.id.fieldName);
        mEmailField = findViewById(R.id.fieldEmail);
        mSaveButton = findViewById(R.id.btnSave);
        mChangePassword = findViewById(R.id.btnChangePassword);
        mChangeProfilePicture = findViewById(R.id.btnChangeProfilePicture);
        mSignOutButton = findViewById(R.id.btnSignOut);

        profileUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String myProfileImage = dataSnapshot.child("profileImage").getValue().toString();
                    String myUsername = dataSnapshot.child("username").getValue().toString();
                    String myName = dataSnapshot.child("name").getValue().toString();
                    String myEmail = dataSnapshot.child("email").getValue().toString();
                    String myBio = dataSnapshot.child("bio").getValue().toString();

                    // Profile Picture
                    Picasso.get().load(myProfileImage).placeholder(R.drawable.profile).into(mProfilePicture);

                    // User info
                    mUsernameField.setText(myUsername);
                    mBioField.setText(myBio);
                    mNameField.setText(myName);
                    mEmailField.setText(myEmail);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                // code
            }
        });

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut(view);
            }
        });

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_explore:
                        Intent a = new Intent(ProfileActivity.this, MapsActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_events:
                        Intent c = new Intent(ProfileActivity.this, CalendarActivity.class);
                        startActivity(c);
                        break;
                    case R.id.navigation_search:
                        Intent d = new Intent(ProfileActivity.this, SearchActivity.class);
                        startActivity(d);
                        break;
                    case R.id.navigation_profile:
                        break;
                }
                return true;
            }
        });

    }

    public void signOut(View v){
        firebaseAuth.signOut();
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}