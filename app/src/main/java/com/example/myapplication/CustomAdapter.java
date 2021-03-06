package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> reminderId,reminderTitle,reminderDesc,reminderDateTime,reminderUri,reminderLocation;

    CustomAdapter(Context context, ArrayList reminderId, ArrayList reminderTitle,ArrayList reminderDesc, ArrayList reminderDateTime,
                  ArrayList reminderUri, ArrayList reminderLocation) {
        this.context = context;
        this.reminderId = reminderId;
        this.reminderTitle = reminderTitle;
        this.reminderDesc = reminderDesc;
        this.reminderDateTime = reminderDateTime;
        this.reminderUri = reminderUri;
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
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.reminderId.setText(String.valueOf(reminderId.get(position)));
        holder.reminderTitle.setText(String.valueOf(reminderTitle.get(position)));
        holder.reminderDateTime.setText(String.valueOf(reminderDateTime.get(position)));
        holder.reminderLocation.setText(String.valueOf(reminderLocation.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShowReminders.class);
                intent.putExtra("reminderId",reminderId.get(position));
                intent.putExtra("reminderTitle",reminderTitle.get(position));
                intent.putExtra("reminderDesc",reminderDesc.get(position));
                intent.putExtra("reminderDateTime",reminderDateTime.get(position));
                intent.putExtra("reminderUri",reminderUri.get(position));
                intent.putExtra("reminderLocation",reminderLocation.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reminderId, reminderTitle, reminderLocation, reminderDateTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            reminderId = itemView.findViewById(R.id.reminderId);
            reminderTitle = itemView.findViewById(R.id.reminderTitle);
            reminderLocation = itemView.findViewById(R.id.reminderLocation);
            reminderDateTime = itemView.findViewById(R.id.reminderDateTime);
        }
    }
}
