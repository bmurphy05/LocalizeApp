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

public class SignUpActivity extends AppCompatActivity {

    // Firebase
    private DatabaseReference users;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    private EditText mUsernameField;
    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private Button mSignUpButton;
    private Button mBackButton;
    private Button mSkipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        // Views
        mUsernameField = findViewById(R.id.fieldUsername);
        mNameField = findViewById(R.id.fieldName);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        mConfirmPasswordField = findViewById(R.id.fieldConfirmPassword);
        mSignUpButton = findViewById(R.id.btnSignUp);
        mBackButton = findViewById(R.id.btnBack);
        mSkipButton = findViewById(R.id.btnSkip);

        // Click listeners
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(s);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User(mUsernameField.getText().toString(),
                        mNameField.getText().toString(),
                        mEmailField.getText().toString(),
                        mPasswordField.getText().toString(),
                        mConfirmPasswordField.getText().toString(), "");

                if (TextUtils.isEmpty(mUsernameField.getText().toString()) | TextUtils.isEmpty(mNameField.getText().toString()) | TextUtils.isEmpty(mEmailField.getText().toString()) | TextUtils.isEmpty(mPasswordField.getText().toString()) | TextUtils.isEmpty(mConfirmPasswordField.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }
                else if (!user.getPassword().equals(user.getConfirmPassword())){
                    Toast.makeText(SignUpActivity.this, "Passwords do not match. Try again.", Toast.LENGTH_SHORT).show();
                }
                else {
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getUsername()).exists())
                                Toast.makeText(SignUpActivity.this, "The username already exists!", Toast.LENGTH_SHORT).show();
                            else {
                                users.child(user.getUsername()).setValue(user);
                                Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                Intent s = new Intent(getApplicationContext(), MapsActivity.class);
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

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(s);
            }
        });

    }
}
