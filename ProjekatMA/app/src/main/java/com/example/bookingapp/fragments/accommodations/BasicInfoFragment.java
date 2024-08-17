package com.example.bookingapp.fragments.accommodations;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;

public class BasicInfoFragment extends Fragment {

    private EditText editTextName, editTextDescription;
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDACIJA DA HOTEL SA UNETIM IMENOM VEC POSTOJI
                //VALIDACIJA NPR MINIMUM 3 SLOVA ZA NAZIV
                //VALIDACIJA DA DESKRIPCIJA MORA DA IMA MINIMUM 20SLOVA
                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                if (name.equals("") || name.length() < 3) {
                    Toast.makeText(getContext(), "Name must contain minimum 3 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (description.equals("") || description.length() < 20) {
                    Toast.makeText(getContext(), "Description must contain minimum 20 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ((CreateAccommodationActivity) getActivity()).setBasicData(
                        editTextName.getText().toString(),
                        editTextDescription.getText().toString()
                );
                ((CreateAccommodationActivity) getActivity()).loadLocationFragment();
            }
        });

        return view;
    }
}
