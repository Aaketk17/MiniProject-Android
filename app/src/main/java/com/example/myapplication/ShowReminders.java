package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityShowRemindersBinding;


public class ShowReminders extends AppCompatActivity {

    ActivityShowRemindersBinding binding;
    TextView showTitle, showDesc, showDateTime, showLocation;
    ImageView showImageView;

    String title, desc, dateTime, uri, location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminders);

        title = getIntent().getStringExtra("reminderTitle");
        desc = getIntent().getStringExtra("reminderDesc");
        dateTime = getIntent().getStringExtra("reminderDateTime");
        uri = getIntent().getStringExtra("reminderUri");
        location = getIntent().getStringExtra("reminderLocation");

        System.out.println(Uri.parse(uri));

        showTitle = (TextView) findViewById(R.id.showTitle);
        showDesc = (TextView) findViewById(R.id.showDesc);
        showDateTime = (TextView) findViewById(R.id.showDateTimeLabel);
        showLocation = (TextView) findViewById(R.id.showLocation);
        showImageView = (ImageView) findViewById(R.id.showImageView);

        showTitle.setText(title);
        showDesc.setText(desc);
        showDateTime.setText(dateTime);
        showLocation.setText(location);
//        showImageView.setImageURI(Uri.parse(uri));

    }

}