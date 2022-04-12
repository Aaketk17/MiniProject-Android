package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> reminderId,reminderTitle,reminderDateTime,reminderLocation;

    CustomAdapter(Context context, ArrayList reminderId,ArrayList reminderTitle,ArrayList reminderDateTime,
                  ArrayList reminderLocation) {
        this.context = context;
        this.reminderId = reminderId;
        this.reminderTitle = reminderTitle;
        this.reminderDateTime = reminderDateTime;
        this.reminderLocation = reminderLocation;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.reminderId.setText(String.valueOf(reminderId.get(position)));
        holder.reminderTitle.setText(String.valueOf(reminderTitle.get(position)));
        holder.reminderDateTime.setText(String.valueOf(reminderDateTime.get(position)));
        holder.reminderLocation.setText(String.valueOf(reminderLocation.get(position)));
    }

    @Override
    public int getItemCount() {
        return reminderId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reminderId, reminderTitle, reminderLocation, reminderDateTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reminderId = itemView.findViewById(R.id.reminderId);
            reminderTitle = itemView.findViewById(R.id.reminderTitle);
            reminderLocation = itemView.findViewById(R.id.reminderLocation);
            reminderDateTime = itemView.findViewById(R.id.reminderDateTime);
        }
    }
}
