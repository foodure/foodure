package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.foodure.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

  private static final String TAG = "LoginActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    Objects.requireNonNull(getSupportActionBar()).hide();

    EditText usernameInput = findViewById(R.id.editTextName_login);
    EditText passwordInput = findViewById(R.id.editTextPassword_login);
    Button loginBtn = findViewById(R.id.loginBtn);
    Button signupBtn = findViewById(R.id.gotToSignupBtn);

    loginBtn.setOnClickListener(v -> {
      String email = usernameInput.getText().toString();
      String password = passwordInput.getText().toString();
      if (!email.isEmpty() && !password.isEmpty()) {
        signIn(email, password);
      } else {
        Toast.makeText(LoginActivity.this, "Please insert your info.??", Toast.LENGTH_SHORT).show();
      }
    });

    signupBtn.setOnClickListener(v -> {
      Intent goToSignUp = new Intent(LoginActivity.this, SignupActivity.class);
      startActivity(goToSignUp);
    });

  }

  public void signIn(String username, String password) {
    Amplify.Auth.signIn(
        username,
        password,
        success -> {
          Log.i(TAG, "signIn: worked " + success.toString());
          Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
          goToMain.putExtra("username", username);
          startActivity(goToMain);
        },
        error -> Log.e(TAG, "signIn: failed" + error.toString()));
  }
}