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
import com.foodure.R;

import java.util.Objects;

public class RestaurantProfileActivity extends AppCompatActivity {

  private static final String TAG = "RestaurantProfileActivity";
  private Account account;
  private Handler handler;
  private TextView restaurantName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_profile);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_restaurantProfilePage);

    restaurantName = findViewById(R.id.restaurantName_profile);

    handler = new Handler(Looper.getMainLooper(),
            message -> {
              showData();
              return false;
            });

    back.setOnClickListener(v -> back());
  }

  private void showData() {

  }

  public void back() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void getAccount(){
    Amplify.API.query(ModelQuery.get(Account.class, Amplify.Auth.getCurrentUser().getUserId()),
            response -> {
              account = response.getData();
              handler.sendEmptyMessage(1);
              Log.i(TAG, "success ");},
            error -> Log.e(TAG, "Failed "+ error)
            );
  }
}
