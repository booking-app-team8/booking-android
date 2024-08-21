package com.example.bookingapp.activities.commentsAndGrades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.models.OwnerGrade;
import com.example.bookingapp.utils.ApiUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerGradeCommentActivity extends AppCompatActivity {


    private Spinner spinnerGrade;
    private TextInputEditText editTextComment;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_grade_comment);

        spinnerGrade = findViewById(R.id.spinner_grade);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.accommodation_grades, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(adapter);

        editTextComment = findViewById(R.id.editTextComment);
        submitButton = findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });
    }


    private void handleSubmit() {
        // Proveri da li je selektovan item iz Spinner-a
        if (spinnerGrade.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a grade", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proveri da li polje za unos komentara sadrži najmanje 10 slova
        String comment = editTextComment.getText().toString().trim();
        if (comment.length() < 10) {
            Toast.makeText(this, "Comment must be at least 10 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        Long ownerId = getIntent().getLongExtra("ownerId", 0);
        Long guestId = getIntent().getLongExtra("guestId", 0);
        if (ownerId == 0 || guestId == 0) {
            Toast.makeText(this, "Nesto nije uredu!", Toast.LENGTH_SHORT).show();
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        OwnerGrade ownerGrade = new OwnerGrade(guestId,ownerId, Integer.parseInt(spinnerGrade.getSelectedItem().toString()),editTextComment.getText().toString(), LocalDateTime.now().format(formatter));
        Call<OwnerGrade> call = ApiUtils.getOwnerGradeService().addNew(ownerGrade);
        call.enqueue(new Callback<OwnerGrade>() {
            @Override
            public void onResponse(Call<OwnerGrade> call, Response<OwnerGrade> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OwnerGradeCommentActivity.this, "Grade submitted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Handle error response
                    Toast.makeText(OwnerGradeCommentActivity.this, "Failed to submit grade", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OwnerGrade> call, Throwable t) {
                // Handle failure
                Toast.makeText(OwnerGradeCommentActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Ako su oba uslova ispunjena, nastavi sa obradom
        // Na primer, pozovi metodu za slanje podataka ili izvrši neku drugu akciju
//        Toast.makeText(this, "Comment submitted successfully", Toast.LENGTH_SHORT).show();
//        finish();
    }
}