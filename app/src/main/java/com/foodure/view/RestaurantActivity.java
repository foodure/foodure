package com.foodure.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Account;
import com.amplifyframework.datastore.generated.model.FoodPost;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.foodure.R;
import com.foodure.adapter.AdapterFood;
import com.foodure.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";
    private List<FoodPost> foodPostList;
    private AdapterFood adapterFood;

    private Handler foodHandler;
    private RecyclerView recyclerView;

    private Handler handler;

    private Restaurant restaurantData;
    private Handler RestaurantDataHandler;

    private Handler handlerTry;

    TextView emptyItemText;
    ImageView emptyItemImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant);
        Objects.requireNonNull(getSupportActionBar()).hide();

        getRestaurantDataAPIByName(getUsername());

        RestaurantDataHandler = new Handler(new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if (restaurantData == null) {
                    goToSettings();
                }
                return false;
            }
        });

        handlerTry = new Handler(new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                goneView();
                return false;
            }
        });

        foodHandler = new Handler(new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                return false;
            }
        });

        handler = new Handler(Looper.getMainLooper(),
                message -> {
                    listItemDeleted();
                    return false;
                });

        recyclerView = findViewById(R.id.recyclerView_restaurantPage);

        Button usernameBtn = findViewById(R.id.username_restaurantPage);

        Button logout = findViewById(R.id.logout_restaurantPage);

        Button addFoodBtn = findViewById(R.id.addFood_restaurantPage);

        usernameBtn.setOnClickListener(v -> goToProfile());

        logout.setOnClickListener(v -> logout());

        addFoodBtn.setOnClickListener(v -> gotoAddFood());

        foodPostList = new ArrayList<>();

        adapterFood = new AdapterFood(foodPostList, new AdapterFood.OnFoodClickListener() {
            @Override
            public void onDeleteFood(int position) {

                Amplify.API.mutate(ModelMutation.delete(foodPostList.get(position)),
                        response -> Log.i(TAG, "item deleted from API:"),
                        error -> Log.e(TAG, "Delete failed", error)
                );
                foodPostList.remove(position);
                listItemDeleted();
                visibleView();

            }

            @Override
            public void onItemClick(int position) {
                Intent goToFoodDetails = new Intent(getApplicationContext(), RestaurantFoodDetailsActivity.class);
                goToFoodDetails.putExtra("restaurantLabel", foodPostList.get(position).getRestaurant().getTitle());
                goToFoodDetails.putExtra("foodLabel", foodPostList.get(position).getTitle());
                goToFoodDetails.putExtra("locationLabel", foodPostList.get(position).getLocation());
                goToFoodDetails.putExtra("quantityLabel", foodPostList.get(position).getQuantity());
                goToFoodDetails.putExtra("typeOfQuantityLabel", foodPostList.get(position).getTypeOfQuantity());
                goToFoodDetails.putExtra("fileNameLabel", foodPostList.get(position).getFileName());
                startActivity(goToFoodDetails);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterFood);

    }

    @Override
    protected void onResume() {
        super.onResume();

        foodPostList.clear();

        getFoods();

        foodHandler = new Handler(new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                return false;
            }
        });

        if (foodPostList.size() > 1) {
            goneView();
        }


    }

    private void goneView() {
        emptyItemText = findViewById(R.id.emptyItem_respage);
        emptyItemImg = findViewById(R.id.empty_respage);
        emptyItemText.setVisibility(View.GONE);
        emptyItemImg.setVisibility(View.GONE);
    }

    private void visibleView() {
        if (foodPostList.size() < 1) {
            emptyItemText = findViewById(R.id.emptyItem_respage);
            emptyItemImg = findViewById(R.id.empty_respage);
            emptyItemText.setVisibility(View.VISIBLE);
            emptyItemImg.setVisibility(View.VISIBLE);
        }
    }

    public void goToProfile() {
        Intent goToProfile = new Intent(RestaurantActivity.this, RestaurantProfileActivity.class);
        startActivity(goToProfile);
    }

    public void goToSettings() {
        Intent goToSettings = new Intent(RestaurantActivity.this, RestaurantSettingsActivity.class);
        startActivity(goToSettings);
    }

    public void gotoAddFood() {
        Intent goToSettings = new Intent(RestaurantActivity.this, RestaurantAddFoodActivity.class);
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
                        Log.i(TAG, "getFoods: username from foods --> " + foodPost.getRestaurant().getUsername());
                        if (foodPost.getRestaurant().getUsername().equals(getUsername())) {
                            foodPostList.add(foodPost);
                            handlerTry.sendEmptyMessage(1);
                        }
                    }
                    foodHandler.sendEmptyMessage(1);
                },
                failure -> Log.i(TAG, "getFoods: ")
        );

    }

    public void getRestaurantDataAPIByName(String name) {
        Amplify.API.query(
                ModelQuery.list(Restaurant.class, Restaurant.USERNAME.contains(name)),
                response -> {
                    for (Restaurant restaurant : response.getData()) {
                        Log.i(TAG, "Restaurant data: " + restaurant);
                        restaurantData = restaurant;
                    }
                    RestaurantDataHandler.sendEmptyMessage(1);
                },
                error -> {
                    Log.i(TAG, "Query failure", error);
                }
        );
    }

    private String getUsername() {
        return Amplify.Auth.getCurrentUser().getUsername();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void listItemDeleted() {
        adapterFood.notifyDataSetChanged();
    }
}
