package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.foodure.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Objects.requireNonNull(getSupportActionBar()).hide();

    TextView usernameBtn = findViewById(R.id.username_homePage);
    ImageView usernameImg = findViewById(R.id.profile_homePage);

    TextView settingsBtn = findViewById(R.id.settings_homePage);
    ImageView settingsImg = findViewById(R.id.settingsImg_homePage);

    ImageView logout = findViewById(R.id.logout_home);

    usernameBtn.setOnClickListener(v -> goToProfile());

    usernameImg.setOnClickListener(v -> goToProfile());

    settingsBtn.setOnClickListener(v -> goToSettings());

    settingsImg.setOnClickListener(v -> goToSettings());

    logout.setOnClickListener(v -> logout());

  }

  public void goToProfile(){
    Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
    startActivity(goToProfile);
  }

  public void goToSettings(){
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
}