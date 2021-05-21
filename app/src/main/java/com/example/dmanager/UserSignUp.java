package com.example.dmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmanager.entities.User;
import com.example.dmanager.helpers.StaticHelpers;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UserSignUp extends BaseActivity {
    MaterialEditText PacientNumber, PacientName, PacientSurname, Age, LivingCity;
    Button btnUser;
    TextView errorField;


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
        errorField = (TextView)findViewById(R.id.textView);

        //Init Firebase
        initializeFirebaseAuth();

        //Write error in the screen
        String error = getIntent().getStringExtra("ERROR_MSG");
        if(error!=null && !error.isEmpty())errorField.setText(error);
        btnUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    insertUserData();
            }
        });
    }


    private void insertUserData() {
       try {
           String pacientNumber = PacientNumber.getText().toString();
           String pacientName = PacientName.getText().toString();
           String pacientSurname = PacientSurname.getText().toString();
           String city = LivingCity.getText().toString();
           int age = Integer.parseInt(Age.getText().toString());
           if(!isValidAmka(pacientNumber)){
               Intent userSignUp = new Intent(UserSignUp.this, UserSignUp.class);
               userSignUp.putExtra("ERROR_MSG", StaticHelpers.GetInvalidPatientNumberErrorMsg());
               startActivity(userSignUp);
               return;
           }
           User user = new User(pacientName, pacientSurname, pacientNumber, city, age);
           signUp(user);
       }
       catch(Exception ex){
           System.out.println(ex.getMessage());
       }

}}