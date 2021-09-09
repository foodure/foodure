package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.amplifyframework.datastore.generated.model.User;
import com.foodure.R;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

  static final String TAG = "ProfileActivity";
  User user;
  Handler handler;
  TextView name;
  TextView username;
  TextView location;
  TextView age;
  ImageView emptyImg;
  TextView emptyTxt;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_profile);

    name = findViewById(R.id.name_profile);
    username = findViewById(R.id.username_profile);
    location = findViewById(R.id.location_profile);
    age = findViewById(R.id.age_profile);

    emptyImg = findViewById(R.id.emptyData_profile);
    emptyTxt = findViewById(R.id.noDataTxt_profile);

    name.setVisibility(View.GONE);
    username.setVisibility(View.GONE);
    location.setVisibility(View.GONE);
    age.setVisibility(View.GONE);

    getRestaurant(Amplify.Auth.getCurrentUser().getUsername());

    handler = new Handler(Looper.getMainLooper(),
        message -> {
          Log.i(TAG, "onCreate:  " + user);
          showData();
          return false;
        });

    back.setOnClickListener(v -> back());

  }

  @SuppressLint("SetTextI18n")
  private void showData() {
    emptyImg.setVisibility(View.GONE);
    emptyTxt.setVisibility(View.GONE);

    name.setText(user.getFirstName() + " " + user.getLastName());
    username.setText("Username: "+Amplify.Auth.getCurrentUser().getUsername());
    location.setText("Location: "+ user.getLocation());
    age.setText("Age: " + user.getAge().toString());

    name.setVisibility(View.VISIBLE);
    username.setVisibility(View.VISIBLE);
    location.setVisibility(View.VISIBLE);
    age.setVisibility(View.VISIBLE);
  }

  public void back() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void getRestaurant(String name) {
    Amplify.API.query(
        ModelQuery.list(User.class, User.USERNAME.contains(name)),
        response -> {
          for (User userData : response.getData()) {
            Log.i(TAG, "Restaurant Name: " + userData);
            user = userData;
          }
          handler.sendEmptyMessage(1);
        },
        error -> {
          Log.i(TAG, "Query failure", error);
        }
    );
  }
}