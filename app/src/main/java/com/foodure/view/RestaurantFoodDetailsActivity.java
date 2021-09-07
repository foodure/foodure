package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.foodure.R;

import java.util.Objects;

public class RestaurantFoodDetailsActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantFoodDetailsActivity";
    private TextView restaurantLabel ;
    private TextView foodNameLabel ;
    private TextView foodQuantityLabel ;
    private TextView locationLabel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_food_details);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = getIntent() ;
        String restaurant = intent.getExtras().getString("restaurantLabel");
        String foodName = intent.getExtras().getString("foodLabel");
        String foodQuantity = intent.getExtras().getString("quantityLabel");
        String location = intent.getExtras().getString("locationLabel");

        restaurantLabel = findViewById(R.id.restaurant_Label_details);
        foodNameLabel = findViewById(R.id.foodNameLabel);
        foodQuantityLabel = findViewById(R.id.foodQuantityLabel);
        locationLabel = findViewById(R.id.location_Label_details);

        restaurantLabel.setText(restaurant);
        foodNameLabel.setText(foodName);
        foodQuantityLabel.setText(foodQuantity);
        locationLabel.setText(location);

        Log.i(TAG, "onCreate:   --> " + restaurant);

    }
}