package com.example.dmanager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.dmanager.helpers.Context;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuItemAnalyzeActivity extends BaseActivity {
    TextView ItemName, ItemDescription, ItemIngredients, ItemPrice;
    ImageView imageView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_analyze);

        ItemName = (TextView) findViewById(R.id.ItemName);
        ItemName.setText(Context.getInstance().activeMenuItem.ItemName);

        ItemDescription = (TextView)findViewById(R.id.ItemDescription);
        ItemDescription.setText(Context.getInstance().activeMenuItem.ItemDescription);

        ItemIngredients = (TextView)findViewById(R.id.ItemIngredients);
        ItemIngredients.setText("Ingredients     " + Context.getInstance().activeMenuItem.ItemIngredients);

        ItemPrice = (TextView)findViewById(R.id.ItemPrice);
        ItemPrice.setText("Price           " + Context.getInstance().activeMenuItem.ItemPrice);

        imageView = (ImageView)findViewById(R.id.result);

        //Analyze forbidden ingredients
        if(Context.getInstance().activeUser.ForbiddenIngredients!=null){
            ArrayList<String> forbiddenIngredients = new ArrayList<String>(
                    Arrays.asList(Context.getInstance().activeUser.ForbiddenIngredients.toLowerCase().replaceAll(" ", "")
                            .split(",")));
            ArrayList<String> currentItemIngredients = new ArrayList<String>(
                    Arrays.asList(Context.getInstance().activeMenuItem.ItemIngredients.toLowerCase().replaceAll(" ", "")
                            .split(",")));

            Integer totalCount = currentItemIngredients.size();
            Integer notAllowedCount = 0;
            for (String ingredient:currentItemIngredients) {
                if(forbiddenIngredients.contains(ingredient)){
                    notAllowedCount++;
                }
            }

            int notAllowedPercentage = (notAllowedCount*100)/totalCount;

            if(notAllowedPercentage<10){
                //Green
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.green));
            }
            else if(notAllowedPercentage<40){
                //Orange
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.orange));
            }
            else if(notAllowedPercentage<=100){
                //Red
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.red));
            }
        }


        //Write error in the screen
        String error = getIntent().getStringExtra("ERROR_MSG");
        if(error!=null && !error.isEmpty())
            Toast.makeText(MenuItemAnalyzeActivity.this, error, Toast.LENGTH_SHORT).show();


    }

}