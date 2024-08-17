package com.example.bookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.R;
import com.example.bookingapp.models.accommodations.TimeSlot;

import java.util.List;
public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    private List<TimeSlot> timeSlotList;

    public TimeSlotAdapter(List<TimeSlot> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeslot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeSlot timeSlot = timeSlotList.get(position);
        holder.tvStartDate.setText(timeSlot.getStartDate().toString());
        holder.tvEndDate.setText(timeSlot.getEndDate().toString());

        // Uklanjanje slota
        holder.btnDelete.setOnClickListener(v -> {
            timeSlotList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, timeSlotList.size());
        });
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStartDate, tvEndDate;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
