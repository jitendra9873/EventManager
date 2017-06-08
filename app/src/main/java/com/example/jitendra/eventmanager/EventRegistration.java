package com.example.jitendra.eventmanager;

/**
 * Created by jitendra on 3/6/17.
 */


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendra.eventmanager.data.EventContract.EventEntry;

public class EventRegistration extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private EditText title;
    private EditText organizer;
    private EditText phone;
    private EditText venue;
    private TextView dateofb;
    private Spinner mspinner;

    private Button mbutton;
    private int mtype = EventEntry.HORSE_RIDING;

    private TextView start, end;
    private DatePicker dpResult;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    int cur = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        setCurrentDateOnView();
        addListenerOnButton();


        // Find all relevant views that we will need to read user input fromtit
        title = (EditText) findViewById(R.id.title1);
        organizer = (EditText) findViewById(R.id.orgname);
        phone = (EditText) findViewById(R.id.phno);
        venue = (EditText) findViewById(R.id.venue1);

        dateofb = (TextView) findViewById(R.id.dob1);
        mspinner = (Spinner) findViewById(R.id.spinner_type);
        mbutton = (Button) findViewById(R.id.button4);
        setupSpinner();


        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read from input fields
                // Use trim to eliminate leading or trailing white space
                String title1 = title.getText().toString().trim();
                if ((title1.matches(""))) {
                    Toast.makeText(getApplicationContext(), "Please specify title", Toast.LENGTH_SHORT).show();
                } else {
                    String organizer1 = organizer.getText().toString().trim();
                    if ((organizer1.matches(""))) {
                        Toast.makeText(getApplicationContext(), "Please specify organizer name", Toast.LENGTH_SHORT).show();
                    } else {
                        String phone1 = phone.getText().toString().trim();
                        if ((phone1.matches(""))) {
                            Toast.makeText(getApplicationContext(), "Please provide phone number", Toast.LENGTH_SHORT).show();
                        } else {
                            String venue1 = venue.getText().toString().trim();
                            if ((venue1.matches(""))) {
                                Toast.makeText(getApplicationContext(), "Please provide venue of event", Toast.LENGTH_SHORT).show();
                            } else {
                                String start1 = start.getText().toString().trim();
                                String end1 = end.getText().toString().trim();
                                String dob1 = dateofb.getText().toString().trim();


                                ContentValues values = new ContentValues();
                                values.put(EventEntry.COLUMN_TITLE, title1);
                                values.put(EventEntry.COLUMN_ORGANIZER_NAME, organizer1);
                                values.put(EventEntry.COLUMN_ORGANIZER_CELL, phone1);
                                values.put(EventEntry.COLUMN_DATE_OF_BIRTH, dob1);
                                values.put(EventEntry.COLUMN_EVENT_START_DATE, start1);
                                values.put(EventEntry.COLUMN_EVENT_END_DATE, end1);
                                values.put(EventEntry.COLUMN_EVENT_TYPE, mtype);

                                Uri newUri = getContentResolver().insert(EventEntry.CONTENT_URI, values);

                                if (newUri == null) {
                                    Toast.makeText(getApplicationContext(), "Failed",
                                            Toast.LENGTH_SHORT).show();
                                } /*else {
                    Toast.makeText(getApplicationContext(), "Success",
                            Toast.LENGTH_SHORT).show();
                }*/
                                onBackPressed();
                            }
                        }
                    }
                }
            }

        });
    }

    // display current date
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCurrentDateOnView() {

        start = (TextView) findViewById(R.id.start);
        end = (TextView) findViewById(R.id.end);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        start.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        end.setText(start.getText().toString());
    }

    public void addListenerOnButton() {


        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

        end.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID2);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_DIALOG_ID;
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
            case DATE_DIALOG_ID2:
                cur = DATE_DIALOG_ID2;
                System.out.println("onCreateDialog2  : " + id);
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            if (cur == DATE_DIALOG_ID) {
                // set selected date into textview
                start.setText(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
            } else {
                end.setText(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
            }

        }
    };

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mspinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.movie))) {
                        mtype = EventEntry.MOVIE_MAKING;
                    } else if (selection.equals(getString(R.string.swim))) {
                        mtype = EventEntry.SWIMMING;
                    } else {
                        mtype = EventEntry.HORSE_RIDING;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mtype = EventEntry.HORSE_RIDING;
            }
        });
    }

    private boolean isValidPin(String pin) {
        if (pin.length() != 6)
            return false;
        return true;
    }


    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
/*        Intent intent = new Intent(EditorActivity.this, CatalogActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                EventEntry._ID,
                EventEntry.COLUMN_TITLE,
                EventEntry.COLUMN_ORGANIZER_NAME,
                EventEntry.COLUMN_ORGANIZER_CELL,
                EventEntry.COLUMN_DATE_OF_BIRTH,
                EventEntry.COLUMN_EVENT_START_DATE,
                EventEntry.COLUMN_EVENT_END_DATE,
                EventEntry.COLUMN_EVENT_TYPE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                EventEntry.CONTENT_URI,
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int add1ColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_TITLE);
            int add2ColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_ORGANIZER_NAME);
            int add3ColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_ORGANIZER_CELL);
            int cityColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_DATE_OF_BIRTH);
            int pinColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_START_DATE);
            int pinColumnIndex1 = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_END_DATE);
            int pinColumnIndex2 = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_TYPE);


            int type = cursor.getInt(pinColumnIndex2);
            // Update the views on the screen with the values from the database
            title.setText(cursor.getString(add1ColumnIndex));
            organizer.setText(cursor.getString(add2ColumnIndex));
            phone.setText(cursor.getString(add3ColumnIndex));
            switch (type) {
                case EventEntry.MOVIE_MAKING:
                    mspinner.setSelection(1);
                    break;
                case EventEntry.SWIMMING:
                    mspinner.setSelection(2);
                    break;
                default:
                    mspinner.setSelection(0);
                    break;
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        title.setText("");
        phone.setText("");
        organizer.setText("");
    }


}
