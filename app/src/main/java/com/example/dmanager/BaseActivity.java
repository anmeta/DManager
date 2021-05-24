package com.example.dmanager;

import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.entities.User;
import com.example.dmanager.helpers.Context;
import com.example.dmanager.helpers.StaticHelpers;
import com.google.android.gms.tasks.Continuation;
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
    protected void signUpRestaurant(Restaurant restaurant){
        try {
            if (mAuth == null) initializeFirebaseAuth();

            String composedEmail = restaurant.getRestaurantPhone().concat("@dmanager.com");
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
                                // Save restaurant data in firebase
                                addRestaurantToFirestore(restaurant);
                            }
                        }
                    });
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    protected Task signIn(String userNumber){
        try {
            if (mAuth == null) initializeFirebaseAuth();

            String composedEmail = userNumber.concat("@dmanager.com");
            return mAuth.signInWithEmailAndPassword(composedEmail, StaticHelpers.GetSecretPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // user has logged in
                                if (isValidAmka(userNumber)) {
                                    prepareUserContext(userNumber);
                                } else if (isValidPhoneNumber(userNumber)) {
                                    getRestaurantData(userNumber);
                                }
                                // redirect user to homepage based on the category (user, restorant)
                            } else {
                                Toast.makeText(BaseActivity.this, "We can't find a user with this AMKA!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private void getRestaurantData(String userNumber) {
        try {
            initializeFirestore();
            CollectionReference dbUsers = db.collection("Restaurants");
            // Create a query against the collection.
            dbUsers.whereEqualTo("restaurantPhone", userNumber)
                    .limit(1)
                    .get().addOnSuccessListener(documents -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            documents.forEach(document -> {
                                // read document data and create the user object (that we will save after login)
                                //PacientName, String PacientSurname,String PacientNumber, String LivingCity, Integer Age
                                //save this user in global Context

                                Context.getInstance().activeRestaurant = new Restaurant(
                                        document.get("restaurantName").toString(),
                                        document.get("restaurantPhone").toString(),
                                        document.get("restaurantCity").toString(),
                                        Integer.parseInt(document.get("restaurantStreetNr").toString()),
                                        document.get("restaurantStreet").toString()
                                );
                                //Redirect to RestaurantActivity
                                Intent main = new Intent(BaseActivity.this, RestaurantActivity.class);
                                startActivity(main);
                            });
                        }
                    }
            );

        }
        catch(Exception ex){
            Toast.makeText(BaseActivity.this, "We can't find the data for this restaurant! Please try again later!", Toast.LENGTH_SHORT).show();
        }
    }
    private void prepareUserContext(String amka) {
        try {
            getUserData(amka).continueWith(new Continuation() {
                @Override
                public Object then(Task task) throws Exception {
                    // Once the task is complete, unblock the test thread, so it can inspect for errors/results.
                    if (task != null && task.isSuccessful() && Context.getInstance().activeUser != null) {
                        // get restaurant and redirect to user activity
                        CollectionReference dbRestaurants = db.collection("Restaurants");
                        dbRestaurants.get().addOnSuccessListener(restaurants -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                restaurants.forEach(restaurant -> {
                                    Context.getInstance().addRestaurant(
                                            new Restaurant(
                                                    restaurant.get("restaurantName").toString(),
                                                    "",
                                                    restaurant.get("restaurantCity").toString(),
                                                    Integer.parseInt(restaurant.get("restaurantStreetNr").toString()),
                                                    restaurant.get("restaurantStreet").toString()
                                            )
                                    );
                                });
                            }
                        });
                        //Redirect to user activity
                        Intent main = new Intent(BaseActivity.this, UserActivity.class);
                        startActivity(main);
                    }
                    return null;
                }

            });
        }
        catch (Exception ex) {
            Toast.makeText(BaseActivity.this, "Something went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
        }

    }


    private Task getUserData(String amka) {
        try {
            initializeFirestore();
            CollectionReference dbUsers = db.collection("Users");
            // Create a query against the collection.
            return dbUsers.whereEqualTo("pacientNumber", amka)
                    .limit(1)
                    .get().addOnSuccessListener(documents -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            documents.forEach(document -> {
                                // read document data and create the user object (that we will save after login)
                                //PacientName, String PacientSurname,String PacientNumber, String LivingCity, Integer Age
                                //save this user in global Context

                                Context.getInstance().activeUser = new User(
                                        document.get("pacientName").toString(),
                                        document.get("pacientSurname").toString(),
                                        document.get("pacientNumber").toString(),
                                        document.get("city").toString(),
                                        Integer.parseInt(document.get("age").toString())
                                        );
                            });
                        }
                    }
            );

        }
        catch(Exception ex){
            Toast.makeText(BaseActivity.this, "We can't find the data for this user! Please try again later!", Toast.LENGTH_SHORT).show();
        }
        return null;
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

                    //Redirect to user activity
                    Intent main = new Intent(BaseActivity.this, UserActivity.class);
                    startActivity(main);
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

    private void addRestaurantToFirestore(Restaurant restaurant) {
        try {
            initializeFirestore();
            // creating a collection reference
            // for our Firebase Firetore database.
            CollectionReference dbResaturants = db.collection("Restaurants");

            // below method is use to add data to Firebase Firestore.
            dbResaturants.add(restaurant).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // after the data addition is successful
                    // we are displaying a success toast message.
                    Toast.makeText(BaseActivity.this, "Your restaurant account has been created!", Toast.LENGTH_SHORT).show();

                    //Redirect to restaurant activity
                    Intent main = new Intent(BaseActivity.this, RestaurantActivity.class);
                    startActivity(main);
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
    /*Methods used for validation*/
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
    protected boolean isValidAmka(String str){
        return str!= null && !str.isEmpty() && hasOnlyDigits(str) && str.length()==11;
    }
    protected boolean isValidPhoneNumber(String str){
        return (str!=null && !str.isEmpty() && hasOnlyDigits(str) && str.length()==10);
    }
}
