package com.example.dododo.finalproject.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DoDo DO on 15/09/2015.
 */
public class Contract {


    public static final String CONTENT_AUTHORITY = "com.example.dododo.finalproject";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_mostpop = "mostpop";
    public static final String PATH_highrate = "highrate";


    public static final class mostpopEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_mostpop).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_mostpop;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_mostpop;


        public static final String TABLE_NAME = "mostpop";
        public static final String ColumnID = "_id";
        public static final String ColumnMovieID = "movie_id";
        public static final String COLUMN_titel = "titel";
        public static final String COLUMN_overview = "overview";
        public static final String COLUMN_rated = "rated";
        public static final String COLUMN_releasdate = "releasdate";
        public static final String COLUMN_image = "image";

        public static Uri buildmostpopUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class highrateEntry implements BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_highrate).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_highrate;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_highrate;

        public static final String TABLE_NAME = "highrate";
        public static final String ColumnID = "_id";
        public static final String ColumnMovieID = "movie_id";
        public static final String COLUMN_titel = "titel";
        public static final String COLUMN_overview = "overview";
        public static final String COLUMN_rated = "rated";
        public static final String COLUMN_releasdate = "releasdate";
        public static final String COLUMN_image = "image";

        public static Uri buildhighrateUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}
