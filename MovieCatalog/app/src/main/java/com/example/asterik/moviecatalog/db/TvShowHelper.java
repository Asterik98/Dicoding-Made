package com.example.asterik.moviecatalog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.asterik.moviecatalog.Detail.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TvShowColumns.ATTENDANCE;
import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TvShowColumns.DATE;
import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TvShowColumns.DESKRIPSI;
import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TvShowColumns.IMAGE;
import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TvShowColumns.SCORE;
import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TvShowColumns.TITLE;
import static com.example.asterik.moviecatalog.db.DatabaseContractTvShow.TABLE_NAME;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelperTvShow databaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        databaseHelper = new DatabaseHelperTvShow(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TvShow> getAllFavoriteTvShow() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setNama(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setTahun(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                tvShow.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                tvShow.setScore(cursor.getString(cursor.getColumnIndexOrThrow(SCORE)));
                tvShow.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                tvShow.setCrew(cursor.getString(cursor.getColumnIndexOrThrow(ATTENDANCE)));

                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavoriteTvShow(TvShow tvShow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, tvShow.getId());
        contentValues.put(TITLE, tvShow.getNama());
        contentValues.put(DATE, tvShow.getTahun());
        contentValues.put(IMAGE, tvShow.getPhoto());
        contentValues.put(SCORE, tvShow.getScore());
        contentValues.put(DESKRIPSI, tvShow.getDeskripsi());
        contentValues.put(ATTENDANCE, tvShow.getCrew());

        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public boolean favorit(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        boolean isFavorite = false;

        try {
            Cursor cursor;
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + "=" + id;
            cursor = db.rawQuery(sql, null);
            isFavorite = cursor.getCount() > 0;
            if (isFavorite != false) {
                Log.d("Ada", sql);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFavorite;
    }

    public int deleteFavoriteTvShow(int id) {
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
