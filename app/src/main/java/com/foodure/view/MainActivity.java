package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodure.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Objects.requireNonNull(getSupportActionBar()).hide();

    TextView usernameBtn = findViewById(R.id.username_homePage);
    ImageView usernameImg = findViewById(R.id.profile_homePage);

    TextView settingsBtn = findViewById(R.id.settings_homePage);
    ImageView settingsImg = findViewById(R.id.settingsImg_homePage);

    usernameBtn.setOnClickListener(v -> {
      goToProfile();
    });

    usernameImg.setOnClickListener(v -> {
      goToProfile();
    });

    settingsBtn.setOnClickListener(v -> {
      goToSettings();
    });

    settingsImg.setOnClickListener(v -> {
      goToSettings();
    });

  }

  public void goToProfile(){
    Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
    startActivity(goToProfile);
  }

  public void goToSettings(){
    Intent goToSettings = new Intent(MainActivity.this, SettingsActivity.class);
    startActivity(goToSettings);
  }
}