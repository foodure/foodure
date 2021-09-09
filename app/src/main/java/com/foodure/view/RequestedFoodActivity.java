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

import com.amplifyframework.datastore.generated.model.FoodPost;
import com.foodure.R;
import com.foodure.adapter.MainAdapter;
import com.foodure.adapter.RequestedFoodAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestedFoodActivity extends AppCompatActivity {
    public static final String RESTAURANTLABEL = "restaurantLabel";
    public static final String FOODLABEL = "foodLabel";
    public static final String LOCATIONLABEL = "locationLabel";
    public static final String QUANTITYLABEL = "quantityLabel";
    public static final String FILENAMELABEL = "fileNameLabel";

    RecyclerView recyclerView;
    Handler handler;
    List<FoodPost> userRequests;
    RequestedFoodAdapter requestedFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_food);
        Objects.requireNonNull(getSupportActionBar()).hide();

        handler = new Handler(new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                return false;
            }
        });
        recyclerView = findViewById(R.id.requestedFood_recyclerview);

        userRequests = new ArrayList<>();

        requestedFoodAdapter = new RequestedFoodAdapter(userRequests, new RequestedFoodAdapter.OnFoodClickListener() {
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
        recyclerView.setAdapter(requestedFoodAdapter);
    }
}