package com.example.asterik.moviecatalog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.asterik.moviecatalog.Detail.TvShow;
import com.example.asterik.moviecatalog.ItemClickSupport.ItemClickSupport;
import com.example.asterik.moviecatalog.ViewModel.SearchTvShowViewModel;
import com.example.asterik.moviecatalog.ViewModel.SearchViewModel;
import com.example.asterik.moviecatalog.adapter.ListViewAdapter;
import com.example.asterik.moviecatalog.adapter.TvShowAdapter;

import java.util.ArrayList;

public class SearcingTvShowFragment extends Fragment {

    private RecyclerView rvCategory;
    private ProgressBar progressBar;
    public static SearchTvShowViewModel ViewModel;
    public static ArrayList<TvShow> list;
    private ArrayList<TvShow> getListTvShow() {
        return list ;
    }
    public static TvShowAdapter listTvShowAdapter;
    public SearcingTvShowFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTvShowAdapter = new TvShowAdapter(getActivity());
        ViewModel = ViewModelProviders.of(this).get(SearchTvShowViewModel.class);
        ViewModel.setTvShow();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searcingtvshow_fragment, container, false);
        ViewModel.getTvShow().observe(this, getTvShow);
        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);
        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(listTvShowAdapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedTvShow(list.get(position));
                TvShow p = getListTvShow().get(position);
                Intent moveWithObjectIntent = new Intent(getContext(), DetailMovieActivity.class);
                moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE,p);
                getContext().startActivity(moveWithObjectIntent);
                showLoading(false);
            }
        });
        return view;
    }


    private void  showSelectedTvShow(TvShow tvshow) {
        Toast.makeText(getContext(), getContext().getString(R.string.choose)+" " + tvshow.getNama(), Toast.LENGTH_SHORT).show();
    }


    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                list=tvShows;
                listTvShowAdapter.setListTvShow(list);
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
