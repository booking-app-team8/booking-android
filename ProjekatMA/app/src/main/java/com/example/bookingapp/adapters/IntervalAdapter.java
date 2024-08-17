package com.example.bookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.models.accommodations.Pricelist;
import com.example.bookingapp.R;
import java.util.List;

public class IntervalAdapter extends RecyclerView.Adapter<IntervalAdapter.IntervalViewHolder> {

    public final List<Pricelist> priceIntervalList;

    public IntervalAdapter(List<Pricelist> priceIntervalList) {
        this.priceIntervalList = priceIntervalList;
    }

    @NonNull
    @Override
    public IntervalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_interval, parent, false);
        return new IntervalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntervalViewHolder holder, int position) {
        Pricelist interval = priceIntervalList.get(position);
        holder.startDateTextView.setText(interval.startDate.toString());
        holder.endDateTextView.setText(interval.endDate.toString());
        holder.priceTextView.setText(String.format("$%.2f", interval.price));

        // Postavljanje listener-a za dugme za uklanjanje
        holder.deleteButton.setOnClickListener(v -> {
            priceIntervalList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, priceIntervalList.size());
        });
    }

    @Override
    public int getItemCount() {
        return priceIntervalList.size();
    }

    // ViewHolder za prikaz svakog intervala
    public static class IntervalViewHolder extends RecyclerView.ViewHolder {

        TextView startDateTextView, endDateTextView, priceTextView;
        Button deleteButton;

        public IntervalViewHolder(@NonNull View itemView) {
            super(itemView);
            startDateTextView = itemView.findViewById(R.id.tvStartDate);
            endDateTextView = itemView.findViewById(R.id.tvEndDate);
            priceTextView = itemView.findViewById(R.id.tvPrice);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}