package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityAddAlarmBinding;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAlarm extends AppCompatActivity {

    private ActivityAddAlarmBinding binding;
    private Calendar calender;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Alarm");
        binding = ActivityAddAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        calendar = Calendar.getInstance();
//        iDate = sdf.format(calendar.getTime());
//        binding.dateText.setText(iDate);
        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        binding.timeText.setText(currentTime);
//        binding.selectDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePicker();
//            }
//        });
        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        binding.setAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });

        binding.cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if(alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this,"Alarm Cancelled",Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this,"Alarm set Successfully",Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                 hour = selectedHour;
                 minute = selectedMinute;

                 calender = Calendar.getInstance();
                 calender.set(Calendar.HOUR_OF_DAY,selectedHour);
                 calender.set(Calendar.MINUTE,selectedMinute);
                 calender.set(Calendar.SECOND,0);
                 calender.set(Calendar.MILLISECOND,0);

                 if(hour > 12 || hour == 12){
                     binding.timeText.setText(String.format(Locale.getDefault(),"%02d:%02d  %s",hour,minute,"PM"));
                 } else if (hour < 12){
                     binding.timeText.setText(String.format(Locale.getDefault(),"%02d:%02d  %s",hour,minute,"AM"));
                 }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

//    private void showDatePicker() {
//        calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                String date = dayOfMonth + "/"+ month + "/" + year;
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                try {
//                    Date strDate = sdf.parse(date);
//                    Long l = strDate.getTime();
//                    if(new Date(l).after(new Date()) || new Date(l).equals(new Date())){
//                        Log.d("Valid","date");
//                        binding.dateText.setText(date);
//                    } else {
//                        Log.d("Invalid","date");
//                        Context context = getApplicationContext();
//                        CharSequence text = "Invalid Date selection.";
//                        int duration = Toast.LENGTH_SHORT;
//                        Toast toast = Toast.makeText(context,text,duration);
//                        toast.show();
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        datePicker = new DatePickerDialog(AddAlarm.this,
//                R.style.MyTimePickerDialogTheme,
//                onDateSetListener,
//                year,
//                month,
//                day);
//
//        datePicker.show();
//
//    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "remindMeChannelAlarm";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("remindMeChannel",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}