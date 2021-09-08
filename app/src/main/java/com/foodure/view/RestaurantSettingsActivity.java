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
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.foodure.R;

import java.util.Objects;

public class RestaurantSettingsActivity extends AppCompatActivity {

  private static final String TAG = "RestaurantSettingsActivity";
  private String spinnerLocation = null;
  private String username = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_settings);

    Objects.requireNonNull(getSupportActionBar()).hide();

    Button submit = findViewById(R.id.updateSettingsBtn);

    TextView resTitle = findViewById(R.id.editRestaurantName_restaurantSettingsPage);


    username = Amplify.Auth.getCurrentUser().getUsername();

    Spinner spinner = findViewById(R.id.spinner_location_restaurantSettingsPage);

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

    submit.setOnClickListener(v -> {
      String title = resTitle.getText().toString();
      Restaurant item = Restaurant.builder().title(title).username(username).location(spinnerLocation).build();
      saveAPI(item);
      Log.i(TAG, "onCreate: title " + title);
      Log.i(TAG, "onCreate: location " + spinnerLocation);
      back();
    });
  }

  public void back() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void saveAPI(Restaurant item) {
    Amplify.API.mutate(ModelMutation.create(item),
        success -> Log.i(TAG, "Saved item to api : " + success.getData()),
        error -> Log.e(TAG, "Could not save item to API/dynamodb", error));
  }

}