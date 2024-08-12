package com.example.bookingapp.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.example.bookingapp.R;
import com.example.bookingapp.models.users.User;
import com.example.bookingapp.utils.AuthService;

public class User_Account extends AppCompatActivity {

    private EditText emailField, passwordField, firstNameField, lastNameField, addressField, phoneNumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        firstNameField = findViewById(R.id.first_name);
        lastNameField = findViewById(R.id.last_name);
        addressField = findViewById(R.id.address);
        phoneNumberField = findViewById(R.id.phone_number);

        User currentUser = AuthService.getCurrentUser();

        if (currentUser != null) {
            emailField.setText(currentUser.getEmail());
            firstNameField.setText(currentUser.getName());
            lastNameField.setText(currentUser.getSurname());
            addressField.setText(currentUser.getAddress());
            phoneNumberField.setText(currentUser.getPhoneNumber());

            // Lozinku možda ne želiš direktno prikazivati iz sigurnosnih razloga,
            // ali možeš ostaviti ovo mesto za unos nove lozinke
            passwordField.setText("");  // Ostavlja polje prazno
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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