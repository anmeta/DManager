package com.example.dmanager;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dmanager.entities.MenuItem;
import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.entities.User;
import com.example.dmanager.entities.UserRestaurant;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
@author: Anna Maria Meta
@description: Base activity will contain the shared functionality among all activities in relation to Firestore database.
 */

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;

    protected void initializeFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }
    protected void initializeFirestore(){
        if(db==null){
            db = FirebaseFirestore.getInstance();
        }
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


    /*Methods used for user sign in and context preparation*/
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
    private void prepareRestaurantContext(String email) {
            initializeFirestore();
            CollectionReference dbRestaurants = db.collection("Restaurants");
            // Create a query against the collection.
            dbRestaurants.whereEqualTo("Email", email)
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


                                getMenuItemsForRestuarant(document);
                                //Redirect to RestaurantActivity
                                Intent main = new Intent(BaseActivity.this, RestaurantActivity.class);
                                startActivity(main);
                            });
                        }
                    }
            );
    }
    private Task getMenuItemsForRestuarant(QueryDocumentSnapshot restaurantDocument){
        return restaurantDocument.getReference().collection("Menu").get().addOnSuccessListener(items -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Context.getInstance().activeRestaurant.MenuItems = new ArrayList<MenuItem>();
                items.forEach(item -> {
                    MenuItem menuItem = new MenuItem();
                    menuItem.ItemPrice = item.get("ItemPrice").toString();
                    menuItem.ItemDescription = item.get("ItemDescription").toString();
                    menuItem.ItemIngredients = item.get("ItemIngredients").toString();
                    menuItem.ItemName = item.get("ItemName").toString();

                    Context.getInstance().activeRestaurant.MenuItems.add(menuItem);
                });
            }
        });
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
                                            Restaurant newRestaurant = new Restaurant(
                                                    restaurant.get("RestaurantName").toString(),
                                                    "",
                                                    restaurant.get("RestaurantCity").toString(),
                                                    Integer.parseInt(restaurant.get("RestaurantStreetNr").toString()),
                                                    restaurant.get("RestaurantStreet").toString(),
                                                    restaurant.get("Email").toString()
                                            );
                                            try {
                                                restaurant.getReference().collection("Menu")
                                                        .get()
                                                        .addOnSuccessListener(items -> {
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                                newRestaurant.MenuItems = new ArrayList<MenuItem>();
                                                                items.forEach(item -> {
                                                                    MenuItem menuItem = new MenuItem();
                                                                    menuItem.ItemPrice = item.get("ItemPrice").toString();
                                                                    menuItem.ItemDescription = item.get("ItemDescription").toString();
                                                                    menuItem.ItemIngredients = item.get("ItemIngredients").toString();
                                                                    menuItem.ItemName = item.get("ItemName").toString();

                                                                    newRestaurant.MenuItems.add(menuItem);
                                                                });
                                                                Context.getInstance().addRestaurant(newRestaurant);
                                                                if (Context.getInstance().userRestaurantEmails.contains(newRestaurant.Email)) {
                                                                    Context.getInstance().lastViewedRestaurants.add(newRestaurant);
                                                                }
                                                            }
                                                        });
                                            } catch (Exception ex) {

                                            }
                                        });

                                    //Redirect to user activity
                                    Intent main = new Intent(BaseActivity.this, UserActivity.class);
                                    startActivity(main);
                                };
                            });
                    } else return null;
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
                                        if(document.get("ForbiddenIngredients")!=null){
                                            Context.getInstance().activeUser.ForbiddenIngredients = document.get("ForbiddenIngredients").toString();
                                        }

                                        CollectionReference userRestaurants = db.collection("UserRestaurant");
                                        userRestaurants.whereEqualTo("UserEmail", Context.getInstance().activeUser.Email)
                                                .get()
                                                .addOnSuccessListener(restaurantEmails -> {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                        restaurantEmails.forEach(restaurant -> {
                                                            Context.getInstance().userRestaurantEmails.add(restaurant.get("RestaurantEmail").toString());
                                                        });
                                                    }
                                                });
                                    }
                            );
                        }
                    });

        }
        catch(Exception ex){
            Toast.makeText(BaseActivity.this, "We can't find the data for this user! Please try again later!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    /*Methods used for Crud of entities*/
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
    public Task addMenuItemForRestaurant(MenuItem item){
        try {
            initializeFirestore();
            CollectionReference dbRestaurants = db.collection("Restaurants");
            return dbRestaurants.whereEqualTo("Email", Context.getInstance().activeRestaurant.Email)
                    .limit(1)
                    .get().addOnSuccessListener(documents -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            documents.forEach(document -> {
                                CollectionReference dmMenuItems = document.getReference().collection("Menu");
                                dmMenuItems.add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(BaseActivity.this, "Your item has been added to the menu!", Toast.LENGTH_SHORT).show();

                                        getMenuItemsForRestuarant(document);
                                        Intent menu = new Intent(BaseActivity.this, MenuActivity.class);
                                        startActivity(menu);
                                    }
                                });
                            });
                        }
                    }
                );
        }
        catch(Exception ex){
            //here we should have logging in production but for the purpose of this project we can just alert users
            Toast.makeText(BaseActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public void removeActiveMenuItem(MenuItem menuItem, String restaurantEmail){
        try {
            initializeFirestore();
            CollectionReference dbRestaurants = db.collection("Restaurants");
            dbRestaurants.whereEqualTo("Email", restaurantEmail)
                    .limit(1)
                    .get().addOnSuccessListener(documents -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    documents.forEach(document -> {
                                        CollectionReference dmMenuItems = document.getReference().collection("Menu");
                                        dmMenuItems.whereEqualTo("ItemName", menuItem.ItemName)
                                                .get()
                                                .addOnSuccessListener(items -> {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                        items.forEach(item -> {
                                                            item.getReference().delete();
                                                        });
                                                    }

                                                    //also deleted it from context
                                                    Context.getInstance().activeRestaurant.MenuItems.remove(menuItem);

                                                    Intent menu = new Intent(BaseActivity.this, MenuActivity.class);
                                                    startActivity(menu);
                                                });

                                    });
                                }
                            }
                    );
        }
        catch(Exception ex) {
            //here we should have logging in production but for the purpose of this project we can just alert users
            Toast.makeText(BaseActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
    public void addUserFavoriteRestaurant(UserRestaurant userRestaurant){
        try {
            if(!Context.getInstance().userRestaurantEmails.contains(userRestaurant.RestaurantEmail)) {
                initializeFirestore();
                CollectionReference dbUserRestaurants = db.collection("UserRestaurant");
                dbUserRestaurants.add(userRestaurant);
            }
        }
        catch(Exception ex){
            //here we should have logging in production but for the purpose of this project we can just alert users
            Toast.makeText(BaseActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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
}
