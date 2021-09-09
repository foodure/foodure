package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Account;
import com.foodure.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

   static final String TAG = "LoginActivity";
   Account AccountData;

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

          String str = Amplify.Auth.getCurrentUser().getUsername();

          getAccountTypeFromAPIByName(str);

          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          if (AccountData.getType().equals("user")) {
            Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
            goToMain.putExtra("username", username);
            startActivity(goToMain);
          } else if (AccountData.getType().equals("restaurant")) {
            Intent goToMain = new Intent(LoginActivity.this, RestaurantActivity.class);
            startActivity(goToMain);
          }

        },
        error -> Log.e(TAG, "signIn: failed" + error.toString()));
  }

  public void getAccountTypeFromAPIByName(String name) {
    Amplify.API.query(
        ModelQuery.list(Account.class, Account.USERNAME.contains(name)),
        response -> {
          for (Account account : response.getData()) {
            Log.i(TAG, "account type: " + account.getType());
            AccountData = account;
          }
        },
        error -> {
          Log.i(TAG, "Query failure", error);
        }
    );
  }
}