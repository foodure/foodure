package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.foodure.R;

import java.util.Objects;

public class RestaurantAddFoodActivity extends AppCompatActivity {

  private String spinnerLocation = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_add_food);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_restaurantAddFoodPage);
    Spinner spinner = findViewById(R.id.spinner_location_restaurantAddFoodPage);

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.location_array, android.R.layout.simple_spinner_item);
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

    back.setOnClickListener(v -> back());
  }

  public void back() {
    Intent goToMain = new Intent(RestaurantAddFoodActivity.this, RestaurantActivity.class);
    startActivity(goToMain);
  }
}