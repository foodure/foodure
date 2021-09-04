package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodure.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    Objects.requireNonNull(getSupportActionBar()).hide();

    TextView usernameBtn = findViewById(R.id.username_settings);
    ImageView usernameImg = findViewById(R.id.profile_settings);

    TextView settingsBtn = findViewById(R.id.settings_settings);
    ImageView settingsImg = findViewById(R.id.settingsImg_settings);

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
    Intent goToProfile = new Intent(SettingsActivity.this, ProfileActivity.class);
    startActivity(goToProfile);
  }

  public void goToSettings(){
    Intent goToSettings = new Intent(SettingsActivity.this, SettingsActivity.class);
    startActivity(goToSettings);
  }
}