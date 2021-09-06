package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.FoodPost;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.foodure.R;

import java.util.Objects;

public class RestaurantAddFoodActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantAddFoodActivity";
    private String username = null;
    private String spinnerLocation = null;
    private Restaurant RestaurantsData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_food);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageView back = findViewById(R.id.back_restaurantAddFoodPage);
        TextView foodName = findViewById(R.id.editFoodName_restaurantAddFoodPage);
        TextView foodQuantity = findViewById(R.id.editTextUsername_restaurantAddFoodPage);
        TextView foodType = findViewById(R.id.editTextPassword_restaurantAddFoodPage);

        Spinner spinner = findViewById(R.id.spinner_location_restaurantAddFoodPage);
        Button addFoodBtn = findViewById(R.id.signupBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.location_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerLocation = (String) parent.getItemAtPosition(position);
                System.out.println(spinnerLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerLocation = (String) parent.getItemAtPosition(0);
            }
        });
        addFoodBtn.setOnClickListener(view -> {
            String foodName1 = foodName.getText().toString();
            String foodQuantity1 = foodQuantity.getText().toString();
            String foodType1 = foodType.getText().toString();
            username = Amplify.Auth.getCurrentUser().getUsername();
            Log.i(TAG, "addFood: " + foodName1);
            Log.i(TAG, "addFood: " + foodQuantity1);
            Log.i(TAG, "addFood: " + foodType1);
            Log.i(TAG, "username: " + username);
            getRestaurantAPIByName(username);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "onCreate:  " + RestaurantsData.getTitle());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FoodPost foodPost = FoodPost.builder().title(foodName1).quantity(foodQuantity1).type(foodType1)
                    .location(spinnerLocation).restaurant(RestaurantsData).build();
            Log.i(TAG, "onCreate: food post>>>>>>>  " + foodPost);
            saveAPI(foodPost);

        });
        back.setOnClickListener(v -> back());
    }

    public void back() {
        Intent goToMain = new Intent(RestaurantAddFoodActivity.this, RestaurantActivity.class);
        startActivity(goToMain);
    }

    public void getRestaurantAPIByName(String name) {
        Amplify.API.query(ModelQuery.list(Restaurant.class, Restaurant.USERNAME.contains(name)), response -> {
            for (Restaurant restaurant : response.getData()) {
                Log.i(TAG, "account type: " + restaurant.getTitle());
                RestaurantsData = restaurant;
            }
        }, error -> {
            Log.i(TAG, "Query failure", error);
        });
    }

    public void saveAPI(FoodPost item) {
        Amplify.API.mutate(ModelMutation.create(item),
                success -> Log.i(TAG, "Saved food item to api : " + success.getData()),
                error -> Log.e(TAG, "Could not save food item to API/dynamodb", error));
    }
}