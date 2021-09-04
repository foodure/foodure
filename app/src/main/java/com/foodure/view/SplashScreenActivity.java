package com.foodure.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.foodure.R;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();

//    throw new RuntimeException("Test Crash");

        handler.postDelayed(() -> {
            Log.i(TAG, "onCreate: ");
            config();
        }, 3000);
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnected();
    }

    public void awsConfiguration() {
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }
    }

    public boolean isCurrentUser() {
        return Amplify.Auth.getCurrentUser() != null;
    }

    public void config() {
        if (isNetworkAvailable(getApplicationContext())) {
            awsConfiguration();
            Log.i(TAG, "NET: the network is available");

            if (isCurrentUser()) {
                Log.i(TAG, "Auth: " + Amplify.Auth.getCurrentUser().toString());
                Intent goToMain = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(goToMain);
            } else {
                Log.i(TAG, "Auth:  no user " + Amplify.Auth.getCurrentUser());
                Intent goToLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }

        } else {
            Log.i(TAG, "NET: net down");
        }
    }
}