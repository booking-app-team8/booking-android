package com.example.bookingapp.activities.reservations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HostReservationsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_reservations);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_back);

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

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Requests");
                    break;
                case 1:
                    tab.setText("Reservations");
                    break;
            }
        }).attach();
    }
}