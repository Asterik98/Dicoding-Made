package com.example.asterik.moviecatalog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.asterik.moviecatalog.Detail.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.asterik.moviecatalog.db.DatabaseContract.MovieColumns.ATTENDANCE;
import static com.example.asterik.moviecatalog.db.DatabaseContract.MovieColumns.DATE;
import static com.example.asterik.moviecatalog.db.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.asterik.moviecatalog.db.DatabaseContract.MovieColumns.IMAGE;
import static com.example.asterik.moviecatalog.db.DatabaseContract.MovieColumns.SCORE;
import static com.example.asterik.moviecatalog.db.DatabaseContract.MovieColumns.TITLE;
import static com.example.asterik.moviecatalog.db.DatabaseContract.TABLE_NAME;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
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

    public ArrayList<Movie> getAllFavoriteMovie() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setNama(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setTahun(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                movie.setScore(cursor.getString(cursor.getColumnIndexOrThrow(SCORE)));
                movie.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                movie.setAttendance(cursor.getString(cursor.getColumnIndexOrThrow(ATTENDANCE)));

                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavoriteMovie(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, movie.getId());
        contentValues.put(TITLE, movie.getNama());
        contentValues.put(DATE, movie.getTahun());
        contentValues.put(IMAGE, movie.getPhoto());
        contentValues.put(SCORE, movie.getScore());
        contentValues.put(DESKRIPSI, movie.getDeskripsi());
        contentValues.put(ATTENDANCE, movie.getAttendance());

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
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFavorite;
    }

    public int deleteFavoriteMovie(int id) {
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
