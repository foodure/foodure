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

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.FoodPost;
import com.foodure.R;
import com.foodure.adapter.AdapterFood;
import com.foodure.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";
    private List<FoodPost> foodPostList ;
    private AdapterFood adapterFood;

    private Handler foodHandler ;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant);
        Objects.requireNonNull(getSupportActionBar()).hide();

        foodHandler = new Handler(new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerView_restaurantPage);

        TextView usernameBtn = findViewById(R.id.username_restaurantPage);
        ImageView usernameImg = findViewById(R.id.profile_restaurantPage);

        TextView settingsBtn = findViewById(R.id.settings_restaurantPage);
        ImageView settingsImg = findViewById(R.id.settingsImg_restaurantPage);

        ImageView logout = findViewById(R.id.logout_restaurantPage);

        Button addFoodBtn = findViewById(R.id.addFood_restaurantPage);
        Button deleteFoodBtn = findViewById(R.id.delete);

        usernameBtn.setOnClickListener(v -> goToProfile());

        usernameImg.setOnClickListener(v -> goToProfile());

        settingsBtn.setOnClickListener(v -> goToSettings());

        settingsImg.setOnClickListener(v -> goToSettings());

        logout.setOnClickListener(v -> logout());

        addFoodBtn.setOnClickListener(v -> gotoAddFood());

        foodPostList = new ArrayList<>();
        getFoods();

        adapterFood = new AdapterFood(foodPostList, new AdapterFood.OnFoodClickListener() {
            @Override
            public void onDeleteFood(int position) {

                Amplify.API.mutate(ModelMutation.delete(foodPostList.get(position)),
                        response -> Log.i(TAG, "item deleted from API:"),
                        error -> Log.e(TAG, "Delete failed", error)
                );
                foodPostList.remove(position);
                listItemDeleted();
            }

            @Override
            public void onItemClick(int position) {
                Intent goToFoodDetails = new Intent(getApplicationContext() , RestaurantFoodDetailsActivity.class) ;
                goToFoodDetails.putExtra("restaurantLabel" , foodPostList.get(position).getRestaurant().getTitle()) ;
                goToFoodDetails.putExtra( "foodLabel" , foodPostList.get(position).getTitle() ) ;
                goToFoodDetails.putExtra("locationLabel" , foodPostList.get(position).getLocation() ) ;
                goToFoodDetails.putExtra("quantityLabel" , foodPostList.get(position).getQuantity()) ;
                startActivity(goToFoodDetails);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this ,
                LinearLayoutManager.VERTICAL ,
                false
        ) ;

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterFood);




    }

    public void goToProfile(){
        Intent goToProfile = new Intent(RestaurantActivity.this, RestaurantProfileActivity.class);
        startActivity(goToProfile);
    }

    public void goToSettings(){
        Intent goToSettings = new Intent(RestaurantActivity.this, RestaurantSettingsActivity.class);
        startActivity(goToSettings);
    }

    public void gotoAddFood(){
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

    private void getFoods(){
        Amplify.API.query(ModelQuery.list(FoodPost.class) ,
                response -> {
                        for (FoodPost foodPost : response.getData()){
                            foodPostList.add(foodPost);
                        }
                        foodHandler.sendEmptyMessage(1);
                } ,
                failure -> Log.i(TAG, "getFoods: ")
                ) ;
    }



@SuppressLint("NotifyDataSetChanged")
private void listItemDeleted() {
    adapterFood.notifyDataSetChanged();
        }
}
