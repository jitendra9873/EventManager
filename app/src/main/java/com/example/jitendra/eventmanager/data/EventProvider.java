package com.example.jitendra.eventmanager.data;

/**
 * Created by jitendra on 3/6/17.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.jitendra.eventmanager.data.EventContract.EventEntry;

public class EventProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = EventProvider.class.getSimpleName();
    private static final int EVENTS = 100;
    private static final int EVENTS_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY, EventContract.PATH_PETS, EVENTS);
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY, EventContract.PATH_PETS + "/#", EVENTS_ID);
    }

    /** Database helper object */
    private EventDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new EventDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                cursor = database.query(EventEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case EVENTS_ID:
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(EventEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {
        // Check that the name is not null
        String title = values.getAsString(EventEntry.COLUMN_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("");
        }

        String oname = values.getAsString(EventEntry.COLUMN_ORGANIZER_NAME);
        if (oname == null) {
            throw new IllegalArgumentException(" ");
        }

        String ocell = values.getAsString(EventEntry.COLUMN_ORGANIZER_CELL);
        if (ocell == null) {
            throw new IllegalArgumentException(" ");
        }

        String dob = values.getAsString(EventEntry.COLUMN_DATE_OF_BIRTH);
        if (dob == null) {
            throw new IllegalArgumentException(" ");
        }

        String sdate = values.getAsString(EventEntry.COLUMN_EVENT_START_DATE);
        if (sdate == null) {
            throw new IllegalArgumentException(" ");
        }

        String edate = values.getAsString(EventEntry.COLUMN_EVENT_END_DATE);
        if (edate == null) {
            throw new IllegalArgumentException(" ");
        }

        // Check that the gender is valid
        Integer type = values.getAsInteger(EventEntry.COLUMN_EVENT_TYPE);
        if (type == null || !EventEntry.isValidType(type)) {
            throw new IllegalArgumentException(" ");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(EventEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case EVENTS_ID:
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(EventEntry.COLUMN_TITLE)) {
            String name = values.getAsString(EventEntry.COLUMN_TITLE);
            if (name == null) {
                throw new IllegalArgumentException(" ");
            }
        }
        if (values.containsKey(EventEntry.COLUMN_ORGANIZER_NAME)) {
            String name = values.getAsString(EventEntry.COLUMN_ORGANIZER_NAME);
            if (name == null) {
                throw new IllegalArgumentException(" ");
            }
        }
        if (values.containsKey(EventEntry.COLUMN_ORGANIZER_CELL)) {
            String name = values.getAsString(EventEntry.COLUMN_ORGANIZER_CELL);
            if (name == null) {
                throw new IllegalArgumentException(" ");
            }
        }
        if (values.containsKey(EventEntry.COLUMN_DATE_OF_BIRTH)) {
            String name = values.getAsString(EventEntry.COLUMN_DATE_OF_BIRTH);
            if (name == null) {
                throw new IllegalArgumentException(" ");
            }
        }
        if (values.containsKey(EventEntry.COLUMN_EVENT_START_DATE)) {
            String name = values.getAsString(EventEntry.COLUMN_EVENT_START_DATE);
            if (name == null) {
                throw new IllegalArgumentException(" ");
            }
        }
        if (values.containsKey(EventEntry.COLUMN_EVENT_END_DATE)) {
            String name = values.getAsString(EventEntry.COLUMN_EVENT_END_DATE);
            if (name == null) {
                throw new IllegalArgumentException(" ");
            }
        }
        if (values.containsKey(EventEntry.COLUMN_EVENT_TYPE)) {
            Integer gender = values.getAsInteger(EventEntry.COLUMN_EVENT_TYPE);
            if (gender == null || !EventEntry.isValidType(gender)) {
                throw new IllegalArgumentException(" ");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(EventEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EVENTS_ID:
                // Delete a single row given by the ID in the URI
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                return EventEntry.CONTENT_LIST_TYPE;
            case EVENTS_ID:
                return EventEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
