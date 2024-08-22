package com.example.bookingapp.activities.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.home.guest.GuestMainActivity;
import com.example.bookingapp.dtos.LoginGETDTO;
import com.example.bookingapp.dtos.UserPOSTDTO;
import com.example.bookingapp.models.enums.Role;
import com.example.bookingapp.utils.ApiUtils;

import org.json.JSONException;

import java.io.IOException;

import kotlin.jvm.internal.Ref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText name, surname, address, phoneNumber, passwordEditText, repeatPassword , emailEditText;
    RadioGroup radioGroup;
    RadioButton radioButtonOwner;
    RadioButton radioButtonGuest;

    RadioButton selectedRadioButton;
    String selectedRole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        address = findViewById(R.id.homeAddress);
        phoneNumber = findViewById(R.id.phoneNumber);
        passwordEditText = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.confirmPassword);
        emailEditText = findViewById(R.id.userEmail);

        radioGroup = findViewById(R.id.radioGroup);
        radioButtonOwner = findViewById(R.id.radioButtonOwner);
        radioButtonGuest = findViewById(R.id.radioButtonGuest);

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // Provera koji RadioButton je označen
//                RadioButton checkedRadioButton = findViewById(checkedId);
//                selectedRadioButton = checkedRadioButton;
//                if (checkedRadioButton != null) {
//                    String selectedText = checkedRadioButton.getText().toString();
//                    Toast.makeText(RegisterActivity.this, "Selected: " + selectedText, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Update selectedRole based on checked RadioButton
                if (checkedId == R.id.radioButtonOwner) {
                    selectedRole = "Owner";
                } else if (checkedId == R.id.radioButtonGuest) {
                    selectedRole = "Guest";
                }

                // Display the selected role (optional)
                Toast.makeText(RegisterActivity.this, "Selected role: " + selectedRole, Toast.LENGTH_SHORT).show();
            }
        });
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!areFieldsValid()){
                    Toast.makeText(RegisterActivity.this,"All of fields must be filled", Toast.LENGTH_LONG).show();
                }else{
//                   String se = selectedRole;
                   register();
//                    try {
//                        System.out.println("dasdsa");
//                        register();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    
                }
            }
        });



    }

    public void register(){
        Role role;
        if (selectedRole.equals("Guest")) {
            role = Role.GUEST;
        } else role = Role.OWNER;
        UserPOSTDTO userPOSTDTO = new UserPOSTDTO(name.getText().toString(), surname.getText().toString(),address.getText().toString(), phoneNumber.getText().toString(), emailEditText.getText().toString(), role, passwordEditText.getText().toString());
        Call<String> register = ApiUtils.getUserService().register(userPOSTDTO);
        System.out.println(register);
        register.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Register request created! Check your email!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        String message = response.errorBody().string();
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Message",  t.getMessage() != null ? t.getMessage() : "error");
            }
        });


    }

    private boolean areFieldsValid() {
        if (isEmpty(name)) {
            name.setError("Name is required");
            return false;
        }
        if (isEmpty(surname)) {
            surname.setError("Surname is required");
            return false;
        }
        if (isEmpty(address)) {
            address.setError("Address is required");
            return false;
        }
        if (isEmpty(phoneNumber)) {
            phoneNumber.setError("Phone number is required");
            return false;
        }
        if (isEmpty(passwordEditText)) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (isEmpty(repeatPassword)) {
            repeatPassword.setError("Repeat password is required");
            return false;
        }
        if (isEmpty(emailEditText)) {
            emailEditText.setError("Email is required");
            return false;
        }

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1 || selectedRole.isEmpty()) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

}