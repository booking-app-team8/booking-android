package com.example.bookingapp.activities.startup;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.ApprovalAccommodationRequestActivity;
import com.example.bookingapp.activities.home.admin.AdminMainActivity;
import com.example.bookingapp.activities.home.guest.GuestMainActivity;
import com.example.bookingapp.activities.home.host.HostMainActivity;
import com.example.bookingapp.activities.user.User_Account;
import com.example.bookingapp.dtos.LoginGETDTO;
import com.example.bookingapp.dtos.LoginPOSTDTO;
import com.example.bookingapp.models.enums.Role;
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
    private static final String CHANNEL_ID = "Zero channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView textViewSignUp = findViewById(R.id.sign_up);

//        TextView textViewForgotPassword = view.findViewById(R.id.forgotPasswordTextView);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        createNotificationChannel();
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

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        };
        // Dodavanje callback-a na onBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

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
                    new AuthService().getMyInfo(request.getEmail());  // load current user
                    AuthService.setAccessToken(response.body().getToken());
                    Role r = AuthService.getCurrentUser().getRole(); new AuthService().getMyInfo(request.getEmail());  // load current user
                    saveToken(response.body().getToken(), emailEditText.getText().toString(), String.valueOf(r));


                    switch (r.toString()) {
                        case "GUEST": {
                                Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LogInActivity.this, GuestMainActivity.class);
                                startActivity(intent);
//                                finish();
                            break;
                        }
                        case "OWNER": {
                                Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LogInActivity.this, HostMainActivity.class);
                                startActivity(intent);
                                finish();
                            break;
                        }
                        case "ADMIN":
                            System.out.println("usao");
                                Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LogInActivity.this, AdminMainActivity.class);
                                startActivity(intent);
//                                finish();
                            break;
                    }


                } else
                    Toast.makeText(LogInActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginGETDTO> call, Throwable t) {
                Toast.makeText(LogInActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




    private void saveToken(String token, String email,String role) {
        // SharedPreferences
        //DODATI OVO OBAVEZNO
        //kao localStorage
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .putString("jwt_token", token)
                .apply();

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userEmail", email);
        editor.putString("role", role);
        editor.apply();
    }

    public void logout() {
//        accessToken = null;
//        currentUser = null;
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Briše sve vrednosti
        editor.apply(); // ili editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("Log out", "log outovao");
//        Toast.makeText(this, "USAO", Toast.LENGTH_SHORT).show();
        logout();
    }

}