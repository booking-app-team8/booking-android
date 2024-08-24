package com.example.bookingapp.activities.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookingapp.fragments.grades.AccommodationGradesFragment;
import com.example.bookingapp.fragments.grades.OwnerGradesFragment;
import com.example.bookingapp.fragments.grades.ReportedGradesFragment;
import com.example.bookingapp.fragments.reservations.FavoriteFragment;
import com.example.bookingapp.fragments.reservations.RequestsFragment;
import com.example.bookingapp.fragments.reservations.ReservationsFragment;

public class ViewPageGradesAdapter extends FragmentStateAdapter {

    public ViewPageGradesAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OwnerGradesFragment();
            case 1:
                return new AccommodationGradesFragment();
            case 2:
                return new ReportedGradesFragment();
            default:
                return new OwnerGradesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
