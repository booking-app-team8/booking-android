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
import com.example.bookingapp.activities.adapters.GradesAdapter;
import com.example.bookingapp.activities.adapters.ReservationAdapter;
import com.example.bookingapp.models.grades.OwnerGrade;
import com.example.bookingapp.models.reservations.ReservationGetFrontDTO;
import com.example.bookingapp.services.IGradesService;
import com.example.bookingapp.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerGradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerGradesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;

    private IGradesService gradesService;

    public OwnerGradesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerGradesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerGradesFragment newInstance(String param1, String param2) {
        OwnerGradesFragment fragment = new OwnerGradesFragment();
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
        Log.d("Test", "onCreateView is called");
        View view = inflater.inflate(R.layout.fragment_owner_grades, container, false);

        listView = view.findViewById(R.id.lv_grades);
        gradesService = ApiUtils.getGradesService();

        loadOwnerGrades();

        return view;
    }

    private void loadOwnerGrades(){
        gradesService.getAllOwnerGrades().enqueue(new Callback<List<OwnerGrade>>() {
            @Override
            public void onResponse(Call<List<OwnerGrade>> call, Response<List<OwnerGrade>> response) {
                if (response.isSuccessful()) {
                    List<OwnerGrade> grades = response.body();
                    if (grades != null) {
                        Log.d("OwnerGradesFragment", "Grades loaded successfully, number of grades: " + grades.size());
                        listView.setAdapter(new GradesAdapter(getContext(), grades));
                    } else {
                        Log.d("OwnerGradesFragment", "Grades list is null");
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load reservations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OwnerGrade>> call, Throwable t) {
                Log.d("OwnerGradesFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void loadOwnerGrades(){
//        gradesService.getAllOwnerGrades().enqueue(new Callback<List<OwnerGrade>>() {
//            @Override
//            public void onResponse(Call<List<OwnerGrade>> call, Response<List<OwnerGrade>> response) {
//                if (response.isSuccessful()) {
//                    List<OwnerGrade> grades = response.body();
//                    if (grades != null) {
//                        // Napuni ListView sa podacima
//                        listView.setAdapter(new GradesAdapter(getContext(), grades));
//                    }
//                } else {
//                    Toast.makeText(getContext(), "Failed to load reservations", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<OwnerGrade>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}