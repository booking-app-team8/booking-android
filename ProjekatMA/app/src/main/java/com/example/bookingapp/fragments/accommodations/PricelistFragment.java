package com.example.bookingapp.fragments.accommodations;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.adapters.IntervalAdapter;
import com.example.bookingapp.models.accommodations.Pricelist;
import com.example.bookingapp.models.accommodations.TimeSlot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PricelistFragment extends Fragment {
    private EditText startDateEditText, endDateEditText, priceEditText;
    private Button addIntervalButton;
    private RecyclerView intervalsRecyclerView;
    private IntervalAdapter intervalsAdapter;
    private List<Pricelist> priceIntervalList = new ArrayList<>();

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDate startDate = null;
    public LocalDate endDate = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pricelist, container, false);

        startDateEditText = view.findViewById(R.id.startDateEditText);
        endDateEditText = view.findViewById(R.id.endDateEditText);
        priceEditText = view.findViewById(R.id.priceEditText);
        addIntervalButton = view.findViewById(R.id.addIntervalButton);
        intervalsRecyclerView = view.findViewById(R.id.intervalsRecyclerView);

        startDateEditText.setInputType(InputType.TYPE_NULL);
        endDateEditText.setInputType(InputType.TYPE_NULL);

        intervalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        intervalsAdapter = new IntervalAdapter(priceIntervalList);
        intervalsRecyclerView.setAdapter(intervalsAdapter);

        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));

        addIntervalButton.setOnClickListener(v -> addInterval());

        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateAccommodationActivity) getActivity()).savePricelistData(priceIntervalList);
                Toast.makeText(getContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
                ((CreateAccommodationActivity) getActivity()).sendAccommodationDataToBackend();
            }
        });

        return view;
    }

    private void showDatePickerDialog(final EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    dateEditText.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    // Metoda za dodavanje intervala sa validacijom
    private void addInterval() {
        String startDateString = startDateEditText.getText().toString();
        String endDateString = endDateEditText.getText().toString();
        String priceString = priceEditText.getText().toString();

        try {
            startDate = LocalDate.parse(startDateString, formatter);
            endDate = LocalDate.parse(endDateString, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date format. Please use yyyy-MM-dd.", Toast.LENGTH_SHORT).show();
            return; // Prekinite ako datum nije validan
        }

        if (startDateString.isEmpty() || endDateString.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;

        }

        if (isDateInPast(startDate)) {
            Toast.makeText(getContext(), "Start date cannot be in the past!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEndDateBeforeStartDate(startDate, endDate)) {
            Toast.makeText(getContext(), "End date cannot be before start date!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceString);
        if (price == 0) {
            Toast.makeText(getContext(), "Price can't be zero!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkIfAlreadyExist(startDateString, endDateString)) {
            Toast.makeText(getContext(), "Same time slot already exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (oneOfThemInExisted(startDate, endDate)){
            Toast.makeText(getContext(), "Invalid time slot input!", Toast.LENGTH_SHORT).show();
            return;
        }


        Pricelist interval = new Pricelist(startDateString, endDateString, price);
        priceIntervalList.add(interval);
        intervalsAdapter.notifyDataSetChanged();
    }

    public boolean checkIfAlreadyExist(String start, String end) {
        for (Pricelist pricelist: priceIntervalList) {
            if (pricelist.startDate.equals(start) && pricelist.endDate.equals(end)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDateInPast(LocalDate dateStr) {
        return dateStr.isBefore(LocalDate.now());

    }

    private boolean isEndDateBeforeStartDate(LocalDate startDateStr, LocalDate endDateStr) {
        return endDateStr.isBefore(startDateStr);

    }

    private boolean oneOfThemInExisted(LocalDate startDateConvert, LocalDate endDateConvert) {
        for (Pricelist timeSlot: priceIntervalList) {
            LocalDate start = LocalDate.parse(timeSlot.startDate, formatter);
            LocalDate end = LocalDate.parse(timeSlot.endDate, formatter);
            //[ s ]
            if (startDateConvert.isAfter(start) && startDateConvert.isBefore(end)) {
                return true;
            }
            //[ e ]
            if (endDateConvert.isBefore(end) && endDateConvert.isAfter(start)) {
                return true;
            }
            //[s e]
            if (startDateConvert.isAfter(start) && endDateConvert.isBefore(end)) {
                return true;
            }
            //pocetni pre postojeceg pocetnog ali je krajnji u postojecem intervalu s [ e ]
            if ((startDateConvert.isBefore(start) || startDateConvert.isEqual(start)) && endDateConvert.isAfter(start) && (endDateConvert.isBefore(end)|| endDateConvert.isEqual(end))) {
                return true;
            }
            //van intervala s [ ] e
            if ((startDateConvert.isBefore(start) || startDateConvert.isEqual(start)) && (endDateConvert.isAfter(end) || endDateConvert.isEqual(end))) {
                return true;
            }
        }
        return false;
    }

}