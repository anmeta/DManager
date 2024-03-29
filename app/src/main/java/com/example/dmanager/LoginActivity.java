package com.example.dmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmanager.helpers.Context;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {
    Button btnUser, btnRest, loginButton;
    TextView medicalCard;
    EditText email, password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeFirebaseAuth();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        btnUser = (Button)findViewById(R.id.btnAddItem);
        btnRest = (Button)findViewById(R.id.btnRest);
        email = findViewById(R.id.email);
        if(Context.getInstance().LastSignedInEmail!=null){
            email.setText(Context.getInstance().LastSignedInEmail);
        }
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);


        loginButton.setText("Sign in");
        loginButton.setEnabled(true);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpUser = new Intent(LoginActivity.this, UserSignUp.class);
                startActivity(SignUpUser);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String emailStr = email.getText().toString();
                    String passwordStr = password.getText().toString();
                    loginButton.setText("Signing in ...");
                    loginButton.setEnabled(false);
                    signIn(emailStr, passwordStr);
                    loginButton.setEnabled(true);
                }
                catch (Exception ex){
                   System.out.println("Something went wrong!");
                }
            }
        });
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpRestaurant = new Intent(LoginActivity.this, RestaurantSignUp.class);
                startActivity(SignUpRestaurant
                );
            }
        });



    }



}