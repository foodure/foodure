package com.foodure.view;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.FoodPost;
import com.amplifyframework.datastore.generated.model.Restaurant;
import com.foodure.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RestaurantAddFoodActivity extends AppCompatActivity {

  private static final String TAG = "RestaurantAddFoodActivity";
  private String username = null;
  private String spinnerLocation = null;
  private String spinnerType = null;
  private Restaurant RestaurantsData = null;

  static String pattern = "yyMMddHHmmssZ";
  @SuppressLint("SimpleDateFormat")
  static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
  private static final String FileUploadName = simpleDateFormat.format(new Date());
  private static String fileUploadExtension = "F";
  private static File uploadFile = null;

  private TextView foodName;
  private TextView foodQuantity;
  private String foodNameStr;
  private String foodQuantityStr;

  @RequiresApi(api = Build.VERSION_CODES.Q)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_add_food);
    Objects.requireNonNull(getSupportActionBar()).hide();

    ImageView back = findViewById(R.id.back_restaurantAddFoodPage);
    foodName = findViewById(R.id.editFoodName_restaurantAddFoodPage);
    foodQuantity = findViewById(R.id.editTextUsername_restaurantAddFoodPage);

    Spinner spinner = findViewById(R.id.spinner_location_restaurantAddFoodPage);
    Spinner typeSpinner = findViewById(R.id.spinner_type);
    Button addFoodBtn = findViewById(R.id.addFood_restaurantAddFoodPage);

    Intent intent = getIntent();
    String action = intent.getAction();
    String type = intent.getType();

    if (Intent.ACTION_SEND.equals(action) && type != null) {
      if (type.startsWith("image/")) {
        handleSendImage(intent); // Handle single image being sent
      }
    }

    Button uploadFile = findViewById(R.id.addImage_restaurantAddFoodPage);
    uploadFile.setOnClickListener(v1 -> getFileFromDevice());


    ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
        R.array.type_array, android.R.layout.simple_spinner_item);
    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    typeSpinner.setAdapter(typeAdapter);

    typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerType = (String) parent.getItemAtPosition(position);
        System.out.println(spinnerType);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        spinnerType = (String) parent.getItemAtPosition(0);
      }
    });

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.location_array,
        android.R.layout.simple_spinner_item);
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
    addFoodBtn.setOnClickListener(addFoodCreateListener);
    back.setOnClickListener(v -> back());
  }

  public void back() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  private final View.OnClickListener addFoodCreateListener = v -> {
    foodNameStr = foodName.getText().toString();
    foodQuantityStr = foodQuantity.getText().toString();
    username = Amplify.Auth.getCurrentUser().getUsername();
    Log.i(TAG, "addFood: " + foodNameStr);
    Log.i(TAG, "addFood: " + foodQuantityStr);
    Log.i(TAG, "username: " + username);
    getRestaurantAPIByName(username);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Log.i(TAG, "onCreate:  " + RestaurantsData.getTitle());
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    FoodPost foodPost = FoodPost.builder()
        .title(foodNameStr)
        .quantity(foodQuantityStr)
        .typeOfQuantity(spinnerType)
        .location(spinnerLocation)
        .restaurant(RestaurantsData)
        .fileName(FileUploadName + "." + fileUploadExtension.split("/")[1])
        .build();
    Log.i(TAG, "onCreate: food post>>>>>>>  " + foodPost);
    saveAPI(foodPost);
    back();
  };

  public void getRestaurantAPIByName(String name) {
    Amplify.API.query(ModelQuery.list(Restaurant.class, Restaurant.USERNAME.contains(name)), response -> {
      for (Restaurant restaurant : response.getData()) {
        Log.i(TAG, "account type: " + restaurant.getTitle());
        RestaurantsData = restaurant;
      }
    }, error -> {
      Log.i(TAG, "Query failure", error);
    });
  }

  public void saveAPI(FoodPost item) {
    Amplify.Storage.uploadFile(
        FileUploadName + "." + fileUploadExtension.split("/")[1],
        uploadFile,
        success -> Log.i(TAG, "uploadFileToS3: succeeded " + success.getKey()),
        error -> Log.e(TAG, "uploadFileToS3: failed " + error.toString())
    );

    Amplify.API.mutate(ModelMutation.create(item),
        success -> Log.i(TAG, "Saved food item to api : " + success.getData()),
        error -> Log.e(TAG, "Could not save food item to API/dynamodb", error));
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 999 && resultCode == RESULT_OK) {
      Uri uri = data.getData();
      fileUploadExtension = getContentResolver().getType(uri);

      Log.i(TAG, "onActivityResult: gg is " + fileUploadExtension);
      Log.i(TAG, "onActivityResult: returned from file explorer");
      Log.i(TAG, "onActivityResult: => " + data.getData());
      Log.i(TAG, "onActivityResult:  data => " + data.getType());

      uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");

      try {
        InputStream inputStream = getContentResolver().openInputStream(data.getData());
        FileUtils.copy(inputStream, new FileOutputStream(uploadFile));
      } catch (Exception exception) {
        Log.e(TAG, "onActivityResult: file upload failed" + exception.toString());
      }

    }
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  public void handleSendImage(Intent intent) {
    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
    if (imageUri != null) {
      // Update UI to reflect image being shared
      fileUploadExtension = getContentResolver().getType(imageUri);

      Log.i(TAG, "onActivityResult: gg is " + fileUploadExtension);
      Log.i(TAG, "onActivityResult: returned from file explorer");
      Log.i(TAG, "onActivityResult: success choose image");


      uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");

      try {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        FileUtils.copy(inputStream, new FileOutputStream(uploadFile));
      } catch (Exception exception) {
        Log.e(TAG, "onActivityResult: file upload failed" + exception.toString());
      }
    }
  }

  private void getFileFromDevice() {
    Intent upload = new Intent(Intent.ACTION_GET_CONTENT);
    upload.setType("*/*");
    upload = Intent.createChooser(upload, "Choose a File");
    startActivityForResult(upload, 999); // deprecated
  }
}