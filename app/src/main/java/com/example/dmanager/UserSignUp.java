package com.example.dmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UserSignUp extends AppCompatActivity {
    MaterialEditText PacientNumber, PacientName, PacientSurname, Age, LivingCity;
    Button btnUser;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        PacientNumber = (MaterialEditText)findViewById(R.id.PacientNumber);
        PacientName = (MaterialEditText)findViewById(R.id.PacientName);
        PacientSurname = (MaterialEditText)findViewById(R.id.PacientSurname);
        Age = (MaterialEditText)findViewById(R.id.Age);
        LivingCity = (MaterialEditText)findViewById(R.id.LivingCity);
        btnUser = (Button)findViewById(R.id.btnUser);

        //Init Firebase
         table_user = FirebaseDatabase.getInstance().getReference().child("User");
        btnUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    insertUserData();
                    Intent HomeUser = new Intent(UserSignUp.this, userHome.class);
                    startActivity(HomeUser);
            }
        });
    }
    private void insertUserData() {
       
        int pacientNumber = Integer.parseInt(PacientNumber.getText().toString());
        String pacientName = PacientName.getText().toString();
        String pacientSurname = PacientSurname.getText().toString();
        String city = LivingCity.getText().toString();
        int age = Integer.parseInt(Age.getText().toString());
        User user = new User(pacientName, pacientSurname, pacientNumber, city, age);

//        if (table_user.child(String.valueOf(pacientNumber)).exists()){
//        table_user.push().setValue(user);
//        Toast.makeText(UserSignUp.this, "User Signed Up Successfully!", Toast.LENGTH_LONG).show();}
//    }
    
//    private void UserSignUp(int pacientNumber, String pacientName, String pacientSurname, String city, int age){
//        ProgressDialog mDialog = new ProgressDialog(UserSignUp.this);
//        mDialog.setMessage("Please wait..");
//        mDialog.show();
//        
//        String Name = pacientName;
//        int number = pacientNumber;
//        String Surname = pacientSurname;
//        String City= city;
//        int Age = age;
//        
//        table_user.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = 
//                
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        
//        
//    }
}}