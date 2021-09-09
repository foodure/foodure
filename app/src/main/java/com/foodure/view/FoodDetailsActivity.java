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

import java.net.URL;
import java.util.Objects;

public class FoodDetailsActivity extends AppCompatActivity {

    static final String TAG = "FoodDetailsActivity";
    URL url = null;
    Handler handler;

    TextView restaurantLabel;
    TextView foodNameLabel;
    TextView foodQuantityLabel;
    TextView locationLabel;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageView back = findViewById(R.id.back_foodDetailsPage);

        Intent intent = getIntent();

        String restaurant = intent.getExtras().getString(MainActivity.RESTAURANTLABEL);
        String foodName = intent.getExtras().getString(MainActivity.FOODLABEL);
        String foodQuantity = intent.getExtras().getString(MainActivity.QUANTITYLABEL);
        String location = intent.getExtras().getString(MainActivity.LOCATIONLABEL);
        String fileName = intent.getExtras().getString(MainActivity.FILENAMELABEL);

        getFileFromS3Storage(fileName);

        restaurantLabel = findViewById(R.id.foodNameLabel_foodDetailsPage);
        foodNameLabel = findViewById(R.id.description_foodDetailsPage);
        foodQuantityLabel = findViewById(R.id.quantity_foodDetailsPage);
        locationLabel = findViewById(R.id.location_foodDetailsPage);

        imageView = findViewById(R.id.img_foodDetailsPage);

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

    void getFileFromS3Storage(String key) {
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