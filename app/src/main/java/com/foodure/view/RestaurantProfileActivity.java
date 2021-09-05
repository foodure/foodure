package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.foodure.R;

import java.util.Objects;

public class RestaurantProfileActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_profile);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_profile);

    back.setOnClickListener(v -> back());
  }


  public void back() {
    Intent goToMain = new Intent(RestaurantProfileActivity.this, MainActivity.class);
    startActivity(goToMain);
  }
}
