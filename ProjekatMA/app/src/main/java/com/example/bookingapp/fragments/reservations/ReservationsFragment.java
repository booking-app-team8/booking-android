package com.example.bookingapp.fragments.reservations;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.adapters.ReservationAdapter;
import com.example.bookingapp.activities.commentsAndGrades.AccommodationGradeCommentActivity;
import com.example.bookingapp.activities.commentsAndGrades.OwnerGradeCommentActivity;
import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;
import com.example.bookingapp.models.accommodations.TimeSlot;
import com.example.bookingapp.models.enums.ReservationStatus;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;
import com.example.bookingapp.services.IReservationService;
import com.example.bookingapp.utils.ApiUtils;
import com.example.bookingapp.utils.AuthService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationsFragment extends Fragment implements ReservationAdapter.OnButtonClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<ReservationGetFrontDTO> reservations = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;

    private ReservationAdapter adapter;

    private IReservationService reservationService;

    public ReservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationsFragment newInstance(String param1, String param2) {
        ReservationsFragment fragment = new ReservationsFragment();
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
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        listView = view.findViewById(R.id.lv_reservations);
        reservationService = ApiUtils.getReservationService();

        // Pozivanje metode za učitavanje rezervacija
        adapter = new ReservationAdapter(getContext(), new ArrayList<>(), this);
        listView.setAdapter(adapter);

        loadReservations();




        return view;
    }

    private void loadReservations() {
//        String email = AuthService.getCurrentUser().getEmail();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", null);
        Log.d("Mail", "Mail: " + email);

        reservationService.getGuestReservations(email).enqueue(new Callback<List<ReservationGetFrontDTO>>() {
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

    @Override
    public void onRateOwnerClick(Long id, Long guestId) {
        getOwnerId(id, new ReservationAdapter.OwnerIdCallback() {
            @Override
            public void onOwnerIdReceived(Long ownerId) {
                getOwnersAccommodations(ownerId, new ReservationAdapter.AccommodationsCallback() {
                    @Override
                    public void onAccommodationsReceived(List<AccommodationSearchRequestDTO> accommodations) {
                        boolean canComment = false;
                        for (AccommodationSearchRequestDTO acc : accommodations) {
                            for (ReservationGetFrontDTO res : reservations) {
                                if (acc.getId().equals(res.getAccommodation().getId()) && res.getReservationStatus().equals(ReservationStatus.FINISHED)) {
                                    canComment = true;
                                    break;
                                }
                            }
                            if (canComment) break;
                        }
                        if (canComment) {
                            Intent intent = new Intent(getContext(), OwnerGradeCommentActivity.class);
                            intent.putExtra("ownerId", ownerId);
                            intent.putExtra("guestId", guestId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "You cannot comment on this owner", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRateAccommodationClick(Long guestId, Long accommodationId) {
//        Toast.makeText(getContext(), "accId:" + accommodationId, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), "guestId:" + guestId, Toast.LENGTH_SHORT).show();
        boolean canComment = false;
        for (ReservationGetFrontDTO res: reservations) {
            if (res.getAccommodation().getId().equals(accommodationId) && res.getReservationStatus().equals(ReservationStatus.FINISHED)) {
                {
//                    Toast.makeText(getContext(), "Moze", Toast.LENGTH_SHORT).show();
                    TimeSlot timeSlot = res.getTimeSlot();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern according to your date format
                    LocalDate endDate = LocalDate.parse(timeSlot.getEndDate(), formatter);

                    // Get the current date
                    LocalDate currentDate = LocalDate.now();

                    // Calculate the difference in days between the end date and the current date
                    long daysBetween = ChronoUnit.DAYS.between(endDate, currentDate);

                    // Check if more than 7 days have passed
                    if (daysBetween > 7) {
                        Toast.makeText(getContext(), "More than 7 days have passed. You cannot rate this accommodation." + daysBetween, Toast.LENGTH_SHORT).show();
                    } else {
                        // Proceed to rate the accommodation
                        canComment = true;
                        break;
                    }

                }
            }
        }
        if (canComment) {
            Toast.makeText(getContext(), "You can rate this accommodation.", Toast.LENGTH_SHORT).show();
            // Add your rating logic here
            Intent intent = new Intent(getContext(), AccommodationGradeCommentActivity.class);
            intent.putExtra("accommodationId", accommodationId);
            intent.putExtra("guestId", guestId);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "You can't rate this accommodation.", Toast.LENGTH_SHORT).show();
        }


    }

    private void getOwnerId(Long id, ReservationAdapter.OwnerIdCallback callback) {
        ApiUtils.getIAccommodationService().forGradeOwner(id).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onOwnerIdReceived(response.body());
                } else {
                    callback.onError(new Throwable("Failed to get owner ID"));
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    private void getOwnersAccommodations(Long ownerId, ReservationAdapter.AccommodationsCallback callback) {
        ApiUtils.getIAccommodationService().getOwnerAccommodations(ownerId).enqueue(new Callback<List<AccommodationSearchRequestDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationSearchRequestDTO>> call, Response<List<AccommodationSearchRequestDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onAccommodationsReceived(response.body());
                } else {
                    callback.onError(new Throwable("Failed to get accommodations"));
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationSearchRequestDTO>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
