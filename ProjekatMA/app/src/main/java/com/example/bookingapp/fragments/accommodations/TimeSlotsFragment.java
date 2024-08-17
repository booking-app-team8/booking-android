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
import android.widget.TextView;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.adapters.TimeSlotAdapter;
import com.example.bookingapp.models.accommodations.TimeSlot;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;


public class TimeSlotsFragment extends Fragment {

    private RecyclerView recyclerViewTimeSlots;
    private TimeSlotAdapter timeSlotAdapter;
    private List<TimeSlot> timeSlotList;

    private TextView tvSelectedStartDate, tvSelectedEndDate;
    private String startDate, endDate;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_slots, container, false);

        tvSelectedStartDate = view.findViewById(R.id.tvSelectedStartDate);
        tvSelectedEndDate = view.findViewById(R.id.tvSelectedEndDate);
        recyclerViewTimeSlots = view.findViewById(R.id.recyclerViewTimeSlots);
        recyclerViewTimeSlots.setLayoutManager(new LinearLayoutManager(getContext()));
        timeSlotList = new ArrayList<>();
        timeSlotAdapter = new TimeSlotAdapter(timeSlotList);
        recyclerViewTimeSlots.setAdapter(timeSlotAdapter);

        Button btnAddTimeSlot = view.findViewById(R.id.btnAddTimeSlot);
        btnAddTimeSlot.setOnClickListener(v -> showStartDatePicker());

        Button nextButton = view.findViewById(R.id.btnNext);
        nextButton.setOnClickListener(v -> {
            ((CreateAccommodationActivity) getActivity()).setTimeSlots(timeSlotList);
            ((CreateAccommodationActivity) getActivity()).loadPricelistFragment();
        });


        return view;
    }

    private void showStartDatePicker() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
//                (view, year, month, dayOfMonth) -> {
//                    startDate = dayOfMonth + "/" + (month + 1) + "/" + year;
//                    tvSelectedStartDate.setText("Start Date: " + startDate);
//                    showEndDatePicker(); // Prikaz end datuma nakon odabira start datuma
//                }, 2023, 0, 1);
//        datePickerDialog.show();
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    startDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    tvSelectedStartDate.setText("Start Date: " + startDate);
                    showEndDatePicker();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showEndDatePicker() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
//                (view, year, month, dayOfMonth) -> {
//                    endDate = dayOfMonth + "/" + (month + 1) + "/" + year;
//                    tvSelectedEndDate.setText("End Date: " + endDate);
//                    addTimeSlot();
//                }, 2023, 0, 1);
//        datePickerDialog.show();
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    endDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    tvSelectedEndDate.setText("End Date: " + endDate);
//                    showEndDatePicker();
                    addTimeSlot();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    private boolean isDateInPast(LocalDate dateStr) {
        return dateStr.isBefore(LocalDate.now());

    }

    private boolean isEndDateBeforeStartDate(LocalDate startDateStr, LocalDate endDateStr) {
        return endDateStr.isBefore(startDateStr);

    }

    public boolean checkIfAlreadyExist(String start, String end) {
        for (TimeSlot timeSlot: timeSlotList) {
            if (timeSlot.startDate.equals(start) && timeSlot.endDate.equals(end)) {
                return true;
            }
        }
        return false;
    }

    private void addTimeSlot() {
        LocalDate startDateConvert;
        LocalDate endDateConvert;
        try {
            startDateConvert = LocalDate.parse(startDate, formatter);
            endDateConvert = LocalDate.parse(endDate, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date format. Please use yyyy-MM-dd.", Toast.LENGTH_SHORT).show();
            return; // Prekinite ako datum nije validan
        }
        if (isDateInPast(startDateConvert)) {
            Toast.makeText(getContext(), "Start date cannot be in the past!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEndDateBeforeStartDate(startDateConvert, endDateConvert)) {
            Toast.makeText(getContext(), "End date cannot be before start date!", Toast.LENGTH_SHORT).show();
            return;
        }

        //PRVO PROVERIM DA LI IMA ISTI VEC
        if (checkIfAlreadyExist(startDate, endDate)) {
            Toast.makeText(getContext(), "Same time slot already exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (oneOfThemInExisted(startDateConvert, endDateConvert)){
            Toast.makeText(getContext(), "Invalid time slot input!", Toast.LENGTH_SHORT).show();
            return;
        }

        TimeSlot timeSlot = new TimeSlot(startDate, endDate);
        timeSlotList.add(timeSlot);
        timeSlotAdapter.notifyDataSetChanged();
    }

    private boolean oneOfThemInExisted(LocalDate startDateConvert, LocalDate endDateConvert) {
        for (TimeSlot timeSlot: timeSlotList) {
            LocalDate start = LocalDate.parse(timeSlot.startDate, formatter);
            LocalDate end = LocalDate.parse(timeSlot.endDate, formatter);
            if (startDateConvert.isEqual(endDateConvert)) {
                return false;
            }
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