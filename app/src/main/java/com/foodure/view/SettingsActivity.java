package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.amplifyframework.datastore.generated.model.User;
import com.foodure.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    static String spinnerLocation = null;
    static String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).hide();


        Button submit = findViewById(R.id.updateSettingsBtn_user);
        username = Amplify.Auth.getCurrentUser().getUsername();
        EditText firstName = findViewById(R.id.firstName_userSettingsPage);
        EditText lastName = findViewById(R.id.lastName_userSettingsPage);
        EditText age = findViewById(R.id.age_userSettingsPage);
        Spinner spinner = findViewById(R.id.spinner_location_userSettingsPage);

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
            String getFirstName = firstName.getText().toString();
            String getLastName = lastName.getText().toString();
            int getAge = Integer.parseInt(age.getText().toString());

            User item = User.builder()
                .username(username)
                .firstName(getFirstName)
                .lastName(getLastName)
                .age(getAge)
                .location(spinnerLocation)
                .build();
            saveAPI(item);
            Log.i(TAG, "onCreate: title " + username);
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

    public void saveAPI(User item) {
        Amplify.API.mutate(ModelMutation.create(item),
                success -> {
                    Log.i(TAG, "Saved item to api : " + success.getData());
                    back();
                },
                error -> Log.e(TAG, "Could not save item to API/dynamodb", error));
    }

}