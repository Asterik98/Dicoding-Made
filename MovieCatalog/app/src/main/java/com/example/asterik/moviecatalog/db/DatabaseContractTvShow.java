package com.example.asterik.moviecatalog.db;

import android.provider.BaseColumns;

public class DatabaseContractTvShow {
    static String TABLE_NAME = "tvShow";

    static final class TvShowColumns implements BaseColumns {
        static String TITLE = "title";
        static String DATE = "date";
        public static String IMAGE = "poster";
        public static String SCORE = "score";
        public static String DESKRIPSI = "description";
        public static String ATTENDANCE = "attendance";
    }
}
