package com.example.bookingapp.activities.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookingapp.fragments.reservations.FavoriteFragment;
import com.example.bookingapp.fragments.reservations.RequestsFragment;
import com.example.bookingapp.fragments.reservations.ReservationsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RequestsFragment();
            case 1:
                return new ReservationsFragment();
            case 2:
                return new FavoriteFragment();
            default:
                return new RequestsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
