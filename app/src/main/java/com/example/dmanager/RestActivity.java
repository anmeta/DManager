package com.example.dmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RestActivity extends AppCompatActivity {
    MaterialEditText RestaurantName, RestaurantCity, RestaurantPhone, RestaurantStNumber;
    Spinner RestaurantStreet;
    Button btnRest;
    DatabaseReference rest_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
            RestaurantName = (MaterialEditText)findViewById(R.id.RestaurantName);
            RestaurantCity = (MaterialEditText)findViewById(R.id.RestaurantCity);
            RestaurantPhone= (MaterialEditText)findViewById(R.id.RestaurantPhone);
            RestaurantStreet=findViewById(R.id.RestaurantStreet);
            RestaurantStNumber= (MaterialEditText)findViewById(R.id.RestaurantStNumber);
            btnRest = (Button)findViewById(R.id.btnRest);

            //Init Firebase
            rest_table = FirebaseDatabase.getInstance().getReference().child("Restaurants");

            btnRest.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    insertRestData();
                    Intent RestHome = new Intent(RestActivity.this, restHome.class);
                    startActivity(RestHome);
                }
            });
        }
        private void insertRestData() {
            String name = RestaurantName.getText().toString();
            String city = RestaurantCity.getText().toString();
            int phone = Integer.parseInt(RestaurantPhone.getText().toString());
            String street = RestaurantStreet.getSelectedItem().toString();
            int streetNr = Integer.parseInt(RestaurantStNumber.getText().toString());
            Restaurants restaurants= new Restaurants();
            rest_table.push().setValue(restaurants);
            Toast.makeText(RestActivity.this, "Restaurant Signed Up Successfully!", Toast.LENGTH_LONG).show();
        }
    }
