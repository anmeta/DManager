package com.example.dmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmanager.entities.MenuItem;
import com.example.dmanager.helpers.Context;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MenuItemActivity extends BaseActivity {
    TextView ItemName, ItemDescription, ItemIngredients, ItemPrice;
    Button btnRemove;
    TextView errorField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        ItemName = (TextView) findViewById(R.id.ItemName);
        ItemName.setText(Context.getInstance().activeMenuItem.ItemName);

        ItemDescription = (TextView)findViewById(R.id.ItemDescription);
        ItemDescription.setText(Context.getInstance().activeMenuItem.ItemDescription);

        ItemIngredients = (TextView)findViewById(R.id.ItemIngredients);
        ItemIngredients.setText("Ingredients     " + Context.getInstance().activeMenuItem.ItemIngredients);

        ItemPrice = (TextView)findViewById(R.id.ItemPrice);
        ItemPrice.setText("Price           " + Context.getInstance().activeMenuItem.ItemPrice);

        btnRemove = (Button)findViewById(R.id.btnRemove);

        //Write error in the screen
        String error = getIntent().getStringExtra("ERROR_MSG");
        if(error!=null && !error.isEmpty())
            Toast.makeText(MenuItemActivity.this, error, Toast.LENGTH_SHORT).show();


        btnRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    removeActiveMenuItem(Context.getInstance().activeMenuItem, Context.getInstance().activeRestaurant.Email);
                } catch (Exception e) {
                    Toast.makeText(MenuItemActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}