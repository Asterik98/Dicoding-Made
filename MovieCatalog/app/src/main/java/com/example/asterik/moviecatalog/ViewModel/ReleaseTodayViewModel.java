package com.example.asterik.moviecatalog.ViewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.asterik.moviecatalog.BuildConfig;
import com.example.asterik.moviecatalog.Detail.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ReleaseTodayViewModel extends ViewModel {
    private ArrayList<Movie> listMovie= new ArrayList<Movie>();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String url="https://api.themoviedb.org/3/discover/movie?api_key="+BuildConfig.API_KEY +"&primary_release_date.gte=&primary_release_date.lte="+dateFormat;

    public void setMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> movieItems = new ArrayList<>();

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
                        JSONObject movieObject = list.getJSONObject(i);
                        Movie movie = new Movie(movieObject);
                        movieItems.add(movie);
                    }
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

    public ArrayList<Movie>getMovie() {
        return listMovie;
    }
}
