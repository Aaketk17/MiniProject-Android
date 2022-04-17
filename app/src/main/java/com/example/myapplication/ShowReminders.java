package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityShowRemindersBinding;


public class ShowReminders extends AppCompatActivity {

    TextView showTitle, showDesc, showDateTime, showLocation;
    Button deleteData;
    ImageView showImageView;

    String title, desc, dateTime, uri, location, reminderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminders);

        title = getIntent().getStringExtra("reminderTitle");
        desc = getIntent().getStringExtra("reminderDesc");
        dateTime = getIntent().getStringExtra("reminderDateTime");
        uri = getIntent().getStringExtra("reminderUri");
        location = getIntent().getStringExtra("reminderLocation");
        reminderId = getIntent().getStringExtra("reminderId");


        showTitle = (TextView) findViewById(R.id.showTitle);
        showDesc = (TextView) findViewById(R.id.showDesc);
        showDateTime = (TextView) findViewById(R.id.showDateTimeLabel);
        showLocation = (TextView) findViewById(R.id.showLocation);
        showImageView = (ImageView) findViewById(R.id.showImageView);
        deleteData = (Button) findViewById(R.id.deleteData);

        showTitle.setText(title);
        showDesc.setText(desc);
        showDateTime.setText(dateTime);
        showLocation.setText(location);

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(ShowReminders.this);
                long result = databaseHelper.deleteData(reminderId);
                if(result == -1) {
                    Toast.makeText(ShowReminders.this,"Failed to Delete Reminder",Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(ShowReminders.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(ShowReminders.this,"Reminder Deleted Successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}