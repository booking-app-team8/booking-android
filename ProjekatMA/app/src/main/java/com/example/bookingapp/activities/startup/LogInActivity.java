package com.example.bookingapp.activities.startup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.semantics.Role;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.dtos.LoginGETDTO;
import com.example.bookingapp.dtos.LoginPOSTDTO;
import com.example.bookingapp.utils.ApiClient;
import com.example.bookingapp.utils.ApiUtils;
import com.example.bookingapp.utils.AuthService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    EditText passwordEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(emailEditText.getText().toString()) || TextUtils.isEmpty(passwordEditText.getText().toString())){
                    Toast.makeText(LogInActivity.this,"Username / Password Required", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        //proceed to login
                        System.out.println(emailEditText.getText());
                        login();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }



    public void login() throws JSONException {
        LoginPOSTDTO request = new LoginPOSTDTO();
        request.setEmail(emailEditText.getText().toString());
        request.setPassword(passwordEditText.getText().toString());

        Call<LoginGETDTO> loginResponseCall = ApiUtils.getUserService().login(request);
        System.out.println(loginResponseCall);
        loginResponseCall.enqueue(new Callback<LoginGETDTO>() {
            @Override
            public void onResponse(Call<LoginGETDTO> call, Response<LoginGETDTO> response) {
                if (response.isSuccessful()) {
                    AuthService.setAccessToken(response.body().getToken());
                    new AuthService().getMyInfo();  // load current user
//                    for (Role r: AuthService.getCurrentUser().getRoles()) {
//                        switch (r.getName()) {
//                            case "ROLE_DRIVER": {
//                                Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(UserLoginActivity.this, DriverMainActivity.class);
//                                startActivity(intent);
//                                break;
//                            }
//                            case "ROLE_PASSENGER": {
//                                Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(UserLoginActivity.this, PassengerMainActivity.class);
//                                startActivity(intent);
//                                break;
//                            }
//                            case "ROLE_ADMIN":
//                                AuthService.logout();
//                                Toast.makeText(UserLoginActivity.this, "Admin cannot log in", Toast.LENGTH_LONG).show();
//                                return;
//                        }
//                    }

                } else
                    Toast.makeText(LogInActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginGETDTO> call, Throwable t) {
                Toast.makeText(LogInActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void saveToken(String token) {
        // Sačuvajte token (npr. u SharedPreferences)
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .putString("jwt_token", token)
                .apply();
    }

}