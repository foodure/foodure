package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.foodure.R;

public class RestaurantFoodDetailsActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantFoodDetailsActivity";
    private TextView restaurantLabel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_food_details);

        Intent intent = getIntent() ;
        String restaurant = intent.getExtras().getString("restaurantLabel");

        restaurantLabel = findViewById(R.id.restaurantLabel);
        restaurantLabel.setText(restaurant);

        Log.i(TAG, "onCreate:   --> " + restaurant);

    }
}