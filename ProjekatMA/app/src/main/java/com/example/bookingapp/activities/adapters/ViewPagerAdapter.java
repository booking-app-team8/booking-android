package com.example.bookingapp.activities.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookingapp.fragments.reservations.FavoriteFragment;
import com.example.bookingapp.fragments.reservations.HostReservationsFragment;
import com.example.bookingapp.fragments.reservations.RequestsFragment;
import com.example.bookingapp.fragments.reservations.ReservationsFragment;
import com.example.bookingapp.utils.AuthService;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final String userRole;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        // Pretpostavljam da ovako dobijaš trenutnu ulogu korisnika
        this.userRole = AuthService.getCurrentUser().getRole().toString();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (userRole.equals("GUEST")) {
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
        } else if (userRole.equals("OWNER")) {
            switch (position) {
                case 0:
                    return new RequestsFragment();
                case 1:
                    return new HostReservationsFragment();
                default:
                    return new RequestsFragment();
            }
        } else {
            return new RequestsFragment(); // default fragment
        }
    }

    @Override
    public int getItemCount() {
        return userRole.equals("GUEST") ? 3 : 2;
    }
}
