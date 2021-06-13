package com.example.dmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmanager.entities.MenuItem;
import com.example.dmanager.entities.User;
import com.example.dmanager.helpers.StaticHelpers;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddMenuItemActivity extends BaseActivity {
    MaterialEditText ItemName, ItemDescription, ItemIngredients, ItemPrice;
    Button btnAddItem;
    TextView errorField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);
        ItemName = (MaterialEditText)findViewById(R.id.ItemName);
        ItemDescription = (MaterialEditText)findViewById(R.id.ItemDescription);
        ItemIngredients = (MaterialEditText)findViewById(R.id.ItemIngredients);
        ItemPrice = (MaterialEditText)findViewById(R.id.ItemPrice);
        btnAddItem = (Button)findViewById(R.id.btnAddItem);

        //Write error in the screen
        String error = getIntent().getStringExtra("ERROR_MSG");
        if(error!=null && !error.isEmpty())
            Toast.makeText(AddMenuItemActivity.this, error, Toast.LENGTH_SHORT).show();
        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addMenuItem();
            }
        });
    }


    private void addMenuItem() {
       try {
           //add menu item here
           MenuItem item = new MenuItem(
                   ItemName.getText().toString(),
                   ItemDescription.getText().toString(),
                   ItemIngredients.getText().toString(),
                   ItemPrice.getText().toString());

           addMenuItemForRestaurant(item).wait();
       }
       catch(Exception ex){
           System.out.println(ex.getMessage());
       }

}}