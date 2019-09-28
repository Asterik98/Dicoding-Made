package com.example.asterik.favapp;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.asterik.favapp.DatabaseContract.MovieColumns.ATTENDANCE;
import static com.example.asterik.favapp.DatabaseContract.MovieColumns.DATE;
import static com.example.asterik.favapp.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.asterik.favapp.DatabaseContract.MovieColumns.IMAGE;
import static com.example.asterik.favapp.DatabaseContract.MovieColumns.SCORE;
import static com.example.asterik.favapp.DatabaseContract.MovieColumns.TITLE;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<Movie> movieList = new ArrayList<>();
        while (movieCursor.moveToNext()) {
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE));
            String description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DESKRIPSI));
            String tahun = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DATE));
            String posterPath=movieCursor.getString(movieCursor.getColumnIndexOrThrow(IMAGE));
            String score=movieCursor.getString(movieCursor.getColumnIndexOrThrow(SCORE));
            String attendance=movieCursor.getString(movieCursor.getColumnIndexOrThrow(ATTENDANCE));
            movieList.add(new Movie(title, description, tahun, posterPath,score,attendance));
        }
        return movieList;
    }
}
