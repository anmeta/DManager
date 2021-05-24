package com.example.dmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {
    Button btnUser, btnRest, loginButton;
    EditText userNumber;//it could be Amka or restaurant phone number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFirebaseAuth();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        btnUser = (Button)findViewById(R.id.btnUser);
        btnRest = (Button)findViewById(R.id.btnRest);
        userNumber = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);

        loginButton.setText("Sign in");
        loginButton.setEnabled(true);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpUser = new Intent(MainActivity.this, UserSignUp.class);
                startActivity(SignUpUser);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = userNumber.getText().toString();
                if(isValidAmka(number) || isValidPhoneNumber(number)) {
                    loginButton.setText("Signing in ...");
                    loginButton.setEnabled(false);
                    signIn(userNumber.getText().toString()).continueWith(new Continuation() {
                        @Override
                        public Object then(Task task) throws Exception {
                         if(task==null){
                             loginButton.setText("Sign in");
                             loginButton.setEnabled(true);
                         }
                         return null;
                        }
                        });
                }
                else{
                    Toast.makeText(MainActivity.this, "Please write a valid number!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpRest = new Intent(MainActivity.this, RestaurantSignUp.class);
                startActivity(SignUpRest
                );
            }
        });
    }



}