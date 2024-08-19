package com.example.bookingapp.activities.commentsAndGrades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bookingapp.R;

public class AccommodationGradeCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_grade_comment);
        Spinner spinnerGrade = findViewById(R.id.spinner_grade);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.accommodation_grades, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(adapter);
    }
}