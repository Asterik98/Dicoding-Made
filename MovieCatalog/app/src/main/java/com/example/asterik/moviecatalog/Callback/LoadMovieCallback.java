package com.example.asterik.moviecatalog.Callback;

import android.database.Cursor;

import com.example.asterik.moviecatalog.Detail.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(Cursor movies);
}
