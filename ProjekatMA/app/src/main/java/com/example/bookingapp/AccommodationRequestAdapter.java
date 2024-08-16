package com.example.bookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookingapp.models.AccommodationRequest;

import java.util.List;

public class AccommodationRequestAdapter extends ArrayAdapter<AccommodationRequest> {
    public AccommodationRequestAdapter(Context context, List<AccommodationRequest> requests) {
        super(context, 0, requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AccommodationRequest request = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_accommodation_request, parent, false);
        }

        // Lookup view for data population
        TextView listRequestId = convertView.findViewById(R.id.listRequestId);
        TextView listAccommodationName = convertView.findViewById(R.id.listAccommodationName);
        TextView listAccommodationAddress = convertView.findViewById(R.id.listAccommodationAddress);
        //ImageView listAccommodationPhoto = convertView.findViewById(R.id.listAccommodationPhoto);

        // Populate the data into the template view using the data object
        listRequestId.setText("Request ID: " + request.getId());
        listAccommodationName.setText("Accommodation Name: " + request.getName());
        listAccommodationAddress.setText("Accommodation Address: " + request.getAddress());

        /*
        // Load image using Picasso or another image loading library
        if (request.getPhoto() != null && request.getPhoto().getPath() != null) {
            Picasso.get().load(request.getPhoto().getPath()).into(listAccommodationPhoto);
        } else {
            listAccommodationPhoto.setImageResource(R.drawable.placeholder_image); // or any default image
        }
         */

        // Return the completed view to render on screen
        return convertView;
    }
}
