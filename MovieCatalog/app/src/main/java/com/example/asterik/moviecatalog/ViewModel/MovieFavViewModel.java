package com.example.asterik.moviecatalog.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.asterik.moviecatalog.Detail.Movie;
import com.example.asterik.moviecatalog.db.MovieHelper;

import java.util.ArrayList;


public class MovieFavViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();
    MovieHelper movieHelper;

    public MovieFavViewModel(@NonNull Application application) {
        super(application);
        movieHelper = MovieHelper.getInstance(application);
        movieHelper.open();
        listMovie.postValue(movieHelper.getAllFavoriteMovie());
        movieHelper.close();
    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovie;
    }


}
