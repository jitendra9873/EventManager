package com.example.jitendra.eventmanager;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jitendra.eventmanager.data.EventContract;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ADD_LOADER = 0;
    /**
     * Adapter for the ListView
     */
    AddCursorAdapter mCursorAdapter;
    TextView mbutton;
    private Uri mCurrentAddUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventRegistration.class);
                startActivity(intent);
            }
        });

        ListView addListView = (ListView) findViewById(R.id.list);
        mCursorAdapter = new AddCursorAdapter(this, null);
        addListView.setAdapter(mCursorAdapter);

        // Setup the item click listener
        addListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = getIntent();
                Uri currentAddUri = ContentUris.withAppendedId(EventContract.EventEntry.CONTENT_URI, id);
                intent.setData(currentAddUri);
                mCurrentAddUri = intent.getData();
                //showDeleteConfirmationDialog();
                details_window(id);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(ADD_LOADER, null, this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                EventContract.EventEntry._ID,
                EventContract.EventEntry.COLUMN_TITLE,
                EventContract.EventEntry.COLUMN_ORGANIZER_NAME,
                EventContract.EventEntry.COLUMN_ORGANIZER_CELL,
                EventContract.EventEntry.COLUMN_DATE_OF_BIRTH,
                EventContract.EventEntry.COLUMN_EVENT_START_DATE,
                EventContract.EventEntry.COLUMN_EVENT_END_DATE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                EventContract.EventEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    private void details_window(long id){
        Intent intent=new Intent(this,details.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }


    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_address);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteaddress();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteaddress() {

        int rowsDeleted = getContentResolver().delete(mCurrentAddUri, null, null);
        /*Intent intent = getIntent();
        finish();
        startActivity(intent);*/
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
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


