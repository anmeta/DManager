package com.example.dmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.dmanager.helpers.Context;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RestaurantActivity extends BaseActivity {
    LinearLayout layout;
    ListView details;
    Button openMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Context.getInstance().activeRestaurant.getFullName());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context.getInstance().cleanContext();
                Intent login = new Intent(RestaurantActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        layout = (LinearLayout) findViewById(R.id.linearLayout);
        details = (ListView)findViewById(R.id.details);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,
                        R.layout.detail_item, R.id.detail, Context.getInstance().activeRestaurant.getDetails());
        details.setAdapter(arrayAdapter);

        openMenu = findViewById(R.id.openMenu).findViewById(R.id.button);
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(RestaurantActivity.this, MenuActivity.class);
                startActivity(main);
            }
        });



    }
}