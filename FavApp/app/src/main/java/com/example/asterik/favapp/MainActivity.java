package com.example.asterik.favapp;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.asterik.favapp.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.example.asterik.favapp.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadMovieCallback {

    private ListViewAdapter consumerAdapter;
    private DataObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Consumer App");

        RecyclerView rvNotes = findViewById(R.id.rv_category);
        consumerAdapter = new ListViewAdapter(this);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);
        rvNotes.setAdapter(consumerAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        new getData(this, this).execute();
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;
        private getData(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }
    @Override
    public void postExecute(Cursor notes) {
        ArrayList<Movie> listMovies = mapCursorToArrayList(notes);
        if (listMovies.size() > 0) {
            consumerAdapter.setListMovies(listMovies);
        } else {
            Toast.makeText(this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            consumerAdapter.setListMovies(new ArrayList<Movie>());
        }
    }
    static class DataObserver extends ContentObserver {
        final Context context;
        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}
