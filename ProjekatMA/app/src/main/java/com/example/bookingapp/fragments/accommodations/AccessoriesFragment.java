package com.example.bookingapp.fragments.accommodations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.adapters.AccessoryAdapter;
import com.example.bookingapp.models.accommodations.Accessories;
import com.example.bookingapp.services.IAccessoriesService;
import com.example.bookingapp.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccessoriesFragment extends Fragment {


    private RecyclerView recyclerViewAccessories;
    private AccessoryAdapter adapter;
    private List<Accessories> accessoryList = new ArrayList<>();
    private Button buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accessories, container, false);

        recyclerViewAccessories = view.findViewById(R.id.recyclerViewAccessories);
        buttonSave = view.findViewById(R.id.buttonSave);
//
        recyclerViewAccessories.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AccessoryAdapter(accessoryList);
        recyclerViewAccessories.setAdapter(adapter);

        // Učitajte dodatke iz baze
        loadAccessoriesFromDatabase();

        buttonSave.setOnClickListener(v -> saveSelectedAccessories());

        return view;
    }

    private void loadAccessoriesFromDatabase() {
        IAccessoriesService accessoriesService = ApiUtils.getAccessoriesService();
        Call<List<Accessories>> call = accessoriesService.getAll();

        call.enqueue(new Callback<List<Accessories>>() {
            @Override
            public void onResponse(Call<List<Accessories>> call, Response<List<Accessories>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Očistite trenutnu listu i dodajte nove podatke
                    accessoryList.clear();
                    accessoryList.addAll(response.body());

                    // Obavestite adapter da su se podaci promenili
                    adapter.notifyDataSetChanged();
                } else {
                    // Rukovanje greškom ako odgovor nije uspešan
                    Toast.makeText(getContext(), "Failed to load accessories: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Accessories>> call, Throwable t) {
                // Rukovanje greškom (npr. problem sa mrežom)
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSelectedAccessories() {
        List<Accessories> selectedAccessories = new ArrayList<>();
        for (Accessories accessory : accessoryList) {
            if (accessory.isSelected()) {
                selectedAccessories.add(accessory);
            }
        }
        ((CreateAccommodationActivity) getActivity()).loadPhotosFragment();
        // Ovde sačuvajte izabrane dodatke, npr. u bazu ili pošaljite na backend
    }
}