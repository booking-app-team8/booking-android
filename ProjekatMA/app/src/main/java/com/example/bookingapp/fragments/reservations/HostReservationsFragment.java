package com.example.bookingapp.fragments.reservations;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.adapters.OwnerReservationAdapter;
import com.example.bookingapp.activities.adapters.ReservationAdapter;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;
import com.example.bookingapp.services.IReservationService;
import com.example.bookingapp.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostReservationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;

    private OwnerReservationAdapter adapter;

    private IReservationService reservationService;

    private List<ReservationGetFrontDTO> reservations = new ArrayList<>();

    public HostReservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HostReservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HostReservationsFragment newInstance(String param1, String param2) {
        HostReservationsFragment fragment = new HostReservationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_host_reservations, container, false);

        listView = view.findViewById(R.id.lv_reservations);
        reservationService = ApiUtils.getReservationService();

        adapter = new OwnerReservationAdapter(getContext(), new ArrayList<>());
        listView.setAdapter(adapter);

        loadReservations();

        return view;
    }

    private void loadReservations() {
//        String email = AuthService.getCurrentUser().getEmail();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", null);
        Log.d("Mail", "Mail: " + email);

        reservationService.getOwnerReservations(email).enqueue(new Callback<List<ReservationGetFrontDTO>>() {
            @Override
            public void onResponse(Call<List<ReservationGetFrontDTO>> call, Response<List<ReservationGetFrontDTO>> response) {
                if (response.isSuccessful()) {
                    reservations = response.body();
                    if (reservations != null) {
                        // Napuni ListView sa podacima

                        adapter.clear();
                        adapter.addAll(reservations);
                        adapter.notifyDataSetChanged();
//                        listView.setAdapter(new ReservationAdapter(getContext(), reservations));
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReservationGetFrontDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}