package com.example.bookingapp.fragments.accommodations;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.adapters.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;


public class PhotosFragment extends Fragment {
    private static final int PICK_IMAGES_REQUEST = 1;
    private static final int MIN_PHOTOS_COUNT = 9;

    private RecyclerView recyclerViewPhotos;
    private PhotoAdapter photoAdapter;
    private List<Uri> photoUris = new ArrayList<>();
    private Button buttonSelectPhotos;

    private Button nextButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        recyclerViewPhotos = view.findViewById(R.id.recyclerViewPhotos);
        buttonSelectPhotos = view.findViewById(R.id.buttonSelectPhotos);
        nextButton = view.findViewById(R.id.buttonNext);

        recyclerViewPhotos.setLayoutManager(new LinearLayoutManager(getContext()));
        photoAdapter = new PhotoAdapter(photoUris, this::removePhoto);
        recyclerViewPhotos.setAdapter(photoAdapter);

        buttonSelectPhotos.setOnClickListener(v -> selectPhotos());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateAccommodationActivity) getActivity()).loadTypeFragment();
            }});

        return view;
    }

    private void selectPhotos() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGES_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        photoUris.add(imageUri);
                    }
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    photoUris.add(imageUri);
                }
                validatePhotoCount();
                photoAdapter.notifyDataSetChanged();
            }
        }
    }

    private void validatePhotoCount() {
        if (photoUris.size() < MIN_PHOTOS_COUNT) {
            Toast.makeText(getContext(), "You must select at least " + MIN_PHOTOS_COUNT + " photos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void removePhoto(int position) {
        photoUris.remove(position);
        photoAdapter.notifyItemRemoved(position);
        validatePhotoCount();
    }

}