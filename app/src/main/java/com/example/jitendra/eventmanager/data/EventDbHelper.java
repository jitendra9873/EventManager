package com.example.jitendra.eventmanager.data;

/**
 * Created by jitendra on 3/6/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jitendra.eventmanager.data.EventContract.EventEntry;

public class EventDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = EventDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "event.db";


    private static final int DATABASE_VERSION = 1;


    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create table
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + EventEntry.TABLE_NAME + " ("
                + EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EventEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + EventEntry.COLUMN_ORGANIZER_NAME + " TEXT, "
                +EventEntry.COLUMN_ORGANIZER_CELL+" TEXT, "
                +EventEntry.COLUMN_DATE_OF_BIRTH+" TEXT, "
                +EventEntry.COLUMN_EVENT_START_DATE+" TEXT, "
                +EventEntry.COLUMN_EVENT_END_DATE+" TEXT, "
                + EventEntry.COLUMN_EVENT_TYPE + " INTEGER NOT NULL);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}