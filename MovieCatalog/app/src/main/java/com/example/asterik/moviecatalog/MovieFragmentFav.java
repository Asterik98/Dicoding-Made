package com.example.asterik.moviecatalog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asterik.moviecatalog.Callback.LoadMovieCallback;
import com.example.asterik.moviecatalog.adapter.ListViewAdapter;
import com.example.asterik.moviecatalog.Detail.DetailMovieActivity;
import com.example.asterik.moviecatalog.Detail.Movie;
import com.example.asterik.moviecatalog.ItemClickSupport.ItemClickSupport;
import com.example.asterik.moviecatalog.ViewModel.MovieFavViewModel;
import com.example.asterik.moviecatalog.db.MovieHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.asterik.moviecatalog.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.example.asterik.moviecatalog.db.MappingHelper.mapCursorToArrayList;

public class MovieFragmentFav extends Fragment implements LoadMovieCallback{
    private MovieHelper movieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private RecyclerView rvCategory;
    private ProgressBar progressBar;
    private MovieFavViewModel mainViewModel;
    private ArrayList<Movie> list;
    private static HandlerThread handlerThread;
    private DataObserver myObserver;
    private ArrayList<Movie> getListMovie() {
        return list;
    }

    private ListViewAdapter listMovieAdapter;

    public MovieFragmentFav() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMovieAdapter = new ListViewAdapter(getActivity());
        mainViewModel = ViewModelProviders.of(this).get(MovieFavViewModel.class);
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getContext());
        getActivity().getApplicationContext().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadAsynctask(getContext(), this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                listMovieAdapter.setListMovies(list);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        mainViewModel.getMovie().observe(this, getMovie);
        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);
        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(listMovieAdapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedMovie(list.get(position));
                Movie p = getListMovie().get(position);
                Intent moveWithObjectIntent = new Intent(getContext(), DetailMovieActivity.class);
                moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, p);
                getContext().startActivity(moveWithObjectIntent);

            }
        });
        return view;
    }

    private void showSelectedMovie(Movie movie) {
        Toast.makeText(getContext(), getContext().getString(R.string.choose) + " " + movie.getNama(), Toast.LENGTH_SHORT).show();
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                list = movies;
                listMovieAdapter.setListMovies(list);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    public void postExecute(Cursor movies) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<Movie> listMovies = mapCursorToArrayList(movies);
        if (listMovies.size() > 0) {
            listMovieAdapter.setListMovies(listMovies);
        } else {
            listMovieAdapter.setListMovies(new ArrayList<Movie>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }
    private static class LoadAsynctask extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadAsynctask(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }
    private void showSnackbarMessage(String message) {
        Snackbar.make(rvCategory, message, Snackbar.LENGTH_SHORT).show();
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadAsynctask(context, (LoadMovieCallback) context).execute();
        }
    }


}
