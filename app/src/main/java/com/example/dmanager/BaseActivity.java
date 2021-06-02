package com.example.dmanager;

import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.entities.User;
import com.example.dmanager.entities.UserRole;
import com.example.dmanager.helpers.Context;
import com.example.dmanager.helpers.Roles;
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

import org.jetbrains.annotations.Nullable;

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

            mAuth.createUserWithEmailAndPassword(user.Email, user.Password)
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
            Intent userSignUp = new Intent(BaseActivity.this, UserSignUp.class);
            userSignUp.putExtra("ERROR_MSG", StaticHelpers.GetSignUpErrorMsg());
            startActivity(userSignUp);
        }
    }
    protected void signUpRestaurant(Restaurant restaurant){
        try {
            if (mAuth == null) initializeFirebaseAuth();

            mAuth.createUserWithEmailAndPassword(restaurant.Email, restaurant.Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                //Redirect to RestaurantSignUp
                                Intent userSignUp = new Intent(BaseActivity.this, RestaurantSignUp.class);
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


    protected Task signIn(String email, String password){
            if (mAuth == null) initializeFirebaseAuth();

            return mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                try {
                                prepareContext(email, password).wait();
                                }
                                catch(Exception ex) {
                                    System.out.println("Something went wrong. Try again later!");
                                }
                            } else {
                                Toast.makeText(BaseActivity.this, "We can't find a user with this email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
    @Nullable
    private Task prepareContext(String email, String password) {
        try {
            initializeFirestore();
            CollectionReference dbUserRoles = db.collection("UserRoles");
            // Create a query against the collection.
            return dbUserRoles.whereEqualTo("Email", email)
                    .limit(1)
                    .get()
                    .addOnSuccessListener(documents -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    documents.forEach(document -> {
                                        if(document.get("Role").equals(Roles.User.toString())){
                                            prepareUserContext(email);
                                        }
                                        else{
                                            prepareRestaurantContext(email);
                                        }
                                    });
                                }
                            }
                    );
        }
        catch (Exception ex){
            Toast.makeText(BaseActivity.this, "Something went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void prepareRestaurantContext(String email) {
            initializeFirestore();
            CollectionReference dbUsers = db.collection("Restaurants");
            // Create a query against the collection.
            dbUsers.whereEqualTo("Email", email)
                    .limit(1)
                    .get().addOnSuccessListener(documents -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            documents.forEach(document -> {
                                // read document data and create the user object (that we will save after login)
                                //PacientName, String PacientSurname,String PacientNumber, String LivingCity, Integer Age
                                //save this user in global Context

                                Context.getInstance().activeRestaurant = new Restaurant(
                                        document.get("RestaurantName").toString(),
                                        document.get("RestaurantPhone").toString(),
                                        document.get("RestaurantCity").toString(),
                                        Integer.parseInt(document.get("RestaurantStreetNr").toString()),
                                        document.get("RestaurantStreet").toString(),
                                        document.get("Email").toString(),
                                        document.get("Password").toString()
                                );
                                //Redirect to RestaurantActivity
                                Intent main = new Intent(BaseActivity.this, RestaurantActivity.class);
                                startActivity(main);
                            });
                        }
                    }
            );
    }
    private void prepareUserContext(String email) {
            getUserData(email).continueWith(new Continuation() {
                @Override
                public Object then(Task task) throws Exception {
                    // Once the task is complete, unblock the test thread, so it can inspect for errors/results.
                    if (task != null && task.isSuccessful() && Context.getInstance().activeUser != null) {
                        // get restaurant and redirect to user activity
                        CollectionReference dbRestaurants = db.collection("Restaurants");
                        return dbRestaurants.get().addOnSuccessListener(restaurants -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                restaurants.forEach(restaurant -> {
                                    Context.getInstance().addRestaurant(
                                            new Restaurant(
                                                    restaurant.get("RestaurantName").toString(),
                                                    "",
                                                    restaurant.get("RestaurantCity").toString(),
                                                    Integer.parseInt(restaurant.get("RestaurantStreetNr").toString()),
                                                    restaurant.get("RestaurantStreet").toString()
                                            )
                                    );
                                });

                                //Redirect to user activity
                                Intent main = new Intent(BaseActivity.this, UserActivity.class);
                                startActivity(main);
                            }
                        });
                    }
                    else return null;
                }

            });
    }
    private Task getUserData(String email) {
        try {
            initializeFirestore();
            CollectionReference dbUsers = db.collection("Users");
            // Create a query against the collection.
            return dbUsers.whereEqualTo("Email", email)
                    .limit(1)
                    .get()
                    .addOnSuccessListener(documents -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            documents.forEach(document -> {
                                // read document data and create the user object (that we will save after login)
                                //PacientName, String PacientSurname,String PacientNumber, String LivingCity, Integer Age
                                //save this user in global Context

                                Context.getInstance().activeUser = new User(
                                        document.get("PacientName").toString(),
                                        document.get("PacientSurname").toString(),
                                        document.get("PacientNumber").toString(),
                                        document.get("LivingCity").toString(),
                                        Integer.parseInt(document.get("Age").toString()),
                                        document.get("Email").toString(),
                                        document.get("Password").toString()
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
            CollectionReference dbUserRoles = db.collection("UserRoles");

            // below method is use to add data to Firebase Firestore.
            dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // after the data addition is successful
                    // we are displaying a success toast message.
                    Toast.makeText(BaseActivity.this, "Your account has been created!", Toast.LENGTH_SHORT).show();
                    dbUserRoles.add(new UserRole(user.Email, Roles.User));

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
            // creating a collection reference for our Firebase Firetore database.
            CollectionReference dbResaturants = db.collection("Restaurants");
            CollectionReference dbUserRoles = db.collection("UserRoles");

            // below method is use to add data to Firebase Firestore.
            dbResaturants.add(restaurant).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // after the data addition is successful
                    // we are displaying a success toast message.
                    Toast.makeText(BaseActivity.this, "Your restaurant account has been created!", Toast.LENGTH_SHORT).show();
                    dbUserRoles.add(new UserRole(restaurant.Email, Roles.Restaurant));

                    //Redirect to restaurant activity
                    Intent main = new Intent(BaseActivity.this, LoginActivity.class);
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
    protected boolean isValidAmka(String amka){
        if (!hasOnlyDigits(amka) || amka.length()!=11 || amka == "00000000000")
        return false;

        Integer iSum = 0;
        for (Integer i = 1; i <= amka.length(); i++) {
            Integer iDigit = Integer.parseInt(String.valueOf(amka.charAt(i - 1)), 10);
            if (i % 2 == 0) {
                iDigit *= 2;
                if (iDigit > 9) {
                    iDigit -= 9;
                }
            }
            iSum += iDigit;
        }
        return (iSum % 10 == 0);
    }
    protected boolean isValidPhoneNumber(String str){
        return (str!=null && !str.isEmpty() && hasOnlyDigits(str) && str.length()==10);
    }
}
