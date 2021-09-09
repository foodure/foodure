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
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Account;
import com.foodure.R;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

   static final String TAG = "SignupActivity";
   String spinnerLocation = null;
   String username = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    Objects.requireNonNull(getSupportActionBar()).hide();

    EditText emailInput = findViewById(R.id.editTextEmail_signup);
    EditText usernameInput = findViewById(R.id.editTextUsername_signup);
    EditText passwordInput = findViewById(R.id.editTextPassword_signup);
    Button signupBtn = findViewById(R.id.signupBtn);
    Button LoginBtn = findViewById(R.id.gotToLoginBtn);
    Spinner spinner = findViewById(R.id.spinner_location);

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

    signupBtn.setOnClickListener(v -> {
      String email = emailInput.getText().toString();
      username = usernameInput.getText().toString();
      String password = passwordInput.getText().toString();
      if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
        signUp(username, email, password);

        Account item = Account.builder()
            .username(username)
            .location(spinnerLocation)
            .type("user")
            .build();

        saveAPI(item);
      } else {
        Toast.makeText(SignupActivity.this, "Please insert your info!", Toast.LENGTH_SHORT).show();
      }
    });

    LoginBtn.setOnClickListener(v -> {
      Intent goToLogin = new Intent(SignupActivity.this, LoginActivity.class);
      startActivity(goToLogin);
    });


  }

  public void signUp(String username, String email, String password) {
    Amplify.Auth.signUp(
        username,
        password,
        AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build(),
        success -> {
          Log.i(TAG, "signUp successful: " + success.toString());
          Intent goToVerification = new Intent(SignupActivity.this, VerificationActivity.class);
          goToVerification.putExtra("username", username);
          goToVerification.putExtra("password", password);
          startActivity(goToVerification);
        },
        error -> {
          Log.e(TAG, "signUp failed: " + error.toString());
        });
  }

  public void saveAPI(Account item) {
    Amplify.API.mutate(ModelMutation.create(item),
        success -> Log.i(TAG, "Saved item to api : " + success.getData()),
        error -> Log.e(TAG, "Could not save item to API/dynamodb", error));
  }
}