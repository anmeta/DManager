package com.example.dmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.example.dmanager.helpers.Context;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuActivity extends BaseActivity {
    LinearLayout layout;
    ListView details;
    Button addItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Context.getInstance().activeRestaurant.getFullName());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context.getInstance().cleanContext();
                Intent main = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(main);
            }
        });

        layout = (LinearLayout) findViewById(R.id.linearLayout);
        details = (ListView)findViewById(R.id.details);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,
                        R.layout.detail_item, R.id.detail, Context.getInstance().activeRestaurant.getMenuItems());
        details.setAdapter(arrayAdapter);
        details.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context.getInstance().activeMenuItem = Context.getInstance().activeRestaurant.MenuItems.get(position);
                Intent menuItem = new Intent(MenuActivity.this, MenuItemActivity.class);
                startActivity(menuItem);
            }
        });

        addItem = findViewById(R.id.addItem).findViewById(R.id.button);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMenuItemActivity = new Intent(MenuActivity.this, AddMenuItemActivity.class);
                startActivity(addMenuItemActivity);
            }
        });



    }
}