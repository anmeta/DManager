package com.example.dmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dmanager.entities.Restaurant;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RestaurantSignUp extends BaseActivity {
    MaterialEditText RestaurantName, RestaurantCity, RestaurantPhone, RestaurantStNumber;
    Spinner RestaurantStreet;
    Button btnSignUpRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
            RestaurantName = (MaterialEditText)findViewById(R.id.RestaurantName);
            RestaurantCity = (MaterialEditText)findViewById(R.id.RestaurantCity);
            RestaurantPhone= (MaterialEditText)findViewById(R.id.RestaurantPhone);
            RestaurantStreet=findViewById(R.id.RestaurantStreet);
            RestaurantStNumber= (MaterialEditText)findViewById(R.id.RestaurantStNumber);
            btnSignUpRestaurant = (Button)findViewById(R.id.btnRest);

            btnSignUpRestaurant.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    insertRestaurantData();
                }
            });
        }
        private void insertRestaurantData() {
            String name = RestaurantName.getText().toString();
            String city = RestaurantCity.getText().toString();
            String phone = RestaurantPhone.getText().toString();
            String street = RestaurantStreet.getSelectedItem().toString();
            int streetNr = Integer.parseInt(RestaurantStNumber.getText().toString());
            signUpRestaurant(new Restaurant(name, phone, city, streetNr, street));
        }
    }
