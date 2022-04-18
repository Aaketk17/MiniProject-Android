package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "remindMeReminder.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "reminderDetails";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "reminderTitle";
    private static final String COLUMN_DATE_TIME = "reminderDateTime";
    private static final String COLUMN_DESC = "reminderDesc";
    private static final String COLUMN_LOCATION = "reminderLocation";
    private static final String COLUMN_URI = "reminderUri";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_DATE_TIME + " TEXT, " +
                COLUMN_URI + " TEXT, " +
                COLUMN_LOCATION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(String title, String location, String desc, String dateTime, String uri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESC, desc);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_DATE_TIME, dateTime);
        cv.put(COLUMN_URI, uri);

        long result = db.insert(TABLE_NAME,null,cv);

        if(result == -1){
            Toast.makeText(context,"Failed to Update Reminder", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Reminder Successfully Added", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null) {
            cursor = db.rawQuery(query,null);
        }

        return cursor;
    }

    public long deleteData(String reminderId){
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[] {reminderId});

        return result;
    }
}
