package com.example.bookingapp.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.models.AccommodationGrade;
import com.example.bookingapp.models.OwnerGrade;

import java.text.DecimalFormat;
import java.util.List;

public class AccommodationGradeAdapter extends RecyclerView.Adapter<AccommodationGradeAdapter.ViewHolder> {

    private List<AccommodationGrade> ownerGrades;
    private Context context;

    public AccommodationGradeAdapter(Context context, List<AccommodationGrade> ownerGrades) {
        this.context = context;
        this.ownerGrades = ownerGrades;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_owner_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccommodationGrade ownerGrade = ownerGrades.get(position);

        // Set the grade value
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedGrade = decimalFormat.format(ownerGrade.getGrade());
        holder.gradeValue.setText(formattedGrade);

        // Set the comment if available
        if (ownerGrade.getComment() != null) {
            holder.commentValue.setText(ownerGrade.getComment());
        } else {
            holder.commentValue.setText("No comment");
        }
    }

    @Override
    public int getItemCount() {
        return ownerGrades.size();
    }

    public void setGrades(List<AccommodationGrade> ownerGrades) {
        this.ownerGrades = ownerGrades;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gradeValue;
        TextView commentValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeValue = itemView.findViewById(R.id.tv_grade);
            commentValue = itemView.findViewById(R.id.tv_comment);
        }
    }
}

