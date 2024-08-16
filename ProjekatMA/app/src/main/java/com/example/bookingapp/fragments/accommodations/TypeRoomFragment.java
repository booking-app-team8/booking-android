package com.example.bookingapp.fragments.accommodations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.models.accommodations.PriceUnit;
import com.example.bookingapp.models.accommodations.TypeOfAccommodation;


public class TypeRoomFragment extends Fragment {

    private EditText editTextMinGuests, editTextMaxGuests, editTextCancellationDays, editTextDefaultedPrice;
    private Spinner spinnerAccommodationType;
    private Spinner spinnerPricePerUnit;
    private Button buttonSaveDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_room, container, false);

        editTextMinGuests = view.findViewById(R.id.editTextMinGuests);
        editTextMaxGuests = view.findViewById(R.id.editTextMaxGuests);
        editTextCancellationDays = view.findViewById(R.id.editTextCancellationDays);
        spinnerAccommodationType = view.findViewById(R.id.spinnerAccommodationType);
        spinnerPricePerUnit = view.findViewById(R.id.spinnerPricePerUnit);
        editTextDefaultedPrice = view.findViewById(R.id.editTextDefaultedPrice);

        buttonSaveDetails = view.findViewById(R.id.buttonSaveDetails);

        // Postavljanje opcija za spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.accommodation_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccommodationType.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.price_per_unit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPricePerUnit.setAdapter(adapter2);


        buttonSaveDetails.setOnClickListener(v -> saveAccommodationDetails());

        return view;
    }

    private void saveAccommodationDetails() {
        // Dohvatanje unetih podataka
        String minGuests = editTextMinGuests.getText().toString().trim();
        String maxGuests = editTextMaxGuests.getText().toString().trim();
        String cancellationDays = editTextCancellationDays.getText().toString().trim();
        String accommodationType = spinnerAccommodationType.getSelectedItem().toString();
        String priceUnit = spinnerPricePerUnit.getSelectedItem().toString();
        String defaultedPrice = editTextDefaultedPrice.getText().toString();

        // Validacija podataka (primer)
        if (minGuests.isEmpty() || maxGuests.isEmpty() || cancellationDays.isEmpty() || priceUnit.isEmpty() ||
                defaultedPrice.isEmpty() || accommodationType.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (minGuests.equals("0")) {
            Toast.makeText(getContext(), "Number of guests can't be 0!", Toast.LENGTH_SHORT).show();
            return;
        }
        int minGuestsInt = Integer.parseInt(minGuests);
        int maxGuestsInt = Integer.parseInt(maxGuests);
        int cancellationDaysInt = Integer.parseInt(cancellationDays);
        double defaultedPriceDouble = Double.parseDouble(defaultedPrice);

        if (cancellationDaysInt == 0) {
            Toast.makeText(getContext(), "Cancellation days before reservation can't be 0!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (defaultedPriceDouble == 0) {
            Toast.makeText(getContext(), "Defaulted price can't be 0!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (minGuestsInt >= maxGuestsInt) {
            Toast.makeText(getContext(), "Min guests cannot be greater than max guests", Toast.LENGTH_SHORT).show();
            return;
        }
        ((CreateAccommodationActivity) getActivity()).setTypeInformation(minGuestsInt, maxGuestsInt, cancellationDaysInt, TypeOfAccommodation.valueOf(accommodationType.toUpperCase()), PriceUnit.valueOf(priceUnit.toUpperCase()), defaultedPriceDouble);
        ((CreateAccommodationActivity) getActivity()).loadTimeSlots();

        // Sada imate sve podatke koje možete sačuvati ili proslediti dalje
        // Na primer, možete ih proslediti nekoj aktivnosti ili ih sačuvati u bazi podataka
    }


}