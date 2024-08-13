package com.example.bookingapp.fragments.accommodations;

import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationFragment extends Fragment {

    private EditText addressEditText, cityEditText, countryEditText;
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        addressEditText = view.findViewById(R.id.editTextAddress);
        cityEditText = view.findViewById(R.id.editTextCity);
        countryEditText = view.findViewById(R.id.editTextCountry);
        nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String country = countryEditText.getText().toString();

                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocationName(address + ", " + city + ", " + country, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address location = addresses.get(0);
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
//                        textViewCoordinates.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                        System.out.println("Lat" + latitude);
                        System.out.println("Long" + longitude);
                        Toast.makeText(getContext(), "Latitude: \" + latitude + \"\\nLongitude: \" + longitude", Toast.LENGTH_SHORT).show();
                        ((CreateAccommodationActivity) getActivity()).loadAccessoriesFragment();
                    } else {
//                        textViewCoordinates.setText("Koordinate nisu pronađene za unetu adresu.");
                        Toast.makeText(getContext(), "Koordinate nisu pronađene za unetu adresu.", Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
//                    textViewCoordinates.setText("Greška pri dohvaćanju koordinata.");
                    Toast.makeText(getContext(), "Greška pri dohvaćanju koordinata.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}