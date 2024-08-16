package com.example.bookingapp.fragments.accommodations;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.content.ContentResolver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.activities.accommodations.CreateAccommodationActivity;
import com.example.bookingapp.adapters.PhotoAdapter;
import com.example.bookingapp.models.accommodations.Photo;
import com.example.bookingapp.services.IAccommodationService;
import com.example.bookingapp.utils.ApiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.webkit.MimeTypeMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class PhotosFragment extends Fragment {
    private static final int PICK_IMAGES_REQUEST = 1;
    private static final int MIN_PHOTOS_COUNT = 9;

    private RecyclerView recyclerViewPhotos;
    private PhotoAdapter photoAdapter;
    private List<Uri> photoUris = new ArrayList<>();
    private Button buttonSelectPhotos;

    private Button nextButton;

    private List<Photo> images = new ArrayList<>();


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
                List<String> fileNames = new ArrayList<>();
                for (Uri uri: photoUris) {
                    String[] split = uri.getPath().split("/");
                    int len = split.length;
                    String fileName = split[len-1];
                    fileNames.add(fileName);
                    images.add(new Photo("images/accommodations/"+fileName));
                }



                System.out.println(fileNames);
////                System.out.println(photoUris);
//                if (photoUris.size() >= MIN_PHOTOS_COUNT) {
//                    List<MultipartBody.Part> imageParts = prepareFileParts(photoUris);
//                    IAccommodationService service = ApiUtils.getAccommodationService();
//                    Call<Void> call = service.uploadImages(1L, imageParts);
//                    call.enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            if (response.isSuccessful()) {
//                                Toast.makeText(getContext(), "Images uploaded successfully", Toast.LENGTH_SHORT).show();
//                                ((CreateAccommodationActivity) getActivity()).loadTypeFragment();
//                            } else {
//                                Toast.makeText(getContext(), "Failed to upload images", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//                            Toast.makeText(getContext(), "Upload error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(getContext(), "You must select at least " + MIN_PHOTOS_COUNT + " photos.", Toast.LENGTH_SHORT).show();
//                }
//
                if (fileNames.size() < 9) {
                    Toast.makeText(getContext(), "Must choose minimum 9 images!", Toast.LENGTH_SHORT).show();
                    return;
                }

                MultipartBody.Part[] lista = prepareFileParts(photoUris);

                ((CreateAccommodationActivity) getActivity()).setPhotos(images, lista);
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
//                        System.out.println("Uri" + getFileName(imageUri));
                        photoUris.add(imageUri);
                    }
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
//                    System.out.println("Uri" + getFileName(imageUri));
                    photoUris.add(imageUri);
                }
//                validatePhotoCount();
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
        if (position >= 0 && position < photoUris.size()) {
            photoUris.remove(position);
            photoAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(getContext(), "Invalid photo position", Toast.LENGTH_SHORT).show();
        }
//        photoUris.remove(position);
//        photoAdapter.notifyItemRemoved(position);
//        validatePhotoCount();
    }


//    private MultipartBody.Part[] prepareFileParts(List<Uri> uris) {
//        MultipartBody.Part[] imageParts = new MultipartBody.Part[uris.size()];
//
//        for (int i = 0; i < uris.size(); i++) {
//            Uri imageUri = uris.get(i);
//
//            // Dobijanje MIME tipa
//            String mimeType = requireActivity().getContentResolver().getType(imageUri);
//            String extension = null;
//            if (mimeType != null) {
//                // Dobijanje ekstenzije iz MIME tipa
//                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
//            }
//
//            if (extension == null) {
//                // Ako nije moguće dobiti ekstenziju, koristi se podrazumevana ekstenzija (npr. jpg)
//                extension = "jpg";
//            }
//
//            // Dobijanje puta do fajla i dodavanje ekstenzije
//            String originalPath = getPathFromUri(imageUri);
//            String correctedPath = originalPath + "." + extension;
//            File file = new File(correctedPath);
//
//            // Kreiranje RequestBody i MultipartBody.Part objekata
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//            imageParts[i] = MultipartBody.Part.createFormData("photos", file.getName(), requestFile);
//        }
//
//        return imageParts;
//    }

    private MultipartBody.Part[] prepareFileParts(List<Uri> uris) {
        MultipartBody.Part[] imageParts = new MultipartBody.Part[uris.size()];

        for (int i = 0; i < uris.size(); i++) {
            Uri imageUri = uris.get(i);
            File file = new File(getPathFromUri(imageUri));

            // Proveri da li fajl već ima ekstenziju
            String fileName = file.getName();
            String mimeType = getMimeType(imageUri);
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

            if (!fileName.endsWith("." + extension)) {
                fileName += "." + extension;
            }

            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
            imageParts[i] = MultipartBody.Part.createFormData("photos", fileName, requestFile);
        }

        return imageParts;
    }

    private String getMimeType(Uri uri) {
        String mimeType;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver contentResolver = requireActivity().getContentResolver();
            mimeType = contentResolver.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        ContentResolver contentResolver = requireActivity().getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

}