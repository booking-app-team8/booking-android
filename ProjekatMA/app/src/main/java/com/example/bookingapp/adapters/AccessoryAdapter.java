package com.example.bookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.R;

import com.example.bookingapp.models.accommodations.Accessories;

import java.util.List;

public class AccessoryAdapter extends RecyclerView.Adapter<AccessoryAdapter.AccessoryViewHolder> {

    private List<Accessories> accessoryList;

    public AccessoryAdapter(List<Accessories> accessoryList) {
        this.accessoryList = accessoryList;
    }

    @NonNull
    @Override
    public AccessoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accessory, parent, false);
        return new AccessoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoryViewHolder holder, int position) {
        Accessories accessory = accessoryList.get(position);
        holder.textViewName.setText(accessory.getName());
        holder.checkBox.setChecked(accessory.isSelected());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> accessory.setSelected(isChecked));
    }

    @Override
    public int getItemCount() {
        return accessoryList.size();
    }

    public static class AccessoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        CheckBox checkBox;

        public AccessoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}

