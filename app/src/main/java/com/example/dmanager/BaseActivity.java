package com.example.dmanager;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dmanager.helpers.StaticHelpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;
    protected void initializeFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }
    protected void signUp(User user){
        try {
            if (mAuth == null) initializeFirebaseAuth();

            String composedEmail = user.PacientNumber.concat("@dmanager.com");
            mAuth.createUserWithEmailAndPassword(composedEmail, StaticHelpers.GetSecretPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                //Redirect to UserSignUp
                                Intent userSignUp = new Intent(BaseActivity.this, UserSignUp.class);
                                userSignUp.putExtra("ERROR_MSG", StaticHelpers.GetSignUpErrorMsg());
                                startActivity(userSignUp);
                            }
                            else{
                                // Save user data in firebase
                                addUserToFirestore(user);
                            }
                        }
                    });
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    protected void signIn(String amka){
        try {
            if (mAuth == null) initializeFirebaseAuth();

            String composedEmail = amka.concat("@dmanager.com");
            mAuth.signInWithEmailAndPassword(composedEmail, StaticHelpers.GetSecretPassword())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // user has logged in
                        // get data for user
                        // redirect user to homepage based on the category (user, restorant)
                    }
                    else{
                        //Redirect to UserLogin
                    }
                }
            });

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    protected boolean hasOnlyDigits(String str){
        // Regex to check string
        // contains only digits
        String regex = "[0-9]+";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }
    protected void initializeFirestore(){
        if(db==null){
            db = FirebaseFirestore.getInstance();
        }
    }
    private void addUserToFirestore(User user) {
        try {
            initializeFirestore();
            // creating a collection reference
            // for our Firebase Firetore database.
            CollectionReference dbUsers = db.collection("Users");

            // below method is use to add data to Firebase Firestore.
            dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // after the data addition is successful
                    // we are displaying a success toast message.
                    Toast.makeText(BaseActivity.this, "Your account has been created!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // this method is called when the data addition process is failed.
                    // displaying a toast message when data addition is failed.
                    Toast.makeText(BaseActivity.this, "Ups something wrong happened! Please try again!" + e, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(BaseActivity.this, "Soemthing wrong happened!", Toast.LENGTH_SHORT).show();
        }
    }
}
