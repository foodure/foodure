package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.foodure.R;

import java.util.Objects;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView usernameBtn = findViewById(R.id.username_restaurantPage);
        ImageView usernameImg = findViewById(R.id.profile_restaurantPage);

        TextView settingsBtn = findViewById(R.id.settings_restaurantPage);
        ImageView settingsImg = findViewById(R.id.settingsImg_restaurantPage);

        ImageView logout = findViewById(R.id.logout_restaurantPage);

        Button addFoodBtn = findViewById(R.id.addFood_restaurantPage);

        usernameBtn.setOnClickListener(v -> goToProfile());

        usernameImg.setOnClickListener(v -> goToProfile());

        settingsBtn.setOnClickListener(v -> goToSettings());

        settingsImg.setOnClickListener(v -> goToSettings());

        logout.setOnClickListener(v -> logout());

        addFoodBtn.setOnClickListener(v -> gotoAddFood());
    }

    public void goToProfile(){
        Intent goToProfile = new Intent(RestaurantActivity.this, RestaurantProfileActivity.class);
        startActivity(goToProfile);
    }

    public void goToSettings(){
        Intent goToSettings = new Intent(RestaurantActivity.this, SettingsActivity.class);
        startActivity(goToSettings);
    }

    public void gotoAddFood(){
        Intent goToSettings = new Intent(RestaurantActivity.this, RestaurantAddFoodActivity.class);
        startActivity(goToSettings);
    }

    public void logout() {
        Amplify.Auth.signOut(
            () -> {
                Log.i("AuthQuickstart", "Signed out successfully");
                Intent goToLogin = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(goToLogin);
            },
            error -> Log.e("AuthQuickstart", error.toString())
        );
    }
}
