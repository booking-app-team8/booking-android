package com.example.bookingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookingapp.activities.adapters.AccommodationAdapter;
import com.example.bookingapp.models.accommodations.AccommodationSearchRequestDTO;

import java.util.List;

public class AccommodationSearchAdapter extends ArrayAdapter<AccommodationSearchRequestDTO> {

    public interface OnShowMoreClickListener {
        void onShowMoreClick(AccommodationSearchRequestDTO accommodation);
    }

    private Context context;
    private List<AccommodationSearchRequestDTO> accommodationList;
    private OnShowMoreClickListener listener;



    public AccommodationSearchAdapter(Context context, List<AccommodationSearchRequestDTO> accommodationList, OnShowMoreClickListener listener) {
        super(context, 0, accommodationList);
        this.context = context;
        this.accommodationList = accommodationList;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Recikliranje prikaza (view recycling)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_accommodation, parent, false);
        }

        // Dohvatanje podataka za trenutni smeštaj
        AccommodationSearchRequestDTO accommodation = accommodationList.get(position);
//        Toast.makeText(context, "accId:"+accommodation.getName(), Toast.LENGTH_SHORT).show();


        // Pronalaženje elemenata u prikazu
        TextView tvName = convertView.findViewById(R.id.tv_apartment_name);
        TextView tvAddress = convertView.findViewById(R.id.tv_apartment_address);
        TextView tvGuests = convertView.findViewById(R.id.tv_max_guests);
        TextView tvPrice = convertView.findViewById(R.id.tv_price_per_unit);
        TextView tvTotalPrice = convertView.findViewById(R.id.tv_total_price);
        TextView tvRating = convertView.findViewById(R.id.tv_average_rating);
        TextView tvAmenities = convertView.findViewById(R.id.tv_amenities);
        ImageView ivImage = convertView.findViewById(R.id.iv_apartment_image);
        Button btnShowMore = convertView.findViewById(R.id.btn_show_more);

        // Popunjavanje elemenata
        tvName.setText(accommodation.getName());
        tvAddress.setText(accommodation.getLocation().getAddress() + ", " + accommodation.getLocation().getCity());
        tvGuests.setText(String.valueOf(accommodation.getMaxGuests()));
        tvPrice.setText(String.format(String.valueOf(accommodation.getPricePerUnit())));
        tvTotalPrice.setText(String.format("$%s", accommodation.getTotalPrice()));
        tvRating.setText(String.valueOf(accommodation.getAverageGrade()));
        tvAmenities.setText(accommodation.getAccessories().get(0).getAccessories() + ", " + accommodation.getAccessories().get(1).getAccessories());

        // Učitavanje slike pomoću Picasso biblioteke
        //Picasso.get().load(accommodation.getImageUrl()).into(ivImage);
//        String imageUrl = "http://172.20.10.5:8081/"; BOKI
        String imageUrl = "http://192.168.0.15:8081/";
        String newPath = accommodation.getPhoto().getPath();
        if (!accommodation.getPhoto().getPath().contains(".jpg") && !(accommodation.getPhoto().getPath().contains(".png"))) {
            newPath = newPath + ".jpg";
        }
        Glide.with(context)
                .load(imageUrl + newPath)
                .into(ivImage);
        Log.d("URL image", imageUrl + newPath);

        // Implementacija za dugme "Show more"
        btnShowMore.setOnClickListener(v -> {
//            Toast.makeText(context, "stisnuo", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onShowMoreClick(accommodation);
            }
        });

        return convertView;
    }
}
