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
import android.widget.EditText;

import com.example.bookingapp.R;

import java.util.ArrayList;


public class TimeSlotPricelistFragment extends Fragment {
    private EditText startDateInput, endDateInput, priceStartDateInput, priceEndDateInput, priceInput;
    private Button addTimeSlotButton, addPriceButton;
    private RecyclerView timeSlotsRecyclerView, priceIntervalsRecyclerView;
    private TimeSlotAdapter timeSlotAdapter;
    private PriceIntervalAdapter priceIntervalAdapter;
    private List<TimeSlot> timeSlotList = new ArrayList<>();
    private List<PriceInterval> priceIntervalList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_slot_pricelist, container, false);

        startDateInput = view.findViewById(R.id.startDateInput);
        endDateInput = view.findViewById(R.id.endDateInput);
        priceStartDateInput = view.findViewById(R.id.priceStartDateInput);
        priceEndDateInput = view.findViewById(R.id.priceEndDateInput);
        priceInput = view.findViewById(R.id.priceInput);
        addTimeSlotButton = view.findViewById(R.id.addTimeSlotButton);
        addPriceButton = view.findViewById(R.id.addPriceButton);
        timeSlotsRecyclerView = view.findViewById(R.id.timeSlotsRecyclerView);
        priceIntervalsRecyclerView = view.findViewById(R.id.priceIntervalsRecyclerView);

        timeSlotAdapter = new TimeSlotAdapter(timeSlotList);
        priceIntervalAdapter = new PriceIntervalAdapter(priceIntervalList);

        timeSlotsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        priceIntervalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeSlotsRecyclerView.setAdapter(timeSlotAdapter);
        priceIntervalsRecyclerView.setAdapter(priceIntervalAdapter);

        addTimeSlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = startDateInput.getText().toString();
                String endDate = endDateInput.getText().toString();
                timeSlotList.add(new TimeSlot(startDate, endDate));
                timeSlotAdapter.notifyDataSetChanged();
            }
        });

        addPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceStartDate = priceStartDateInput.getText().toString();
                String priceEndDate = priceEndDateInput.getText().toString();
                double price = Double.parseDouble(priceInput.getText().toString());
                priceIntervalList.add(new PriceInterval(priceStartDate, priceEndDate, price));
                priceIntervalAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

}