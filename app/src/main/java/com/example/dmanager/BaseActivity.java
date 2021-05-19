package com.example.dmanager;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dmanager.helpers.StaticHelpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;

    protected void initializeFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }
    protected void signUp(User user){
        try {
            if (mAuth == null) initializeFirebaseAuth();

            String composedEmail = user.PacientName.concat(user.PacientSurname).concat("@gmail.com");
            mAuth.createUserWithEmailAndPassword(composedEmail, StaticHelpers.GetSecretPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                //Redirect to UserSignUp
                                Intent userSignUp = new Intent(BaseActivity.this, UserSignUp.class);
                                userSignUp.putExtra("ERROR_MSG", StaticHelpers.GetSignUpErrorMsg());
                                startActivity(userSignUp);
                            }
                        }
                    });
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
