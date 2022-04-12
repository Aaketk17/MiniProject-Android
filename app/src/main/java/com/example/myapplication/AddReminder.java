package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityAddReminderBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminder extends AppCompatActivity {

    private ActivityAddReminderBinding binding;
    int hour,minute,year,month,day;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  HH:mm a");
        Calendar calendar = Calendar.getInstance();
        String cDate = sdf.format(calendar.getTime());
        binding.dateTimeLabel.setText(cDate);

        binding.dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog();
            }
        });

        binding.uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        binding.saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(binding.title.getText().toString().isEmpty() || binding.desc.getText().toString().isEmpty() || binding.location.getText().toString().isEmpty()
                        || binding.imageView.getDrawable() == null){
                        Toast.makeText(AddReminder.this,"Fill all the Fields before Saving, Selected Date time should be future date and time",Toast.LENGTH_LONG).show();
                 } else {
                        Log.d("value","not come");
                        DatabaseHelper remindMeDB = new DatabaseHelper(AddReminder.this);
                        remindMeDB.addData(binding.title.getText().toString().trim(),binding.location.getText().toString().trim(),binding.desc.getText().toString().trim(),binding.dateTimeLabel.getText().toString().trim(),imageUri.toString().trim());
                        binding.title.setText("");
                        binding.title.setHint("Add Title");
                        binding.desc.setText("");
                        binding.desc.setHint("Add Description");
                        binding.location.setText("");
                        binding.location.setHint("Add Location");
                        binding.imageView.setImageURI(null);
                 }
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            binding.imageView.setImageURI(imageUri);
        }
    }

    private void showDateTimeDialog() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDayOfMonth;

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinute) {
                        hour = selectedHourOfDay;
                        minute = selectedMinute;

                        if(hour > 12 || hour == 12){
                            binding.dateTimeLabel.setText(String.format(Locale.getDefault(),"%02d-%02d-%02d  %02d:%02d  %s",day,month+1,year,hour,minute,"PM"));
                        } else if (hour < 12){
                            binding.dateTimeLabel.setText(String.format(Locale.getDefault(),"%02d-%02d-%02d  %02d:%02d  %s",day,month+1,year,hour,minute,"AM"));
                        }
                    }

                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddReminder.this,
                        R.style.MyDateTimePickerDialogTheme,
                        timeSetListener,
                        hour,
                        minute,
                        true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();

            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.MyDateTimePickerDialogTheme,dateSetListener,year,month,day);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
}