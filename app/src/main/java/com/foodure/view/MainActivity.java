package com.foodure.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.FoodPost;
import com.foodure.R;
import com.foodure.adapter.AdapterFood;
import com.foodure.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  public static final String RESTAURANTLABEL = "restaurantLabel";
  public static final String FOODLABEL = "foodLabel";
  public static final String LOCATIONLABEL = "locationLabel";
  public static final String QUANTITYLABEL = "quantityLabel";
  public static final String FILENAMELABEL = "fileNameLabel";

  RecyclerView recyclerView;
  Handler handler;
  MainAdapter userAdapter;
  List<FoodPost> userRequests;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Objects.requireNonNull(getSupportActionBar()).hide();

    handler = new Handler(new Handler.Callback() {
      @SuppressLint("NotifyDataSetChanged")
      @Override
      public boolean handleMessage(@NonNull Message message) {
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        return false;
      }
    });

    recyclerView = findViewById(R.id.user_card_recyclerview);

    Button usernameBtn = findViewById(R.id.username_homePage);
    Button logout = findViewById(R.id.logout_home);

    usernameBtn.setOnClickListener(v -> goToProfile());

    logout.setOnClickListener(v -> logout());

    userRequests = new ArrayList<>();
    getFoods();

    userAdapter = new MainAdapter(userRequests, new MainAdapter.OnFoodClickListener() {
      @Override
      public void onRequestFood(int position) {

      }

      @Override
      public void onItemClick(int position) {
        Intent goToFoodDetails = new Intent(getApplicationContext(), FoodDetailsActivity.class);
        goToFoodDetails.putExtra(RESTAURANTLABEL, userRequests.get(position).getRestaurant().getTitle());
        goToFoodDetails.putExtra(FOODLABEL, userRequests.get(position).getTitle());
        goToFoodDetails.putExtra(LOCATIONLABEL, userRequests.get(position).getLocation());
        goToFoodDetails.putExtra(QUANTITYLABEL, userRequests.get(position).getQuantity());
        goToFoodDetails.putExtra(FILENAMELABEL, userRequests.get(position).getFileName());
        startActivity(goToFoodDetails);
      }
    });

    LinearLayoutManager layoutManager = new LinearLayoutManager(
        this,
        LinearLayoutManager.VERTICAL,
        false
    );

    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(userAdapter);

  }

  public void goToProfile() {
    Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
    startActivity(goToProfile);
  }

  public void goToSettings() {
    Intent goToSettings = new Intent(MainActivity.this, SettingsActivity.class);
    startActivity(goToSettings);
  }

  public void logout() {
    Amplify.Auth.signOut(
        () -> {
          Log.i("AuthQuickstart", "Signed out successfully");
          Intent goToLogin = new Intent(getBaseContext(), LoginActivity.class);
          startActivity(goToLogin);
        },
        error -> Log.e("AuthQuickstart", error.toString())
    );
  }

  private void getFoods() {
    Amplify.API.query(ModelQuery.list(FoodPost.class),
        response -> {
          for (FoodPost foodPost : response.getData()) {
            userRequests.add(foodPost);
          }
          handler.sendEmptyMessage(1);
        },
        failure -> Log.i(TAG, "getFoods: ")
    );
  }
}