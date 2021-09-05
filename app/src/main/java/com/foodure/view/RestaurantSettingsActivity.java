package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.foodure.R;

import java.util.Objects;

public class RestaurantSettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_settings);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_restaurantSettingsPage);

    back.setOnClickListener(v -> back());
  }

  public void back(){
    Intent goToMain = new Intent(RestaurantSettingsActivity.this, RestaurantActivity.class);
    startActivity(goToMain);
  }

}