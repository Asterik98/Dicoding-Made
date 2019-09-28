package com.example.asterik.favapp;

import android.database.Cursor;

public interface LoadMovieCallback {
    void postExecute(Cursor movies);
}
