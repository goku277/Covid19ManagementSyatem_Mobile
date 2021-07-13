package com.example.covid_19managementsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19managementsystem.Model.DisplayPatientDetails;
import com.example.covid_19managementsystem.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder>{

    ArrayList<DisplayPatientDetails> displaypatientDetails;
    Context context;

    public DisplayAdapter(Context context, ArrayList<DisplayPatientDetails> displaypatientDetails) {
        this.context= context;
        this.displaypatientDetails= displaypatientDetails;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpatient_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DisplayAdapter.ViewHolder holder, int position) {
        holder.status.setText(displaypatientDetails.get(position).getStatus());
        holder.age.setText(displaypatientDetails.get(position).getAge());
        holder.name.setText(displaypatientDetails.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return displaypatientDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, status;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.name_id);
            age= (TextView) itemView.findViewById(R.id.age_id);
            status= (TextView) itemView.findViewById(R.id.status_id);
        }
    }
}