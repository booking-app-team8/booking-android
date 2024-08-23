package com.example.bookingapp.fragments.reservations;

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
import com.example.bookingapp.activities.adapters.ReportedOwnerGradesAdapter;
import com.example.bookingapp.activities.adapters.ReservationRequestsAdapter;
import com.example.bookingapp.dtos.grades.OwnerCommentDTO;
import com.example.bookingapp.dtos.reservations.ReservationRequestsGetDTO;
import com.example.bookingapp.services.IReservationRequestService;
import com.example.bookingapp.utils.ApiUtils;
import com.example.bookingapp.utils.AuthService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private Button btnAll;
    private Button btnPending;
    private IReservationRequestService requestService;

    public RequestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestsFragment newInstance(String param1, String param2) {
        RequestsFragment fragment = new RequestsFragment();
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
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        listView = view.findViewById(R.id.lv_reservation_requests);
        btnAll = view.findViewById(R.id.btn_all);
        btnPending = view.findViewById(R.id.btn_created);

        requestService = ApiUtils.getReservationRequestService();

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllRequests();
            }
        });

        return view;
    }

    private void loadAllRequests(){
        String email = AuthService.getCurrentUser().getEmail();

        requestService.getAllRequests(email).enqueue(new Callback<List<ReservationRequestsGetDTO>>() {
            @Override
            public void onResponse(Call<List<ReservationRequestsGetDTO>> call, Response<List<ReservationRequestsGetDTO>> response) {
                if (response.isSuccessful()) {
                    List<ReservationRequestsGetDTO> requests = response.body();
                    if (requests != null) {
                        Log.d("RequestsFragment", "Requests loaded successfully, number of requests: " + requests.size());
                        listView.setAdapter(new ReservationRequestsAdapter(getContext(), requests));
                    } else {
                        Log.d("RequestsFragment", "Requests list is null");
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReservationRequestsGetDTO>> call, Throwable t) {
                Log.d("RequestsFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPendingRequests(){

    }
}