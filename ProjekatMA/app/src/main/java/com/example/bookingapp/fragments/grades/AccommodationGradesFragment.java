package com.example.bookingapp.fragments.grades;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.adapters.AccommodationGradesAdapter;
import com.example.bookingapp.activities.adapters.GradesAdapter;
import com.example.bookingapp.dtos.grades.AccommodationCommentDTO;
import com.example.bookingapp.models.grades.OwnerGrade;
import com.example.bookingapp.services.IGradesService;
import com.example.bookingapp.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationGradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationGradesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;

    private IGradesService gradesService;

    public AccommodationGradesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccommodationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccommodationGradesFragment newInstance(String param1, String param2) {
        AccommodationGradesFragment fragment = new AccommodationGradesFragment();
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

        View view = inflater.inflate(R.layout.fragment_accommodation_grades, container, false);

        listView = view.findViewById(R.id.lv_grades);
        gradesService = ApiUtils.getGradesService();

        loadAccommodationGrades();

        return view;
    }

    private void loadAccommodationGrades(){
        gradesService.getAllAccommodationGrades().enqueue(new Callback<List<AccommodationCommentDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationCommentDTO>> call, Response<List<AccommodationCommentDTO>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationCommentDTO> grades = response.body();
                    if (grades != null) {
                        Log.d("AccommodationGradesFragment", "Grades loaded successfully, number of grades: " + grades.size());
                        listView.setAdapter(new AccommodationGradesAdapter(getContext(), grades));
                    } else {
                        Log.d("AccommodationGradesFragment", "Grades list is null");
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationCommentDTO>> call, Throwable t) {
                Log.d("AccommodationGradesFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}