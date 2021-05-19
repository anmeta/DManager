package com.example.dmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btnUser, btnRest;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUser = (Button)findViewById(R.id.btnUser);
        btnRest = (Button)findViewById(R.id.btnRest);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpUser = new Intent(MainActivity.this, UserSignUp.class);
                startActivity(SignUpUser);
            }
        });

        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpRest = new Intent(MainActivity.this, RestActivity.class);
                startActivity(SignUpRest
                );
            }
        });
    }



}