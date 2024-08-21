package com.example.bookingapp.activities.commentsAndGrades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.time.format.DateTimeFormatter;

public class AccommodationGradeCommentActivity extends AppCompatActivity {

    private Spinner spinnerGrade;
    private TextInputEditText editTextComment;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_grade_comment);
        Spinner spinnerGrade = findViewById(R.id.spinner_grade);
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



    }
}