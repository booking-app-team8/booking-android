package com.example.bookingapp.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.startup.LogInActivity;
import com.example.bookingapp.dtos.UserDto;
import com.example.bookingapp.dtos.UserPutDTO;
import com.example.bookingapp.models.users.User;
import com.example.bookingapp.services.IUserService;
import com.example.bookingapp.utils.ApiUtils;
import com.example.bookingapp.utils.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Account extends AppCompatActivity implements SensorEventListener {

    private EditText emailField, passwordField, firstNameField, lastNameField, addressField, phoneNumberField;
    private Button saveChangesButton, deleteAccountButton, logOutButton;
    private IUserService userService;
    private UserDto user;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_back);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(this, "Light sensor not available", Toast.LENGTH_SHORT).show();
            }
        }

        int width = 28; // širina u pixelima
        int height = 28; // visina u pixelima
        drawable.setBounds(0, 0, width, height);

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable scaledDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));

        toolbar.setNavigationIcon(scaledDrawable);

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed(); // Ili finish(), zavisno od potreba
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        firstNameField = findViewById(R.id.first_name);
        lastNameField = findViewById(R.id.last_name);
        addressField = findViewById(R.id.address);
        phoneNumberField = findViewById(R.id.phone_number);
        saveChangesButton = findViewById(R.id.btn_save_changes);
        deleteAccountButton = findViewById(R.id.btn_delete_account);
        logOutButton = findViewById(R.id.btn_log_out);

        userService = ApiUtils.getUserService();

        User currentUser = AuthService.getCurrentUser();

        Call<UserDto> call = userService.getUserByEmailAddress(currentUser.getEmail());

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    emailField.setText(user.getEmail());
                    firstNameField.setText(user.getName());
                    lastNameField.setText(user.getSurname());
                    addressField.setText(user.getAddress());
                    phoneNumberField.setText(user.getPhoneNumber());
                    passwordField.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Korisnik nije pronađen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Došlo je do greške: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        saveChangesButton.setOnClickListener(v -> {
            String name = firstNameField.getText().toString();
            String surname = lastNameField.getText().toString();
            String address = addressField.getText().toString();
            String phoneNumber = phoneNumberField.getText().toString();
            String password;

            if (passwordField.getText().toString().isEmpty()) {
                password = user.getPassword();
            } else {
                password = passwordField.getText().toString();
            }

            if (validateInput(name, surname, address, phoneNumber)) {
                showConfirmationDialog(name, surname, address, phoneNumber, password);
            }

        });

        deleteAccountButton.setOnClickListener(v -> {
            showDeleteAccountConfirmationDialog(user.getId());
        });

        logOutButton.setOnClickListener(v -> {
            AuthService.logout();
            Intent intent = new Intent(User_Account.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void showConfirmationDialog(String name, String surname, String address, String phoneNumber, String password) {
        new AlertDialog.Builder(User_Account.this)
                .setTitle("Confirm change")
                .setMessage("Are you sure that you want to change your data?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    UserPutDTO userPutDTO = new UserPutDTO(name, surname, address, phoneNumber, password);
                    Long userId = user.getId();
                    updateUser(userId, userPutDTO);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private boolean validateInput(String name, String surname, String address, String phoneNumber) {
        if (TextUtils.isEmpty(name) || name.length() < 1 || name.length() > 16 || !name.matches("\\D+")) {
            Toast.makeText(User_Account.this, "Name is not valid.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(surname) || surname.length() < 1 || surname.length() > 16 || !surname.matches("\\D+")) {
            Toast.makeText(User_Account.this, "Surname is not valid.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(address) || !address.matches(".*\\S.*")) {
            Toast.makeText(User_Account.this, "Address is not valid.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 9 || !phoneNumber.matches("\\d+")) {
            Toast.makeText(User_Account.this, "Phone number is not valid.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateUser(Long userId, UserPutDTO userPutDTO) {
        Call<User> call = userService.updateUser(userId, userPutDTO);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(User_Account.this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(User_Account.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(User_Account.this, "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
     */

    private void showDeleteAccountConfirmationDialog(Long userId) {
        new AlertDialog.Builder(User_Account.this)
                .setTitle("Confirm deletion")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteUserAccount(userId);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteUserAccount(Long userId) {
        Call<Void> call = userService.deleteUserAccount(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(User_Account.this, "Account successfully deleted!", Toast.LENGTH_SHORT).show();
                    AuthService.logout();
                    Intent intent = new Intent(User_Account.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(User_Account.this, "You have active reservations!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(User_Account.this, "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {

            float lux = sensorEvent.values[0]; // Uzimamo vrednost svetlosti u luksima
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = lux / SensorManager.LIGHT_SUNLIGHT_MAX;
            getWindow().setAttributes(layoutParams);
            getWindow().getDecorView().requestLayout();
//
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = sensorEvent.values[0];
            Log.d("LightSensor", "Light value: " + lightValue);
            float brightnessIncrement = 0.1f; // Možete prilagoditi vrednost
            float newBrightness = lightValue / SensorManager.LIGHT_SUNLIGHT_MAX + brightnessIncrement;

            // Ako je novi nivo svetlosti van opsega [0.0, 1.0], postavite ga na granice
            newBrightness = Math.max(0.0f, Math.min(1.0f, newBrightness));

            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = newBrightness;

            // Postavljanje promenjenih vrednosti u okviru ekrana
            getWindow().setAttributes(layoutParams);
            updateColorsBasedOnBrightness(newBrightness);
        }
    }

    private void updateColorsBasedOnBrightness(float brightness) {
        // Prilagodite ove boje prema vašim potrebama
        int whiteColor = getResources().getColor(android.R.color.white);
        int blackColor = getResources().getColor(android.R.color.black);

//                    User_Account binding = User_Account.inflate(getLayoutInflater());
        ConstraintLayout rootLayout = findViewById(R.id.root_id);
        if (brightness < 0.5f) {
            rootLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_language:
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
     */
}