package com.example.jitendra.eventmanager;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jitendra.eventmanager.data.EventContract;

/**
 * Created by jitendra on 4/6/17.
 */

public class details extends AppCompatActivity{

    public static final String TAG = details.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        int id=(int)getIntent().getLongExtra("id",0);
        int id1;
        Log.d(TAG, "onCreate: "+id);
        //id=1;
        //Uri currentAddUri = ContentUris.withAppendedId(EventContract.EventEntry.CONTENT_URI, id);
        Cursor cursor=getContentResolver().query(EventContract.EventEntry.CONTENT_URI,null,null,null,null);
        cursor.moveToPosition(id-1);
        //if(cursor!=null){
        int flatColumnIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_TITLE);
        int streetColumnIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_ORGANIZER_NAME);
        int landmarkColumnIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_EVENT_TYPE);
        int start=cursor.getColumnIndex((EventContract.EventEntry.COLUMN_EVENT_START_DATE));
        int end=cursor.getColumnIndex((EventContract.EventEntry.COLUMN_EVENT_END_DATE));

            Log.d(TAG, "onCreate: flat"+flatColumnIndex);
            Log.d(TAG, "onCreate: street"+streetColumnIndex);
            Log.d(TAG, "onCreate: land"+landmarkColumnIndex);
            Log.d(TAG, "onCreate: start"+start);
        String flat = cursor.getString(flatColumnIndex);
        String street = cursor.getString(streetColumnIndex);
        int landmark = cursor.getInt(landmarkColumnIndex);
        TextView x=(TextView)findViewById(R.id.test);
        x.setText(flat+"  "+street+"  "+landmark);
        String type;
                switch (landmark) {
            case 0:
                type="Horse Riding";
                break;
            case 1:
                type="Movie Making";
                break;
            case 2:
                type="Swimming";
                break;
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


