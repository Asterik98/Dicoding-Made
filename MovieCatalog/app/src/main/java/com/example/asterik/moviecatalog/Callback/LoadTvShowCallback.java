package com.example.asterik.moviecatalog.Callback;

import com.example.asterik.moviecatalog.Detail.TvShow;

import java.util.ArrayList;

public interface LoadTvShowCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> tvShow);
}
