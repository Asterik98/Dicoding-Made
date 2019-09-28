package com.example.asterik.moviecatalog.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.asterik.moviecatalog.BuildConfig;
import com.example.asterik.moviecatalog.Detail.Movie;
import com.example.asterik.moviecatalog.Detail.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchTvShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();
    public static String query;
    private String url="https://api.themoviedb.org/3/search/tv?api_key="+ BuildConfig.API_KEY + "&language=en-US&query="+query;

    public void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> tvshowItems = new ArrayList<>();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShowObject = list.getJSONObject(i);
                        TvShow tvShow = new TvShow(tvShowObject);
                        tvshowItems.add(tvShow);
                    }
                    listTvShow.postValue(tvshowItems);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
    public static void getQuery(String tvShow){
        query=tvShow;
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShow;
    }
}
