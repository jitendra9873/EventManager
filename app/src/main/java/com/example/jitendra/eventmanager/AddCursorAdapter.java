/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.jitendra.eventmanager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jitendra.eventmanager.data.EventContract;

public class AddCursorAdapter extends CursorAdapter {
    public static final String LOG_TAG = AddCursorAdapter.class.getSimpleName();

    public AddCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView mflat = (TextView) view.findViewById(R.id.title1);
        TextView mstreet = (TextView) view.findViewById(R.id.organizer1);
        TextView mcity_pin = (TextView) view.findViewById(R.id.startdate);
        TextView mcity_pin2 = (TextView) view.findViewById(R.id.enddate);

        int flatColumnIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_TITLE);
        int streetColumnIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_ORGANIZER_NAME);
        int landmarkColumnIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_EVENT_TYPE);
        int start=cursor.getColumnIndex((EventContract.EventEntry.COLUMN_EVENT_START_DATE));
        int end=cursor.getColumnIndex((EventContract.EventEntry.COLUMN_EVENT_END_DATE));
        int hi1 = cursor.getColumnIndex(EventContract.EventEntry._ID);

        String hi=cursor.getString(hi1);
        String flat = cursor.getString(flatColumnIndex);
        String street = cursor.getString(streetColumnIndex);
//        int landmark = cursor.getInt(landmarkColumnIndex);
        String new1=cursor.getString(start);
        String new2=cursor.getString(end);
        StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
        SpannableStringBuilder sb1 = new SpannableStringBuilder("Title: " + flat+" "+hi);
        SpannableStringBuilder sb2 = new SpannableStringBuilder("Organizer:  " + street);
        SpannableStringBuilder sb3 = new SpannableStringBuilder("Start: " + new1);
        SpannableStringBuilder sb4 = new SpannableStringBuilder("End: " + new2);
        sb1.setSpan(b, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb2.setSpan(b, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb3.setSpan(b, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb4.setSpan(b, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // Update the TextViews
        mflat.setText(sb1);
        mstreet.setText(sb2);
        mcity_pin.setText(sb3);
        mcity_pin2.setText(sb4);
/*        switch (landmark) {
            case 0:
                mcity_pin.setText("Horse Riding");
                break;
            case 1:
                mcity_pin.setText("Movie Making");
                break;
            case 2:
                mcity_pin.setText("Swimming");
                break;
        }*/


    }
}
