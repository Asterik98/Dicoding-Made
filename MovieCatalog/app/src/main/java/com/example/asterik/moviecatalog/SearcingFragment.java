package com.example.asterik.moviecatalog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asterik.moviecatalog.Detail.DetailMovieActivity;
import com.example.asterik.moviecatalog.Detail.Movie;
import com.example.asterik.moviecatalog.ItemClickSupport.ItemClickSupport;
import com.example.asterik.moviecatalog.ViewModel.SearchViewModel;
import com.example.asterik.moviecatalog.adapter.ListViewAdapter;

import java.util.ArrayList;

public class SearcingFragment extends Fragment {

    private RecyclerView rvCategory;
    private ProgressBar progressBar;
    public static SearchViewModel ViewModel;
    public static ArrayList<Movie> list;
    private ArrayList<Movie> getListMovie() {
        return list ;
    }
    public static ListViewAdapter listMovieAdapter;
    public SearcingFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMovieAdapter = new ListViewAdapter(getActivity());
        ViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        ViewModel.setMovie();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searcing_fragment, container, false);
        ViewModel.getMovie().observe(this, getMovie);
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
                moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE,p);
                getContext().startActivity(moveWithObjectIntent);
                showLoading(false);
            }
        });
        return view;
    }


    private void  showSelectedMovie(Movie movie) {
        Toast.makeText(getContext(), getContext().getString(R.string.choose)+" " + movie.getNama(), Toast.LENGTH_SHORT).show();
    }


    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                list=movies;
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

}
