package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentReminderBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ReminderFragment extends Fragment {

    CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    FragmentReminderBinding binding;
    DatabaseHelper databaseHelper;
    ArrayList<String> reminderId, reminderTitle,reminderDesc, reminderDateTime,reminderUri,reminderLocation;

    public ReminderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        FloatingActionButton addReminder = (FloatingActionButton) view.findViewById(R.id.addReminderBtn);

        addReminder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddReminder.class);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getActivity());

        reminderId = new ArrayList<>();
        reminderTitle = new ArrayList<>();
        reminderDesc = new ArrayList<>();
        reminderDateTime = new ArrayList<>();
        reminderUri = new ArrayList<>();
        reminderLocation = new ArrayList<>();

        storeDataToArray();
        customAdapter = new CustomAdapter(getActivity(),reminderId,reminderTitle,reminderDesc,reminderDateTime,reminderUri,reminderLocation);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }



    public void storeDataToArray() {
        Cursor cursor = databaseHelper.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"No reminders to Show, add Reminders.",Toast.LENGTH_LONG).show();
        } else {
            while(cursor.moveToNext()){
                reminderId.add(cursor.getString(0));
                reminderTitle.add(cursor.getString(1));
                reminderDesc.add(cursor.getString(2));
                reminderDateTime.add(cursor.getString(3));
                reminderUri.add(cursor.getString(4));
                reminderLocation.add(cursor.getString(5));
            }
        }
    }
}