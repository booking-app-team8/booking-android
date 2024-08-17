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
import com.example.bookingapp.models.AccommodationRequest;
import com.example.bookingapp.utils.ApiUtils;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationRequestAdapter extends ArrayAdapter<AccommodationRequest> {

    private Context context;
    private List<AccommodationRequest> requests;

    public AccommodationRequestAdapter(Context context, List<AccommodationRequest> requests){
        super(context, 0, requests);
        this.context = context;
        this.requests = requests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_accommodation_request, parent, false);
        }

        AccommodationRequest request = requests.get(position);

        ImageView imageViewPhoto = convertView.findViewById(R.id.imageViewPhoto);
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewId = convertView.findViewById(R.id.textViewId);
        TextView textViewAddress = convertView.findViewById(R.id.textViewAddress);
        TextView textViewOwner = convertView.findViewById(R.id.textViewOwner);
        Button buttonAccept = convertView.findViewById(R.id.buttonAccept);
        Button buttonCancel = convertView.findViewById(R.id.buttonCancel);

        textViewName.setText(request.getName());
        textViewId.setText(request.getId().toString());
        textViewAddress.setText(request.getAddress() + ", " + request.getCity() + ", " + request.getCountry());
        textViewOwner.setText("Owner: " + request.getOwner());

        String imageUrl = "http://172.20.10.5:8081/";
        Glide.with(context)
                .load(imageUrl + request.getPhotoUrl())
                .into(imageViewPhoto);
        Log.d("URL image", imageUrl + request.getPhotoUrl());

        buttonAccept.setOnClickListener(v -> {
            ApiUtils.getAccommodationRequestService().acceptAccommodationStatus(request.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Accommodation approved", Toast.LENGTH_SHORT).show();
                        requests.remove(position);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Failed to approve accommodation", Toast.LENGTH_SHORT).show();
                }
            });
        });

        buttonCancel.setOnClickListener(v -> {
            ApiUtils.getAccommodationRequestService().rejectAccommodationStatus(request.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Accommodation rejected", Toast.LENGTH_SHORT).show();
                        requests.remove(position);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Failed to reject accommodation", Toast.LENGTH_SHORT).show();
                }
            });
        });


        return convertView;
    }
}
