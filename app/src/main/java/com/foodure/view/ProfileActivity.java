package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodure.R;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    Objects.requireNonNull(getSupportActionBar()).hide();

    TextView usernameBtn = findViewById(R.id.username_profile);
    ImageView usernameImg = findViewById(R.id.profile_profile);

    TextView settingsBtn = findViewById(R.id.settings_profile);
    ImageView settingsImg = findViewById(R.id.settingsImg_profile);

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
    Intent goToProfile = new Intent(ProfileActivity.this, ProfileActivity.class);
    startActivity(goToProfile);
  }

  public void goToSettings(){
    Intent goToSettings = new Intent(ProfileActivity.this, SettingsActivity.class);
    startActivity(goToSettings);
  }
}