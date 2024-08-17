package com.example.bookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;

import java.util.List;

public class AccommodationSearchAdapter extends ArrayAdapter<AccommodationSearchRequestDTO> {

    private Context context;
    private List<AccommodationSearchRequestDTO> accommodationList;

    public AccommodationSearchAdapter(Context context, List<AccommodationSearchRequestDTO> accommodationList) {
        super(context, 0, accommodationList);
        this.context = context;
        this.accommodationList = accommodationList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Recikliranje prikaza (view recycling)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_accommodation, parent, false);
        }

        // Dohvatanje podataka za trenutni smeštaj
        AccommodationSearchRequestDTO accommodation = accommodationList.get(position);

        // Pronalaženje elemenata u prikazu
        TextView tvName = convertView.findViewById(R.id.tv_apartment_name);
        TextView tvAddress = convertView.findViewById(R.id.tv_apartment_address);
        TextView tvGuests = convertView.findViewById(R.id.tv_max_guests);
        TextView tvPrice = convertView.findViewById(R.id.tv_price_per_unit);
        TextView tvTotalPrice = convertView.findViewById(R.id.tv_total_price);
        TextView tvRating = convertView.findViewById(R.id.tv_average_rating);
        ImageView ivImage = convertView.findViewById(R.id.iv_apartment_image);
        Button btnShowMore = convertView.findViewById(R.id.btn_show_more);

        // Popunjavanje elemenata
        tvName.setText(accommodation.getName());
        tvAddress.setText(accommodation.getLocation().getAddress() + ", " + accommodation.getLocation().getCity());
        tvGuests.setText(String.valueOf(accommodation.getMaxGuests()));
        tvPrice.setText(String.format("$%s per night", accommodation.getPricePerUnit()));
        tvTotalPrice.setText(String.format("$%s", accommodation.getTotalPrice()));
        tvRating.setText(String.valueOf(accommodation.getAverageGrade()));

        // Učitavanje slike pomoću Picasso biblioteke
        //Picasso.get().load(accommodation.getImageUrl()).into(ivImage);

        // Implementacija za dugme "Show more"
        btnShowMore.setOnClickListener(v -> {
            // Implementacija za prikaz detalja o smeštaju
        });

        return convertView;
    }
}
