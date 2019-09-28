package com.example.asterik.favapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "movie";
    public static final String AUTHORITY = " com.example.asterik.moviecatalog";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DATE = "date";
        public static String IMAGE = "poster";
        public static String SCORE = "score";
        public static String DESKRIPSI = "description";
        public static String ATTENDANCE = "attendance";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
