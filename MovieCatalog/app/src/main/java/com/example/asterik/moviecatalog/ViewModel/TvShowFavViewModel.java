package com.example.asterik.moviecatalog.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.asterik.moviecatalog.Detail.TvShow;
import com.example.asterik.moviecatalog.db.TvShowHelper;

import java.util.ArrayList;

public class TvShowFavViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();
    TvShowHelper tvShowHelper;

    public TvShowFavViewModel(@NonNull Application application) {
        super(application);
        tvShowHelper = TvShowHelper.getInstance(application);
        tvShowHelper.open();
        listTvShow.postValue(tvShowHelper.getAllFavoriteTvShow());
        tvShowHelper.close();
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShow;
    }
}
