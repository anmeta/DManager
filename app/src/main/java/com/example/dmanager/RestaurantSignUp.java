package com.example.dmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.dmanager.entities.Restaurant;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RestaurantSignUp extends BaseActivity {
    MaterialEditText RestaurantName, RestaurantCity,RestaurantStreet , RestaurantStNumber,RestaurantPhone;
    Button btnSignUpRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_sing_up);
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
            String street = RestaurantStreet.getText().toString();
            int streetNr = Integer.parseInt(RestaurantStNumber.getText().toString());
            signUpRestaurant(new Restaurant(name, phone, city, streetNr, street));
        }
    }
