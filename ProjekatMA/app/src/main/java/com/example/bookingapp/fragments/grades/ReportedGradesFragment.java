package com.example.bookingapp.fragments.grades;

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
import com.example.bookingapp.activities.adapters.ReportedAccommodationGradesAdapter;
import com.example.bookingapp.activities.adapters.ReportedOwnerGradesAdapter;
import com.example.bookingapp.dtos.grades.AccommodationCommentDTO;
import com.example.bookingapp.dtos.grades.OwnerCommentDTO;
import com.example.bookingapp.services.IGradesService;
import com.example.bookingapp.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportedGradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportedGradesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private Button btnOwners;
    private Button btnAccommodations;
    private IGradesService gradesService;

    public ReportedGradesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportedGradesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportedGradesFragment newInstance(String param1, String param2) {
        ReportedGradesFragment fragment = new ReportedGradesFragment();
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
        View view = inflater.inflate(R.layout.fragment_reported_grades, container, false);

        listView = view.findViewById(R.id.lv_reporetd_grades);
        btnOwners = view.findViewById(R.id.btn_owners);
        btnAccommodations = view.findViewById(R.id.btn_accommodations);

        gradesService = ApiUtils.getGradesService();

        btnOwners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadReportedOwnerGrades();
            }
        });

        btnAccommodations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadReportedAccommodationGrades();
            }
        });


        return view;
    }

    private void loadReportedOwnerGrades(){
        gradesService.getReportOwnerGrade().enqueue(new Callback<List<OwnerCommentDTO>>() {
            @Override
            public void onResponse(Call<List<OwnerCommentDTO>> call, Response<List<OwnerCommentDTO>> response) {
                if (response.isSuccessful()) {
                    List<OwnerCommentDTO> grades = response.body();
                    if (grades != null) {
                        Log.d("OwnerGradesFragment", "Grades loaded successfully, number of grades: " + grades.size());
                        listView.setAdapter(new ReportedOwnerGradesAdapter(getContext(), grades));
                    } else {
                        Log.d("OwnerGradesFragment", "Grades list is null");
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OwnerCommentDTO>> call, Throwable t) {
                Log.d("OwnerGradesFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReportedAccommodationGrades(){
        gradesService.getReportedAccommodationGrade().enqueue(new Callback<List<AccommodationCommentDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationCommentDTO>> call, Response<List<AccommodationCommentDTO>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationCommentDTO> grades = response.body();
                    if (grades != null) {
                        Log.d("OwnerGradesFragment", "Grades loaded successfully, number of grades: " + grades.size());
                        listView.setAdapter(new ReportedAccommodationGradesAdapter(getContext(), grades));
                    } else {
                        Log.d("OwnerGradesFragment", "Grades list is null");
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationCommentDTO>> call, Throwable t) {
                Log.d("OwnerGradesFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}