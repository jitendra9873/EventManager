package com.example.jitendra.eventmanager.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jitendra on 3/6/17.
 */

public final class EventContract {
    private EventContract(){}
    public static final String CONTENT_AUTHORITY="com.example.jitendra.eventmanager";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_PETS = "eventmanager";

    public static final class EventEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public final static String TABLE_NAME = "eventmanager";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_TITLE ="title";


        public final static String COLUMN_ORGANIZER_NAME = "organizer";
        public final static String COLUMN_DATE_OF_BIRTH = "organizerdob";
        public final static String COLUMN_ORGANIZER_CELL = "organizercell";
        //public final static String COLUMN_DATE_OF_APPLICATION = "applicationdate";


        public final static String COLUMN_EVENT_TYPE = "eventtype";


        public final static String COLUMN_EVENT_START_DATE= "startdate";
        public final static String COLUMN_EVENT_END_DATE= "enddate";


        public static final int HORSE_RIDING = 0;
        public static final int MOVIE_MAKING = 1;
        public static final int SWIMMING = 2;


        public static boolean isValidType(int type) {
            if (type == HORSE_RIDING || type == MOVIE_MAKING || type == SWIMMING) {
                return true;
            }
            return false;
        }
    }

}
