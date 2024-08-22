package com.example.bookingapp.activities.grades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.adapters.ViewPageGradesAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReporetdCommentsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPageGradesAdapter viewPageGradesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporetd_comments);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPageGradesAdapter = new ViewPageGradesAdapter(this);
        viewPager.setAdapter(viewPageGradesAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Owner grades");
                    break;
                case 1:
                    tab.setText("Accommodation grades");
                    break;
                case 2:
                    tab.setText("Reported comments");
                    break;
            }
        }).attach();
    }

}