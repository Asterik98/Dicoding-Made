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

import com.example.asterik.moviecatalog.Detail.DetailTvShow;
import com.example.asterik.moviecatalog.Detail.TvShow;
import com.example.asterik.moviecatalog.ItemClickSupport.ItemClickSupport;
import com.example.asterik.moviecatalog.ViewModel.TvShowFavViewModel;
import com.example.asterik.moviecatalog.adapter.TvShowAdapter;
import com.example.asterik.moviecatalog.db.TvShowHelper;

import java.util.ArrayList;


public class TVShowFragmentFav extends Fragment {
    private TvShowHelper tvShowHelper;
    private RecyclerView rvTv_category;
    private ProgressBar progressBar;
    private TvShowFavViewModel mainViewModel;
    private TvShowAdapter listTvShowAdapter;
    private ArrayList<TvShow> list = new ArrayList<>();
    ;

    private ArrayList<TvShow> getListTvShow() {
        return list;
    }

    public TVShowFragmentFav() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTvShowAdapter = new TvShowAdapter(getActivity());
        mainViewModel = ViewModelProviders.of(this).get(TvShowFavViewModel.class);
        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        mainViewModel.getTvShow().observe(this, getTvShow);
        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);
        rvTv_category = view.findViewById(R.id.rvTv_category);
        rvTv_category.setHasFixedSize(true);
        rvTv_category.setAdapter(listTvShowAdapter);
        rvTv_category.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ItemClickSupport.addTo(rvTv_category).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedTVShow(list.get(position));
                TvShow p = getListTvShow().get(position);
                Intent moveWithObjectTvIntent = new Intent(getContext(), DetailTvShow.class);
                moveWithObjectTvIntent.putExtra(DetailTvShow.EXTRA_TVSHOW, p);
                getContext().startActivity(moveWithObjectTvIntent);
            }
        });
        return view;
    }


    private void showSelectedTVShow(TvShow tvshow) {
        Toast.makeText(getContext(), getContext().getString(R.string.choose) + " " + tvshow.getNama(), Toast.LENGTH_SHORT).show();
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvshows) {
            if (tvshows != null) {
                list = tvshows;
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
