package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.foodure.R;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class RestaurantFoodDetailsActivity extends AppCompatActivity {
  private static final String TAG = "RestaurantFoodDetailsActivity";

  private URL url = null;
  private Handler handler;

  private TextView restaurantLabel;
  private TextView foodNameLabel;
  private TextView foodQuantityLabel;
  private TextView locationLabel;
  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_food_details);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_detailspage);

    Intent intent = getIntent();

    String restaurant = intent.getExtras().getString("restaurantLabel");
    String foodName = intent.getExtras().getString("foodLabel");
    String foodQuantity = intent.getExtras().getString("quantityLabel");
    String location = intent.getExtras().getString("locationLabel");
    String fileName = intent.getExtras().getString("fileNameLabel");

    getFileFromS3Storage(fileName);

    restaurantLabel = findViewById(R.id.restaurant_Label_details);
    foodNameLabel = findViewById(R.id.foodNameLabel);
    foodQuantityLabel = findViewById(R.id.foodQuantityLabel);
    locationLabel = findViewById(R.id.location_Label_details);

    imageView = findViewById(R.id.foodDetailImg);

    restaurantLabel.setText(restaurant);
    foodNameLabel.setText(foodName);
    foodQuantityLabel.setText(foodQuantity);
    locationLabel.setText(location);

    handler = new Handler(Looper.getMainLooper(),
        message -> {
          Glide.with(getBaseContext())
              .load(url.toString())
              .placeholder(R.drawable.ic_pictures)
              .error(R.drawable.ic_pictures)
              .centerCrop()
              .into(imageView);
          Log.i(TAG, "Glide URL: " + url);
          return false;
        });

    back.setOnClickListener(v -> back());

  }

  public void back() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  private void getFileFromS3Storage(String key) {
    Amplify.Storage.getUrl(
        key,
        result -> {
          Log.i(TAG, "Successfully generated: " + result.getUrl());
          url = result.getUrl();
          handler.sendEmptyMessage(1);
        },
        error -> Log.e(TAG, "URL generation failure", error)
    );
  }
}