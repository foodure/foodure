package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Account;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.foodure.R;

import java.util.Objects;

public class RestaurantProfileActivity extends AppCompatActivity {

  private static final String TAG = "RestaurantProfileActivity";
  private Restaurant restaurant;
  private Handler handler;
  private TextView restaurantName;
  private TextView accountUsername ;
  private TextView accountEmail ;
  private TextView restaurantLocation ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_profile);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_restaurantProfilePage);

    restaurantName = findViewById(R.id.restaurantName_profile);
    accountUsername = findViewById(R.id.restaurantUsername_profile) ;
    accountEmail = findViewById(R.id.restaurantEmail_profile) ;
    restaurantLocation = findViewById(R.id.restaurantLocation_profile);




    handler = new Handler(Looper.getMainLooper(),
            message -> {
              showData();
              return false;
            });

    getRestaurant();

    back.setOnClickListener(v -> back());
  }

  private void showData() {
      restaurantName.setText(restaurant.getTitle());
      accountUsername.setText(Amplify.Auth.getCurrentUser().getUsername());
      restaurantLocation.setText(restaurant.getLocation());

  }

  public void back() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void getRestaurant(){
    Amplify.API.query(ModelQuery.get(Restaurant.class, Amplify.Auth.getCurrentUser().getUsername()),
            response -> {
              restaurant = response.getData();
              handler.sendEmptyMessage(1);
              Log.i(TAG, "success " + response.toString());},
            error -> Log.e(TAG, "Failed "+ error)
            );
  }
}
