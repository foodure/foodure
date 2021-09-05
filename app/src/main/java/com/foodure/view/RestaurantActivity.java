package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.foodure.R;

import java.util.Objects;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant);
      Objects.requireNonNull(getSupportActionBar()).hide();
    }
}
