package com.example.asterik.moviecatalog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TABLE_NAME;

public class DatabaseHelperTvShow extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbtvshowapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +" %s TEXT NOT NULL,"+" %s TEXT NOT NULL,"+" %s TEXT NOT NULL,"+" %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            DatabaseContractTvShow.TvShowColumns._ID,
            DatabaseContractTvShow.TvShowColumns.TITLE,
            DatabaseContractTvShow.TvShowColumns.DATE,
            DatabaseContractTvShow.TvShowColumns.IMAGE,
            DatabaseContractTvShow.TvShowColumns.SCORE,
            DatabaseContractTvShow.TvShowColumns.DESKRIPSI,
            DatabaseContractTvShow.TvShowColumns.ATTENDANCE
    );

    DatabaseHelperTvShow(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
