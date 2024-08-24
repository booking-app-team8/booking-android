package com.example.bookingapp.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;

import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.AccommodationViewHolder> {

    public interface OnShowMoreClickListener {
        void onShowMoreClick(AccommodationSearchRequestDTO accommodation);
    }

    private List<AccommodationSearchRequestDTO> accommodations; // Ovo je lista smeštaja koja će se prikazati

    private OnShowMoreClickListener listener;

    public AccommodationAdapter(List<AccommodationSearchRequestDTO> accommodations, OnShowMoreClickListener listener) {
        this.accommodations = accommodations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_accommodation, parent, false);
        return new AccommodationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationViewHolder holder, int position) {
        AccommodationSearchRequestDTO accommodation = accommodations.get(position);
        // Postavi podatke u karticu
        holder.tvApartmentName.setText(accommodation.getName());
        holder.tvApartmentAddress.setText(accommodation.getLocation().getAddress()+" , "+
                accommodation.getLocation().getCity()+" , "+accommodation.getLocation().getCountry());
        holder.tvMaxGuests.setText("Max guests: " + accommodation.getMaxGuests());
        holder.tvPricePerUnit.setText("Price per unit: $" + accommodation.getPricePerUnit());
        holder.tvAmenities.setText("Amenities: " + accommodation.getAccessories().get(0) + " , " +
                accommodation.getAccessories().get(1) + " , " + accommodation.getAccessories().get(2));
        holder.tvTotalPrice.setText("Total: $" + accommodation.getTotalPrice());
        holder.tvAverageRating.setText("Rating: " + accommodation.getAverageGrade() + "/5");
        // Setuj sliku i "Show more" dugme po potrebi

        holder.btnShowMore.setOnClickListener(v -> {
            if (listener != null) {
                listener.onShowMoreClick(accommodation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    public static class AccommodationViewHolder extends RecyclerView.ViewHolder {
        ImageView ivApartmentImage;
        TextView tvApartmentName, tvApartmentAddress, tvMaxGuests, tvPricePerUnit, tvAmenities, tvTotalPrice, tvAverageRating;
        Button btnShowMore;

        public AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivApartmentImage = itemView.findViewById(R.id.iv_apartment_image);
            tvApartmentName = itemView.findViewById(R.id.tv_apartment_name);
            tvApartmentAddress = itemView.findViewById(R.id.tv_apartment_address);
            tvMaxGuests = itemView.findViewById(R.id.tv_max_guests);
            tvPricePerUnit = itemView.findViewById(R.id.tv_price_per_unit);
            tvAmenities = itemView.findViewById(R.id.tv_amenities);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvAverageRating = itemView.findViewById(R.id.tv_average_rating);
            btnShowMore = itemView.findViewById(R.id.btn_show_more);
        }
    }
}
